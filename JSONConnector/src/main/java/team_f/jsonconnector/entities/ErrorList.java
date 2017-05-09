package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorList implements JSONObjectEntity {
    private List<Pair<JSONObjectEntity, List<Error>>> _errors;

    @JsonGetter("errors")
    public List<Pair<JSONObjectEntity, List<Error>>> getKeyValueList() {
        return _errors;
    }

    @JsonSetter("errors")
    public void setErrorList(List<Pair<JSONObjectEntity, List<Error>>> errors) {
        _errors = errors;
    }
}