package team_f.database_wrapper.facade;

import javax.persistence.EntityManager;

public class Main {
    public static void main(final String[] args) throws Exception {
        EntityManager session = SessionFactory.getSession();

        Facade facade = new Facade(session);
        /*
        try {
            System.out.println("Testing to add Entities in Database ...");

            LocalDateTime s = LocalDateTime.of(2012,2,11,00,00, 00, 00);
            LocalDateTime e = LocalDateTime.of(2012,2,12,00,00, 00, 00);

            facade.addEvent("TestEvent4", "dies ist ein Test", s, e, EventType.Concert, EventStatus.Unpublished, "Test Conductor", "Opernhaus", 2, 1);
            facade.addEvent("TestEvent5", "dies ist ein Test", s, e, EventType.Concert, EventStatus.Unpublished, "Test Conductor", "Opernhaus", 2, 1);
            facade.addEvent("New Event5", "dies ist ein Test", s, e, EventType.Concert, EventStatus.Unpublished, "Test Conductor", "Opernhaus", 2, 1);

            System.out.println("TestEvents added...");
            List<EventDutyEntity> events = facade.getEvents(4, 2017);

            for(EventDutyEntity event : events) {
                System.out.println(event.getName());
            }
        } finally {
            session.close();
        }

        System.exit(0);
        */
    }
}