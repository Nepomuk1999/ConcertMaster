package team_f.database_wrapper.facade;

import team_f.database_wrapper.database.EventDutyEntity;
import team_f.database_wrapper.enums.EventStatus;
import team_f.database_wrapper.enums.EventType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDateTime;
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
     * @param name              String      name of the event
     * @param description       String      description of the event
     * @param start             Timestamp   startTime of the event, saved as DateTime in team_f.database_wrapper.database
     * @param end               Timestamp   endTime of the event, saved as DateTime in team_f.database_wrapper.database
     * @param eventType         Enum        type of the event
     * @param status            Enum        status of the event
     * @param conductor         String      conductor
     * @param location          String      location
     * @param defaultPoints     int         default Points musicians get for this event
     * @param instrumentation   int         refers to a instrumentation with the primarykey instrumentation
     * @return EventDutyId      int         returns the primary key of the event
     */

    public Integer addEvent(String name, String description, LocalDateTime start, LocalDateTime end, EventType eventType,
                            EventStatus status, String conductor, String location, double defaultPoints, int instrumentation) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        EventDutyEntity event = new EventDutyEntity();
        event.setName(name);
        event.setDescription(description);
        event.setStarttime(start);
        event.setEndtime(end);
        event.setEventType(eventType);
        event.setEventStatus(status);
        event.setConductor(conductor);
        event.setLocation(location);
        event.setDefaultPoints(defaultPoints);
        event.setInstrumentation(instrumentation);

        session.persist(event);

        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return event.getEventDutyId();
    }

    public List<EventDutyEntity> getEvents(int month, int year) {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from EventDutyEntity where MONTH(starttime) = :month and YEAR(starttime) = :year");
        query.setParameter("month", month);
        query.setParameter("year", year);

        List<EventDutyEntity> events = query.getResultList();

        return events;
    }
}
