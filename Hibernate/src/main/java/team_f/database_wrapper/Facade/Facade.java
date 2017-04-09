package team_f.database_wrapper.Facade;

import team_f.database_wrapper.database.EventDutyEntity;
import team_f.database_wrapper.enums.EventStatus;
import team_f.database_wrapper.enums.EventType;
import org.hibernate.Session;
import java.sql.Timestamp;
import java.util.List;

public class Facade {
    Session _session;

    public Facade() {
        _session = SessionFactory.getSession();
    }

    public Facade(Session session) {
        _session = session;
    }

    protected Session getCurrentSession(){
        return _session;
    }

    /**
     * Function to add a EventDuty to team_f.database_wrapper.database. Returns the EventDutyId after saving it in the team_f.database_wrapper.database
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

    public Integer addEvent(String name, String description, Timestamp start, Timestamp end, EventType eventType,
                         EventStatus status, String conductor, String location, double defaultPoints, int instrumentation) {

        Session session = getCurrentSession();
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

        session.save(event);

        session.getTransaction().commit();

        return event.getEventDutyId();
    }

    public List<EventDutyEntity> getEvents(int month, int year) {
        Session session = getCurrentSession();
        session.getTransaction().begin();

        List<EventDutyEntity> events = (List<EventDutyEntity>) session.createQuery("from database.EventDutyEntity where MONTH(starttime) = " + month + " and YEAR(starttime) = " + year).list();

        return events;
    }
}
