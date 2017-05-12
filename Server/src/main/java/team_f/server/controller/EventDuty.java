package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.EventApplication;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.list.EventDutyList;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.special.request.EventDutyRequest;
import team_f.jsonconnector.enums.request.EventDutyParameter;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.converter.EventDutyConverter;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {URIList.event})
public class EventDuty extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            EventDutyRequest request = (EventDutyRequest) ReadHelper.readJSONObject(req.getReader(), EventDutyRequest.class);

            if(request != null) {
                EventApplication facade = new EventApplication();

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        int month = -1;
                        int year = -1;

                        for(Pair<String, String> item : request.getParameterValueList()) {
                            if(String.valueOf(EventDutyParameter.MONTH).equals(item.getKey())) {
                                try {
                                    month = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            } else if(String.valueOf(EventDutyParameter.YEAR).equals(item.getKey())) {
                                try {
                                    year = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            }
                        }

                        if(month > 0 && year > 0) {
                            List<team_f.domain.entities.EventDuty> eventDuties = facade.getEventsByMonth(month, year);
                            EventDutyList eventDutyList = new EventDutyList();
                            List<team_f.jsonconnector.entities.EventDuty> tmpList = new ArrayList<>(eventDuties.size());
                            team_f.jsonconnector.entities.EventDuty eventDuty;

                            for(team_f.domain.entities.EventDuty item : eventDuties) {
                                eventDuty = EventDutyConverter.convertToJSON(item);
                                tmpList.add(eventDuty);
                            }

                            eventDutyList.setEventDutyList(tmpList);

                            resp.setContentType(MediaType.APPLICATION_JSON);
                            resp.setCharacterEncoding("UTF-8");
                            WriteHelper.writeJSONObject(resp.getWriter(), eventDutyList);
                        }

                        break;
                    default:
                        break;
                }
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}