package database;

/**
 * Created by Home on 06.04.2017.
 */
public class EventDutyMusicalWorkEntity {
    private int eventDuty;
    private int musicalWork;
    private Integer alternativeInstrumentation;

    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    public int getMusicalWork() {
        return musicalWork;
    }

    public void setMusicalWork(int musicalWork) {
        this.musicalWork = musicalWork;
    }

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
