package team_f.database_wrapper.facade;

import team_f.database_wrapper.database.*;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class EventFacade {
    EntityManager _session;

    public EventFacade() {
        _session = SessionFactory.getSession();
    }

    public EventFacade(EntityManager session) {
        _session = session;
    }

    public void closeSession() {
        _session.close();
        _session = null;
    }

    protected EntityManager getCurrentSession(){
        return _session;
    }

    /**
     * Function to add a ModelLogic. Returns the EventDutyId after saving it in database
     * @return EventDutyId      int         returns the primary key of the event
     */
    public Integer addEvent(EventDuty event) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        EventDutyEntity eventEntity = convertToEventDutyEntity(event);

        if (eventEntity.getEventDutyId() > 0) {
            session.merge(eventEntity);
        } else {
            session.persist(eventEntity);
            session.flush();
        }

        if (!(eventEntity.getEventType().equals(EventType.NonMusicalEvent))) {

            Collection<EventDutyMusicalWorkEntity> emweList = new LinkedList<>();
            EventDutyMusicalWorkEntity emwe;
            EventDuty intermediateEntities = getEventById(eventEntity.getEventDutyId());
            List<MusicalWork> intermediateMusicalWorkEntities = getMusicalWorksForEvent(eventEntity.getEventDutyId());

            loop: for (MusicalWork musicalWork : event.getMusicalWorkList()) {
                // @TODO: this loop and the following if are only to avoid duplicated primary keys for musicalwork and eventIds
                // @TODO: a possible workaround could be to remove the intermediate table objects which will be updated
                // @TODO: Hibernate does not really like manyToMany relationships
                for(MusicalWork intermeWork : intermediateMusicalWorkEntities) {
                    if(intermeWork.getMusicalWorkID() == musicalWork.getMusicalWorkID()) {
                        continue loop;
                    }

                    break;
                }

                emwe = new EventDutyMusicalWorkEntity();
                emwe.setEventDuty(eventEntity.getEventDutyId());
                emwe.setMusicalWork(musicalWork.getMusicalWorkID());
                emwe.setAlternativeInstrumentation(musicalWork.getAlternativeInstrumentationId());

                session.persist(emwe);
                /*
                if(session.contains(emwe)) {
                    session.merge(emwe);
                } else {
                    session.persist(emwe);
                }*/

                emweList.add(emwe);
            }

            eventEntity.setEventDutyMusicalWorksByEventDutyId(emweList);

            if (eventEntity.getEventDutyId() > 0) {
                session.merge(eventEntity);
            } else {
                session.persist(eventEntity);
                session.flush();
            }
        }

        try {
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return eventEntity.getEventDutyId();
    }

    public EventDuty getEventById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from EventDutyEntity where eventDutyId = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<EventDutyEntity> eventDutyEntities = query.getResultList();
        EventDuty event = new EventDuty();

        if(eventDutyEntities.size() > 0) {
            EventDutyEntity e = eventDutyEntities.get(0);
            event = convertToEventDuty(e);

            Query queryRehearsal = session.createQuery("from EventDutyEntity where eventDutyId = :rid");
            queryRehearsal.setParameter("rid", e.getRehearsalFor());
            queryRehearsal.setMaxResults(1);

            List<EventDutyEntity> rehearsalEntities = queryRehearsal.getResultList();

            EventDuty rehearsal;
            if (rehearsalEntities.size() > 0) {
                EventDutyEntity rehearsalFor;
                rehearsalFor = rehearsalEntities.get(0);
                rehearsal = convertToEventDuty(rehearsalFor);
                event.setRehearsalFor(rehearsal);
            }

            event.setMusicalWorkList(getMusicalWorksForEvent(e.getEventDutyId()));
        }

        return event;
    }

    public List<EventDuty> getEventsByMonth(int month, int year) {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from EventDutyEntity where MONTH(starttime) = :month and YEAR(starttime) = :year");
        query.setParameter("month", month);
        query.setParameter("year", year);

        List<EventDutyEntity> eventEntities = query.getResultList();
        List<EventDuty> events = new ArrayList<>();

        for(EventDutyEntity eventDutyEntity : eventEntities) {
            EventDuty event = convertToEventDuty(eventDutyEntity);

            events.add(event);
        }

        return events;
    }

    public List<EventDuty> getEventsByDay(int day, int month, int year) {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from EventDutyEntity where DAY(starttime) = :day and MONTH(starttime) = :month and YEAR(starttime) = :year");
        query.setParameter("day", day);
        query.setParameter("month", month);
        query.setParameter("year", year);

        List<EventDutyEntity> eventEntities = query.getResultList();
        List<EventDuty> events = new ArrayList<>();

        for(EventDutyEntity eventDutyEntity : eventEntities) {
            EventDuty event;
            event = convertToEventDuty(eventDutyEntity);

            events.add(event);
        }

        return events;
    }

    public List<EventDuty> getEventsByTimeFrame(LocalDateTime start, LocalDateTime end) {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from EventDutyEntity where not (starttime > :end) OR (:start > endtime)");
        query.setParameter("end", end);
        query.setParameter("start", start);

        List<EventDutyEntity> eventEntities = query.getResultList();
        List<EventDuty> events = new ArrayList<>();

        for (EventDutyEntity eventDutyEntity : eventEntities) {
            EventDuty event = convertToEventDuty(eventDutyEntity);

            events.add(event);
        }

        return events;
    }

    public EventDuty convertToEventDuty(EventDutyEntity e) {
        EventDuty event = new EventDuty();

        event.setEventDutyId(e.getEventDutyId());
        event.setName(e.getName());
        event.setLocation(e.getLocation());
        event.setConductor(e.getConductor());
        event.setStartTime(e.getStarttime());
        event.setDescription(e.getDescription());
        event.setEndTime(e.getEndtime());
        event.setEventStatus(EventStatus.valueOf(e.getEventStatus().toString()));
        event.setEventType(EventType.valueOf(e.getEventType().toString()));
        event.setDefaultPoints(e.getDefaultPoints());
        if (e.getRehearsalFor() != null) {
            event.setRehearsalFor(getEventById(e.getRehearsalFor()));
        }

        event.setMusicalWorkList(getMusicalWorksForEvent(e.getEventDutyId()));

        return event;
    }

    public EventDutyEntity convertToEventDutyEntity(EventDuty event) {
        EventDutyEntity eventDutyEntity = new EventDutyEntity();

        eventDutyEntity.setEventDutyId(event.getEventDutyId());
        eventDutyEntity.setName(event.getName());
        eventDutyEntity.setDescription(event.getDescription());
        eventDutyEntity.setStarttime(event.getStartTime());
        eventDutyEntity.setEndtime(event.getEndTime());
        eventDutyEntity.setEventType(team_f.database_wrapper.entities.EventType.valueOf(event.getEventType().toString()));
        eventDutyEntity.setEventStatus(team_f.database_wrapper.entities.EventStatus.valueOf(event.getEventStatus().toString()));
        eventDutyEntity.setConductor(event.getConductor());
        eventDutyEntity.setLocation(event.getLocation());
        eventDutyEntity.setDefaultPoints(event.getDefaultPoints());
        if (event.getRehearsalFor() != null) {
            eventDutyEntity.setRehearsalFor(event.getRehearsalFor().getEventDutyId());
        }

        // There are only a few musical works associated with an event. Therefore get the full list instead of asking the DB every time and modify the entities when needed.
        Collection<EventDutyMusicalWorkEntity> eventDutyMusicalWorkEntities = eventDutyEntity.getEventDutyMusicalWorksByEventDutyId();
        List<MusicalWork> musicalWorks = event.getMusicalWorkList();

        if(eventDutyMusicalWorkEntities != null) {
            for (MusicalWork musicalWork : musicalWorks) {
                for(EventDutyMusicalWorkEntity item : eventDutyMusicalWorkEntities) {
                    if(item.getMusicalWork() == musicalWork.getMusicalWorkID() && item.getEventDuty() == event.getEventDutyId()) {
                        item.setAlternativeInstrumentation(musicalWork.getAlternativeInstrumentationId());
                    }
                }
            }

            eventDutyEntity.setEventDutyMusicalWorksByEventDutyId(eventDutyMusicalWorkEntities);
        }

        return eventDutyEntity;
    }

    public List<MusicalWork> getMusicalWorksForEvent(int eventId) {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from EventDutyMusicalWorkEntity where eventDuty = :id");
        query.setParameter("id", eventId);

        List<MusicalWork> mwList = new ArrayList<>();

        List<EventDutyMusicalWorkEntity> eList = query.getResultList();
        MusicalWork musicalWork;

        for (EventDutyMusicalWorkEntity edmwe :eList) {

            Query musicalQuery = session.createQuery("from MusicalWorkEntity where musicalWorkId = :id");
            musicalQuery.setParameter("id", edmwe.getMusicalWork());


            List<MusicalWorkEntity> mweList = musicalQuery.getResultList();

            for (MusicalWorkEntity mwe : mweList) {
                musicalWork = convertToMusicalWork(mwe);

                // set the alternative instrumentation on this object to avoid unnecessary lookups in the DB
                musicalWork.setAlternativeInstrumentationId(edmwe.getAlternativeInstrumentation());

                mwList.add(musicalWork);
            }
        }

        return mwList;
    }

    public Instrumentation getInstrumentationByID(int id) {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from InstrumentationEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<InstrumentationEntity> instrumentations = query.getResultList();
        InstrumentationEntity instrumentationEntity = new InstrumentationEntity();
        Instrumentation instrumentation = new Instrumentation();

        if(instrumentations.size() > 0) {
            instrumentationEntity = instrumentations.get(0);
            instrumentation = convertToInstrumentation(instrumentationEntity);
        }

        return instrumentation;
    }

    public List<Instrumentation> getInstrumentations() {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from InstrumentationEntity");
        List<InstrumentationEntity> instrumentationEntities = query.getResultList();
        List<Instrumentation> instrumentations = new ArrayList<>();

        for (InstrumentationEntity ie: instrumentationEntities) {
            instrumentations.add(convertToInstrumentation(ie));
        }

        return instrumentations;
    }

    /*
    public Integer addInstrumentation(InstrumentationEntity inst) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        // create new Instrumentation entity
        InstrumentationEntity instrumentation = new InstrumentationEntity();
        StringInstrumentationEntity stringInstrumentation = new StringInstrumentationEntity();
        BrassInstrumentationEntity brassInstrumentation = new BrassInstrumentationEntity();
        WoodInstrumentationEntity woodInstrumentation = new WoodInstrumentationEntity();
        PercussionInstrumentationEntity percussionInstrumentation = new PercussionInstrumentationEntity();

        // temporarily store all child entities in the transaction and get the id
        session.persist(stringInstrumentation);
        session.persist(brassInstrumentation);
        session.persist(woodInstrumentation);
        session.persist(percussionInstrumentation);

        // set the received
        instrumentation.setStringInstrumentation(stringInstrumentation.getStringInstrumentationId());
        instrumentation.setBrassInstrumentation(brassInstrumentation.getBrassInstrumentationId());
        instrumentation.setWoodInstrumentation(woodInstrumentation.getWoodInstrumentationId());
        instrumentation.setPercussionInstrumentation(percussionInstrumentation.getPercussionInstrumentationId());

        session.persist(instrumentation);

        try {
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return instrumentation.getInstrumentationId();
    }

    */

    public MusicalWork getMusicalWorkById(int id){
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from MusicalWorkEntity where :id");
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<MusicalWorkEntity> instrumentations = query.getResultList();
        MusicalWorkEntity musicalWorkEntity;
        MusicalWork musicalWork = new MusicalWork();

        if(instrumentations.size() > 0) {
            musicalWorkEntity = instrumentations.get(0);
            musicalWork = convertToMusicalWork(musicalWorkEntity);
        }

        return musicalWork;
    }

    public List<MusicalWork> getMusicalWorks(){
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from MusicalWorkEntity");

        List<MusicalWorkEntity> musicalWorkEntities = query.getResultList();
        List<MusicalWork> musicalWorks = new ArrayList<>();

        for (MusicalWorkEntity entity : musicalWorkEntities) {
            MusicalWork musicalWork = convertToMusicalWork(entity);

            musicalWorks.add(musicalWork);
        }

        return musicalWorks;
    }

    public Instrumentation convertToInstrumentation (InstrumentationEntity ie) {
        Instrumentation i = new Instrumentation();
        i.setInstrumentationID(ie.getInstrumentationId());

        BrassInstrumentationEntity bie = getBrassInstrumentationEntity(ie.getBrassInstrumentation());
        WoodInstrumentationEntity wie = getWoodInstrumentationEntity(ie.getWoodInstrumentation());
        StringInstrumentationEntity sie = getStringInstrumentationEntity(ie.getStringInstrumentation());
        PercussionInstrumentationEntity pie = getPercussionInstrumentationEntity(ie.getPercussionInstrumentation());
        List<SpecialInstrumentationEntity> speiList = getSpecialInstrumentationEntities(ie.getInstrumentationId());

        i.setFlute(wie.getFlute());
        i.setOboe(wie.getOboe());
        i.setBassoon(wie.getBassoon());
        i.setClarinet(wie.getClarinet());

        i.setTube(bie.getTube());
        i.setHorn(bie.getHorn());
        i.setTrombone(bie.getTrombone());
        i.setTrumpet(bie.getTrumpet());

        i.setViolin1(sie.getViolin1());
        i.setViolin2(sie.getViolin2());
        i.setViola(sie.getViola());
        i.setViolincello(sie.getViolincello());
        i.setDoublebass(sie.getDoublebass());

        i.setKettledrum(pie.getKettledrum());
        i.setHarp(pie.getHarp());
        i.setPercussion(pie.getPercussion());

        /*
        if (speiList != null) {
            for (SpecialInstrumentationEntity spei : speiList) {
                i.addToSpecial(spei.getSpecialInstrument(), spei.getSpecialInstrumentationNumber(), spei.getSectionType().toString());
            }
        }*/

        return i;
    }

    private BrassInstrumentationEntity getBrassInstrumentationEntity (int brassInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from BrassInstrumentationEntity where brassInstrumentationId = :id");
        query.setParameter("id", brassInstrumentationID);
        query.setMaxResults(1);

        List<BrassInstrumentationEntity> bie = query.getResultList();

        BrassInstrumentationEntity e = new BrassInstrumentationEntity();

        if(bie.size() > 0) {
            e = bie.get(0);
        }

        return e;
    }

    private WoodInstrumentationEntity getWoodInstrumentationEntity (int woodInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from WoodInstrumentationEntity where woodInstrumentationId = :id");
        query.setParameter("id", woodInstrumentationID);
        query.setMaxResults(1);

        List<WoodInstrumentationEntity> wie = query.getResultList();

        WoodInstrumentationEntity e = new WoodInstrumentationEntity();

        if(wie.size() > 0) {
            e = wie.get(0);
        }

        return e;
    }

    private StringInstrumentationEntity getStringInstrumentationEntity (int stringInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from StringInstrumentationEntity where stringInstrumentationId = :id");
        query.setParameter("id", stringInstrumentationID);
        query.setMaxResults(1);

        List<StringInstrumentationEntity> sie = query.getResultList();

        StringInstrumentationEntity e = new StringInstrumentationEntity();

        if(sie.size() > 0) {
            e = sie.get(0);
        }

        return e;
    }

    private PercussionInstrumentationEntity getPercussionInstrumentationEntity (int percussionInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from PercussionInstrumentationEntity where percussionInstrumentationId = :id");
        query.setParameter("id", percussionInstrumentationID);
        query.setMaxResults(1);

        List<PercussionInstrumentationEntity> pie = query.getResultList();

        PercussionInstrumentationEntity e = new PercussionInstrumentationEntity();

        if(pie.size() > 0) {
            e = pie.get(0);
        }

        return e;
    }

    private List<SpecialInstrumentationEntity> getSpecialInstrumentationEntities(int instrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from SpecialInstrumentationEntity where specialInstrumentationId = :id");
        query.setParameter("id", instrumentationID);

        List<SpecialInstrumentationEntity> spei = query.getResultList();

        return spei;
    }


    public Integer addMusicalWork(MusicalWork musicalWork){

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        MusicalWorkEntity mwEntity = convertToMusicalWorkEntity(musicalWork);

        session.persist(mwEntity);

        try {
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return mwEntity.getMusicalWorkId();
    }

    public MusicalWork convertToMusicalWork (MusicalWorkEntity mwe) {
        MusicalWork musicalWork = new MusicalWork();
        musicalWork.setMusicalWorkID(mwe.getMusicalWorkId());
        musicalWork.setInstrumentationID(mwe.getInstrumentationId());
        musicalWork.setComposer(mwe.getComposer());
        musicalWork.setName(mwe.getName());

        return musicalWork;
    }

    public MusicalWorkEntity convertToMusicalWorkEntity (MusicalWork mw) {
        MusicalWorkEntity mwe = new MusicalWorkEntity();

        mwe.setInstrumentationId(mw.getInstrumentationID());
        mwe.setComposer(mw.getComposer());
        mwe.setName(mw.getName());

        return mwe;
    }
}
