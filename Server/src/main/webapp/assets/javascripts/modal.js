function showModal(id, uri, dataForModal) {
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

    var mainLoadAnimationSelector = "main_content";

    // load the modal from the server
    $.ajax({
        url: uri,
        data: dataForModal.toJSON,
        cache: false,
        beforeSend: function() {
            showLoadAnimation(mainLoadAnimationSelector);
        },
        success: function (content) {
            $("#" + contentQualifier).append(content);

            $("#" + modalQualifier).modal({
                backdrop: 'static',
                keyboard: false,
                show: true
            });

            // add window listeners
            $(window).bind("resize", function() {
                _resizeModal(modalQualifier);
            });

            $("#" + modalQualifier).on('show.bs.modal', function() {
                _resizeModal(modalQualifier);
            });

            // add other listeners
            var modalEdit = $("#" + modalQualifier).find("#modal-edit");

            if(modalEdit.length) {
                modalEdit[0].onclick = function() {
                    _switchEditable(modalQualifier)
                };
            }

            var modalCancel = $("#" + modalQualifier).find("#modal-cancel");

             if(modalCancel.length) {
                 modalCancel.on('click', function(e) {
                     $('#' + modalQualifier).on('hide.bs.modal.prevent', function (e) {
                         e.preventDefault()
                     });

                     confirmDialog("cancel", "Cancel", "Unsafed changes will we be rejected. Are you sure?", function() {
                        _close(modalQualifier)
                     });
                 });
             }

            var modalSave = $("#" + modalQualifier).find("#modal-form");

            if(modalSave.length) {
                modalSave[0].onsubmit = function(ev) {
                    ev.preventDefault(); // to stop the form from submitting

                    if(_isCorrect(modalQualifier)) {
                        confirmDialog("save", "Save", "Changes will be saved. Are you sure?", function() {
                            _save(modalQualifier, contentQualifier)
                        });
                    }

                    return false;
                };
            };

            // set non editable
            _switchEditable(modalQualifier);
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

    var modalContent = $("#" + modalQualifier).find(".modal-body");

    modalContent.css("height", height * 0.74);
}

function _switchEditable(modalQualifier) {
    var attributeName = "disabled";
    var inputFields = $("#" + modalQualifier).find("input");

    for(var i = 0; i < inputFields.length; i++) {
        if(inputFields[i].hasAttribute(attributeName)) {
            inputFields[i].removeAttribute(attributeName)
        } else {
            inputFields[i].setAttribute(attributeName, "");
            $("#modal-form")[0].setAttribute(attributeName, "");
        }
    }

    _switchSaveButtonVisibility(modalQualifier);
}

function _switchSaveButtonVisibility(modalQualifier) {
    var modalSave = $("#" + modalQualifier).find("#modal-save");

    if(modalSave.length) {
        modalSave.toggle();
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

function _save(modalQualifier, contentQualifier) {
    var modalForm = $("#" + modalQualifier).find("#modal-form");
    var loadAnimationSelector = "#" + contentQualifier + " #modal-form > .modal-body";

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
        },
        error: function (xhr, status, error) {
            hideLoadAnimation(loadAnimationSelector);

            if (xhr.status == 400) {
                // replace the content with the new one to show the errors
                $("#" + contentQualifier).innerHTML = (xhr.responseText);
            } else {
                _showServerError();
            }
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