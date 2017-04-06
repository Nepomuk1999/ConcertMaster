package database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "EventDuty_MusicalWork", schema = "sem4_team2", catalog = "")
public class EventDutyMusicalWorkEntity {
    private int eventDuty;
    private int musicalWork;
    private Integer alternativeInstrumentation;

    @Basic
    @Column(name = "eventDuty", nullable = false)
    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Basic
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
