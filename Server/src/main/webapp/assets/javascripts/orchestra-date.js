var lastClickedEvent = null;
var lastClickedEventColor = null;

$(document).ready(function() {
    showCalendar(showDayModal, showEventModal);
});

function showDayModal(date, jsEvent, view) {
    // date contains only a date in the format 2017-04-04T00:00:00.000Z
    showModal("date", "/Date", date.toJSON, true);
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

    showModal("date", "/Date", eventData, false);
}