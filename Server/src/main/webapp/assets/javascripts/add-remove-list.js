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

function addRemoveList(isListEditable, excludeFromSearch, maxItems) {
    var list = $('.' + addListItem);

    list.addClass(addRemoveGlyhpicon);
    list.addClass(addRemoveGlyhpiconPlus);

    for(var i = 0; i < list.length; i++) {
        $(list[i]).off("click");

        $(list[i]).on('click', function(event) {
            _addListItem(event, isListEditable, excludeFromSearch, maxItems);
        });
    }
}

function removeAddRemoveList() {
    var main = $('.add-list-item').parents('.' + addRemoveListMain);
    var target = main.find('.' + addListItemTarget);
    var targetList = target.find('.' + removeListItemContent);
    $(targetList).remove();
}

function _addListItem(event, isListEditable, excludeFromSearch, maxItems) {
    var main = $(event.target).parents('.' + addRemoveListMain);

    if(main.length > 0) {
        var content = main.find('.' + addListItemContent);
        var target = main.find('.' + addListItemTarget);

        if(content.length > 0 && target.length > 0) {
            if(_check(content, target, excludeFromSearch, maxItems)) {
                var nodeCopy = content.clone();

                // remove the events on the select picker
                var selectPickerList = $(nodeCopy).find('.selectpicker');
                selectPickerList.addClass(addRemoveTmpSelect);
                selectPickerList.selectpicker('destroy');

                nodeCopy.removeClass(addListItemContent);

                var button = nodeCopy.find('.' + addListItem);

                if(button.length > 0) {
                    $(button[i]).off("click");

                    $(button[0]).on('click', function(event) {
                        _removeListItem(event);
                    });

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
                }
            }
        }
    }
}

function _removeListItem(event) {
    var node = $(event.target).parents('.' + removeListItemContent);
    node.detach();
}

function _check(content, target, excludeFromSearch, maxItems) {
    // do not search for selects
    var contentCheckList = content.find('.' + addListItemCheck).not(excludeFromSearch);
    var targetList = target.find('.' + removeListItemContent);
    var targetCheckList;

    if(targetList.length + 1 > maxItems) {
        return false;
    }

    // check if empty
    for(var n = 0; n < contentCheckList.length; n++) {
        if(contentCheckList[n].value === '') {
            return false;
        }
    }

    var correctCount;
    var tmp1;
    var tmp2;

    outer: for(var i = 0; i < targetList.length; i++) {
        correctCount = 0;
        targetCheckList = targetList.find('.' + addListItemCheck).not(excludeFromSearch);

        for(var n = 0; n < contentCheckList.length && n < targetCheckList.length; n++) {
            tmp1 = $(targetCheckList[n]).val();
            tmp2 = $(contentCheckList[n]).val();

            if(tmp1 !== tmp2) {
                continue outer;
            }
        }

        return false;
    }

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