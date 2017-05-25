package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.enums.DutyDispositionStatus;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

public class DutyDisposition implements JSONObjectEntity {
    private EventDuty _eventDuty;
    private Person _person;
    private double _points;
    private String _description;
    private DutyDispositionStatus _dutyDispositionStatus;

    @JsonGetter("event")
    public EventDuty getEventDuty() {
        return _eventDuty;
    }

    @JsonGetter("person")
    public Person getPerson() {
        return _person;
    }

    @JsonGetter("points")
    public double getPoints() {
        return _points;
    }

    @JsonGetter("description")
    public String getDescription() {
        return _description;
    }

    @JsonGetter("status")
    public DutyDispositionStatus getDutyDispositionStatus() {
        return _dutyDispositionStatus;
    }

    @JsonSetter("event")
    public void setEventDuty(EventDuty eventDuty) {
        _eventDuty = eventDuty;
    }

    @JsonSetter("person")
    public void setPerson(Person person) {
        _person = person;
    }

    @JsonSetter("points")
    public void setPoints(double points) {
        _points = points;
    }

    @JsonSetter("description")
    public void setDescription(String description) {
        _description = description;
    }

    @JsonSetter("status")
    public void setDutyDispositionStatus(DutyDispositionStatus dutyDispositionStatus) {
        _dutyDispositionStatus = dutyDispositionStatus;
    }

    @Override
    public String getEntityName() {
        return "Duty Disposition";
    }

    @Override
    public String getDisplayName() {
        return getDescription();
    }
}
