package team_f.jsonconnector.entities;

import team_f.jsonconnector.enums.DutyDispositionStatus;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

public class DutyDisposition implements JSONObjectEntity {
    private EventDuty eventDuty;
    private Person musician;
    private double points;
    private String description;
    private DutyDispositionStatus dutyDispositionStatus;

    public EventDuty getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(EventDuty eventDuty) {
        this.eventDuty = eventDuty;
    }

    public Person getMusician() {
        return musician;
    }

    public void setMusician(Person musician) {
        this.musician = musician;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DutyDispositionStatus getDutyDispositionStatus() {
        return dutyDispositionStatus;
    }

    public void setDutyDispositionStatus(DutyDispositionStatus dutyDispositionStatus) {
        this.dutyDispositionStatus = dutyDispositionStatus;
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
