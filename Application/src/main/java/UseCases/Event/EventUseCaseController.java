package UseCases.Event;

import Database.Facade.DatabaseFacade;
import Domain.Event.EventDomain;
import Domain.Event.EventDomainInterface;
import Domain.Event.EventViewInterface;
import Domain.Instrumentation.*;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;
import Domain.Person.PartDomainObject;
import Domain.Person.PersonDomainObject;
import Enums.EventType;
import Exceptions.DateException;
import Exceptions.RequiredFieldMissingException;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Julian
 */
public class EventUseCaseController {
    private final DatabaseFacade facade;

    public EventUseCaseController() {
        facade = new DatabaseFacade();
    }

    protected void handleSetCancelled() {
    }

    public EventViewInterface handleCreateEvent(EventValueContainer event) throws RequiredFieldMissingException, DateException {
        EventDomainInterface eventDomain = EventValueTranslator.translateToDomainInterface(event);
        eventDomain.validate();
        int id = facade.saveEventDuty(eventDomain);
        ((EventDomain) eventDomain).setId(id);
        return eventDomain;
    }

    public List<EventViewInterface> getAllEvents() {
        List<EventViewInterface> events = new LinkedList<>();
        events.addAll(facade.getAllEvents());
        return events;
    }

    public void saveMusicalWorksForEvent(EventViewInterface e) {
        if (e.getMusicalWorks() != null) {
            List<MusicalWorkDomainInterface> mWorks = new LinkedList<>();
            for (MusicalWorkViewInterface mW : e.getMusicalWorks()) {
                mWorks.add(((MusicalWorkDomainInterface) mW));
            }
            facade.saveMusicalWorksForEvent(((EventDomainInterface) e), mWorks);
        }
    }

    public EventViewInterface getEventForId(int id) {
        return facade.getEventForId(id);
    }

    public EventValueContainer getEventInstance(EventViewInterface eventView) {
        return EventValueTranslator.translateToValueContainer(eventView);
    }

    public EventValueContainer getEventInstance(EventType type) {
        return new EventValueContainer(type);
    }

    public List<EventViewInterface> getUnpublishedEventsForMonth(YearMonth month) {
        List<EventViewInterface> view = new LinkedList<>();
        view.addAll(facade.getUnpublishedEventsForMonth(month));
        return view;
    }

    public EventViewInterface handleEditEvent(EventValueContainer values) {
        EventDomainInterface eventDomain = EventValueTranslator.translateToDomainInterface(values);
        facade.saveEventDuty(eventDomain);
        return eventDomain;
    }

    public void publishSchedule(YearMonth month) {
        facade.publishScheduleForMonth(month);
    }

    public boolean checkMusicians(EventValueContainer e) {
        if ((e.getMusicalWorks() == null) || (e.getMusicalWorks().size() == 0)) {
            return true;
        }
        EventViewInterface event = EventValueTranslator.translateToViewInterface(e);
        InstrumentationDomainInterface generalInstrumentationForEvent = (InstrumentationDomainInterface) event.getGeneralInstrumentation();
        e.setGeneralInstrumentation(generalInstrumentationForEvent);
        List<EventDomainInterface> concurrentEvents = getConcurrentEvents(event);
        InstrumentationDomainInterface totalInstrumentation = getTotalInstrumentation(event, concurrentEvents);
        InstrumentationDomainInterface availableInstrumentation = getAvailableInstrumentation();
        return checkResources(totalInstrumentation, availableInstrumentation);
    }

