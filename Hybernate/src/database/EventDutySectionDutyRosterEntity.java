package database;

/**
 * Created by Home on 06.04.2017.
 */
public class EventDutySectionDutyRosterEntity {
    private int eventDuty;
    private int sectionDutyRoster;

    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

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
