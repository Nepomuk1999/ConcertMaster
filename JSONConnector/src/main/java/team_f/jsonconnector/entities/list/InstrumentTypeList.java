package team_f.jsonconnector.entities.list;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.entities.InstrumentType;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentTypeList implements JSONObjectEntity {
    private List<InstrumentType> _instrumentTypeList;

    @JsonGetter("instrument_types")
    public List<InstrumentType> getInstrumentTypeList() {
        return _instrumentTypeList;
    }

    @JsonSetter("instrument_types")
    public void setEventDutyList(List<InstrumentType> instrumentTypeList) {
        _instrumentTypeList = instrumentTypeList;
    }

    @Override
    public String getEntityName() {
        return "Instrument Types";
    }

    @Override
    public String getDisplayName() {
        return getEntityName();
    }
}
