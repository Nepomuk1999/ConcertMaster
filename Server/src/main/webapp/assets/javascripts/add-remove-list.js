function addRemoveList(isListEditable) {
    var list = $('.add-list-item');

    list.addClass('glyphicon');
    list.addClass('glyphicon-plus');

    for(var i = 0; i < list.length; i++) {
        list[i].onclick = function(event) {
            _addListItem(event, isListEditable);
        };
    }
}

function _addListItem(event, isListEditable) {
    var main = $(event.target).parents('.add-remove-list-main');

    if(main.length > 0) {
        var content = main.find('.add-list-item-content');
        var target = main.find('.add-list-item-target');

        if(content.length > 0 && target.length > 0) {
            if(_check(content, target)) {
                var nodeCopy = content.clone(false, false);
                nodeCopy.children().unbind();
                var button = nodeCopy.find('.add-list-item');

                if(button.length > 0) {
                    button[0].onclick = function(event) {
                        _removeListItem(event);
                    };

                    button.removeClass('add-list-item');
                    button.removeClass('glyphicon-plus');
                    button.addClass('remove-list-item');
                    button.addClass('glyphicon-remove');
                    nodeCopy.addClass('remove-list-item-content');

                    var selectPicker = nodeCopy.find('.selectpicker');
                    var bootstrapSelect = nodeCopy.find('.bootstrap-select');

                    if(!isListEditable) {
                        var items = nodeCopy.find('button, input').not(".remove-list-item");

                        for(var i = 0; i < items.length; i++) {
                            items[i].setAttribute("disabled", "");
                        }
                    }

                    target[0].prepend(nodeCopy[0]);

                    if(selectPicker.length > 0 && bootstrapSelect.length > 0) {
                        $(document).ready(function() {
                        });
                    }
                }
            }
        }
    }
}

function _removeListItem(event) {
    var node = $(event.target).parents('.remove-list-item-content');
    node.detach();
}

function _check(content, target) {
    var contentCheckList = content.find('.add-list-item-check');
    var targetList = target.find('.remove-list-item-content');
    var targetCheckList;

    /*outer: for(var i = 0; i < targetList.length; i++) {
        targetCheckList = targetList.find('.add-list-item-check');

        for(var n = 0; n < contentCheckList.length && n < targetCheckList.length; n++) {
            if(targetList[i].text() !== contentCheckList[n].text()) {
                continue outer;
            }
        }

        return false;
    }*/

    return true;
}