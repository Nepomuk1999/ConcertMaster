package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

public class ErrorList implements JSONObjectEntity {
    private List<Error> _errors;

    @JsonGetter("errors")
    public List<Error> getKeyValueList() {
        return _errors;
    }

    @JsonSetter("errors")
    public void setValue(List<Error> errors) {
        _errors = errors;
    }
}