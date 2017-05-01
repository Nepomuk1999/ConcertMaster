package team_f.server.helper.request;

import team_f.domain.enums.EventDutyProperty;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import team_f.server.helper.DateTimeHelper;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class EventDutyRequest {
    private int _id;
    private LocalDateTime _startDateTime;
    private LocalDateTime _endDateTime;
    private String _name;
    private String _location;
    private String _conductor;
    private Double _standardPoints;
    private EventType _eventType;
    private int[] _musicalWorkIdList;
    private int[] _alternativeInstrumentationIdList;
    private int _rehearsalForId;
    private EventStatus _eventStatus;
    private String _description;
    private int _instrumentationId;

    public EventDutyRequest(HttpServletRequest request) {
        String tmp, tmp2;

        tmp = request.getParameter(String.valueOf(EventDutyProperty.ID));

        try {
            _id = Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            _id = -1;
        }

        tmp = request.getParameter(String.valueOf(EventDutyProperty.START_DATE));
        tmp2 = request.getParameter(String.valueOf(EventDutyProperty.START_TIME));

        try {
            _startDateTime = DateTimeHelper.getLocalDateTime(tmp, tmp2);
        } catch (Exception e) {
            _startDateTime = null;
        }

        tmp = request.getParameter(String.valueOf(EventDutyProperty.END_DATE));
        tmp2 = request.getParameter(String.valueOf(EventDutyProperty.END_TIME));

        try {
            _endDateTime = DateTimeHelper.getLocalDateTime(tmp, tmp2);
        } catch (Exception e) {
            _endDateTime = null;
        }

        _name = request.getParameter(String.valueOf(EventDutyProperty.NAME));
        _location = request.getParameter(String.valueOf(EventDutyProperty.LOCATION));
        _conductor = request.getParameter(String.valueOf(EventDutyProperty.CONDUCTOR));

        tmp = request.getParameter(String.valueOf(EventDutyProperty.DEFAULT_POINTS));

        try {
            _standardPoints = Double.parseDouble(tmp);
        } catch (NumberFormatException e) {
            _standardPoints = -1.0;
        }

        tmp = request.getParameter(String.valueOf(EventDutyProperty.INSTRUMENTATION));

        try {
            _instrumentationId = Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            _instrumentationId = -1;
        }

        String[] tempMusicalWorkIdList = request.getParameterValues(String.valueOf(EventDutyProperty.MUSICAL_WORK_LIST));
        String[] tempAlternativeInstrumentationIdList = request.getParameterValues(String.valueOf(EventDutyProperty.ALTERNATIVE_INSTRUMENTATION_LIST));

        if(tempMusicalWorkIdList != null && tempAlternativeInstrumentationIdList != null) {
            _musicalWorkIdList = new int[tempMusicalWorkIdList.length];
            _alternativeInstrumentationIdList = new int[tempMusicalWorkIdList.length];

            for(int i = 0; i < tempMusicalWorkIdList.length && i < tempAlternativeInstrumentationIdList.length; i++) {
                try {
                    _musicalWorkIdList[i] = Integer.parseInt(tempMusicalWorkIdList[i]);
                } catch (NumberFormatException e) {
                    _musicalWorkIdList[i] = -1;
                }

                try {
                    _alternativeInstrumentationIdList[i] = Integer.parseInt(tempAlternativeInstrumentationIdList[i]);
                } catch (NumberFormatException e) {
                    _alternativeInstrumentationIdList[i] = -1;
                }
            }
        }

        tmp = request.getParameter(String.valueOf(EventDutyProperty.REHEARSAL_FOR));

        try {
            _rehearsalForId = Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            _rehearsalForId = -1;
        }

        tmp = request.getParameter(String.valueOf(EventDutyProperty.EVENT_TYPE));

        try {
            _eventType = EventType.valueOf(tmp);
        } catch (Exception e) {
            _eventType = null;
        }

        tmp = request.getParameter(String.valueOf(EventDutyProperty.EVENT_STATUS));

        try {
            _eventStatus = EventStatus.valueOf(tmp);
        } catch (Exception e) {
            _eventStatus = null;
        }

        _description = request.getParameter(String.valueOf(EventDutyProperty.DESCRIPTION));
    }

    public int getEventDutyId() {
        return _id;
    }

    public LocalDateTime getStartTime() {
        return _startDateTime;
    }

    public LocalDateTime getEndTime() {
        return _endDateTime;
    }

    public String getName() {
        return _name;
    }

    public String getLocation() {
        return _location;
    }

    public String getConductor() {
        return _conductor;
    }

    public Double getStandardPoints() {
        return _standardPoints;
    }

    public EventType getEventType() {
        return _eventType;
    }

    public int[] getMusicalWorkList() {
        return _musicalWorkIdList;
    }

    public int[] getAlternativeInstrumentationList() {
        return _alternativeInstrumentationIdList;
    }

    public int getRehearsalForId() {
        return _rehearsalForId;
    }

    public EventStatus getEventStatus() {
        return _eventStatus;
    }

    public String getDescription() {
        return _description;
    }

    public int getInstrumentationId() {
        return _instrumentationId;
    }
}
