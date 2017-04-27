package team_f.domain.entities;

import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import team_f.domain.interfaces.DomainEntity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EventDuty implements DomainEntity {
    private int eventDutyId;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventType eventType;
    private EventStatus eventStatus;
    private String conductor;
    private String location;
    private EventDuty rehearsalFor;
    private double defaultPoints;
    private Instrumentation instrumentation;
    private List<MusicalWork> _musicalWorkList = new LinkedList<>();
    private List<Instrumentation> _alternativeInstrumentationList = new LinkedList<>();

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

    public String getEndDate(String format) {
        if(endTime != null) {
            Date date = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
    }

    public String getStartDate(String format) {
        if(startTime != null) {
            Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
    }

    public String getStartTime(String format) {
        if(startTime != null) {
            Date date = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
    }

    public String getEndTime(String format) {
        if(endTime != null) {
            Date date = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public EventDuty getRehearsalFor() {
        return rehearsalFor;
    }

    public void setRehearsalFor(EventDuty rehearsalFor) {
        this.rehearsalFor = rehearsalFor;
    }

    public double getDefaultPoints() {
        return defaultPoints;
    }

    public void setDefaultPoints(double defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public void addMusicalWork(MusicalWork musicalWork, Instrumentation alternativeInstrumentation) {
        _musicalWorkList.add(musicalWork);
        _alternativeInstrumentationList.add(alternativeInstrumentation);
    }

    public List<MusicalWork> getMusicalWorkList() {
        return _musicalWorkList;
    }

    public void setMusicalWorkList(List e) {
        this._musicalWorkList = e;
    }

    public List<Instrumentation> getAlternativeInstrumentationList() {
        return _alternativeInstrumentationList;
    }
}