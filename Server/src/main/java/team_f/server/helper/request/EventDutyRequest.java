package team_f.server.helper.request;

import javax.servlet.http.HttpServletRequest;

public class EventDutyRequest {
    private String _startdate;
    private String _starttime;
    private String _enddate;
    private String _endtime;
    private String _name;
    private String _location;
    private String _conductor;
    private String _standardPoints;
    private String _eventtype;
    private String _musicalWork;
    private String _rehearsalFor;
    private String _eventstatus;
    private String _description;
    private String _instrumentation;

    public EventDutyRequest(HttpServletRequest request) {
        _startdate = request.getParameter("startdate");
        _starttime = request.getParameter("starttime");
        _enddate = request.getParameter("enddate");
        _endtime = request.getParameter("endtime");
        _name = request.getParameter("name");
        _location = request.getParameter("location");
        _conductor = request.getParameter("conductor");
        _standardPoints = request.getParameter("standard-points");
        _eventtype = request.getParameter("eventtype");
        _musicalWork = request.getParameter("musical-work");
        _rehearsalFor = request.getParameter("rehearsal-for");
        _eventstatus = request.getParameter("eventstatus");
        _description = request.getParameter("description");
        _instrumentation = request.getParameter("instrumentation");
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

    public String getMusicalWork() {
        return _musicalWork;
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
