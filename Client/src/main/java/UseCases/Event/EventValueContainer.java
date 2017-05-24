package UseCases.Event;

import Domain.Event.EventViewInterface;
import Domain.Instrumentation.InstrumentationViewInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;
import Enums.EventStatus;
import Enums.EventType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Julian
 */
public class EventValueContainer {
    private int id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String conductor;
    private String location;
    private int defaultPoints;
    private final EventType eventType;
    private EventStatus eventStatus;
    private InstrumentationViewInterface generalInstrumentation;
    private List<MusicalWorkViewInterface> musicalWorks;
    private EventViewInterface rehearsalFor;

    EventValueContainer(int id, EventType type) {
        this.id = id;
        this.eventType = type;
    }

    EventValueContainer(EventType type) {
        this.eventType = type;
    }

    public int getId() {
        return id;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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

    public int getDefaultPoints() {
        return defaultPoints;
    }

    public void setDefaultPoints(int defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    public EventType getEventType() {
        return eventType;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public List<MusicalWorkViewInterface> getMusicalWorks() {
        return musicalWorks;
    }

    public void setMusicalWorks(List<MusicalWorkViewInterface> musicalWorks) {
        this.musicalWorks = musicalWorks;
    }

    public InstrumentationViewInterface getGeneralInstrumentation() {
        return generalInstrumentation;
    }

    public void setGeneralInstrumentation(InstrumentationViewInterface generalInstrumentation) {
        this.generalInstrumentation = generalInstrumentation;
    }

    public EventViewInterface getRehearsalFor() {
        return rehearsalFor;
    }

    public void setRehearsalFor(EventViewInterface rehearsalFor) {
        this.rehearsalFor = rehearsalFor;
    }
}