    private List<EventDomainInterface> getConcurrentEvents(EventViewInterface event) {
        List<EventDomainInterface> events = facade.getAllEvents();
        List<EventDomainInterface> concurrentEvents = new LinkedList<>();
        LocalDateTime startTime = LocalDateTime.of(event.getStartDate(), event.getStartTime());
        LocalDateTime endTime = LocalDateTime.of(event.getEndDate(), event.getEndTime());
        for (EventDomainInterface e : events) {
            if (event.getId() != e.getId()) {
                LocalDateTime tempStartTime = LocalDateTime.of(e.getStartDate(), e.getStartTime());
                LocalDateTime tempEndTime = LocalDateTime.of(e.getEndDate(), e.getEndTime());
                if ((tempStartTime.isBefore(startTime) || tempStartTime.isEqual(startTime)) && ((tempEndTime.isAfter(startTime)) || (tempEndTime.isEqual(startTime))) && ((tempEndTime.isBefore(endTime) || (tempEndTime.isEqual(endTime))))) {
                    concurrentEvents.add(e);
                } else if ((tempStartTime.isBefore(endTime) || tempStartTime.isEqual(endTime)) && (tempEndTime.isEqual(endTime) || tempEndTime.isAfter(endTime))) {
                    concurrentEvents.add(e);
                } else if ((tempStartTime.isBefore(startTime) || tempStartTime.isEqual(startTime)) && (tempEndTime.isAfter(endTime) || tempEndTime.isEqual(endTime))) {
                    concurrentEvents.add(e);
                } else if ((tempStartTime.isAfter(startTime) || tempStartTime.isEqual(startTime)) && (tempEndTime.isBefore(endTime) || tempEndTime.isEqual(endTime))) {
                    concurrentEvents.add(e);
                }
            }
        }
        return concurrentEvents;
    }

