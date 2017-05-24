package Database.Entity;

import java.io.Serializable;

/**
 * @author Benjamin Grabherr
 */
class EventDutySectionDutyRosterEntityPK implements Serializable {
    private EventDutyEntity eventDuty;
    private SectionDutyRosterEntity sectionDutyRoster;

    public EventDutyEntity getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(EventDutyEntity eventDuty) {
        this.eventDuty = eventDuty;
    }

    public SectionDutyRosterEntity getSectionDutyRoster() {
        return sectionDutyRoster;
    }

    public void setSectionDutyRoster(SectionDutyRosterEntity sectionDutyRoster) {
        this.sectionDutyRoster = sectionDutyRoster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDutySectionDutyRosterEntityPK that = (EventDutySectionDutyRosterEntityPK) o;

        if (eventDuty != that.eventDuty) return false;
        if (sectionDutyRoster != that.sectionDutyRoster) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventDuty.getEventDutyId();
        result = 31 * result + sectionDutyRoster.getSectionDutyRosterId();
        return result;
    }
}
