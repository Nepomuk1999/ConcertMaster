package team_f.database_wrapper.facade;

import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SessionFactory {
    private static final EntityManagerFactory _entityManagerFactory;

    static {
        try {
            _entityManagerFactory = Persistence.createEntityManagerFactory("sem4_team2");
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getSession() throws HibernateException {
        return _entityManagerFactory.createEntityManager();
    }
}