    private InstrumentationDomainInterface getTotalInstrumentation(EventViewInterface event, List<EventDomainInterface> concurrentEvents) {
        InstrumentationDomainInterface instrumentationDomainInterface = new InstrumentationDomainObject();
        instrumentationDomainInterface.setBrassInstrumentation(new BrassInstrumentationDomainObject());
        instrumentationDomainInterface.setPercussionInstrumentation(new PercussionInstrumentationDomainObject());
        instrumentationDomainInterface.setStringInstrumentation(new StringInstrumentationDomainObject());
        instrumentationDomainInterface.setWoodInstrumentation(new WoodInstrumentationDomainObject());
        if ((event.getGeneralInstrumentation() == null) && (event.getMusicalWorks() != null)) {
            ((EventDomainInterface) event).setGeneralInstrumentation((InstrumentationDomainInterface) event.getGeneralInstrumentation());
        } else if ((event.getEventType().equals(EventType.Rehearsal)) && (event.getRehearsalFor().getMusicalWorks() != null)) {
            ((EventDomainInterface) event).setGeneralInstrumentation((InstrumentationDomainInterface) event.getGeneralInstrumentation());
        }
        if (event.getGeneralInstrumentation() != null) {
            instrumentationDomainInterface.getBrassInstrumentation().setHorn(event.getGeneralInstrumentation().getBrassInstrumentation().getHorn());
            instrumentationDomainInterface.getBrassInstrumentation().setTrombone(event.getGeneralInstrumentation().getBrassInstrumentation().getTrombone());
            instrumentationDomainInterface.getBrassInstrumentation().setTrumpet(event.getGeneralInstrumentation().getBrassInstrumentation().getTrumpet());
            instrumentationDomainInterface.getBrassInstrumentation().setTube(event.getGeneralInstrumentation().getBrassInstrumentation().getTube());
            instrumentationDomainInterface.getPercussionInstrumentation().setHarp(event.getGeneralInstrumentation().getPercussionInstrumentation().getHarp());
            instrumentationDomainInterface.getPercussionInstrumentation().setPercussion(event.getGeneralInstrumentation().getPercussionInstrumentation().getPercussion());
            instrumentationDomainInterface.getPercussionInstrumentation().setKettledrum(event.getGeneralInstrumentation().getPercussionInstrumentation().getKettledrum());
            instrumentationDomainInterface.getStringInstrumentation().setDoublebass(event.getGeneralInstrumentation().getStringInstrumentation().getDoublebass());
            instrumentationDomainInterface.getStringInstrumentation().setViola(event.getGeneralInstrumentation().getStringInstrumentation().getViola());
            instrumentationDomainInterface.getStringInstrumentation().setViolin1(event.getGeneralInstrumentation().getStringInstrumentation().getViolin1());
            instrumentationDomainInterface.getStringInstrumentation().setViolin2(event.getGeneralInstrumentation().getStringInstrumentation().getViolin2());
            instrumentationDomainInterface.getStringInstrumentation().setViolincello(event.getGeneralInstrumentation().getStringInstrumentation().getViolincello());
            instrumentationDomainInterface.getWoodInstrumentation().setBassoon(event.getGeneralInstrumentation().getWoodInstrumentation().getBassoon());
            instrumentationDomainInterface.getWoodInstrumentation().setClarinet(event.getGeneralInstrumentation().getWoodInstrumentation().getClarinet());
            instrumentationDomainInterface.getWoodInstrumentation().setFlute(event.getGeneralInstrumentation().getWoodInstrumentation().getFlute());
            instrumentationDomainInterface.getWoodInstrumentation().setOboe(event.getGeneralInstrumentation().getWoodInstrumentation().getOboe());
        }
        for (EventDomainInterface e : concurrentEvents) {
            if ((e.getGeneralInstrumentation() == null) && (e.getMusicalWorks() != null)) {
                e.setGeneralInstrumentation((InstrumentationDomainInterface) e.getGeneralInstrumentation());
            } else if ((e.getEventType().equals(EventType.Rehearsal)) && (e.getRehearsalFor().getMusicalWorks() != null)) {
                e.setGeneralInstrumentation((InstrumentationDomainInterface) e.getGeneralInstrumentation());
            }
            if (e.getGeneralInstrumentation() != null) {
                instrumentationDomainInterface.getBrassInstrumentation().setHorn(e.getGeneralInstrumentation().getBrassInstrumentation().getHorn() + instrumentationDomainInterface.getBrassInstrumentation().getHorn());
                instrumentationDomainInterface.getBrassInstrumentation().setTrombone(e.getGeneralInstrumentation().getBrassInstrumentation().getTrombone() + instrumentationDomainInterface.getBrassInstrumentation().getTrombone());
                instrumentationDomainInterface.getBrassInstrumentation().setTrumpet(e.getGeneralInstrumentation().getBrassInstrumentation().getTrumpet() + instrumentationDomainInterface.getBrassInstrumentation().getTrumpet());
                instrumentationDomainInterface.getBrassInstrumentation().setTube(e.getGeneralInstrumentation().getBrassInstrumentation().getTube() + instrumentationDomainInterface.getBrassInstrumentation().getTube());
                instrumentationDomainInterface.getPercussionInstrumentation().setHarp(e.getGeneralInstrumentation().getPercussionInstrumentation().getHarp() + instrumentationDomainInterface.getPercussionInstrumentation().getHarp());
                instrumentationDomainInterface.getPercussionInstrumentation().setPercussion(e.getGeneralInstrumentation().getPercussionInstrumentation().getPercussion() + instrumentationDomainInterface.getPercussionInstrumentation().getPercussion());
                instrumentationDomainInterface.getPercussionInstrumentation().setKettledrum(e.getGeneralInstrumentation().getPercussionInstrumentation().getKettledrum() + instrumentationDomainInterface.getPercussionInstrumentation().getKettledrum());
                instrumentationDomainInterface.getStringInstrumentation().setDoublebass(e.getGeneralInstrumentation().getStringInstrumentation().getDoublebass() + instrumentationDomainInterface.getStringInstrumentation().getDoublebass());
                instrumentationDomainInterface.getStringInstrumentation().setViola(e.getGeneralInstrumentation().getStringInstrumentation().getViola() + instrumentationDomainInterface.getStringInstrumentation().getViola());
                instrumentationDomainInterface.getStringInstrumentation().setViolin1(e.getGeneralInstrumentation().getStringInstrumentation().getViolin1() + instrumentationDomainInterface.getStringInstrumentation().getViolin1());
                instrumentationDomainInterface.getStringInstrumentation().setViolin2(e.getGeneralInstrumentation().getStringInstrumentation().getViolin2() + instrumentationDomainInterface.getStringInstrumentation().getViolin2());
                instrumentationDomainInterface.getStringInstrumentation().setViolincello(e.getGeneralInstrumentation().getStringInstrumentation().getViolincello() + instrumentationDomainInterface.getStringInstrumentation().getViolincello());
                instrumentationDomainInterface.getWoodInstrumentation().setBassoon(e.getGeneralInstrumentation().getWoodInstrumentation().getBassoon() + instrumentationDomainInterface.getWoodInstrumentation().getBassoon());
                instrumentationDomainInterface.getWoodInstrumentation().setClarinet(e.getGeneralInstrumentation().getWoodInstrumentation().getClarinet() + instrumentationDomainInterface.getWoodInstrumentation().getClarinet());
                instrumentationDomainInterface.getWoodInstrumentation().setFlute(e.getGeneralInstrumentation().getWoodInstrumentation().getFlute() + instrumentationDomainInterface.getWoodInstrumentation().getFlute());
                instrumentationDomainInterface.getWoodInstrumentation().setOboe(e.getGeneralInstrumentation().getWoodInstrumentation().getOboe() + instrumentationDomainInterface.getWoodInstrumentation().getOboe());
            }
        }
        return instrumentationDomainInterface;
    }

