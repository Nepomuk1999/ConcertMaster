package Database.Entity;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "EventDuty_MusicalWork", schema = "sem4_team2")
@IdClass(EventDutyMusicalWorkEntityPK.class)
public class EventDutyMusicalWorkEntity {
    private EventDutyEntity eventDuty;
    private MusicalWorkEntity musicalWork;
    private InstrumentationEntity alternativeInstrumentation;

    @Id
    @ManyToOne
    @JoinColumn(name = "eventDuty", nullable = false, referencedColumnName = "eventDutyID")
    public EventDutyEntity getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(EventDutyEntity eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "musicalWork", nullable = false, referencedColumnName = "musicalWorkID")
    public MusicalWorkEntity getMusicalWork() {
        return musicalWork;
    }

    public void setMusicalWork(MusicalWorkEntity musicalWork) {
        this.musicalWork = musicalWork;
    }

    @ManyToOne
    @JoinColumn(name = "alternativeInstrumentation", referencedColumnName = "instrumentationID")
    public InstrumentationEntity getAlternativeInstrumentation() {
        return alternativeInstrumentation;
    }

    public void setAlternativeInstrumentation(InstrumentationEntity alternativeInstrumentation) {
        this.alternativeInstrumentation = alternativeInstrumentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDutyMusicalWorkEntity that = (EventDutyMusicalWorkEntity) o;

        if (eventDuty != that.eventDuty) return false;
        if (musicalWork != that.musicalWork) return false;
        if (alternativeInstrumentation != null ? !alternativeInstrumentation.equals(that.alternativeInstrumentation) : that.alternativeInstrumentation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventDuty.getEventDutyId();
        result = 31 * result + musicalWork.getMusicalWorkId();
        result = 31 * result + (alternativeInstrumentation != null ? alternativeInstrumentation.hashCode() : 0);
        return result;
    }
}
