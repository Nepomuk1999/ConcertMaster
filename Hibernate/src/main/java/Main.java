

import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Timestamp;

import static enums.EventStatus.UNPUBLISHED;
import static enums.EventType.OPRA;

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

            Timestamp s = new Timestamp(2017,6,12,18,00, 00, 00);
            Timestamp e = new Timestamp(2017,6,12,22,00, 00, 00);

            facade.addEvent("TestEvent", "dies ist ein Test", s, e, OPRA, UNPUBLISHED, "Test Conductor", "Opernhaus", 2, 1);

        } finally {
            session.close();
        }
    }
}