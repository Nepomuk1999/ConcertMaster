package Domain.Event;

import Domain.Duty.DutyViewInterface;
import Domain.Instrumentation.*;
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
public class ConcertEventDomain extends EventDomain implements EventDomainInterface, EventViewInterface {
    private String description;
    private String conductor;
    private InstrumentationDomainInterface generalInstrumentation;
    private List<MusicalWorkDomainInterface> musicalWorks;
    private List<DutyViewInterface> duties;


    public ConcertEventDomain() {
        super(EventType.Concert);
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
    public void setMusicalWorks(List<MusicalWorkDomainInterface> mWork) {
        musicalWorks = mWork;
    }

    @Override
    public void setRehearsalFor(EventDomainInterface event) {
        throw new NotImplementedException();
    }

    @Override
    public void setEndTime(LocalTime endTime) {
        super.setEndTime(endTime);
    }

    @Override
    public String getConductor() {
        return conductor;
    }

    @Override
    public void setConductor(String conductor) {
        this.conductor = conductor;
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
        List<MusicalWorkViewInterface> mwViews = new LinkedList<>();
        mwViews.addAll(musicalWorks);
        return mwViews;
    }

    @Override
    public void setGeneralInstrumentation(InstrumentationDomainInterface generalInstrumentation) {
        this.generalInstrumentation = generalInstrumentation;
    }

    @Override
    public EventDomainInterface getRehearsalFor() {
        return null;
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
    public void validate() throws DateException, RequiredFieldMissingException {
        super.validate();
        if (!super.getStartDate().isEqual(super.getEndDate())) {
            throw new DateException("The start- and end-date have to be the same day!");
        }
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
    public InstrumentationViewInterface getGeneralInstrumentation() {
        if (generalInstrumentation != null) {
            return generalInstrumentation;
        }
        if ((musicalWorks == null) || (musicalWorks.size() == 0)) {
            return null;
        }
        InstrumentationDomainInterface instrumentationDomainInterface = new InstrumentationDomainObject();
        instrumentationDomainInterface.setBrassInstrumentation(new BrassInstrumentationDomainObject());
        instrumentationDomainInterface.setPercussionInstrumentation(new PercussionInstrumentationDomainObject());
        instrumentationDomainInterface.setStringInstrumentation(new StringInstrumentationDomainObject());
        instrumentationDomainInterface.setWoodInstrumentation(new WoodInstrumentationDomainObject());

        for (MusicalWorkViewInterface m : musicalWorks) {
            instrumentationDomainInterface.getBrassInstrumentation().setHorn(Integer.max(m.getInstrumentation().getBrassInstrumentation().getHorn(), instrumentationDomainInterface.getBrassInstrumentation().getHorn()));
            instrumentationDomainInterface.getBrassInstrumentation().setTrombone(Integer.max(m.getInstrumentation().getBrassInstrumentation().getTrombone(), instrumentationDomainInterface.getBrassInstrumentation().getTrombone()));
            instrumentationDomainInterface.getBrassInstrumentation().setTrumpet(Integer.max(m.getInstrumentation().getBrassInstrumentation().getTrumpet(), instrumentationDomainInterface.getBrassInstrumentation().getTrumpet()));
            instrumentationDomainInterface.getBrassInstrumentation().setTube(Integer.max(m.getInstrumentation().getBrassInstrumentation().getTube(), instrumentationDomainInterface.getBrassInstrumentation().getTube()));
            instrumentationDomainInterface.getPercussionInstrumentation().setHarp(Integer.max(m.getInstrumentation().getPercussionInstrumentation().getHarp(), instrumentationDomainInterface.getPercussionInstrumentation().getHarp()));
            instrumentationDomainInterface.getPercussionInstrumentation().setPercussion(Integer.max(m.getInstrumentation().getPercussionInstrumentation().getPercussion(), instrumentationDomainInterface.getPercussionInstrumentation().getPercussion()));
            instrumentationDomainInterface.getPercussionInstrumentation().setKettledrum(Integer.max(m.getInstrumentation().getPercussionInstrumentation().getKettledrum(), instrumentationDomainInterface.getPercussionInstrumentation().getKettledrum()));
            instrumentationDomainInterface.getStringInstrumentation().setDoublebass(Integer.max(m.getInstrumentation().getStringInstrumentation().getDoublebass(), instrumentationDomainInterface.getStringInstrumentation().getDoublebass()));
            instrumentationDomainInterface.getStringInstrumentation().setViola(Integer.max(m.getInstrumentation().getStringInstrumentation().getViola(), instrumentationDomainInterface.getStringInstrumentation().getViola()));
            instrumentationDomainInterface.getStringInstrumentation().setViolin1(Integer.max(m.getInstrumentation().getStringInstrumentation().getViolin1(), instrumentationDomainInterface.getStringInstrumentation().getViolin1()));
            instrumentationDomainInterface.getStringInstrumentation().setViolin2(Integer.max(m.getInstrumentation().getStringInstrumentation().getViolin2(), instrumentationDomainInterface.getStringInstrumentation().getViolin2()));
            instrumentationDomainInterface.getStringInstrumentation().setViolincello(Integer.max(m.getInstrumentation().getStringInstrumentation().getViolincello(), instrumentationDomainInterface.getStringInstrumentation().getViolincello()));
            instrumentationDomainInterface.getWoodInstrumentation().setBassoon(Integer.max(m.getInstrumentation().getWoodInstrumentation().getBassoon(), instrumentationDomainInterface.getWoodInstrumentation().getBassoon()));
            instrumentationDomainInterface.getWoodInstrumentation().setClarinet(Integer.max(m.getInstrumentation().getWoodInstrumentation().getClarinet(), instrumentationDomainInterface.getWoodInstrumentation().getClarinet()));
            instrumentationDomainInterface.getWoodInstrumentation().setFlute(Integer.max(m.getInstrumentation().getWoodInstrumentation().getFlute(), instrumentationDomainInterface.getWoodInstrumentation().getFlute()));
            instrumentationDomainInterface.getWoodInstrumentation().setOboe(Integer.max(m.getInstrumentation().getWoodInstrumentation().getOboe(), instrumentationDomainInterface.getWoodInstrumentation().getOboe()));
        }
        return instrumentationDomainInterface;
    }
}
