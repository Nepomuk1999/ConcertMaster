package Database.Entity;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "EventDuty_SectionDutyRoster", schema = "sem4_team2")
@IdClass(EventDutySectionDutyRosterEntityPK.class)
public class EventDutySectionDutyRosterEntity {
    private EventDutyEntity eventDuty;
    private SectionDutyRosterEntity sectionDutyRoster;

    @Id
    @OneToOne
    @JoinColumn(name = "eventDuty", nullable = false, referencedColumnName = "eventDutyID")
    public EventDutyEntity getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(EventDutyEntity eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Id
    @OneToOne
    @JoinColumn(name = "sectionDutyRoster", nullable = false, referencedColumnName = "sectionDutyRosterID")
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

        EventDutySectionDutyRosterEntity that = (EventDutySectionDutyRosterEntity) o;

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
