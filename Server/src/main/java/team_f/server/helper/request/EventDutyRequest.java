package team_f.server.helper.request;

import team_f.domain.enums.EventDutyProperty;
import javax.servlet.http.HttpServletRequest;

public class EventDutyRequest {
    private String _id;
    private String _startdate;
    private String _starttime;
    private String _enddate;
    private String _endtime;
    private String _name;
    private String _location;
    private String _conductor;
    private String _standardPoints;
    private String _eventtype;
    private String[] _musicalWorkList;
    private String[] _alternativeInstrumentationList;
    private String _rehearsalFor;
    private String _eventstatus;
    private String _description;
    private String _instrumentation;

    public EventDutyRequest(HttpServletRequest request) {
        _id = request.getParameter(String.valueOf(EventDutyProperty.ID));
        _startdate = request.getParameter(String.valueOf(EventDutyProperty.START_DATE));
        _starttime = request.getParameter(String.valueOf(EventDutyProperty.START_TIME));
        _enddate = request.getParameter(String.valueOf(EventDutyProperty.END_DATE));
        _endtime = request.getParameter(String.valueOf(EventDutyProperty.END_TIME));
        _name = request.getParameter(String.valueOf(EventDutyProperty.NAME));
        _location = request.getParameter(String.valueOf(EventDutyProperty.LOCATION));
        _conductor = request.getParameter(String.valueOf(EventDutyProperty.CONDUCTOR));
        _standardPoints = request.getParameter(String.valueOf(EventDutyProperty.DEFAULT_POINTS));
        _eventtype = request.getParameter(String.valueOf(EventDutyProperty.EVENT_TYPE));
        _musicalWorkList = request.getParameterValues(String.valueOf(EventDutyProperty.MUSICAL_WORK_LIST));
        _alternativeInstrumentationList = request.getParameterValues(String.valueOf(EventDutyProperty.ALTERNATIVE_INSTRUMENTATION_LIST));
        _rehearsalFor = request.getParameter(String.valueOf(EventDutyProperty.REHEARSAL_FOR));
        _eventstatus = request.getParameter(String.valueOf(EventDutyProperty.EVENT_STATUS));
        _description = request.getParameter(String.valueOf(EventDutyProperty.DESCRIPTION));
        _instrumentation = request.getParameter(String.valueOf(EventDutyProperty.INSTRUMENTATION));
    }

    public String getEventDutyId() {
        return _id;
    }

    public String getStartDate() {
        return _startdate;
    }

    public String getStartTime() {
        return _starttime;
    }

    public String getEndDate() {
        return _enddate;
    }

    public String getEndTime() {
        return _endtime;
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

    public String getStandardPoints() {
        return _standardPoints;
    }

    public String getEventtype() {
        return _eventtype;
    }

    public String[] getMusicalWorkList() {
        return _musicalWorkList;
    }

    public String[] getAlternativeInstrumentationList() {
        return _alternativeInstrumentationList;
    }

    public String getRehearsalFor() {
        return _rehearsalFor;
    }

    public String getEventstatus() {
        return _eventstatus;
    }

    public String getDescription() {
        return _description;
    }

    public String getInstrumentation() {
        return _instrumentation;
    }
}
