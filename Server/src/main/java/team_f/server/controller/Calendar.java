package team_f.server.controller;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.UidGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import team_f.application.EventApplication;
import team_f.application.helper.EventDutyHelper;
import team_f.domain.entities.EventDuty;
import team_f.jsonconnector.common.URIList;
import team_f.server.helper.CSSHelper;
import team_f.server.helper.DateTimeHelper;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(urlPatterns = {URIList.calendar})
public class Calendar extends HttpServlet {
    private static String _iCalendarMediaType = "text/calendar";

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        String headerTypeAccept = req.getHeader("accept");
        String headerTypeXMsCookieURIrequested = req.getHeader("x-ms-cookieuri-requested");

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject;

            // get the parameters
            String startParameter = req.getParameter("start");
            String endParameter = req.getParameter("end");

            LocalDate startDate = null;
            LocalDate endDate = null;

            try {
                startDate = LocalDate.parse(startParameter);
                endDate = LocalDate.parse(endParameter);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(startDate != null && endDate != null) {
                EventApplication facade = new EventApplication();
                List<EventDuty> eventList = facade.getEventsByMonth(startDate.getMonthValue() +1, startDate.getYear());

                for(EventDuty event : eventList) {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", event.getEventDutyID());
                    jsonObject.put("start", event.getStartTime());
                    jsonObject.put("end", event.getEndTime());
                    jsonObject.put("title","(" + EventDutyHelper.getEventTypeCode(event.getEventType()) + ")" +"\t"+event.getName());
                    jsonObject.put("color", CSSHelper.getColor(EventDutyHelper.getColor(event.getEventType())));
                    //jsonObject.put("className", "");
                    //jsonObject.put("backgroundColor", "");
                    //jsonObject.put("borderColor", "");
                    //jsonObject.put("textColor", "");

                    jsonArray.put(jsonObject);
                }
            }

            CommonResponse.writeJSONObject(resp, jsonArray);
        } else if((headerTypeAccept != null && headerTypeAccept.startsWith(_iCalendarMediaType)) || (headerTypeXMsCookieURIrequested != null && headerTypeXMsCookieURIrequested.trim().equals("t"))) {
            // response.setHeader("Content-type", "text/calendar");
            // pacify outlook
            resp.setHeader("Content-type", "application/x-msoutlook");
            resp.setHeader("Content-disposition", "inline; filename=calendar.ics");

            net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
            calendar.getProperties().add(new ProdId("-//Concert Master//iCal4j 1.0//EN"));
            calendar.getProperties().add(Version.VERSION_2_0);
            calendar.getProperties().add(CalScale.GREGORIAN);
            UidGenerator uidGenerator = new UidGenerator("concert-master");

            EventApplication facade = new EventApplication();

            LocalDateTime start = LocalDateTime.now();
            //start = start.minusMonths(1);

            LocalDateTime end = LocalDateTime.now();
            // @TODO: set for production a broader limit
            end = end.plusWeeks(6);

            List<EventDuty> eventList = facade.getEventsByTimeFrame(start, end);
            VEvent vEvent;
            DateTime startDateTime;
            DateTime endDateTime;
            String rehearsalFor;
            String instrumentation;
            String summary;
            String conductor;
            String description;

            for(EventDuty event : eventList) {
                startDateTime = DateTimeHelper.convertToICalDateTime(event.getStartTime());
                endDateTime = DateTimeHelper.convertToICalDateTime(event.getEndTime());
                vEvent = new VEvent(startDateTime, endDateTime, event.getName());
                vEvent.getProperties().add(new Location(event.getLocation()));

                rehearsalFor = "";

                if(event.getRehearsalFor() != null) {
                    rehearsalFor = "RehearsalFor:" + "\n" +
                                   "* " + event.getRehearsalFor().getName() + "\n\n";
                }

                conductor = "";

                if(event.getConductor() != null) {
                    conductor = "Conductor:" + "\n" +
                                "* " + conductor + "\n\n";
                }

                description = "";

                if(event.getDescription() != null) {
                    description = "Description:" + "\n" +
                                  event.getDescription() + "\n\n";
                }

                instrumentation = "";

                if(event.getInstrumentation() != null) {
                    // @TODO: should we show the instrumentation?
                    // therefore we would need for getBasson() and other methods instead of int to return a Basson, ... object
                    /*instrumentation = "Instrumentation:" + "\n" +
                                      instrumentation;*/
                }

                summary = "Event Type:" + "\n" +
                          "* " + event.getEventType() + "\n\n" +
                          "Status:" + "\n" +
                          "* " + event.getEventStatus() + "\n\n" +
                          conductor +
                          "Default Points:" + "\n" +
                          "* " + event.getDefaultPoints() + "\n\n" +
                          rehearsalFor +
                          description +
                          instrumentation;

                vEvent.getProperties().add(new Description(summary));

                vEvent.getProperties().add(uidGenerator.generateUid());
                calendar.getComponents().add(vEvent);
            }

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, resp.getWriter());
            // for debugging purposes
            //outputter.output(calendar, System.out);
            resp.getWriter().close();
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            resp.setCharacterEncoding("UTF-8");
            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/pages/plan_overview.jsp").include(req, resp);
        }
    }
}
