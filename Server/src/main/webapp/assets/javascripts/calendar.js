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
        eventDurationEditable: false,
        eventLimit: true, // allow "more" link when too many events
        navLinks: true,
        lazyFetching: false,
        viewRender: function(view, element) {
            _refreshCalendar(view.start, view.end, view.timezone, view.jsonpCallback, view.events);
        },
        dayClick: dayClickCallback,
        eventClick: eventClickCallback
    });
}

function refreshCalendar() {
    var view = $('#calendar').fullCalendar('getView');
    _refreshCalendar(view.start, view.end, view.timezone, view.jsonpCallback, view.events);
}

function _refreshCalendar(start, end, timezone, callback, events) {
    $('.fc-event').remove();

    $.ajax({
        url: "/Calendar",
        contentType: "application/json",
        cache: false,
        data: {
            start: start.toISOString(),
            end: end.toISOString()
        },
        success: function (eventList) {
            var view = $('#calendar').fullCalendar('getView');

            if (view.name == 'month') {
                $("#calendar").fullCalendar('removeEvents');
            }

            $('#calendar').fullCalendar('addEventSource', eventList);

            if (view.name == 'month') {
                $("#calendar").fullCalendar( 'refresh' );
            }
        }
    });
}