package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.database.InstrumentationEntity;
import team_f.database_wrapper.facade.Facade;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import team_f.domain.interfaces.DomainEntity;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.EventDutyLogic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Application {

    public Application (){}

    private Facade facade = new Facade();

    public void closeSession() {
        facade.closeSession();
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
            EventDuty rehearsalFor = facade.getEventById(rehearsalForId);
            eventDuty.setRehearsalFor(rehearsalFor);
        }

        if(instrumentationId > 0) {
            Instrumentation instrumentation = facade.getInstrumentationById(instrumentationId);
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
                tmpMusicalWork.setMusicalWorkID(musicalWorkIdList[i]);

                tmpInstrumentation = new Instrumentation();
                tmpInstrumentation.setInstrumentationID(alternativeInstrumentationIdList[i]);

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

        facade.addEvent(eventDuty, eventMusicalList);

        return new Pair<>(eventDuty, new LinkedList<>());
    }

    public EventDuty getEventById(int id) {
        EventDuty entity = facade.getEventById(id);

        if(entity != null) {
            return entity;
        }

        return null;
    }

    public List<EventDuty> getEventsByMonth(int month, int year) {
        return facade.getEventsByMonth(month, year);
    }

    public List<MusicalWork> getMusicalWorkList() {
        List<MusicalWork> musicalWork = facade.getMusicalWorks();
        List<MusicalWork> musicalWorks = new ArrayList<>(musicalWork.size());

        for(MusicalWork item : musicalWork) {
            musicalWorks.add(item);
        }

        return musicalWorks;
    }

    public List<Instrumentation> getInstrumentationList() {
        List<InstrumentationEntity> instrumentationEntities = facade.getInstrumentations();
        List<Instrumentation> instrumentations = new ArrayList<>(instrumentationEntities.size());
        Instrumentation instrumentation;

        for(InstrumentationEntity item : instrumentationEntities) {
            instrumentation = facade.convertToInstrumentation(item.getInstrumentationId());
            instrumentations.add(instrumentation);
        }

        return instrumentations;
    }

    public List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        List<Pair<MusicalWork, Instrumentation>> list = new LinkedList<>();

        // @TODO: this method will be useful when we have to map a musicalWork to an alternativeInstrumenation in the view

        return list;
    }

    /*
    private EventDuty transformEventDutyEntity(EventDutyEntity eventDutyEntity) {
        EventDuty event = null;
        Integer tmp;
        Instrumentation instrumentation;

        if(eventDutyEntity != null) {
            event = new EventDuty();
            event.setConductor(eventDutyEntity.getConductor());
            event.setDefaultPoints(eventDutyEntity.getDefaultPoints());
            event.setDescription(eventDutyEntity.getDescription());
            event.setEndTime(eventDutyEntity.getEndtime());
            event.setStartTime(eventDutyEntity.getStarttime());
            event.setEventDutyId(eventDutyEntity.getEventDutyId());

            // load the data from the DB
            tmp = eventDutyEntity.getRehearsalFor();

            if(tmp != null) {
                EventDuty rehearsalFor = transformEventDutyEntity(facade.getEventById(tmp));
                event.setRehearsalFor(rehearsalFor);
            }

            event.setLocation(eventDutyEntity.getLocation());

            // load the data from the DB
            tmp = eventDutyEntity.getInstrumentation();

            if(tmp != null) {
                instrumentation = transformInstrumentationEntity(facade.getInstrumentationById(tmp));
                event.setInstrumentation(instrumentation);
            }

            event.setName(eventDutyEntity.getName());

            event.setEventType(EventType.valueOf(String.valueOf(eventDutyEntity.getEventType())));
            event.setEventStatus(EventStatus.valueOf(String.valueOf(eventDutyEntity.getEventStatus())));

            MusicalWorkEntity musicalWorkEntity;
            MusicalWork musicalWork;
            InstrumentationEntity instrumentationEntity;
            Collection<EventDutyMusicalWorkEntity> eventDutyMusicalWorkEntityList = eventDutyEntity.getEventDutyMusicalWorksByEventDutyId();

            if (eventDutyMusicalWorkEntityList != null) {
                for (EventDutyMusicalWorkEntity item : eventDutyEntity.getEventDutyMusicalWorksByEventDutyId()) {
                    musicalWorkEntity = item.getMusicalWorkByMusicalWork();
                    instrumentationEntity = item.getInstrumentationByAlternativeInstrumentation();

                    musicalWork = null;

                    if (musicalWorkEntity != null) {
                        musicalWork = transformMusicalWorkEntity(musicalWorkEntity);
                    }

                    instrumentation = null;

                    if (instrumentationEntity != null) {
                        instrumentation = transformInstrumentationEntity(instrumentationEntity);
                    }

                    event.addMusicalWork(musicalWork, instrumentation);
                }
            }
        }

        return event;
    }
    */

    /*
    private MusicalWork transformMusicalWorkEntity(MusicalWorkEntity musicalWorkEntity) {
        MusicalWork musicalWork = null;

        if(musicalWorkEntity != null) {
            musicalWork = new MusicalWork();
            musicalWork.setComposer(musicalWorkEntity.getComposer());
            musicalWork.setInstrumentationID(musicalWorkEntity.getInstrumentationId());
            musicalWork.setMusicalWorkID(musicalWorkEntity.getMusicalWorkId());
            musicalWork.setName(musicalWorkEntity.getName());
        }

        return musicalWork;
    }
    */

    /*
    private Instrumentation transformInstrumentationEntity(InstrumentationEntity instrumentationEntity) {
        Instrumentation instrumentation = null;

        if(instrumentationEntity != null) {
            instrumentation = new Instrumentation();

            instrumentation.setInstrumentationID(instrumentationEntity.getInstrumentationId());

            // @TODO: use Instrumentation Objects instead of ids and check in the validation the id, ...
            BrassInstrumentationEntity brassInstrumentationEntity = instrumentationEntity.getBrassInstrumentationByBrassInstrumentation();
            PercussionInstrumentationEntity percussionInstrumentationEntity = instrumentationEntity.getPercussionInstrumentationByPercussionInstrumentation();
            StringInstrumentationEntity stringInstrumentationEntity = instrumentationEntity.getStringInstrumentationByStringInstrumentation();
            WoodInstrumentationEntity woodInstrumentationEntity = instrumentationEntity.getWoodInstrumentationByWoodInstrumentation();

            instrumentation.setHorn(brassInstrumentationEntity.getHorn());
            instrumentation.setTrombone(brassInstrumentationEntity.getTrombone());
            instrumentation.setTrumpet(brassInstrumentationEntity.getTrumpet());
            instrumentation.setTube(brassInstrumentationEntity.getTube());

            instrumentation.setPercussion(percussionInstrumentationEntity.getPercussion());
            instrumentation.setKettledrum(percussionInstrumentationEntity.getKettledrum());
            instrumentation.setHarp(percussionInstrumentationEntity.getHarp());

            instrumentation.setViola(stringInstrumentationEntity.getViola());
            instrumentation.setViolin1(stringInstrumentationEntity.getViolin1());
            instrumentation.setViolin2(stringInstrumentationEntity.getViolin2());
            instrumentation.setViolincello(stringInstrumentationEntity.getViolincello());
            instrumentation.setDoublebass(stringInstrumentationEntity.getDoublebass());

            instrumentation.setBassoon(woodInstrumentationEntity.getBassoon());
            instrumentation.setClarinet(woodInstrumentationEntity.getClarinet());
            instrumentation.setOboe(woodInstrumentationEntity.getOboe());
            instrumentation.setFlute(woodInstrumentationEntity.getFlute());
        }

        return instrumentation;
    }
    */
}

