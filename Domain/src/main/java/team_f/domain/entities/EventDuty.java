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
    private LocalDateTime starttime;
    private LocalDateTime endtime;
    private EventType eventType;
    private EventStatus eventStatus;
    private String conductor;
    private String location;
    private Integer rehearsalFor;
    private double defaultPoints;
    private Integer instrumentationId;
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
        if(endtime != null) {
            Date date = Date.from(endtime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
    }

    public String getStartDate(String format) {
        if(starttime != null) {
            Date date = Date.from(starttime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
    }

    public String getStartTime(String format) {
        if(starttime != null) {
            Date date = Date.from(starttime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
    }

    public String getEndTime(String format) {
        if(endtime != null) {
            Date date = Date.from(endtime.atZone(ZoneId.systemDefault()).toInstant());

            Format formatter = new SimpleDateFormat(format);
            String result = formatter.format(date);

            return result;
        }

        return "";
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

    public Integer getInstrumentationId() {
        return instrumentationId;
    }

    public void setInstrumentationId(Integer instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    public void addMusicalWork(MusicalWork musicalWorkId, Instrumentation alternativeInstrumentationId) {
        _musicalWorkList.add(musicalWorkId);
        _alternativeInstrumentationList.add(alternativeInstrumentationId);
    }

    public List<MusicalWork> getMusicalWorkList() {
        return _musicalWorkList;
    }

    public List<Instrumentation> getAlternativeInstrumentationList() {
        return _alternativeInstrumentationList;
    }
}