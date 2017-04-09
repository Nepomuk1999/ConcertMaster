package Facade;

import database.EventDutyEntity;
import enums.EventStatus;
import enums.EventType;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import java.sql.Timestamp;
import java.util.List;

public class Main {
    public static void main(final String[] args) throws Exception {
        final Session session = SessionFactory.getSession();
        Facade facade = new Facade(session);

        try {
            System.out.println("Testing to add Entities in Database ...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();

            //Kalender 0 indiziert --> Maerz somit 2
            Timestamp s = new Timestamp(2012,2,11,00,00, 00, 00);
            Timestamp e = new Timestamp(2012,2,12,00,00, 00, 00);


            facade.addEvent("TestEvent4", "dies ist ein Test", s, e, EventType.Concert, EventStatus.Unpublished, "Test Conductor", "Opernhaus", 2, 1);
            facade.addEvent("TestEvent5", "dies ist ein Test", s, e, EventType.Concert, EventStatus.Unpublished, "Test Conductor", "Opernhaus", 2, 1);
            System.out.println("TestEvents added...");
            List<EventDutyEntity> events = facade.getEvents(3,2012);
            for(EventDutyEntity event : events) {
                System.out.println(event.getName());
            }
        } finally {
            session.close();
        }
    }
}