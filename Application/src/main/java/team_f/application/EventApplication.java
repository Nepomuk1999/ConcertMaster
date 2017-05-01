package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.EventFacade;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.entities.Person;
import team_f.domain.enums.*;
import team_f.domain.helper.DateTimeHelper;
import team_f.domain.interfaces.DomainEntity;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.EventDutyLogic;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static team_f.domain.enums.EventDutyProperty.START_DATE;

public class EventApplication {

    public EventApplication() {
    }

    private EventFacade eventFacade = new EventFacade();
    private PersonApplication personApplication = new PersonApplication();

    public void closeSession() {
        eventFacade.closeSession();
    }

    public Pair<DomainEntity, List<Pair<String, String>>> addEvent(int id, String name, String description, String location, LocalDateTime startTime,
                                                                   LocalDateTime endTime, String conductor, EventType type, int rehearsalForId,
                                                                   Double standardPoints, int instrumentationId, int[] musicalWorkIdList,
                                                                   int[] alternativeInstrumentationIdList) {

        // transform the parameters in a domain object
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
        if (rehearsalForId >= 0) {
            EventDuty rehearsalFor = eventFacade.getEventById(rehearsalForId);
            eventDuty.setRehearsalFor(rehearsalFor);
        }

        if (instrumentationId > 0) {
            Instrumentation instrumentation = eventFacade.getInstrumentationByID(instrumentationId);
            eventDuty.setInstrumentation(instrumentation);
        }

        eventDuty.setDefaultPoints(standardPoints);

        Instrumentation instrumentation = new Instrumentation();
        instrumentation.setInstrumentationID(instrumentationId);
        eventDuty.setInstrumentation(instrumentation);

        MusicalWork tmpMusicalWork;

        if (musicalWorkIdList != null && alternativeInstrumentationIdList != null) {
            for (int i = 0; i < musicalWorkIdList.length && i < alternativeInstrumentationIdList.length; i++) {
                tmpMusicalWork = new MusicalWork();
                tmpMusicalWork.setMusicalWorkID(musicalWorkIdList[i]);

                // set the alternative instrumentation even when it's the same as in the musical work
                tmpMusicalWork.setAlternativeInstrumentationId(alternativeInstrumentationIdList[i]);

                eventDuty.addMusicalWork(tmpMusicalWork, null);
            }
        }

        // check for errors
        EventDutyLogic eventDutyLogic = (EventDutyLogic) DomainEntityManager.getLogic(EntityType.EVENT_DUTY);
        List<Pair<String, String>> errorList = eventDutyLogic.validate(eventDuty);

        if (!DateTimeHelper.takesPlaceInFuture(eventDuty.getStartTime())) {
            errorList.add(new Pair<>(String.valueOf(START_DATE), "cannot be modified"));
        }

        //errorList.addAll(evaluateMusicianCountForEvent(eventDuty));

        // return the errorList when the validation is not successful
        if (errorList.size() > 0) {
            return new Pair<>(eventDuty, errorList);
        }

        List<MusicalWork> eventMusicalList = new ArrayList<>();

        for (MusicalWork musicalWork : eventDuty.getMusicalWorkList()) {
            MusicalWork mE = new MusicalWork();
            mE.setComposer(musicalWork.getComposer());
            // do not overwrite the predefined instrumentationId: use the intermediate table instead
            //mE.setInstrumentationID(musicalWork.getInstrumentationID());
            mE.setAlternativeInstrumentationId(musicalWork.getAlternativeInstrumentationId());

            mE.setName(musicalWork.getName());
            eventMusicalList.add(mE);
        }

        eventFacade.addEvent(eventDuty);

        return new Pair<>(eventDuty, new LinkedList<>());
    }

    public EventDuty getEventById(int id) {
        EventDuty entity = eventFacade.getEventById(id);

        if (entity != null) {
            return entity;
        }

        return null;
    }

    public List<EventDuty> getEventsByMonth(int month, int year) {
        return eventFacade.getEventsByMonth(month, year);
    }

