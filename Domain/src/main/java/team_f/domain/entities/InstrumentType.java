package team_f.domain.entities;

import team_f.domain.enums.EntityType;
import team_f.domain.enums.properties.InstrumentTypeProperty;

public class InstrumentType extends BaseDomainEntity<InstrumentTypeProperty> {
    private int _id;
    private String _name;

    public InstrumentType() {
        super(EntityType.INSTRUMENT_TYPE);
    }

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
