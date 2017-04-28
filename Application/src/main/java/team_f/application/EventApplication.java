package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.EventFacade;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.entities.Person;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import team_f.domain.enums.InstrumentType;
import team_f.domain.interfaces.DomainEntity;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.EventDutyLogic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EventApplication {

    public EventApplication(){}

    private EventFacade eventFacade = new EventFacade();
    private PersonApplication personApplication = new PersonApplication();

    public void closeSession() {
        eventFacade.closeSession();
    }

    public Pair<DomainEntity, List<Pair<String, String>>> addEvent(int id, String name, String description, String location, LocalDateTime startTime,
                                                                   LocalDateTime endTime, String conductor, EventType type, int rehearsalForId,
                                                                   Double standardPoints, int instrumentationId, int[] musicalWorkIdList,
                                                                   int[] alternativeInstrumentationIdList) {

        // transform the parameters in an domain object
        EventDuty eventDuty = new EventDuty();
        eventDuty.setEventDutyId(id);
        eventDuty.setName(name);
        eventDuty.setDescription(description);
        eventDuty.setLocation(location);
        eventDuty.setStartTime(startTime);
        eventDuty.setEndTime(endTime);
        eventDuty.setConductor(conductor);
        eventDuty.setEventType(type);
        eventDuty.setEventStatus(EventStatus.Unpublished);

        // load the data from the DB
        if(rehearsalForId >= 0) {
            EventDuty rehearsalFor = eventFacade.getEventById(rehearsalForId);
            eventDuty.setRehearsalFor(rehearsalFor);
        }

        if(instrumentationId > 0) {
            Instrumentation instrumentation = eventFacade.getInstrumentationByID(instrumentationId);
            eventDuty.setInstrumentation(instrumentation);
        }

        eventDuty.setDefaultPoints(standardPoints);

        Instrumentation instrumentation = new Instrumentation();
        instrumentation.setInstrumentationID(instrumentationId);
        eventDuty.setInstrumentation(instrumentation);

        MusicalWork tmpMusicalWork;
        Instrumentation tmpInstrumentation;

        if(musicalWorkIdList != null && alternativeInstrumentationIdList != null) {
            for(int i = 0; i < musicalWorkIdList.length && i < alternativeInstrumentationIdList.length; i++) {
                tmpMusicalWork = new MusicalWork();
                tmpMusicalWork.setMusicalWorkID(musicalWorkIdList[i]);          // @TODO CHANGE BACK TO VALUES
                tmpMusicalWork.setInstrumentationID(1);

                tmpInstrumentation = new Instrumentation();
                tmpInstrumentation.setInstrumentationID(1);

                eventDuty.addMusicalWork(tmpMusicalWork, tmpInstrumentation);
            }
        }

        // check for errors
        EventDutyLogic eventDutyLogic = (EventDutyLogic) DomainEntityManager.getLogic(EntityType.EVENT_DUTY);
        List<Pair<String, String>> errorList = eventDutyLogic.validate(eventDuty);

        // return the errorList when the validation is not successful
        if(errorList.size() > 0) {
            return new Pair<>(eventDuty, errorList);
        }

        List<MusicalWork> eventMusicalList = new ArrayList<>();

        for (MusicalWork musicalWork : eventDuty.getMusicalWorkList()) {
            MusicalWork mE = new MusicalWork();
            mE.setComposer(musicalWork.getComposer());
            mE.setInstrumentationID(musicalWork.getInstrumentationID());
            mE.setName(musicalWork.getName());
            eventMusicalList.add(mE);
        }

        // @TODO: Musicalwork Instrumentation save to correct musicalWork from Event

        eventFacade.addEvent(eventDuty);

        return new Pair<>(eventDuty, new LinkedList<>());
    }

    public EventDuty getEventById(int id) {
        EventDuty entity = eventFacade.getEventById(id);

        if(entity != null) {
            return entity;
        }

        return null;
    }

    public List<EventDuty> getEventsByMonth(int month, int year) {
        return eventFacade.getEventsByMonth(month, year);
    }

    public List<MusicalWork> getMusicalWorkList() {
        List<MusicalWork> musicalWork = eventFacade.getMusicalWorks();
        List<MusicalWork> musicalWorks = new ArrayList<>(musicalWork.size());

        for(MusicalWork item : musicalWork) {
            musicalWorks.add(item);
        }

        return musicalWorks;
    }

    public List<Instrumentation> getInstrumentationList() {
        return eventFacade.getInstrumentations();
    }

    public List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        List<Pair<MusicalWork, Instrumentation>> list = new LinkedList<>();

        // @TODO: this method will be useful when we have to map a musicalWork to an alternativeInstrumenation in the view

        return list;
    }

    //TODO: Get list of Event for one day
    public void getDateEventList(){
    }

    public List<Pair<String, String>> evaluateMusicianCountForEvent (LocalDateTime start, LocalDateTime end) {
        List<Pair<String, String>> errorList = new LinkedList<>();
        List<EventDuty> eventList = new LinkedList<>();

        eventList = eventFacade.getEventsByTimeFrame(start, end);

        List<Pair<InstrumentType, List<Person>>> list = personApplication.getMusicianListByPlayedInstrument(personApplication.getAllMusicians());

        for (InstrumentType instrumentType : InstrumentType.values()) {
            // @TODO CHECK INSTRUMENTATION OF EVENT / SUM OF MUCICIANCOUNT FOR TIMEFRAME
        }

        return errorList;
    }
}