    private InstrumentationDomainInterface getAvailableInstrumentation() {
        List<PersonDomainObject> musicians = facade.getMusicians();
        InstrumentationDomainInterface availableInstrumentation = new InstrumentationDomainObject();
        availableInstrumentation.setBrassInstrumentation(new BrassInstrumentationDomainObject());
        availableInstrumentation.setPercussionInstrumentation(new PercussionInstrumentationDomainObject());
        availableInstrumentation.setStringInstrumentation(new StringInstrumentationDomainObject());
        availableInstrumentation.setWoodInstrumentation(new WoodInstrumentationDomainObject());
        for (PersonDomainObject person : musicians) {
            for (PartDomainObject part : person.getParts()) {
                String partType = part.getPartType().getDescription();
                switch (partType) {
                    case "First Violin":
                        availableInstrumentation.getStringInstrumentation().setViolin1(availableInstrumentation.getStringInstrumentation().getViolin1() + 1);
                        break;
                    case "Second Violin":
                        availableInstrumentation.getStringInstrumentation().setViolin2(availableInstrumentation.getStringInstrumentation().getViolin2() + 1);
                        break;
                    case "Viola":
                        availableInstrumentation.getStringInstrumentation().setViola(availableInstrumentation.getStringInstrumentation().getViola() + 1);
                        break;
                    case "Violoncello":
                        availableInstrumentation.getStringInstrumentation().setViolincello(availableInstrumentation.getStringInstrumentation().getViolincello() + 1);
                        break;
                    case "Double Bass":
                        availableInstrumentation.getStringInstrumentation().setDoublebass(availableInstrumentation.getStringInstrumentation().getDoublebass() + 1);
                        break;
                    case "Flute":
                        availableInstrumentation.getWoodInstrumentation().setFlute(availableInstrumentation.getWoodInstrumentation().getFlute() + 1);
                        break;
                    case "Oboe":
                        availableInstrumentation.getWoodInstrumentation().setOboe(availableInstrumentation.getWoodInstrumentation().getOboe() + 1);
                        break;
                    case "Clarinet":
                        availableInstrumentation.getWoodInstrumentation().setClarinet(availableInstrumentation.getWoodInstrumentation().getClarinet() + 1);
                        break;
                    case "Bassoon":
                        availableInstrumentation.getWoodInstrumentation().setBassoon(availableInstrumentation.getWoodInstrumentation().getBassoon() + 1);
                        break;
                    case "Horn":
                        availableInstrumentation.getBrassInstrumentation().setHorn(availableInstrumentation.getBrassInstrumentation().getHorn() + 1);
                        break;
                    case "Trumpet":
                        availableInstrumentation.getBrassInstrumentation().setTrumpet(availableInstrumentation.getBrassInstrumentation().getTrumpet() + 1);
                        break;
                    case "Trombone":
                        availableInstrumentation.getBrassInstrumentation().setTrombone(availableInstrumentation.getBrassInstrumentation().getTrombone() + 1);
                        break;
                    case "Tuba":
                        availableInstrumentation.getBrassInstrumentation().setTube(availableInstrumentation.getBrassInstrumentation().getTube() + 1);
                        break;
                    case "Harp":
                        availableInstrumentation.getPercussionInstrumentation().setHarp(availableInstrumentation.getPercussionInstrumentation().getHarp() + 1);
                        break;
                    case "Percussion":
                        availableInstrumentation.getPercussionInstrumentation().setPercussion(availableInstrumentation.getPercussionInstrumentation().getPercussion() + 1);
                        break;
                    case "Kettledrum":
                        availableInstrumentation.getPercussionInstrumentation().setKettledrum(availableInstrumentation.getPercussionInstrumentation().getKettledrum() + 1);
                        break;
                }
            }
        }
        return availableInstrumentation;
    }

