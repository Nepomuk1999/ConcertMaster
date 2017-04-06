package database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "EventDuty_SectionDutyRoster", schema = "sem4_team2", catalog = "")
public class EventDutySectionDutyRosterEntity {
    private int eventDuty;
    private int sectionDutyRoster;

    @Basic
    @Column(name = "eventDuty", nullable = false)
    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Basic
    @Column(name = "sectionDutyRoster", nullable = false)
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

        EventDutySectionDutyRosterEntity that = (EventDutySectionDutyRosterEntity) o;

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
