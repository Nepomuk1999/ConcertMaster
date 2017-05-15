package team_f.database_wrapper.facade;

import team_f.database_wrapper.interfaces.Editeable;
import team_f.database_wrapper.interfaces.Session;
import javax.persistence.EntityManager;

public abstract class BaseDatabaseFacade<V> implements Session, Editeable<V> {
    private EntityManager _session;

    public BaseDatabaseFacade() {
        this(SessionFactory.getSession());
    }

    public BaseDatabaseFacade(EntityManager session) {
        _session = session;
    }

    @Override
    public void closeSession() {
        if(_session != null) {
            _session.close();
            _session = null;
        }
    }

    @Override
    public EntityManager getCurrentSession() {
        return _session;
    }
}