    private boolean checkResources(InstrumentationDomainInterface totalInstrumentation, InstrumentationDomainInterface availableInstrumentation) {
        if (totalInstrumentation.getBrassInstrumentation().getHorn() > availableInstrumentation.getBrassInstrumentation().getHorn()) {
            return false;
        }
        if (totalInstrumentation.getBrassInstrumentation().getTrombone() > availableInstrumentation.getBrassInstrumentation().getTrombone()) {
            return false;
        }
        if (totalInstrumentation.getBrassInstrumentation().getTrumpet() > availableInstrumentation.getBrassInstrumentation().getTrumpet()) {
            return false;
        }
        if (totalInstrumentation.getBrassInstrumentation().getTube() > availableInstrumentation.getBrassInstrumentation().getTube()) {
            return false;
        }
        if (totalInstrumentation.getPercussionInstrumentation().getHarp() > availableInstrumentation.getPercussionInstrumentation().getHarp()) {
            return false;
        }
        if (totalInstrumentation.getPercussionInstrumentation().getPercussion() > availableInstrumentation.getPercussionInstrumentation().getPercussion()) {
            return false;
        }
        if (totalInstrumentation.getPercussionInstrumentation().getKettledrum() > availableInstrumentation.getPercussionInstrumentation().getKettledrum()) {
            return false;
        }
        if (totalInstrumentation.getWoodInstrumentation().getBassoon() > availableInstrumentation.getWoodInstrumentation().getBassoon()) {
            return false;
        }
        if (totalInstrumentation.getWoodInstrumentation().getClarinet() > availableInstrumentation.getWoodInstrumentation().getClarinet()) {
            return false;
        }
        if (totalInstrumentation.getWoodInstrumentation().getFlute() > availableInstrumentation.getWoodInstrumentation().getFlute()) {
            return false;
        }
        if (totalInstrumentation.getWoodInstrumentation().getOboe() > availableInstrumentation.getWoodInstrumentation().getOboe()) {
            return false;
        }
        if (totalInstrumentation.getStringInstrumentation().getViolincello() > availableInstrumentation.getStringInstrumentation().getViolincello()) {
            return false;
        }
        if (totalInstrumentation.getStringInstrumentation().getDoublebass() > availableInstrumentation.getStringInstrumentation().getDoublebass()) {
            return false;
        }
        if (totalInstrumentation.getStringInstrumentation().getViola() > availableInstrumentation.getStringInstrumentation().getViola()) {
            return false;
        }
        if (totalInstrumentation.getStringInstrumentation().getViolin1() > availableInstrumentation.getStringInstrumentation().getViolin1()) {
            return false;
        }
        if (totalInstrumentation.getStringInstrumentation().getViolin2() > availableInstrumentation.getStringInstrumentation().getViolin2()) {
            return false;
        }
        return true;
    }
}


