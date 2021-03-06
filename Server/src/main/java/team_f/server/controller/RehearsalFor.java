package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.EventApplication;
import team_f.domain.enums.EventType;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.server.helper.request.CalendarRequest;
import team_f.server.helper.request.EventDutyRequest;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {URIList.rehearsalFor})
public class RehearsalFor extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            CalendarRequest.getAndSetParameters(req, true);
            setAttributes(req);

            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/event_type.jsp").include(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            EventApplication facade = EventApplication.getInstance();
            EventDutyRequest eventDutyRequest = new EventDutyRequest(req);

            Pair<DomainEntity, List<Pair<String, String>>> result = facade.addEvent(eventDutyRequest.getEventDutyId(), eventDutyRequest.getName(),
                                                                                    eventDutyRequest.getDescription(), eventDutyRequest.getLocation(),
                                                                                    eventDutyRequest.getStartTime(), eventDutyRequest.getEndTime(),
                                                                                    eventDutyRequest.getConductor(), EventType.Rehearsal,
                                                                                    eventDutyRequest.getRehearsalForId(), eventDutyRequest.getStandardPoints(),
                                                                                    -1, null,
                                                                                    null);

            if(result.getValue().size() > 0) {
                resp.setContentType(MediaType.TEXT_HTML);
                CommonResponse.setErrorParameters(req, resp, result, "eventDuty");
                setAttributes(req);

                req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/event_type.jsp").include(req, resp);
            } else {
                resp.setContentType(MediaType.TEXT_HTML);
            }
        }
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("IS_REHEARSAL_FOR", true);
    }
}
