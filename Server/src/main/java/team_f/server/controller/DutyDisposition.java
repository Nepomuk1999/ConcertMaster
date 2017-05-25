package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.DutyDispositionApplication;
import team_f.domain.enums.DutyDispositionStatus;
import team_f.domain.enums.EventType;
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
import team_f.server.helper.response.CommonResponse;
import team_f.server.helper.response.JsonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            DutyDispositionRequest request = (DutyDispositionRequest) ReadHelper.readJSONObject(req.getReader(), DutyDispositionRequest.class);

            if(request != null) {
                DutyDispositionApplication facade = new DutyDispositionApplication();
                team_f.jsonconnector.entities.DutyDisposition dutyDisposition;
                javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;
                ErrorList errorList = null;

                switch (request.getActionType()) {
                    case GET_BY_PARAMETER:
                        int month = -1;
                        int year = -1;
                        int personId = -1;

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
                            }
                        }

                        if(month > 0 && year > 0 && personId > 0) {
                            double points = facade.getDutyDispositionByMonthAndPersonID(personId, month, year);
                            DutyDispositionList dutyDispositionList = new DutyDispositionList();
                            dutyDispositionList.setPoints(points);

                            resp.setContentType(MediaType.APPLICATION_JSON);
                            WriteHelper.writeJSONObject(resp.getWriter(), dutyDispositionList);
                        }

                        break;
                    case GET_ALL:
                        // @TODO: implement
                        break;
                    case CREATE:
                        dutyDisposition = request.getEntity();

                        if(dutyDisposition != null) {
                            int personID = -1;
                            int eventDutyID = -1;
                            DutyDispositionStatus dutyDispositionStatus = null;

                            if(dutyDisposition.getMusician() != null) {
                                personID = dutyDisposition.getMusician().getPersonID();
                            }

                            if(dutyDisposition.getEventDuty() != null) {
                                eventDutyID = dutyDisposition.getEventDuty().getEventDutyID();
                            }

                            try {
                                dutyDispositionStatus = DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getDutyDispositionStatus()));
                            } catch (Exception e) {
                            }

                            tmpErrorList = facade.addDutyDisposition(eventDutyID, personID, dutyDisposition.getPoints(),
                                                                     dutyDisposition.getDescription(), dutyDispositionStatus);

                            errorList = JsonResponse.prepareErrorMessage(DutyDispositionConverter.convertToJSON((team_f.domain.entities.DutyDisposition) tmpErrorList.getKey()), tmpErrorList.getValue());
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);

                        break;
                    case UPDATE:
                        /*eventDuty = request.getEntity();

                        if(eventDuty != null) {
                            // @TODO: add update functionality
                            tmpErrorList = facade.update(eventDuty);
                            errorList = JsonResponse.prepareErrorMessage(EventDutyConverter.convertToJSON((team_f.domain.entities.EventDuty) tmpErrorList.getKey()), tmpErrorList.getValue());
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);
*/
                        break;
                    case DELETE:
                        /*eventDuty = request.getEntity();

                        if(eventDuty != null) {
                            // @TODO: add delete functionality
                            tmpErrorList = facade.delete(eventDuty.getEventDutyID());
                            errorList = JsonResponse.prepareErrorMessage(EventDutyConverter.convertToJSON((team_f.domain.entities.EventDuty) tmpErrorList.getKey()), tmpErrorList.getValue());
                        }

                        resp.setContentType(MediaType.APPLICATION_JSON);
                        WriteHelper.writeJSONObject(resp.getWriter(), errorList);
*/
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