package team_f.domain.entities;

import team_f.database_wrapper.database.*;
import team_f.database_wrapper.entities.EventStatus;
import team_f.database_wrapper.entities.EventType;
import team_f.domain.interfaces.DomainEntity;
import java.time.LocalDateTime;
import java.util.Collection;

public class EventDuty implements DomainEntity {
    private int eventDutyId;
    private String name;
    private String description;
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private EventType eventType;
    private EventStatus eventStatus;
    private String conductor;
    private String location;
    private Integer rehearsalFor;
    private double defaultPoints;
    private Integer instrumentation;
    private Collection<DutyDispositionEntity> dutyDispositionsByEventDutyId;
    private EventDutyEntity eventDutyByRehearsalFor;
    private Collection<EventDutyEntity> eventDutiesByEventDutyId;
    private InstrumentationEntity instrumentationByInstrumentation;
    private Collection<EventDutyMusicalWorkEntity> eventDutyMusicalWorksByEventDutyId;
    private Collection<EventDutySectionDutyRosterEntity> eventDutySectionDutyRostersByEventDutyId;
    private Collection<RequestEntity> requestsByEventDutyId;

    public int getEventDutyId() {
        return eventDutyId;
    }

    public void setEventDutyId(int eventDutyId) {
        this.eventDutyId = eventDutyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalDateTime endtime) {
        this.endtime = endtime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRehearsalFor() {
        return rehearsalFor;
    }

    public void setRehearsalFor(Integer rehearsalFor) {
        this.rehearsalFor = rehearsalFor;
    }

    public double getDefaultPoints() {
        return defaultPoints;
    }

    public void setDefaultPoints(double defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    public Integer getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(Integer instrumentation) {
        this.instrumentation = instrumentation;
    }

    public Collection<DutyDispositionEntity> getDutyDispositionsByEventDutyId() {
        return dutyDispositionsByEventDutyId;
    }

    public void setDutyDispositionsByEventDutyId(Collection<DutyDispositionEntity> dutyDispositionsByEventDutyId) {
        this.dutyDispositionsByEventDutyId = dutyDispositionsByEventDutyId;
    }

    public EventDutyEntity getEventDutyByRehearsalFor() {
        return eventDutyByRehearsalFor;
    }

    public void setEventDutyByRehearsalFor(EventDutyEntity eventDutyByRehearsalFor) {
        this.eventDutyByRehearsalFor = eventDutyByRehearsalFor;
    }

    public Collection<EventDutyEntity> getEventDutiesByEventDutyId() {
        return eventDutiesByEventDutyId;
    }

    public void setEventDutiesByEventDutyId(Collection<EventDutyEntity> eventDutiesByEventDutyId) {
        this.eventDutiesByEventDutyId = eventDutiesByEventDutyId;
    }

    public InstrumentationEntity getInstrumentationByInstrumentation() {
        return instrumentationByInstrumentation;
    }

    public void setInstrumentationByInstrumentation(InstrumentationEntity instrumentationByInstrumentation) {
        this.instrumentationByInstrumentation = instrumentationByInstrumentation;
    }

    public Collection<EventDutyMusicalWorkEntity> getEventDutyMusicalWorksByEventDutyId() {
        return eventDutyMusicalWorksByEventDutyId;
    }

    public void setEventDutyMusicalWorksByEventDutyId(Collection<EventDutyMusicalWorkEntity> eventDutyMusicalWorksByEventDutyId) {
        this.eventDutyMusicalWorksByEventDutyId = eventDutyMusicalWorksByEventDutyId;
    }

    public Collection<EventDutySectionDutyRosterEntity> getEventDutySectionDutyRostersByEventDutyId() {
        return eventDutySectionDutyRostersByEventDutyId;
    }

    public void setEventDutySectionDutyRostersByEventDutyId(Collection<EventDutySectionDutyRosterEntity> eventDutySectionDutyRostersByEventDutyId) {
        this.eventDutySectionDutyRostersByEventDutyId = eventDutySectionDutyRostersByEventDutyId;
    }

    public Collection<RequestEntity> getRequestsByEventDutyId() {
        return requestsByEventDutyId;
    }

    public void setRequestsByEventDutyId(Collection<RequestEntity> requestsByEventDutyId) {
        this.requestsByEventDutyId = requestsByEventDutyId;
    }
}