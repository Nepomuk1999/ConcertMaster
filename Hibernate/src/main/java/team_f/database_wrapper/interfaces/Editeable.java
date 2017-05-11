package team_f.database_wrapper.interfaces;

import team_f.domain.interfaces.DomainEntity;

/**
 * Created by Home on 11.05.2017.
 */
public interface Editeable<R , V> {

    public int add(V value);

    public int update(V value);

    public boolean delete(int id);
}
