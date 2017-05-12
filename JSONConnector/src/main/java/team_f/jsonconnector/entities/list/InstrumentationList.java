package team_f.jsonconnector.entities.list;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

import java.util.List;

/**
 * Created by dominik on 12.05.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentationList implements JSONObjectEntity {
    private List<Instrumentation> _instrumentationList;

    @JsonGetter("instrumentation")
    public List<Instrumentation> getInstrumentationList() {
        return _instrumentationList;
    }

    @JsonSetter("instrumentation")
    public void setInstrumentationList(List<Instrumentation> instrumentationList) {
        _instrumentationList = instrumentationList;
    }

    @Override
    public String getEntityName() {
        return "Instrumentation";
    }

    @Override
    public String getDisplayName() {
        return getEntityName();
    }
}
