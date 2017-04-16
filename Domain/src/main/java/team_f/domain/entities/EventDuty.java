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
    private String eventType;
    private String eventStatus;
    private String conductor;
    private String location;
    private Integer rehearsalFor;
    private double defaultPoints;
    private Integer instrumentation;
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
        Date date = Date.from(endtime.atZone(ZoneId.systemDefault()).toInstant());

        Format formatter = new SimpleDateFormat(format);
        String result = formatter.format(date);

        return result;
    }

    public String getStartDate(String format) {
        Date date = Date.from(endtime.atZone(ZoneId.systemDefault()).toInstant());

        Format formatter = new SimpleDateFormat(format);
        String result = formatter.format(date);

        return result;
    }

    public String getStartTime(String format) {
        Date date = Date.from(starttime.atZone(ZoneId.systemDefault()).toInstant());

        Format formatter = new SimpleDateFormat(format);
        String result = formatter.format(date);

        return result;
    }

    public String getEndTime(String format) {
        Date date = Date.from(endtime.atZone(ZoneId.systemDefault()).toInstant());

        Format formatter = new SimpleDateFormat(format);
        String result = formatter.format(date);

        return result;
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

    public String getEventType() {
        return eventType.toString();
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventStatus() {
        return eventStatus.toString();
    }

    public void setEventStatus(String eventStatus) {
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