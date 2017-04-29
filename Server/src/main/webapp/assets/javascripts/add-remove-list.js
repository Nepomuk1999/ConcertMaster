var addRemoveGlyhpicon = 'glyphicon';
var addRemoveGlyhpiconPlus = 'glyphicon-plus';
var addListItem = 'add-list-item';
var addRemoveListMain = 'add-remove-list-main';
var addListItemContent = 'add-list-item-content';
var addListItemTarget = 'add-list-item-target';
var addRemoveTmpSelect = 'tmpSelect';
var removeListItem = 'remove-list-item';
var removeGlyphicon = 'glyphicon-remove';
var removeListItemContent = 'remove-list-item-content';
var addListItemName = 'add-list-item-name';
var addListItemCheck = 'add-list-item-check';

function addRemoveList(isListEditable) {
    var list = $('.' + addListItem);

    list.addClass(addRemoveGlyhpicon);
    list.addClass(addRemoveGlyhpiconPlus);

    for(var i = 0; i < list.length; i++) {
        list[i].onclick = function(event) {
            _addListItem(event, isListEditable);
        };
    }
}

function _addListItem(event, isListEditable) {
    var main = $(event.target).parents('.' + addRemoveListMain);

    if(main.length > 0) {
        var content = main.find('.' + addListItemContent);
        var target = main.find('.' + addListItemTarget);

        if(content.length > 0 && target.length > 0) {
            if(_check(content, target)) {
                var nodeCopy = content.clone();

                // remove the events on the select picker
                var selectPickerList = $(nodeCopy).find('.selectpicker');
                selectPickerList.addClass(addRemoveTmpSelect);
                selectPickerList.selectpicker('destroy');

                nodeCopy.removeClass(addListItemContent);

                var button = nodeCopy.find('.' + addListItem);

                if(button.length > 0) {
                    button[0].onclick = function(event) {
                        _removeListItem(event);
                    };

                    button.removeClass(addListItem);
                    button.removeClass(addRemoveGlyhpiconPlus);
                    button.addClass(removeListItem);
                    button.addClass(removeGlyphicon);
                    nodeCopy.addClass(removeListItemContent);

                    // remove all elements which are not specified in the template
                    nodeCopy.find(':not(.add-list-item-keep)').remove();

                    // the name for form submits
                    tmpAddListItemName = nodeCopy.find('[' + addListItemName + ']');

                    for(var i = 0; i < tmpAddListItemName.length; i++) {
                        tmpAddListItemName[i].setAttribute("name", tmpAddListItemName[i].getAttribute(addListItemName));
                    }

                    target.prepend(nodeCopy);

                    $(document).ready(function() {
                        var tmpSelectParent = $('.' + addRemoveTmpSelect);
                        tmpSelectParent.removeClass(addRemoveTmpSelect);
                        tmpSelectParent.addClass('selectpicker');
                        tmpSelectParent.removeData();

                        // add new values to the half cloned elements
                        var newSelectPickers = tmpSelectParent.selectpicker();
                        var inputSelectPickers = $(content).find('.selectpicker');

                        for(var i = 0; i < newSelectPickers.length && i < inputSelectPickers.length; i++) {
                            newSelectPickers[i].value = inputSelectPickers[i].value;
                            $(newSelectPickers[i]).selectpicker('refresh');
                        }

                        _enable(isListEditable, nodeCopy);
                    });
                }
            }
        }
    }
}

function _removeListItem(event) {
    var node = $(event.target).parents('.' + removeListItemContent);
    node.detach();
}

function _check(content, target) {
    var contentCheckList = content.find('.' + addListItemCheck);
    var targetList = target.find('.' + removeListItemContent);
    var targetCheckList;

    /*outer: for(var i = 0; i < targetList.length; i++) {
        targetCheckList = targetList.find('.' + addListItemCheck);

        for(var n = 0; n < contentCheckList.length && n < targetCheckList.length; n++) {
            if(targetList[i].text() !== contentCheckList[n].text()) {
                continue outer;
            }
        }

        return false;
    }*/

    return true;
}

function _enable(isEnabled, node) {
    var items = node.find('button, input').not('.' + removeListItem);

    for(var i = 0; i < items.length; i++) {
        if(isEnabled) {
            items[i].removeAttribute("disabled");
        } else {
            items[i].setAttribute("disabled", "");
        }
    }
}