$(document).ready(function() {
    showCalendar(showDayModal, showEventModal);
});

function showDayModal(date, jsEvent, view) {
    showModal("date", "/Date", date);
}

function showEventModal(calEvent, jsEvent, view) {
    showModal("date", "/Date", calEvent);
}