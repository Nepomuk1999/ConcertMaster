var modalBodySelector = "#modal-form > .modal-body";

function showModal(id, uri, dataForModal, isEditable, isFurtherEditable, onCompleteCallback) {
    var modalQualifier = id + "-modal";
    var contentQualifier = id + "-content";
    var modalWrapper = '<!-- Modal -->' +
        '<div id="' + modalQualifier + '" class="modal fade" role="dialog">' +
        '<div class="modal-dialog">' +
        '<div id="' + contentQualifier + '" class="modal-content">' +
        '</div>' +
        '</div>' +
        '</div>';

    // add the element when it isn't available
    if($("#" + modalQualifier).length) {
        // remove the content from the modal to ensure that the user sees no invalid data
        $("#" + contentQualifier).html("");
    } else {
        $("html").append(modalWrapper);
        _initializeModal();
    }

    var mainLoadAnimationSelector = "#main_content";

    showLoadAnimation(mainLoadAnimationSelector);

    // load the modal from the server
    $.ajax({
        url: uri,
        method: "get",
        data: dataForModal,
        cache: false,
        beforeSend: function() {
            //showLoadAnimation(mainLoadAnimationSelector);
        },
        success: function (content) {
            $("#" + contentQualifier).html(content);

            $("#" + modalQualifier).modal({
                backdrop: 'static',
                keyboard: false,
                show: true
            });

            // initialize the size of the modal
            _resizeModal(modalQualifier);

            // add window listeners
            $(window).bind("resize", function() {
                _resizeModal(modalQualifier);
            });

            $("#" + modalQualifier).on('show.bs.modal', function() {
                _resizeModal(modalQualifier);
            });

            _addContentListeners(modalQualifier, contentQualifier, isFurtherEditable, onCompleteCallback);
            _switchEditable(modalQualifier, isEditable, isFurtherEditable);
        },
        error: function (xhr, status, error) {
            _showServerError();
        },
        complete: function () {
            hideLoadAnimation(mainLoadAnimationSelector);
        }
    });
}

function _initializeModal() {
    $(".modal-dialog").draggable();
    /*$(".modal-content").resizable();*/
}

function _resizeModal(modalQualifier) {
    var height = $(window).height();

    var modalContent = $("#" + modalQualifier).find(modalBodySelector);

    modalContent.css("height", height * 0.74);
}

function _switchEditable(modalQualifier, isEditable, isFurtherEditable) {
    var attributeName = "disabled";
    var helperAttributeName = "standard";

    var inputFields = $("#" + modalQualifier + " " + modalBodySelector).find("input, select, button");

    for(var i = 0; i < inputFields.length; i++) {
        if(isEditable && isFurtherEditable) {
            if(!$("#modal-form")[0].hasAttribute(attributeName)) {
                inputFields[i].removeAttribute(attributeName);
            }
        } else {
            if($("#modal-form")[0].hasAttribute(attributeName)) {
                inputFields[i].setAttribute(helperAttributeName, "");
            } else {
                inputFields[i].setAttribute(attributeName, "");
            }
        }
    }

    if(isFurtherEditable) {
        _switchEditButtonVisibility(modalQualifier, !isEditable);
        _switchSaveButtonVisibility(modalQualifier, isEditable);
    } else {
        _switchEditButtonVisibility(modalQualifier, false);
        _switchSaveButtonVisibility(modalQualifier, false);
    }
}

function _switchSaveButtonVisibility(modalQualifier) {
    var modalSave = $("#" + modalQualifier).find("#modal-save");

    if(modalSave.length) {
        modalSave.toggle();
    }
}

function _switchSaveButtonVisibility(modalQualifier, isEditable) {
    var modalSave = $("#" + modalQualifier).find("#modal-save");

    if(modalSave.length) {
        modalSave.toggle(isEditable);
    }
}

function _switchEditButtonVisibility(modalQualifier) {
    var modalEdit = $("#" + modalQualifier).find("#modal-edit");

    if(modalEdit.length) {
        modalEdit.toggle();
    }
}

function _switchEditButtonVisibility(modalQualifier, isEditable) {
    var modalEdit = $("#" + modalQualifier).find("#modal-edit");

    if(modalEdit.length) {
        modalEdit.toggle(isEditable);
    }
}

function _showServerError() {
    alert("A server error occurred");
}

function _actionSuccessful() {
    alert("Successful");
}

function _close(modalQualifier) {
    $('#' + modalQualifier).off('hide.bs.modal.prevent')
    $("#" + modalQualifier).modal('hide')
}

function _save(modalQualifier, contentQualifier, isFurtherEditable, onCompleteCallback) {
    var modalForm = $("#" + modalQualifier).find("#modal-form");
    var loadAnimationSelector = "#" + contentQualifier + " " + modalBodySelector;

    if(!_isCorrect(modalQualifier)) {
        return;
    }

    $.ajax({
        url: modalForm[0].action,
        method: "post",
        cache: false,
        data: modalForm.serializeArray(),
        beforeSend: function() {
            showLoadAnimation(loadAnimationSelector);
        },
        success: function (result, status, xhr) {
            hideLoadAnimation(loadAnimationSelector);
            _actionSuccessful();

            // close the modal window
            _close(modalQualifier);
            onCompleteCallback(true);
        },
        error: function (xhr, status, error) {
            hideLoadAnimation(loadAnimationSelector);

            if (xhr.status == 400) {
                // replace the content with the new one to show the errors
                $("#" + contentQualifier).html(xhr.responseText);

                // rebind the event listeners
                _addContentListeners(modalQualifier, contentQualifier, isFurtherEditable, onCompleteCallback);

                _switchEditable(modalQualifier, true, isFurtherEditable);
            } else {
                _showServerError();
            }

            onCompleteCallback(false);
        },
        complete: function () {
        }
    });
}

function _isCorrect(modalQualifier) {
    var modalForm = $("#" + modalQualifier).find("#modal-form");

    if(!modalForm[0].checkValidity()) {
        return false;
    }

    return true;
}

function _addContentListeners(modalQualifier, contentQualifier, onCompleteCallback, isFurtherEditable) {
    // add other listeners
    var modalEdit = $("#" + modalQualifier).find("#modal-edit");

    if(modalEdit.length) {
        modalEdit[0].onclick = function() {
            _switchEditable(modalQualifier, true, isFurtherEditable);
        };
    }

    var modalCancel = $("#" + modalQualifier).find("#modal-cancel");

    if(modalCancel.length) {
        modalCancel.on('click', function(e) {
            // close immediately
            _close(modalQualifier);

            /*$('#' + modalQualifier).on('hide.bs.modal.prevent', function (e) {
             e.preventDefault()
             });

             confirmDialog("cancel", "Cancel", "Unsafed changes will we be rejected. Are you sure?", function() {
             _close(modalQualifier);
             });*/
        });
    }

    var modalSave = $("#" + modalQualifier).find("#modal-form");

    if(modalSave.length) {
        modalSave[0].onsubmit = function(ev) {
            ev.preventDefault(); // to stop the form from submitting

            if(_isCorrect(modalQualifier)) {
                confirmDialog("save", "Save", "Changes will be saved. Are you sure?", function() {
                    _save(modalQualifier, contentQualifier, isFurtherEditable, onCompleteCallback)
                });
            }

            return false;
        };
    }

    var selectPicker = $('.selectpicker');

    if(selectPicker.length) {
        selectPicker.selectpicker();
        $("#" + modalQualifier).on('shown', function(){
            $("#" + modalQualifier).selectpicker('refresh');
        });
    }
}