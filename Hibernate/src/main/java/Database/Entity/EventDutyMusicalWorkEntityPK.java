package Database.Entity;

import java.io.Serializable;

/**
 * @author Benjamin Grabherr
 */
class EventDutyMusicalWorkEntityPK implements Serializable {
    private EventDutyEntity eventDuty;
    private MusicalWorkEntity musicalWork;

    public EventDutyEntity getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(EventDutyEntity eventDuty) {
        this.eventDuty = eventDuty;
    }

    public MusicalWorkEntity getMusicalWork() {
        return musicalWork;
    }

    public void setMusicalWork(MusicalWorkEntity musicalWork) {
        this.musicalWork = musicalWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDutyMusicalWorkEntityPK that = (EventDutyMusicalWorkEntityPK) o;

        if (eventDuty != that.eventDuty) return false;
        if (musicalWork != that.musicalWork) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventDuty.getEventDutyId();
        result = 31 * result + musicalWork.getMusicalWorkId();
        return result;
    }
}
