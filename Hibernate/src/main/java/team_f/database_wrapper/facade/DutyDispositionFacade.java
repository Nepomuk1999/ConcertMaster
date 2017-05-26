package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.DutyDispositionEntity;
import team_f.database_wrapper.helper.StoreHelper;
import team_f.domain.entities.DutyDisposition;
import team_f.domain.entities.EventDuty;
import team_f.domain.enums.DutyDispositionStatus;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DutyDispositionFacade extends BaseDatabaseFacade<DutyDisposition> {
    private static PersonFacade _personFacade = new PersonFacade();
    private static EventFacade _eventFacade = new EventFacade();

    public DutyDispositionFacade() {
        super();
    }

    public DutyDispositionFacade(EntityManager session) {
        super(session);
    }

    public double getPointsForPersonAndMonth(int personID, int month, int year) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("FROM DutyDispositionEntity where musician = :musician and MONTH(eventDutyByEventDuty.starttime) = :month and YEAR(eventDutyByEventDuty.starttime) = :year", DutyDispositionEntity.class);
        query.setParameter("musician", personID);
        query.setParameter("month", month);
        query.setParameter("year", year);

        List<DutyDispositionEntity> dutyDispositionEntities = query.getResultList();
        double result = 0;
        long days;

        for (DutyDispositionEntity dutyDispositionEntity : dutyDispositionEntities) {
            days = ChronoUnit.DAYS.between(dutyDispositionEntity.getEventDutyByEventDuty().getEndtime(), dutyDispositionEntity.getEventDutyByEventDuty().getStarttime());

            if(days == 0) {
                days = 1;
            } else {
                System.out.println("minus value detected");
            }

            result += dutyDispositionEntity.getPoints() * days;
        }

        return result;
    }

    public List<DutyDisposition> getDutyDispositionsForPersonID(int personID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("FROM DutyDispositionEntity where musician = :musician", DutyDispositionEntity.class);
        query.setParameter("musician", personID);

        List<DutyDispositionEntity> dutyDispositionEntities = query.getResultList();
        List<DutyDisposition> dutyDispositions = new ArrayList<>(dutyDispositionEntities.size());
        DutyDisposition dutyDisposition;

        for (DutyDispositionEntity dutyDispositionEntity : dutyDispositionEntities) {
            dutyDisposition = convertToDutyDisposition(dutyDispositionEntity);
            dutyDispositions.add(dutyDisposition);
        }

        return dutyDispositions;
    }

    public List<DutyDisposition> getDutyDispositionsForEventID(int eventID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("FROM DutyDispositionEntity where eventDuty = :event", DutyDispositionEntity.class);
        query.setParameter("event", eventID);

        List<DutyDispositionEntity> dutyDispositionEntities = query.getResultList();
        List<DutyDisposition> dutyDispositions = new ArrayList<>(dutyDispositionEntities.size());
        DutyDisposition dutyDisposition;

        for (DutyDispositionEntity dutyDispositionEntity : dutyDispositionEntities) {
            dutyDisposition = convertToDutyDisposition(dutyDispositionEntity);
            dutyDispositions.add(dutyDisposition);
        }

        return dutyDispositions;
    }

    private int addDutyDisposition(DutyDisposition dutyDisposition) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        DutyDispositionEntity dutyDispositionEntity = convertToDutyDispositionEntity(dutyDisposition);

        session.persist(dutyDispositionEntity);
        StoreHelper.storeEntities(session);

        return 1;
    }

    @Override
    public int add(DutyDisposition value) {
        return addDutyDisposition(value);
    }

    @Override
    public int update(DutyDisposition value) {
        return addDutyDisposition(value);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public boolean delete(int eventID, int musicianID) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        Query query = session.createQuery("DELETE FROM DutyDispositionEntity where musician = :musician and eventDuty = :event");
        query.setParameter("musician", musicianID);
        query.setParameter("event", eventID);

        return query.executeUpdate() > 0;
    }

    protected DutyDisposition convertToDutyDisposition(DutyDispositionEntity entity) {
        DutyDisposition dutyDisposition = new DutyDisposition();
        dutyDisposition.setDescription(entity.getDescription());
        dutyDisposition.setDutyDispositionStatus(DutyDispositionStatus.valueOf(String.valueOf(entity.getDutyDispositionStatus())));
        dutyDisposition.setEventDuty(_eventFacade.convertToEventDuty(entity.getEventDutyByEventDuty()));
        dutyDisposition.setMusician(_personFacade.convertToPerson(entity.getPersonByMusician()));
        dutyDisposition.setPoints(entity.getPoints());

        return dutyDisposition;
    }

    protected DutyDispositionEntity convertToDutyDispositionEntity(DutyDisposition dutyDisposition) {
        DutyDispositionEntity entity = new DutyDispositionEntity();
        entity.setDescription(dutyDisposition.getDescription());
        entity.setDutyDispositionStatus(team_f.database_wrapper.enums.DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getDutyDispositionStatus())));
        entity.setEventDuty(dutyDisposition.getEventDuty().getEventDutyID());
        entity.setMusician(dutyDisposition.getMusician().getPersonID());
        entity.setPoints(dutyDisposition.getPoints());

        return entity;
    }
}
