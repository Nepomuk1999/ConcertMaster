package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.Application;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.server.helper.DateTimeHelper;
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
                    e.printStackTrace();
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
            EventDuty eventDuty = new EventDuty();

            int id;

            try {
                id = Integer.parseInt(eventDutyRequest.getEventDutyId());
            } catch (NumberFormatException e) {
                id = -1;
            }

            eventDuty.setEventDutyId(id);

            eventDuty.setName(eventDutyRequest.getName());
            eventDuty.setDescription(eventDutyRequest.getDescription());

            LocalDateTime tmpDateTime;

            try {
                tmpDateTime = DateTimeHelper.getLocalDateTime(eventDutyRequest.getStartDate(), eventDutyRequest.getStartTime());
            } catch (Exception e) {
                tmpDateTime = null;
            }

            eventDuty.setStarttime(tmpDateTime);

            try {
                tmpDateTime = DateTimeHelper.getLocalDateTime(eventDutyRequest.getEndDate(), eventDutyRequest.getEndTime());
            } catch (Exception e) {
                tmpDateTime = null;
            }

            eventDuty.setEndtime(tmpDateTime);

            eventDuty.setEventType(eventDutyRequest.getEventtype());
            eventDuty.setEventStatus(eventDutyRequest.getEventstatus());

            eventDuty.setConductor(eventDutyRequest.getConductor());
            eventDuty.setLocation(eventDutyRequest.getLocation());
            eventDuty.setDefaultPoints(Double.parseDouble(eventDutyRequest.getStandardPoints()));
            eventDuty.setInstrumentationId(Integer.parseInt(eventDutyRequest.getInstrumentation()));

            Integer musicalWorkId;
            Integer alternativeInstrumentationId;
            MusicalWork musicalWork;
            Instrumentation alternativeInstrumentation;
            String[] musicalWorkList = eventDutyRequest.getMusicalWorkList();
            String[] alternativeInstrumentationList = eventDutyRequest.getAlternativeInstrumentationList();

            if(musicalWorkList != null && alternativeInstrumentationList != null) {
                for(int i = 0; i < musicalWorkList.length && i < alternativeInstrumentationList.length; i++) {
                    try {
                        musicalWorkId = Integer.parseInt(musicalWorkList[i]);
                    } catch (NumberFormatException e) {
                        musicalWorkId = -1;
                    }

                    try {
                        alternativeInstrumentationId = Integer.parseInt(alternativeInstrumentationList[i]);
                    } catch (NumberFormatException e) {
                        alternativeInstrumentationId = -1;
                    }

                    musicalWork = new MusicalWork();
                    musicalWork.setMusicalWorkID(musicalWorkId);

                    alternativeInstrumentation = new Instrumentation();
                    alternativeInstrumentation.setInstrumentationID(alternativeInstrumentationId);

                    eventDuty.addMusicalWork(musicalWork, alternativeInstrumentation);
                }
            }

            List<Pair<String, String>> errorList = facade.addEvent(eventDuty);

            if(errorList.size() > 0) {
                resp.setContentType(MediaType.TEXT_HTML);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                req.setAttribute("eventDuty", eventDuty);

                Object attributeContent;

                for(Pair<String, String> item : errorList) {
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
