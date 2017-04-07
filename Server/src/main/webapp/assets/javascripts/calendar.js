// how to used it: http://jsfiddle.net/shaunp/u8Ksw/29/

function showCalendar(dayClickCallback, eventClickCallback) {
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listWeek'
        },
        editable: true,
        eventStartEditable: false,
        eventLimit: true, // allow "more" link when too many events
        navLinks: true,
        viewRender: function( view, element ) {
            _refreshCalendar(view.start, view.end, view.timezone, view.jsonpCallback);
        },
        dayClick: dayClickCallback,
        eventClick: eventClickCallback
    });
}

function _refreshCalendar(start, end, timezone, callback) {
    $.ajax({
        url: "/Calendar",
        contentType: "application/json",
        cache: false,
        data: {
            start: start.toISOString(),
            end: end.toISOString()
        },
        success: function (eventList) {
            $("#calendar").fullCalendar('removeEvents');
            $("#calendar").fullCalendar('addEventSource', eventList);
        }
    });
}