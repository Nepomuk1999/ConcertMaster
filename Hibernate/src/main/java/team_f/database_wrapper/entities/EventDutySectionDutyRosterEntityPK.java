package team_f.database_wrapper.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class EventDutySectionDutyRosterEntityPK implements Serializable {
    private int eventDuty;
    private int sectionDutyRoster;

    @Column(name = "eventDuty", nullable = false)
    @Id
    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Column(name = "sectionDutyRoster", nullable = false)
    @Id
    public int getSectionDutyRoster() {
        return sectionDutyRoster;
    }

    public void setSectionDutyRoster(int sectionDutyRoster) {
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
        int result = eventDuty;
        result = 31 * result + sectionDutyRoster;
        return result;
    }
}
