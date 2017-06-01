package team_f.application;

import javafx.util.Pair;
import team_f.application.interfaces.BaseApplicationFacade;
import team_f.database_wrapper.facade.EventFacade;
import team_f.database_wrapper.facade.InstrumentationFacade;
import team_f.database_wrapper.facade.MusicalWorkFacade;
import team_f.database_wrapper.facade.SessionFactory;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.entities.Person;
import team_f.domain.enums.*;
import team_f.domain.enums.properties.EventDutyProperty;
import team_f.domain.helper.DateTimeHelper;
import team_f.domain.interfaces.DomainEntity;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.EventDutyLogic;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static team_f.domain.enums.properties.EventDutyProperty.START_DATE;

public class EventApplication extends BaseApplicationFacade<EventDuty> {
    private static EventApplication _instance;
    private EntityManager session = SessionFactory.getSession();
    private EventFacade eventFacade = new EventFacade(session);
    private InstrumentationFacade instrumentationFacade = new InstrumentationFacade(session);
    private MusicalWorkFacade musicalWorkFacade = new MusicalWorkFacade(session);
    private PersonApplication personApplication = PersonApplication.getInstance();

    private EventApplication() { }

    public static EventApplication getInstance() {
        if(_instance == null) {
            _instance = new EventApplication();
        }

        return _instance;
    }

    @Override
    public void closeSession() {
        eventFacade.closeSession();
    }

    /** Function creates a domain entity EventDuty object and sets its values
     * checks if Event is a rehearsal for another Event and sets with with its Id
     * sets the instrumentation of the EventDuty
     * sets the points
     * sets alternative musicalWorks to the eventDuty
     * returns a errorList using EventDutyLogic when errors occur
     * sets the eventMusicalList with the composers, alternativeInstrumentations and names
     * saves the eventDuty to the Database and sets the eventDutyID from the DB
     * returns the domain entity eventDuty object and an empty list
     *
     * @param id
     * @param name
     * @param description
     * @param location
     * @param startTime
     * @param endTime
     * @param conductor
     * @param type
     * @param rehearsalForId
     * @param standardPoints
     * @param instrumentationId
     * @param musicalWorkIdList
     * @param alternativeInstrumentationIdList
     * @return  Pair<>(eventDuty, errorList) if errors occurred    or    Pair<>(eventDuty, new LinkedList<>())
     */
    public Pair<DomainEntity, List<Pair<String, String>>> addEvent(int id, String name, String description, String location, LocalDateTime startTime,
                                                                   LocalDateTime endTime, String conductor, EventType type, int rehearsalForId,
                                                                   Double standardPoints, int instrumentationId, int[] musicalWorkIdList,
                                                                   int[] alternativeInstrumentationIdList) {

        // transform the parameters in a domain object
        EventDuty eventDuty = new EventDuty();
        eventDuty.setEventDutyID(id);
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
            Instrumentation instrumentation = instrumentationFacade.getInstrumentationByID(instrumentationId);
            eventDuty.setInstrumentation(instrumentation);
        }

        eventDuty.setDefaultPoints(standardPoints);

        Instrumentation instrumentation = new Instrumentation();
        instrumentation.setInstrumentationID(instrumentationId);
        eventDuty.setInstrumentation(instrumentation);


        if (musicalWorkIdList != null && alternativeInstrumentationIdList != null) {
            for (int i = 0; i < musicalWorkIdList.length && i < alternativeInstrumentationIdList.length; i++) {
                MusicalWork tmpMusicalWork = new MusicalWork();
                tmpMusicalWork.setMusicalWorkID(musicalWorkIdList[i]);

                // set the alternative instrumentation even when it's the same as in the musical work
                tmpMusicalWork.setAlternativeInstrumentation(instrumentationFacade.getInstrumentationByID(alternativeInstrumentationIdList[i]));

                eventDuty.addMusicalWork(tmpMusicalWork, null);
            }
        }

        // check for errors
        EventDutyLogic eventDutyLogic = (EventDutyLogic) DomainEntityManager.getLogic(EntityType.EVENT_DUTY);
        List<Pair<String, String>> errorList = eventDutyLogic.validate(eventDuty);

        if (!DateTimeHelper.takesPlaceInFuture(eventDuty.getStartTime())) {
            errorList.add(new Pair<>(String.valueOf(START_DATE), "cannot be modified"));
        }

        if(type.equals(EventType.NonMusicalEvent)){
            errorList.addAll(evaluateMusicianCountForEvent(eventDuty));
        }

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
            mE.setAlternativeInstrumentation(musicalWork.getAlternativeInstrumentation());

