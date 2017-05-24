package Domain.Duty;

import Domain.Event.EventDomainInterface;
import Domain.Person.PersonDomainObject;
import Enums.DutyDispositionStatus;

/**
 * @author Julian
 */
public class DutyDispositionDomainObject implements DutyDomainInterface {
    private EventDomainInterface event;
    private String description;
    private int points;
    private PersonDomainObject musician;
    private DutyDispositionStatus status;

    public void setEvent(EventDomainInterface event) {
        this.event = event;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoints(double points) {
        this.points = (int) Math.round(points);
    }

    public void setMusician(PersonDomainObject musician) {
        this.musician = musician;
    }

    public void setStatus(DutyDispositionStatus status) {
        this.status = status;
    }

    public EventDomainInterface getEvent() {
        return event;
    }

    public String getDescription() {
        return description;
    }

    public double getPoints() {
        return points;
    }

    public PersonDomainObject getMusician() {
        return musician;
    }

    public DutyDispositionStatus getStatus() {
        return status;
    }
}
