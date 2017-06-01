package team_f.domain.entities;

import team_f.domain.enums.EntityType;
import team_f.domain.enums.properties.SpecialInstrumentationProperty;

public class SpecialInstrumentation extends BaseDomainEntity<SpecialInstrumentationProperty> {
    private int _specialInstrumentationID;
    private String _sectionType;
    private String _specialInstrument;
    private int _specialInstrumentCount;

    public SpecialInstrumentation() {
        super(EntityType.SPECIAL_INSTRUMENTATION_TYPE);
    }

    public int getSpecialInstrumentationID() {
        return _specialInstrumentationID;
    }

    public String getSectionType() {
        return _sectionType;
    }

    public String getSpecialInstrument() {
        return _specialInstrument;
    }

    public int getSpecialInstrumentCount() {
        return _specialInstrumentCount;
    }

    public void setSpecialInstrumentationID(int specialInstrumentationID) {
        _specialInstrumentationID = specialInstrumentationID;
    }

    public void setSectionType(String sectionType) {
        _sectionType = sectionType;
    }

    public void setSpecialInstrument(String specialInstrument) {
        _specialInstrument = specialInstrument;
    }

    public void setSpecialInstrumentCount(int specialInstrumentCount) {
        _specialInstrumentCount = specialInstrumentCount;
    }

    @Override
    public int getID() {
        return getSpecialInstrumentationID();
    }
}
