package team_f.server.helper.request;

import team_f.application.EventApplication;
import team_f.domain.entities.EventDuty;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarRequest {
    public static void getAndSetParameters(HttpServletRequest request, boolean isRehearsal) {
        String dateParameter = request.getParameter("date");
        LocalDateTime date;

        String eventDutyId = request.getParameter("EventDutyId");
        int eventId;
        EventDuty eventDuty;

        try {
            eventId = Integer.parseInt(eventDutyId);
        } catch (NumberFormatException e) {
            eventId = -1;
        }

        EventApplication facade = new EventApplication();

        if(eventId > 0 && !isRehearsal) {
            eventDuty = facade.getEventById(eventId);
        } else {
            eventDuty = new EventDuty();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                date = LocalDateTime.parse(dateParameter + " 00:00", formatter);
            } catch (Exception e) {
                date = LocalDateTime.now();
            }

            eventDuty.setStartTime(date);
            eventDuty.setEndTime(date);

            if(eventId > 0 && isRehearsal) {
                eventDuty.setRehearsalFor(facade.getEventById(eventId));
            }
        }

        if(eventDuty != null) {
            request.setAttribute("eventDuty", eventDuty);
        }
    }
}
