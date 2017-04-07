function confirmDialog(id, title, question, okCallback) {
    var dialogQualifier = id + '-dialog-confirm';
    var dialogWrapper = '<div id="' + dialogQualifier + '" title="' + title + '">' +
                            '<p><span class="ui-icon ui-icon-alert dialog"></span>' + question + '</p>' +
                        '</div>';

    // remove the dialog if it exists
    _remove(dialogQualifier);

    // add the content
    $("html").append(dialogWrapper);

    // generate the dialog
    $("#" + dialogQualifier).dialog({
        resizable: false,
        height: "auto",
        width: "auto",
        modal: true,
        buttons: {
            Cancel: function () {
                $(this).dialog("close");
            },
            "OK": function () {
                $(this).dialog("close");
                okCallback();
            }
        }
    });
}

function _remove(dialogQualifier) {
    var dialog = $("html").find("#" + dialogQualifier);

    if(dialog.length) {
        dialog.remove();
    }
}