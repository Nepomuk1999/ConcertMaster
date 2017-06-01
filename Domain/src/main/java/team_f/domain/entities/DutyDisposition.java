package team_f.domain.entities;

import javafx.util.Pair;
import team_f.domain.enums.DutyDispositionStatus;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.properties.DutyDispositionProperty;

import java.util.LinkedList;
import java.util.List;

public class DutyDisposition extends BaseDomainEntity<DutyDispositionProperty> {
    private EventDuty eventDuty;
    private Person musician;
    private double points;
    private String description;
    private DutyDispositionStatus dutyDispositionStatus;

    public DutyDisposition() {
        super(EntityType.DUTY_DISPOSITION);
    }

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
    public int getID() {
        return -1;
    }
}
