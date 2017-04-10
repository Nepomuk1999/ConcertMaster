package team_f.server.controller;

import org.json.JSONArray;
import team_f.database_wrapper.facade.Facade;
import team_f.database_wrapper.enums.EventStatus;
import team_f.database_wrapper.enums.EventType;
import team_f.database_wrapper.helper.model.EventDuty;
import team_f.server.helper.request.EventDutyRequest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
            boolean isCorrect = true;

            EventDutyRequest eventDuty = new EventDutyRequest(req);

            List<Map.Entry<String, String>> problems = EventDuty.validate();

            if(problems.size() > 0) {
                isCorrect = false;

                req.setAttribute("problems", problems);
            }

            if(!isCorrect) {
                resp.setContentType(MediaType.TEXT_HTML);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                req.setAttribute("eventDuty", eventDuty);
                req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/date.jsp").include(req, resp);
            } else {
                Facade facade = new Facade();
                Timestamp startTime = Timestamp.valueOf(eventDuty.getStartDate() + " " + eventDuty.getStartTime());
                Timestamp endTime = Timestamp.valueOf(eventDuty.getEndDate() + " " + eventDuty.getEndTime());
                EventType eventType = EventType.valueOf(eventDuty.getEventtype());
                EventStatus eventStatus = EventStatus.valueOf(eventDuty.getEventstatus());

                facade.addEvent(eventDuty.getName(), eventDuty.getDescription(), startTime, endTime, eventType, eventStatus, eventDuty.getConductor(),
                                eventDuty.getLocation(), Double.parseDouble(eventDuty.getStandardPoints()), Integer.parseInt(eventDuty.getInstrumentation()));

                resp.setContentType(MediaType.TEXT_HTML);
            }
        }
    }
}
