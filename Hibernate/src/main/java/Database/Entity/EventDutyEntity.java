package Database.Entity;

import Enums.EventStatus;
import Enums.EventType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "EventDuty", schema = "sem4_team2")
public class EventDutyEntity {
    private int eventDutyId;
    private String name;
    private String description;
    private Timestamp starttime;
    private Timestamp endtime;
    private EventType eventType;
    private EventStatus eventStatus;
    private String conductor;
    private String location;
    private EventDutyEntity rehearsalFor;
    private double defaultPoints;
    private InstrumentationEntity instrumentation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventDutyID", nullable = false)
    public int getEventDutyId() {
        return eventDutyId;
    }

    public void setEventDutyId(int eventDutyId) {
        this.eventDutyId = eventDutyId;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "starttime", nullable = false)
    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    @Basic
    @Column(name = "endtime", nullable = false)
    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "eventType", nullable = false, columnDefinition = "enum('Opera','Concert','Hofkapelle','Tour','Rehearsal','NonMusicalEvent')")
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "eventStatus", nullable = false, columnDefinition = "enum('Unpublished','Published','Cancelled')")
    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    @Basic
    @Column(name = "conductor")
    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @ManyToOne
    @JoinColumn(name = "rehearsalFor", referencedColumnName = "eventDutyId")
    public EventDutyEntity getRehearsalFor() {
        return rehearsalFor;
    }

    public void setRehearsalFor(EventDutyEntity rehearsalFor) {
        this.rehearsalFor = rehearsalFor;
    }

    @Basic
    @Column(name = "defaultPoints", nullable = false)
    public double getDefaultPoints() {
        return defaultPoints;
    }

    public void setDefaultPoints(double defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    @ManyToOne
    @JoinColumn(name = "instrumentation", referencedColumnName = "instrumentationID")
    public InstrumentationEntity getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(InstrumentationEntity instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDutyEntity that = (EventDutyEntity) o;

        if (eventDutyId != that.eventDutyId) return false;
        if (Double.compare(that.defaultPoints, defaultPoints) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (endtime != null ? !endtime.equals(that.endtime) : that.endtime != null) return false;
        if (eventType != null ? !eventType.equals(that.eventType) : that.eventType != null) return false;
        if (eventStatus != null ? !eventStatus.equals(that.eventStatus) : that.eventStatus != null) return false;
        if (conductor != null ? !conductor.equals(that.conductor) : that.conductor != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (rehearsalFor != null ? !rehearsalFor.equals(that.rehearsalFor) : that.rehearsalFor != null) return false;
        if (instrumentation != null ? !instrumentation.equals(that.instrumentation) : that.instrumentation != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = eventDutyId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        result = 31 * result + (eventType != null ? eventType.hashCode() : 0);
        result = 31 * result + (eventStatus != null ? eventStatus.hashCode() : 0);
        result = 31 * result + (conductor != null ? conductor.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (rehearsalFor != null ? rehearsalFor.hashCode() : 0);
        temp = Double.doubleToLongBits(defaultPoints);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (instrumentation != null ? instrumentation.hashCode() : 0);
        return result;
    }
}
