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
    private Instrumentation maxInstrumetation;
    private List<MusicalWork> _musicalWorkList = new LinkedList<>();
    private List<Instrumentation> _instrumentationList = new LinkedList<>();

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
        return  maxInstrumetation;
    }

    public void setInstrumentation(Instrumentation instrumentation) {
        this.maxInstrumetation = instrumentation;
    }

    public void addMusicalWork(MusicalWork musicalWork, Instrumentation instrumentation) {
        _musicalWorkList.add(musicalWork);
        _instrumentationList.add(instrumentation);
    }

    public List<MusicalWork> getMusicalWorkList() {
        return _musicalWorkList;
    }

    public void setMusicalWorkList(List e) {
        this._musicalWorkList = e;
    }

    public List<Instrumentation> getInstrumentationList() {
        return _instrumentationList;
    }

    public void setInstrumentationList(List<Instrumentation> l) {
        this._instrumentationList = l;
    }

    public Instrumentation getMaxInstrumetation() {
        return maxInstrumetation;
    }

    public void setMaxInstrumetation(Instrumentation maxInstrumetation) {
        this.maxInstrumetation = maxInstrumetation;
    }

    public void calculateMaxInstrumentation () {
        int maxFlute = 0;
        int maxOboe = 0;
        int maxClarinet = 0;
        int maxBassoon = 0;

        int maxViolin1 = 0;
        int maxViolin2 = 0;
        int maxViola = 0;
        int maxViolincello = 0;
        int maxDoublebass = 0;

        int maxHorn = 0;
        int maxTrumpet = 0;
        int maxTrombone = 0;
        int maxTube = 0;

        int maxKettledrum = 0;
        int maxPercussion = 0;
        int maxHarp = 0;

        for (int i = 0; i < this._musicalWorkList.size(); i++) {
            Instrumentation instrumentation = _instrumentationList.get(i);

            if (maxFlute < instrumentation.getFlute()) { maxFlute = instrumentation.getFlute(); }
            if (maxOboe < instrumentation.getOboe()) { maxOboe = instrumentation.getOboe(); }
            if (maxClarinet < instrumentation.getClarinet()) { maxClarinet = instrumentation.getClarinet(); }
            if (maxBassoon < instrumentation.getBassoon()) { maxBassoon = instrumentation.getBassoon(); }
            if (maxViolin1 < instrumentation.getViolin1()) { maxViolin1 = instrumentation.getViolin1(); }
            if (maxViolin2 < instrumentation.getViolin2()) { maxViolin2 = instrumentation.getViolin2(); }
            if (maxViola < instrumentation.getViola()) { maxViola = instrumentation.getViola(); }
            if (maxViolincello < instrumentation.getViolincello()) { maxViolincello = instrumentation.getViolincello(); }
            if (maxDoublebass < instrumentation.getDoublebass()) { maxDoublebass = instrumentation.getDoublebass(); }
            if (maxHorn < instrumentation.getHorn()) { maxHorn = instrumentation.getHorn(); }
            if (maxTrumpet < instrumentation.getTrumpet()) { maxTrumpet = instrumentation.getTrumpet(); }
            if (maxTrombone < instrumentation.getTrombone()) { maxTrombone = instrumentation.getTrombone(); }
            if (maxTube < instrumentation.getTube()) { maxTube = instrumentation.getTube(); }
            if (maxKettledrum < instrumentation.getKettledrum()) { maxKettledrum = instrumentation.getKettledrum(); }
            if (maxPercussion < instrumentation.getPercussion()) { maxPercussion = instrumentation.getPercussion(); }
            if (maxHarp < instrumentation.getHarp()) { maxHarp = instrumentation.getHarp(); }
        }

        Instrumentation maxInstrumentation = new Instrumentation();

        maxInstrumentation.setFlute(maxFlute);
        maxInstrumentation.setOboe(maxOboe);
        maxInstrumentation.setClarinet(maxClarinet);
        maxInstrumentation.setBassoon(maxBassoon);

        maxInstrumentation.setViolin1(maxViolin1);
        maxInstrumentation.setViolin2(maxViolin2);
        maxInstrumentation.setViola(maxViola);
        maxInstrumentation.setViolincello(maxViolincello);
        maxInstrumentation.setDoublebass(maxDoublebass);

        maxInstrumentation.setHorn(maxHorn);
        maxInstrumentation.setTrumpet(maxTrumpet);
        maxInstrumentation.setTrombone(maxTrombone);
        maxInstrumentation.setTube(maxTube);

        maxInstrumentation.setKettledrum(maxKettledrum);
        maxInstrumentation.setPercussion(maxPercussion);
        maxInstrumentation.setHarp(maxHarp);

        this.maxInstrumetation = maxInstrumentation;
    }
}