package team_f.client.pages.monthpublish;

import java.time.LocalDateTime;

public class Event {
    private Integer id;
    private String name;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private String conductor;
    private String location;
    private String points;
    private String description;
    private String eventtype;
    private String eventstatus;

    public Event(Integer id, String name, LocalDateTime startdate, LocalDateTime enddate, String conductor, String location, String points, String description, String eventtype, String eventstatus) {
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

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
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