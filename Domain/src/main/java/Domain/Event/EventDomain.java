package Domain.Event;

import Enums.EventStatus;
import Enums.EventType;
import Exceptions.DateException;
import Exceptions.RequiredFieldMissingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Julian
 */
public abstract class EventDomain {
    private int id;

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
    private final EventType eventType;
    private EventStatus eventStatus;
    private int defaultPoints;

    EventDomain(EventType type) {
        eventType = type;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    LocalDate getStartDate() {
        return startDate;
    }

    void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    LocalDate getEndDate() {
        return endDate;
    }

    void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    LocalTime getStartTime() {
        return startTime;
    }

    void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    LocalTime getEndTime() {
        return endTime;
    }

    void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    String getLocation() {
        return location;
    }

    void setLocation(String location) {
        this.location = location;
    }

    int getDefaultPoints() {
        return defaultPoints;
    }

    void setDefaultPoints(int defaultPoints) {
        this.defaultPoints = defaultPoints;
    }

    EventType getEventType() {
        return eventType;
    }

    EventStatus getEventStatus() {
        return eventStatus;
    }

    void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    void validate() throws RequiredFieldMissingException, DateException {
        checkRequiredFields();
        checkDates();
    }

    private void checkRequiredFields() throws RequiredFieldMissingException {
        // check if name is set
        if ((name == null) || (name.equals(""))) {
            throw new RequiredFieldMissingException("Please enter a name!");
        }
        // check if dates are set
        if (startDate == null) {
            throw new RequiredFieldMissingException("Please enter a start date!");
        }
        if (endDate == null) {
            throw new RequiredFieldMissingException("Please enter an end date!");
        }
        // check if times are set
        if (startTime == null) {
            throw new RequiredFieldMissingException("Please enter a start time!");
        }
        if (endTime == null) {
            throw new RequiredFieldMissingException("Please enter an end time!");
        }
        //Check if the Event-Type was set.
        if (eventType == null) {
            throw new RequiredFieldMissingException("The Event-Type is not set!");
        }
    }

    private void checkDates() throws DateException {
        if (!LocalDateTime.of(startDate, startTime).isBefore(LocalDateTime.of(endDate, endTime))) {
            throw new DateException("The start-date should be before the end-date.");
        }
    }
}
