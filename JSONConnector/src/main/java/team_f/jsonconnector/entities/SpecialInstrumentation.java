package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecialInstrumentation implements JSONObjectEntity {
    private int _specialInstrumentationID;
    private String _sectionType;
    private String _specialInstrument;
    private int _specialInstrumentCount;

    @JsonGetter("id")
    public int getSpecialInstrumentationID() {
        return _specialInstrumentationID;
    }

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

    @JsonSetter("id")
    public void setSpecialInstrumentationID(int id) {
        _specialInstrumentationID = id;
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

    @Override
    public String getEntityName() {
        return "Special Instrumentation";
    }

    @Override
    public String getDisplayName() {
        return getSectionType() + " " + getSpecialInstrument();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