            mE.setName(musicalWork.getName());
            eventMusicalList.add(mE);
        }

        Integer resultID = eventFacade.add(eventDuty);
        eventDuty.setEventDutyID(resultID);

        return new Pair<>(eventDuty, new LinkedList<>());
    }

    public List<EventDuty> getEventsByMonth(int month, int year) {
        return eventFacade.getEventsByMonth(month, year);
    }

    public List<EventDuty> getEventsByTimeFrame(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventFacade.getEventsByTimeFrame(startDateTime, endDateTime);
    }

    public List<MusicalWork> getMusicalWorkList() {
        return musicalWorkFacade.getMusicalWorks();
    }

    public List<Pair<MusicalWork, Instrumentation>> getMusicalWorkInstrumentationList() {
        List<Pair<MusicalWork, Instrumentation>> list = new LinkedList<>();

        // @TODO: this method will be useful when we have to map a musicalWork to an alternativeInstrumenation in the view

        return list;
    }

    public List<EventDuty> getDateEventList(LocalDateTime dateTime) {
        return eventFacade.getEventsByDay(dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear());
    }

    /** Function returns errorList which's entries show if and for what instrumentType there are not enough musicians
     *
     * @param eventDuty
     * @return  errorList
     */
    protected List<Pair<String, String>> evaluateMusicianCountForEvent(EventDuty eventDuty) {
        List<Pair<String, String>> errorList = new LinkedList<>();
        List<EventDuty> eventList;

        eventList = eventFacade.getEventsByTimeFrame(eventDuty.getStartTime(), eventDuty.getEndTime());

        calculateMaxInstrumentation(eventDuty);

        List<Pair<InstrumentType, List<Person>>> list = personApplication.getMusicianListByPlayedInstrumentType(personApplication.getList());
        Instrumentation totalInstrumentation = eventDuty.getMaxInstrumentation();

        for (EventDuty event : eventList) {
                calculateMaxInstrumentation(event);
                totalInstrumentation.addToInstrumentations(event.getMaxInstrumentation());
        }

        for (InstrumentType instrumentType : InstrumentType.values()) {
            Pair<InstrumentType, List<Person>> pairList = getMusicianListByInstrumentType(instrumentType, list);

            if (totalInstrumentation.getByInstrumentType(instrumentType) > pairList.getValue().size()) {
                errorList.add(new Pair<>(String.valueOf(EventDutyProperty.START_DATE), "not enough musicians for section " + instrumentType + " at the given timeframe"));
            }
        }

        return errorList;
    }

    /** Function to calculate the maximum number of musicians needed for an event. First sets the required number of
     *      instruments to 0 for each insturmentType. For the instrumentationList of the eventDuty checks if more than 0
     *          instruments of the instrumentType are required and sets that number into the maxInstrumentation of the
     *          eventDuty.
     *
     * @param eventDuty
     */
    protected void calculateMaxInstrumentation(EventDuty eventDuty) {

        Instrumentation maxInstrumentation = new Instrumentation();
        for (InstrumentType instrumentType : InstrumentType.values()) {
            maxInstrumentation.setByInstrumentType(instrumentType, 0);
        }

        for (Instrumentation instrumentation : eventDuty.getInstrumentationList()) {
            for (InstrumentType instrumentType: InstrumentType.values()) {
                if (maxInstrumentation.getByInstrumentType(instrumentType) < instrumentation.getByInstrumentType(instrumentType)) {
                    maxInstrumentation.setByInstrumentType(instrumentType, instrumentation.getByInstrumentType(instrumentType));
                }
            }
        }

        eventDuty.setMaxInstrumentation(maxInstrumentation);
    }

    /**  Function to be used in the evaluateMusicianCountForEvent-Method of EventApplication.
     * @param instrumentType
     * @param list          a list of pairs of the instrumentTypes and lists of persons playing them
     * @return  pair        list of pairs of instrumentTypes and the persons playing them
     */
    protected Pair<InstrumentType, List<Person>> getMusicianListByInstrumentType(InstrumentType instrumentType, List<Pair<InstrumentType, List<Person>>> list) {
        Pair<InstrumentType, List<Person>> pair = null;

        for (Pair<InstrumentType, List<Person>> exPair : list) {
            if (exPair.getKey().equals(instrumentType)) {
                pair = exPair;
            }
        }

        return pair;
    }

    /** Function to publish and persist events of a specific month in a specific year. Sets their status to published.
     *           Returns an errorList of events that could not be published
     *
     * @param month
     * @param year
     * @return
     */
    public List<Pair<DomainEntity, List<Pair<String, String>>>> publishEventsByMonth(int month, int year) {
        List<Pair<DomainEntity, List<Pair<String, String>>>> fullErrorList = new LinkedList<>();

        List<EventDuty> events = eventFacade.getEventsByMonth(month, year);

        for (EventDuty event : events) {
            event.setEventStatus(EventStatus.Published);

            try {
                eventFacade.add(event);
            } catch (Exception e) {
                List<Pair<String, String>> errorList = new LinkedList<>();
                errorList.add(new Pair<>("", "could not publish"));
                fullErrorList.add(new Pair<>(event, errorList));
            }
        }

        return fullErrorList;
    }

    /** Function to unpublish a specific month in a specific year. The status is set to unpublished. When no errors this
     *          is persisted. If errors occur a errorList is returned. if not an empty list is returned
     *
     * @param month
     * @param year
     * @return    errorList     if errors occured they are returned in this list
     */
    public List<Pair<DomainEntity, List<Pair<String, String>>>> unpublishEventsByMonth(int month, int year) {
        List<Pair<DomainEntity, List<Pair<String, String>>>> fullErrorList = new LinkedList<>();
        List<Pair<String, String>> errorList;
        List<EventDuty> events = eventFacade.getEventsByMonth(month, year);

        EventDutyLogic eventDutyLogic = (EventDutyLogic) DomainEntityManager.getLogic(EntityType.EVENT_DUTY);
        List<Pair<String, String>> tmpErrorList;

        for (EventDuty event : events) {
            event.setEventStatus(EventStatus.Unpublished);

            tmpErrorList = eventDutyLogic.validate(event);

            if(tmpErrorList.size() == 0) {
                try {
                    eventFacade.add(event);
                } catch (Exception e) {
                    errorList = new LinkedList<>();
                    errorList.add(new Pair<>("", "could not unpublish"));
                    fullErrorList.add(new Pair<>(event, errorList));
                }
            } else {
                fullErrorList.add(new Pair<>(event, tmpErrorList));
            }
        }

        return fullErrorList;
    }

    @Override
    public EventDuty getByID(int id) {
        EventDuty entity = eventFacade.getEventById(id);

        if (entity != null) {
            return entity;
        }

        return null;
    }

    @Override
    public List<EventDuty> getList() {
        return getEventsByTimeFrame(LocalDateTime.now().minusYears(100), LocalDateTime.now().plusYears(100));
    }
}