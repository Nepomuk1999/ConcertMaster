var lastClickedEvent = null;
var lastClickedEventColor = null;

$(document).ready(function() {
    showCalendar(showDayModal, showEventModal);
});

function showDayModal(date, jsEvent, view) {
    // date contains only a date in the format 2017-04-04T00:00:00.000Z
    showModal("date", "/Date", {"date": JSON.stringify(date).replace(/"/g, '')}, true, function(isSuccessful) {
        if(isSuccessful) {
            $('#calendar').fullCalendar('gotoDate', date);
            _onComplete(isSuccessful);
        }
    });
}

function showEventModal(calEvent, jsEvent, view) {
    // contains the full event with an unique id
    var eventData = {
        "EventDutyId": calEvent.id
    };

    if(lastClickedEvent != null) {
        lastClickedEvent.color = lastClickedEventColor;
    }

    lastClickedEventColor = calEvent.color;
    lastClickedEvent = calEvent;
    calEvent.color = "orange";

    $('#calendar').fullCalendar('updateEvent', calEvent);

    showModal("date", "/CalendarActionChooser", eventData, false, function (isSuccessful) {
        if(isSuccessful) {
            $('#calendar').fullCalendar('gotoDate', calEvent);
            _onComplete(isSuccessful);
        }
    });
}

function _onComplete(isSuccessful) {
    if(isSuccessful) {
        refreshCalendar();
    }
}