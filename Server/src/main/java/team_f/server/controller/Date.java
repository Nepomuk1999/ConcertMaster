package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.Application;
import team_f.domain.entities.EventDuty;
import team_f.server.helper.request.EventDutyRequest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(urlPatterns = {"/Date"})
public class Date extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (MediaType.APPLICATION_JSON.equals(contentType)) {
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding("UTF-8");

            JSONArray jsonArray = new JSONArray();

            PrintWriter writer = resp.getWriter();
            writer.write(jsonArray.toString());

            writer.flush();
            writer.close();
        } else {
            resp.setContentType(MediaType.TEXT_HTML);

            String dateParameter = req.getParameter("date");
            LocalDateTime date = null;

            String eventDutyId = req.getParameter("EventDutyId");
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
                req.setAttribute("eventDuty", eventDuty);
            }

            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/date.jsp").include(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(MediaType.APPLICATION_JSON.equals(contentType)) {
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding("UTF-8");

            JSONArray jsonArray = new JSONArray();

            PrintWriter writer = resp.getWriter();
            writer.write(jsonArray.toString());

            writer.flush();
            writer.close();
        } else {
            Application facade = new Application();
            EventDutyRequest eventDutyRequest = new EventDutyRequest(req);

            Pair<EventDuty, List<Pair<String, String>>> result = facade.addEvent(eventDutyRequest.getEventDutyId(), eventDutyRequest.getName(),
                                                                                 eventDutyRequest.getDescription(), eventDutyRequest.getLocation(),
                                                                                 eventDutyRequest.getStartTime(), eventDutyRequest.getEndTime(),
                                                                                 eventDutyRequest.getConductor(), eventDutyRequest.getEventtype(),
                                                                                 eventDutyRequest.getRehearsalForId(), eventDutyRequest.getStandardPoints(),
                                                                                 eventDutyRequest.getInstrumentationId(), eventDutyRequest.getMusicalWorkList(),
                                                                                 eventDutyRequest.getAlternativeInstrumentationList());

            if(result.getValue().size() > 0) {
                resp.setContentType(MediaType.TEXT_HTML);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                req.setAttribute("eventDuty", result.getKey());

                Object attributeContent;

                for(Pair<String, String> item : result.getValue()) {
                    attributeContent = req.getAttribute(item.getKey());

                    if(attributeContent != null) {
                        req.setAttribute("PROBLEM_" + item.getKey(), ((String) attributeContent) + "<br>" + item.getValue());
                    } else {
                        req.setAttribute("PROBLEM_" + item.getKey(), item.getValue());
                    }
                }

                req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/date.jsp").include(req, resp);
            } else {
                resp.setContentType(MediaType.TEXT_HTML);
            }
        }
    }
}
