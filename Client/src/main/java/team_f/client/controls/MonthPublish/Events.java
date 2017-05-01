package team_f.client.controls.MonthPublish;

public class Events {

    private Integer id;
    private String name;
    private String startdate;
    private String enddate;
    private String conductor;
    private String location;
    private String points;
    private String description;
    private String eventtype;
    private String eventstatus;

    public Events(Integer id, String name, String startdate, String enddate, String conductor, String location, String points, String description, String eventtype, String eventstatus) {
        super();
        this.id = id;
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.conductor = conductor;
        this.location = location;
        this.points = points;
        this.description = description;
        this.eventtype = eventtype;
        this.eventstatus = eventstatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public String getEventstatus() {
        return eventstatus;
    }

    public void setEventstatus(String eventstatus) {
        this.eventstatus = eventstatus;
    }
}