    public List<MusicalWork> getMusicalWorkList() {
        return eventFacade.getMusicalWorks();
    }

    public List<Instrumentation> getInstrumentationList() {
        return eventFacade.getInstrumentations();
    }

    public List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        List<Pair<MusicalWork, Instrumentation>> list = new LinkedList<>();

        // @TODO: this method will be useful when we have to map a musicalWork to an alternativeInstrumenation in the view

        return list;
    }

    public List<EventDuty> getDateEventList(LocalDateTime dateTime) {
        return eventFacade.getEventsByDay(dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear());
    }

    public List<Pair<String, String>> evaluateMusicianCountForEvent(EventDuty eventDuty) {
        List<Pair<String, String>> errorList = new LinkedList<>();
        List<EventDuty> eventList = new LinkedList<>();

        eventList = eventFacade.getEventsByTimeFrame(eventDuty.getStartTime(), eventDuty.getEndTime());

        List<Pair<InstrumentType, List<Person>>> list = personApplication.getMusicianListByPlayedInstrument(personApplication.getAllMusicians());
        Instrumentation totalInstrumentation = eventDuty.getInstrumentation();

        for (InstrumentType instrumentType : InstrumentType.values()) {
            for (EventDuty event : eventList) {
                event.calculateMaxInstrumentation();
                totalInstrumentation.addToInstrumentations(event.getMaxInstrumetation());
            }

            Pair<InstrumentType, List<Person>> pairList = getMusicianListByInstrumentType(instrumentType, list);

            if (totalInstrumentation.getByInstrumentType(instrumentType) > pairList.getValue().size()) {
                errorList.add(new Pair<>(String.valueOf(EventDutyProperty.START_DATE), "not enough musicians for section " + instrumentType + " at the given timeframe"));
            }
        }

        return errorList;
    }

    public Pair<InstrumentType, List<Person>> getMusicianListByInstrumentType(InstrumentType instrumentType, List<Pair<InstrumentType, List<Person>>> list) {
        Pair<InstrumentType, List<Person>> pair = null;

        for (Pair<InstrumentType, List<Person>> exPair : list) {
            if (exPair.getKey().equals(instrumentType)) {
                pair = exPair;
            }
        }

        return pair;
    }

    public List<Pair<String, String>> getEvenetsByFrame(EventDuty eventDuty) {
        List<EventDuty> eventDutyList = eventFacade.getEventsByTimeFrame(eventDuty.getStartTime(), eventDuty.getEndTime());
        List<EventDuty> rehearsalList = new ArrayList<>();
        List<Pair<String, String>> errorList = new LinkedList<>();

        for (EventDuty event : eventDutyList) {
            if (event.getRehearsalFor() != null) {
                rehearsalList.add(event);
            }
        }
        if (rehearsalList.size() > 0) {
            for (EventDuty rehearsal : rehearsalList) {
                if (eventDuty.getRehearsalFor().getEventDutyId() == rehearsal.getEventDutyId()) {
                    errorList.add(new Pair<>(String.valueOf(EventDutyProperty.REHEARSAL_FOR), "you have already a rehearsal for this event"));
                }
            }

        }
        return errorList;
    }

    public boolean publishEventsByMonth(int month, int year) {
        try {
            List<EventDuty> events = eventFacade.getEventsByMonth(month, year);

            for (EventDuty event : events) {
                event.setEventStatus(EventStatus.Published);
                eventFacade.addEvent(event);
            }

            return true;
        }catch (Exception exp){
        }

        return false;
    }

    public boolean unpublishEventsByMonth(int month, int year) {
        try {
            List<EventDuty> events = eventFacade.getEventsByMonth(month, year);

            for (EventDuty event : events) {
                event.setEventStatus(EventStatus.Unpublished);
                eventFacade.addEvent(event);
            }

            return true;
        }catch (Exception exp){
        }

        return false;
    }
}
