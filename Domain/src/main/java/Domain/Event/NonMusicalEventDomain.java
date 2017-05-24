package Domain.Event;

import Domain.Duty.DutyViewInterface;
import Domain.Instrumentation.InstrumentationDomainInterface;
import Domain.Instrumentation.InstrumentationViewInterface;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;
import Enums.EventStatus;
import Enums.EventType;
import Exceptions.DateException;
import Exceptions.RequiredFieldMissingException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
public class NonMusicalEventDomain extends EventDomain implements EventDomainInterface, EventViewInterface {
    private String description;
    private List<DutyViewInterface> duties;


    public NonMusicalEventDomain() {
        super(EventType.NonMusicalEvent);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public LocalDate getStartDate() {
        return super.getStartDate();
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        super.setStartDate(startDate);
    }

    @Override
    public LocalDate getEndDate() {
        return super.getEndDate();
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        super.setEndDate(endDate);
    }

    @Override
    public LocalTime getStartTime() {
        return super.getStartTime();
    }

    @Override
    public void setStartTime(LocalTime startTime) {
        super.setStartTime(startTime);
    }

    @Override
    public LocalTime getEndTime() {
        return super.getEndTime();
    }

    @Override
    public EventType getEventType() {
        return super.getEventType();
    }

    @Override
    public EventStatus getEventStatus() {
        return super.getEventStatus();
    }

    @Override
    public void setEventStatus(EventStatus status) {
        super.setEventStatus(status);
    }

    @Override
    public void setMusicalWorks(List<MusicalWorkDomainInterface> event) {
        throw new NotImplementedException();
    }

    @Override
    public void setGeneralInstrumentation(InstrumentationDomainInterface instrumentation) {
        throw new NotImplementedException();
    }

    @Override
    public String getConductor() {
        return null;
    }

    @Override
    public void setConductor(String conductor) {
        throw new NotImplementedException();
    }

    @Override
    public void setEndTime(LocalTime endTime) {
        super.setEndTime(endTime);
    }

    @Override
    public String getLocation() {
        return super.getLocation();
    }

    @Override
    public void setLocation(String location) {
        super.setLocation(location);
    }

    @Override
    public int getDefaultPoints() {
        return super.getDefaultPoints();
    }

    @Override
    public void setDefaultPoints(int points) {
        super.setDefaultPoints(points);
    }

    @Override
    public List<MusicalWorkViewInterface> getMusicalWorks() {
        return null;
    }

    @Override
    public EventDomainInterface getRehearsalFor() {
        return null;
    }

    @Override
    public void setRehearsalFor(EventDomainInterface event) {
        throw new NotImplementedException();
    }

    @Override
    public void setDuties(List<DutyViewInterface> duties) {
        this.duties = duties;
    }

    @Override
    public List<DutyViewInterface> getDuties() {
        return duties;
    }

    @Override
    public void addDuty(DutyViewInterface duty) {
        if(duties == null){
            duties = new LinkedList<>();
        }
        duties.add(duty);
    }

    @Override
    public void removeDuty(DutyViewInterface duty) {
        Iterator<DutyViewInterface> iterator = duties.iterator();
        boolean found = false;
        while(!found && iterator.hasNext()){
            DutyViewInterface d = iterator.next();
            if(d.getMusician().equals(duty.getMusician())){
                found = true;
            }
            iterator.remove();
        }
    }

    @Override
    public void validate() throws DateException, RequiredFieldMissingException {
        super.validate();
        if (!super.getStartDate().isEqual(super.getEndDate())) {
            throw new DateException("The start- and end-date have to be the same day!");
        }
    }

    @Override
    public InstrumentationViewInterface getGeneralInstrumentation() {
        return null;
    }
}
