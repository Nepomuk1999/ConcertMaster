package team_f.domain.entities;

import team_f.domain.interfaces.DomainEntity;

public class InstrumentType implements DomainEntity {
    private int _id;
    private String _name;

    public int getID() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setID(int id) {
        _id = id;
    }

    public void setName(String name) {
        _name = name;
    }
}
