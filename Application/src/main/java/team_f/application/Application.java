package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.database.EventDutyEntity;
import team_f.database_wrapper.database.EventDutyMusicalWorkEntity;
import team_f.database_wrapper.database.InstrumentationEntity;
import team_f.database_wrapper.database.MusicalWorkEntity;
import team_f.database_wrapper.entities.EventStatus;
import team_f.database_wrapper.entities.EventType;
import team_f.database_wrapper.facade.Facade;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.EventDutyProperty;
import team_f.domain.interfaces.DomainEntityProperty;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.EventDutyLogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Application {

    public Application (){}

    Facade facade = new Facade();

    public List<Pair<String, String>> addEvent(EventDuty eventDuty) {
        EventDutyLogic eventDutyLogic = (EventDutyLogic) DomainEntityManager.getLogic(EntityType.EVENT_DUTY);
        List<Pair<String, String>> errorList = eventDutyLogic.validate(eventDuty);

        // return the errorList when the validation is not successful
        if(errorList.size() > 0) {
            return errorList;
        }

        EventDutyEntity event = new EventDutyEntity();

        event.setEventDutyId(eventDuty.getEventDutyId());
        event.setName(eventDuty.getName());
        event.setDescription(eventDuty.getDescription());
        event.setStarttime(eventDuty.getStarttime());
        event.setEndtime(eventDuty.getEndtime());

        event.setEventType(EventType.valueOf(eventDuty.getEventType()));
        event.setEventStatus(EventStatus.valueOf(eventDuty.getEventStatus()));

        event.setConductor(eventDuty.getConductor());
        event.setLocation(eventDuty.getLocation());
        event.setDefaultPoints(eventDuty.getDefaultPoints());
        event.setInstrumentation(eventDuty.getInstrumentation());
        event.setRehearsalFor(eventDuty.getRehearsalFor());

        // @TODO: musical works have to be save in this stage

        facade.addEvent(event);

        return new LinkedList<>();
    }

    public EventDuty getEventById(int id) {
        EventDutyEntity entity = facade.getEventById(id);

        if(entity != null) {
            return transformEventDutyEntity(entity);
        }

        return null;
    }

    public List<EventDuty> getEvents(int month, int year) {
        List<EventDutyEntity> entities = facade.getEvents(month, year);
        List<EventDuty> eventDuties = new ArrayList<>(entities.size());
        EventDuty event;

        for(EventDutyEntity eventDuty : entities) {
            event = transformEventDutyEntity(eventDuty);
            eventDuties.add(event);
        }

        return eventDuties;
    }

    private EventDuty transformEventDutyEntity(EventDutyEntity eventDutyEntity) {
        EventDuty event = new EventDuty();
        event.setConductor(eventDutyEntity.getConductor());
        event.setDefaultPoints(eventDutyEntity.getDefaultPoints());
        event.setDescription(eventDutyEntity.getDescription());
        event.setEndtime(eventDutyEntity.getEndtime());
        event.setStarttime(eventDutyEntity.getStarttime());
        event.setEventDutyId(eventDutyEntity.getEventDutyId());
        event.setRehearsalFor(eventDutyEntity.getRehearsalFor());
        event.setLocation(eventDutyEntity.getLocation());
        event.setInstrumentation(eventDutyEntity.getInstrumentation());
        event.setName(eventDutyEntity.getName());

        event.setEventType(String.valueOf(eventDutyEntity.getEventType()));
        event.setEventStatus(String.valueOf(eventDutyEntity.getEventStatus()));
        event.setEventStatus(String.valueOf(eventDutyEntity.getEventStatus()));

        MusicalWorkEntity musicalWorkEntity;
        MusicalWork musicalWork;
        InstrumentationEntity instrumentationEntity;
        Instrumentation instrumentation;

        Collection<EventDutyMusicalWorkEntity> eventDutyMusicalWorkEntityList = eventDutyEntity.getEventDutyMusicalWorksByEventDutyId(); //=  facade.getMusicalWorksByEventId(int eventId);

        if(eventDutyMusicalWorkEntityList != null) {
            for (EventDutyMusicalWorkEntity item : eventDutyEntity.getEventDutyMusicalWorksByEventDutyId()) {
                musicalWorkEntity = item.getMusicalWorkByMusicalWork();
                instrumentationEntity = item.getInstrumentationByAlternativeInstrumentation();

                musicalWork = null;

                if (musicalWorkEntity != null) {
                    musicalWork = transformMusicalWorkEntity(musicalWorkEntity);
                }

                instrumentation = null;

                if (instrumentationEntity != null) {
                    instrumentation = transformInstrumenationEntity(instrumentationEntity);
                }

                event.addMusicalWork(musicalWork, instrumentation);
            }
        }

        return event;
    }

    private MusicalWork transformMusicalWorkEntity(MusicalWorkEntity musicalWorkEntity) {
        MusicalWork musicalWork = new MusicalWork();
        musicalWork.setComposer(musicalWorkEntity.getComposer());
        musicalWork.setInstrumentationID(musicalWorkEntity.getInstrumentationId());
        musicalWork.setMusicalWorkID(musicalWorkEntity.getMusicalWorkId());
        musicalWork.setName(musicalWorkEntity.getName());

        return musicalWork;
    }

    private Instrumentation transformInstrumenationEntity(InstrumentationEntity instrumentationEntity) {
        Instrumentation instrumentation = new Instrumentation();
        // @TODO: use Instrumenation Objects instead of ids and check in the validation the id, ...
        //instrumentation.setBassoon(instrumentationEntity.getBra);
        //instrumentation.setClarinet();
        //instrumentation.setDoublebass();
        //instrumentation.setFlute();
        //instrumentation.setHarp();
        //instrumentation.setHorn();
        instrumentation.setInstrumentationID(instrumentationEntity.getInstrumentationId());
        //instrumentation.setKettledrum(instrumentationEntity.get);
        /*instrumentation.setOboe();
        instrumentation.setPercussion();
        instrumentation.setTrombone();
        instrumentation.setTrumpet();
        instrumentation.setTube();
        instrumentation.setViola();
        instrumentation.setViolin1();
        instrumentation.setViolin2();
        instrumentation.setViolincello();*/

        return instrumentation;
    }
}

