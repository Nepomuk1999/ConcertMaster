package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.DutyDispositionApplication;
import team_f.domain.entities.*;
import team_f.domain.enums.DutyDispositionStatus;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.list.DutyDispositionList;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.entities.special.request.DutyDispositionRequest;
import team_f.jsonconnector.enums.request.DutyDispositionParameter;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.converter.DutyDispositionConverter;
import team_f.server.helper.request.CalendarRequest;
import team_f.server.helper.response.CommonResponse;
import team_f.server.helper.response.JsonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@WebServlet(urlPatterns = {URIList.dutyDisposition})
public class DutyDisposition extends HttpServlet {
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

            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/duty_dispositions.jsp").include(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            DutyDispositionRequest request = (DutyDispositionRequest) ReadHelper.readJSONObject(req.getReader(), DutyDispositionRequest.class);

            if(request != null) {
                DutyDispositionApplication facade = DutyDispositionApplication.getInstance();
                team_f.jsonconnector.entities.DutyDisposition dutyDisposition;
                javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;
                ErrorList errorList = null;

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        int month = -1;
                        int year = -1;
                        int personId = -1;
                        int eventId = -1;

                        for(Pair<String, String> item : request.getParameterValueList()) {
                            if(String.valueOf(DutyDispositionParameter.MONTH).equals(item.getKey())) {
                                try {
                                    month = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            } else if(String.valueOf(DutyDispositionParameter.YEAR).equals(item.getKey())) {
                                try {
                                    year = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            } else if(String.valueOf(DutyDispositionParameter.PERSON_ID).equals(item.getKey())) {
                                try {
                                    personId = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            } else if(String.valueOf(DutyDispositionParameter.EVENT_ID).equals(item.getKey())) {
                                try {
                                    eventId = Integer.parseInt(item.getValue());
                                } catch (NumberFormatException e) {
                                }
                            }
                        }

                        if(month > 0 && year > 0 && personId > 0) {
                            double points = facade.getDutyDispositionByMonthAndPersonID(personId, month, year);
                            DutyDispositionList dutyDispositionList = new DutyDispositionList();
                            dutyDispositionList.setPoints(points);

                            resp.setContentType(MediaType.APPLICATION_JSON);
                            WriteHelper.writeJSONObject(resp.getWriter(), dutyDispositionList);
                        } else if(eventId > 0) {
                            List<team_f.domain.entities.DutyDisposition> dutyDispositionList = facade.getDutyDispositionByEventID(eventId);
                            DutyDispositionList dutyDispositions = new DutyDispositionList();
                            List<team_f.jsonconnector.entities.DutyDisposition> tmpDutyDispositions = new LinkedList<>();
                            team_f.jsonconnector.entities.DutyDisposition tmpDutyDisposition;

                            for(team_f.domain.entities.DutyDisposition item : dutyDispositionList) {
                                tmpDutyDisposition = DutyDispositionConverter.convertToJSON(item);
                                tmpDutyDispositions.add(tmpDutyDisposition);
                            }

                            dutyDispositions.setDutyDispositionList(tmpDutyDispositions);

                            resp.setContentType(MediaType.APPLICATION_JSON);
                            WriteHelper.writeJSONObject(resp.getWriter(), dutyDispositions);
                        }

                        break;
                    case GET_ALL:
                        // @TODO: implement
                        break;
                    case CREATE:
                    case UPDATE:
                        dutyDisposition = request.getEntity();

                        if(dutyDisposition != null) {
                            int personID = -1;
                            int eventDutyID = -1;
                            DutyDispositionStatus dutyDispositionStatus = null;

                            if(dutyDisposition.getPerson() != null) {
                                personID = dutyDisposition.getPerson().getPersonID();
                            }

                            if(dutyDisposition.getEventDuty() != null) {
                                eventDutyID = dutyDisposition.getEventDuty().getEventDutyID();
                            }

                            try {
                                dutyDispositionStatus = DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getStatus()));
                            } catch (Exception e) {
                            }

                            tmpErrorList = facade.addDutyDisposition(eventDutyID, personID, dutyDisposition.getPoints(),
                                                                     dutyDisposition.getDescription(), dutyDispositionStatus);

                            errorList = JsonResponse.prepareErrorMessage(DutyDispositionConverter.convertToJSON((team_f.domain.entities.DutyDisposition) tmpErrorList.getKey()), tmpErrorList.getValue());
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

                        break;
                    case DELETE:
                        dutyDisposition = request.getEntity();

                        if(dutyDisposition != null) {
                            // @TODO: add delete functionality
                            int personID = -1;
                            int eventID = -1;

                            if(dutyDisposition.getEventDuty() != null) {
                                eventID = dutyDisposition.getEventDuty().getEventDutyID();
                            }

                            if(dutyDisposition.getPerson() != null) {
                                personID = dutyDisposition.getPerson().getPersonID();
                            }

                            boolean result = facade.delete(eventID, personID);
                            // @TODO: perhaps send an error back
                            tmpErrorList = new javafx.util.Pair<>(null, new LinkedList<>());
                            errorList = JsonResponse.prepareErrorMessage(DutyDispositionConverter.convertToJSON((team_f.domain.entities.DutyDisposition) tmpErrorList.getKey()), tmpErrorList.getValue());
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

    private void setAttributes(HttpServletRequest request) {
        DutyDispositionApplication facade = DutyDispositionApplication.getInstance();

        String tmp = request.getParameter("EventDutyId");
        int eventDutyID = -1;

        try {
            eventDutyID = Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
        }

        List<team_f.domain.entities.DutyDisposition> dutyDispositionList = facade.getDutyDispositionByEventID(eventDutyID);
        team_f.domain.entities.EventDuty eventDuty = null;

        if(dutyDispositionList != null && dutyDispositionList.size() > 0) {
            eventDuty = dutyDispositionList.get(0).getEventDuty();
        }

        request.setAttribute("DUTY_DISPOSITION_EVENT_DUTY", eventDuty);
        request.setAttribute("DUTY_DISPOSITION_LIST", dutyDispositionList);
    }
}