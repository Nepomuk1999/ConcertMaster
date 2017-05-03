package team_f.database_wrapper.interfaces;

import javax.persistence.EntityManager;

public interface Session {
    public void closeSession();
    public EntityManager getCurrentSession();
}