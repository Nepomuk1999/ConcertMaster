package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.EventApplication;
import team_f.domain.enums.EventType;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.entities.list.EventDutyList;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.special.request.EventDutyRequest;
import team_f.jsonconnector.enums.request.EventDutyParameter;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.converter.EventDutyConverter;
import team_f.server.helper.response.CommonResponse;
import team_f.server.helper.response.JsonResponse;
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
        resp.setCharacterEncoding("UTF-8");

        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            EventDutyRequest request = (EventDutyRequest) ReadHelper.readJSONObject(req.getReader(), EventDutyRequest.class);

            if(request != null) {
                EventApplication facade = new EventApplication();
                team_f.jsonconnector.entities.EventDuty eventDuty;
                javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;
                ErrorList errorList = null;

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

                            for(team_f.domain.entities.EventDuty item : eventDuties) {
                                eventDuty = EventDutyConverter.convertToJSON(item);
                                tmpList.add(eventDuty);
                            }

                            eventDutyList.setEventDutyList(tmpList);

                            resp.setContentType(MediaType.APPLICATION_JSON);
                            WriteHelper.writeJSONObject(resp.getWriter(), eventDutyList);
                        }

                        break;
                    case GET_ALL:
                        /*List<team_f.domain.entities.EventDuty> eventDutyEntityList = facade.getEventList();
                        List<team_f.jsonconnector.entities.EventDuty> eventDutyList = new LinkedList<>();
                        EventDutyList eventDuties = new EventDutyList();

                        for(team_f.domain.entities.EventDuty item : eventDutyEntityList) {
                            eventDuty = EventDutyConverter.convertToJSON(item);
                            eventDutyList.add(eventDuty);
                        }

                        eventDuties.setEventDutyList(eventDutyList);

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), eventDuties);*/

                        break;
                    case CREATE:
                        eventDuty = request.getEntity();

                        if(eventDuty != null) {
                            int[] musicalWorkList = null;
                            int[] alternativeInstrumentationList = null;
                            int instrumentationID = -1;
                            int rehearsalForID = -1;
                            EventType eventType = null;

                            if(eventDuty.getInstrumentation() != null) {
                                instrumentationID = eventDuty.getInstrumentation().getInstrumentationID();
                            }

                            if(eventDuty.getMusicalWorkList() != null) {
                                musicalWorkList = new int[eventDuty.getMusicalWorkList().size()];
                                alternativeInstrumentationList = new int[musicalWorkList.length];
                            }

                            if(eventDuty.getRehearsalFor() != null) {
                                rehearsalForID = eventDuty.getRehearsalFor().getEventDutyID();
                            }

                            try {
                                eventType = EventType.valueOf(String.valueOf(eventDuty.getEventType()));
                            } catch (Exception e) {
                            }

                            tmpErrorList = facade.addEvent(eventDuty.getEventDutyID(), eventDuty.getName(), eventDuty.getDescription(), eventDuty.getLocation(),
                                                           eventDuty.getStartTime(), eventDuty.getEndTime(), eventDuty.getConductor(), eventType,
                                                           rehearsalForID, eventDuty.getDefaultPoints(), instrumentationID, musicalWorkList, alternativeInstrumentationList);

                            errorList = JsonResponse.prepareErrorMessage(EventDutyConverter.convertToJSON((team_f.domain.entities.EventDuty) tmpErrorList.getKey()), tmpErrorList.getValue());
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

                        break;
                    case UPDATE:
                        eventDuty = request.getEntity();

                        if(eventDuty != null) {
                            // @TODO: add update functionality
                            /*tmpErrorList = facade.update(eventDuty);
                            errorList = JsonResponse.prepareErrorMessage(EventDutyConverter.convertToJSON((team_f.domain.entities.EventDuty) tmpErrorList.getKey()), tmpErrorList.getValue());*/
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

                        break;
                    case DELETE:
                        eventDuty = request.getEntity();

                        if(eventDuty != null) {
                            // @TODO: add delete functionality
                            /*tmpErrorList = facade.delete(eventDuty.getEventDutyID());
                            errorList = JsonResponse.prepareErrorMessage(EventDutyConverter.convertToJSON((team_f.domain.entities.EventDuty) tmpErrorList.getKey()), tmpErrorList.getValue());*/
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

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