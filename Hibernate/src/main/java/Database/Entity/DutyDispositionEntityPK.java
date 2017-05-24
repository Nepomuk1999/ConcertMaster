package Database.Entity;

import java.io.Serializable;

/**
 * @author Benjamin Grabherr
 */
class DutyDispositionEntityPK implements Serializable {
    private EventDutyEntity eventDuty;
    private PersonEntity musician;

    public EventDutyEntity getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(EventDutyEntity eventDuty) {
        this.eventDuty = eventDuty;
    }

    public PersonEntity getMusician() {
        return musician;
    }

    public void setMusician(PersonEntity musician) {
        this.musician = musician;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DutyDispositionEntityPK that = (DutyDispositionEntityPK) o;

        if (eventDuty != that.eventDuty) return false;
        if (musician != that.musician) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventDuty.getEventDutyId();
        result = 31 * result + musician.getPersonId();
        return result;
    }
}
