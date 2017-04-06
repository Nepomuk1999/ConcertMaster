package database;

import javax.persistence.*;

/**
 * Created by Home on 06.04.2017.
 */
@Entity
@Table(name = "EventDuty_MusicalWork", schema = "sem4_team2")
@IdClass(EventDutyMusicalWorkEntityPK.class)
public class EventDutyMusicalWorkEntity {
    private int eventDuty;
    private int musicalWork;
    private Integer alternativeInstrumentation;

    @Id
    @Column(name = "eventDuty", nullable = false)
    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Id
    @Column(name = "musicalWork", nullable = false)
    public int getMusicalWork() {
        return musicalWork;
    }

    public void setMusicalWork(int musicalWork) {
        this.musicalWork = musicalWork;
    }

    @Basic
    @Column(name = "alternativeInstrumentation", nullable = true)
    public Integer getAlternativeInstrumentation() {
        return alternativeInstrumentation;
    }

    public void setAlternativeInstrumentation(Integer alternativeInstrumentation) {
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
        int result = eventDuty;
        result = 31 * result + musicalWork;
        result = 31 * result + (alternativeInstrumentation != null ? alternativeInstrumentation.hashCode() : 0);
        return result;
    }
}
