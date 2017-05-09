package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

public class ErrorList implements JSONObjectEntity {
    private List<Pair<Integer, List<Error>>> _errors;
    private JSONObjectEntity _entity;

    @JsonGetter("errors")
    public List<Pair<Integer, List<Error>>> getKeyValueList() {
        return _errors;
    }

    @JsonGetter("entity")
    public JSONObjectEntity getEntity() {
        return _entity;
    }

    @JsonSetter("errors")
    public void setValue(List<Pair<Integer, List<Error>>> errors) {
        _errors = errors;
    }

    @JsonSetter("entity")
    public void setEntity(JSONObjectEntity entity) {
        _entity = entity;
    }
}