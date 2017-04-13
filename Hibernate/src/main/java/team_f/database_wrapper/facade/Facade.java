package team_f.database_wrapper.facade;

import team_f.database_wrapper.database.*;
import team_f.database_wrapper.entities.EventStatus;
import team_f.database_wrapper.entities.EventType;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class Facade {
    EntityManager _session;

    public Facade() {
        _session = SessionFactory.getSession();
    }

    public Facade(EntityManager session) {
        _session = session;
    }

    protected EntityManager getCurrentSession(){
        return _session;
    }

    /**
     * Function to add a ModelLogic to team_f.database_wrapper.database. Returns the EventDutyId after saving it in the team_f.database_wrapper.database
     * @return EventDutyId      int         returns the primary key of the event
     */

    public Integer addEvent(EventDuty event) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        EventDutyEntity eventEntity = new EventDutyEntity();

        eventEntity.setName(event.getName());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setStarttime(event.getStarttime());
        eventEntity.setEndtime(event.getEndtime());
        eventEntity.setEventType(EventType.valueOf(event.getEventType()));
        eventEntity.setEventStatus(EventStatus.valueOf(event.getEventStatus()));
        eventEntity.setConductor(event.getConductor());
        eventEntity.setLocation(event.getLocation());
        eventEntity.setDefaultPoints(event.getDefaultPoints());
        eventEntity.setInstrumentation(event.getInstrumentation());

        session.persist(event);

        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return eventEntity.getEventDutyId();
    }

    public List<EventDuty> getEvents(int month, int year) {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from EventDutyEntity where MONTH(starttime) = :month and YEAR(starttime) = :year");
        query.setParameter("month", month);
        query.setParameter("year", year);

        List<EventDutyEntity> eventEntities = query.getResultList();
        List<EventDuty> events = new ArrayList<>();

        for(EventDutyEntity e : eventEntities) {
            EventDuty temp = new EventDuty();
            temp.setName(e.getName());
            temp.setDescription(e.getDescription());
            temp.setStarttime(e.getStarttime());
            temp.setEndtime(e.getEndtime());
            temp.setEventType(e.getEventType().toString());
            temp.setEventStatus(e.getEventStatus().toString());
            temp.setConductor(e.getConductor());
            temp.setLocation(e.getLocation());
            temp.setDefaultPoints(e.getDefaultPoints());
            temp.setInstrumentation(e.getInstrumentation());

            events.add(temp);
        }

        return events;
    }

    public List<Instrumentation> getInstrumentations() {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from InstrumentationEntity");


        List<InstrumentationEntity> instrumentationEntities = query.getResultList();
        List<Instrumentation> instrumentations = new ArrayList<>();

        for (InstrumentationEntity e : instrumentationEntities) {
            Instrumentation temp = new Instrumentation();

            int si = e.getStringInstrumentation();
            Query queryString = session.createQuery(String.format("from StringInstrumentationEntity where stringInstrumentationID = %d", si));

            List<StringInstrumentationEntity> stringI = queryString.setMaxResults(1).getResultList();
            temp.setViolin1(stringI.get(0).getViolin1());
            temp.setViolin2(stringI.get(0).getViolin2());
            temp.setViola(stringI.get(0).getViola());
            temp.setViolincello(stringI.get(0).getViolincello());
            temp.setDoublebass(stringI.get(0).getDoublebass());

            int bi = e.getStringInstrumentation();
            Query queryBrass = session.createQuery(String.format("from BrassInstrumentationEntity where brassInstrumentationID = %d", bi));
            List<BrassInstrumentationEntity> brassI = queryBrass.setMaxResults(1).getResultList();
            temp.setHorn(brassI.get(0).getHorn());
            temp.setTrombone(brassI.get(0).getTrombone());
            temp.setTrumpet(brassI.get(0).getTrumpet());
            temp.setTube(brassI.get(0).getTube());

            int wi = e.getStringInstrumentation();
            Query queryWood = session.createQuery(String.format("from WoodInstrumentationEntity where woodInstrumentationID = %d", wi));
            List<WoodInstrumentationEntity> woodI = queryWood.setMaxResults(1).getResultList();
            temp.setBassoon(woodI.get(0).getBassoon());
            temp.setClarinet(woodI.get(0).getClarinet());
            temp.setFlute(woodI.get(0).getFlute());
            temp.setOboe(woodI.get(0).getOboe());

            int pi = e.getStringInstrumentation();
            Query queryPercussion = session.createQuery(String.format("from PercussionInstrumentationEntity where percussionInstrumentationID = %d", pi));
            List<PercussionInstrumentationEntity> percussionI = queryPercussion.setMaxResults(1).getResultList();
            temp.setHarp(percussionI.get(0).getHarp());
            temp.setKettledrum(percussionI.get(0).getKettledrum());
            temp.setPercussion(percussionI.get(0).getPercussion());

            instrumentations.add(temp);
        }

        return instrumentations;
    }

    public Integer addInstrumentation(Instrumentation inst) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        InstrumentationEntity instrumentation = new InstrumentationEntity();
        StringInstrumentationEntity stringInstrumentation = new StringInstrumentationEntity();
        BrassInstrumentationEntity brassInstrumentation = new BrassInstrumentationEntity();
        WoodInstrumentationEntity woodInstrumentation = new WoodInstrumentationEntity();
        PercussionInstrumentationEntity percussionInstrumentation = new PercussionInstrumentationEntity();

        stringInstrumentation.setViolin1(inst.getViolin1());
        stringInstrumentation.setViolin2(inst.getViolin2());
        stringInstrumentation.setViola(inst.getViola());
        stringInstrumentation.setViolincello(inst.getViolincello());
        stringInstrumentation.setDoublebass(inst.getDoublebass());

        session.persist(stringInstrumentation);

        brassInstrumentation.setHorn(inst.getHorn());
        brassInstrumentation.setTrombone(inst.getTrombone());
        brassInstrumentation.setTrumpet(inst.getTrumpet());
        brassInstrumentation.setTube(inst.getTube());

        session.persist(brassInstrumentation);

        woodInstrumentation.setFlute(inst.getFlute());
        woodInstrumentation.setOboe(inst.getOboe());
        woodInstrumentation.setClarinet(inst.getClarinet());
        woodInstrumentation.setBassoon(inst.getBassoon());

        session.persist(woodInstrumentation);

        percussionInstrumentation.setHarp(inst.getHarp());
        percussionInstrumentation.setKettledrum(inst.getKettledrum());
        percussionInstrumentation.setPercussion(inst.getPercussion());

        session.persist(percussionInstrumentation);

        instrumentation.setStringInstrumentation(stringInstrumentation.getStringInstrumentationId());
        instrumentation.setBrassInstrumentation(brassInstrumentation.getBrassInstrumentationId());
        instrumentation.setWoodInstrumentation(woodInstrumentation.getWoodInstrumentationId());
        instrumentation.setPercussionInstrumentation(percussionInstrumentation.getPercussionInstrumentationId());

        session.persist(instrumentation);

        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return instrumentation.getInstrumentationId();
    }

    public List<MusicalWork> getMusicalWorks(){

        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from MusicalWorkEntity");


        List<MusicalWorkEntity> musicalWorkEntities = query.getResultList();
        List<MusicalWork> musicalworks = new ArrayList<>();

        for (MusicalWorkEntity e : musicalWorkEntities) {
            MusicalWork temp = new MusicalWork();

            temp.setInstrumentationID(e.getInstrumentationId());
            temp.setComposer(e.getComposer());
            temp.setName(e.getName());

            musicalworks.add(temp);
        }

        return musicalworks;
    }

    public Integer addMusicalWork(MusicalWork mw){

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        MusicalWorkEntity mwEntity = new MusicalWorkEntity();

        mwEntity.setInstrumentationId(mw.getInstrumentationID());
        mwEntity.setComposer(mw.getComposer());
        mwEntity.setName(mw.getName());

        session.persist(mw);

        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return mwEntity.getMusicalWorkId();
    }
}
