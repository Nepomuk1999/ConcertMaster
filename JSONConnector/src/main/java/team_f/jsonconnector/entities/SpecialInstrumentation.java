package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

public class SpecialInstrumentation implements JSONObjectEntity {
    private String _sectionType;
    private String _specialInstrument;
    private int _specialInstrumentCount;

    @JsonGetter("section_type")
    public String getSectionType() {
        return _sectionType;
    }

    @JsonGetter("special_instrument")
    public String getSpecialInstrument() {
        return _specialInstrument;
    }

    @JsonGetter("special_instrumentation_count")
    public int getSpecialInstrumentCount() {
        return _specialInstrumentCount;
    }

    @JsonSetter("section_type")
    public void setSectionType(String sectionType) {
        _sectionType = sectionType;
    }

    @JsonSetter("special_instrument")
    public void setSpecialInstrument(String specialInstrument) {
        _specialInstrument = specialInstrument;
    }

    @JsonSetter("special_instrumentation_count")
    public void setSpecialInstrumentCount(int specialInstrumentCount) {
        _specialInstrumentCount = specialInstrumentCount;
    }
}
