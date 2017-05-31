package team_f.server.helper.request;

import team_f.domain.enums.properties.RequestProperty;
import javax.servlet.http.HttpServletRequest;

public class RequestRequest {
    private int _eventId;
    private int _personId;
    private String _requestType;
    private String _description;

    public RequestRequest(HttpServletRequest request) {
        _description = request.getParameter(String.valueOf(RequestProperty.DESCRIPTION));
        _requestType = request.getParameter(String.valueOf(RequestProperty.REQUEST_TYPE));

        String tmp;

        tmp = request.getParameter(String.valueOf(RequestProperty.EVENT_DUTY));

        try {
            _eventId = Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            _eventId = -1;
        }

        tmp = request.getParameter(String.valueOf(RequestProperty.PERSON));

        try {
            _personId = Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            _personId = -1;
        }
    }

    public int getEventId() {
        return _eventId;
    }

    public int getPersonId() {
        return _personId;
    }

    public String getRequestType() {
        return _requestType;
    }

    public String getDescription() {
        return _description;
    }
}
