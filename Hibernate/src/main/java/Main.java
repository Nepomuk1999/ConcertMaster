

import database.EventDutyEntity;
import enums.EventStatus;
import enums.EventType;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Timestamp;
import java.util.List;

import static enums.EventStatus.Unpublished;
import static enums.EventType.Opera;

/**
 * Created by Home on 06.04.2017.
 */
public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        Facade facade = new Facade(session);
        try {
            System.out.println("Testing to add Entities in Database ...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();

            Timestamp s = new Timestamp(2017,3,11,00,00, 00, 00);
            Timestamp e = new Timestamp(2017,3,12,00,00, 00, 00);

            //facade.addEvent("TestEvent2", "dies ist ein Test", s, e, EventType.Concert, EventStatus.Unpublished, "Test Conductor", "Opernhaus", 2, 1);
            //facade.addEvent("TestEvent2", "dies ist ein Test", s, e, EventType.Concert, EventStatus.Unpublished, "Test Conductor", "Opernhaus", 2, 1);
            System.out.println("TestEvents added...");
            List<EventDutyEntity> events = facade.getEvents(4,3917);
            for(EventDutyEntity event : events) {
                System.out.println(event.getName());
            }
        } finally {
            session.close();
        }
    }
}