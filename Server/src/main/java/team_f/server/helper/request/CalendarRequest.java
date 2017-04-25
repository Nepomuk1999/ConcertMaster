package team_f.server.helper.request;

import team_f.application.Application;
import team_f.domain.entities.EventDuty;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CalendarRequest {
    public static void getAndSetParameters(HttpServletRequest request) {
        String dateParameter = request.getParameter("date");
        LocalDateTime date;

        String eventDutyId = request.getParameter("EventDutyId");
        int eventId;
        EventDuty eventDuty = null;

        try {
            eventId = Integer.parseInt(eventDutyId);
        } catch (NumberFormatException e) {
            eventId = -1;
        }

        if(eventId > 0) {
            Application facade = new Application();
            eventDuty = facade.getEventById(eventId);
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                date = LocalDateTime.parse(dateParameter + " 00:00", formatter);

                eventDuty = new EventDuty();
                eventDuty.setStarttime(date);
                eventDuty.setEndtime(date);
            } catch (Exception e) {
            }
        }

        if(eventDuty != null) {
            request.setAttribute("eventDuty", eventDuty);
        }
    }
}
