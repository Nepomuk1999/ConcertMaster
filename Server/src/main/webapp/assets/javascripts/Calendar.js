// how to used it: http://jsfiddle.net/shaunp/u8Ksw/29/

$(document).ready(function() {
    $('#calendar').fullCalendar({
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay,listWeek'
        },
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        navLinks: true,
        eventSources: [
            refreshCalendar()
        ],
        viewRender: function( view, element ) {
            refreshCalendar();
        },
        eventClick: function(calEvent, jsEvent, view) {
            $(document).ready(function() {
                $('.modal-dialog').draggable();
                $('.modal-content').resizable();
                $('#myModal').modal({
                    backdrop: 'static',
                    keyboard: false,
                    show: true
                });
            });
        }
    });

    $("#calendar").fullCalendar({
        header: {
            left: "prev,next today",
            center: "title",
            right: "month,agendaWeek,agendaDay"
        }
    });

    function refreshCalendar() {
        $.ajax({
            url: "/Calendar",
            contentType: "application/json",
            success: function (eventList) {
                $("#calendar").fullCalendar('removeEvents');
                $("#calendar").fullCalendar('addEventSource', eventList);
            }
        });
    }
});