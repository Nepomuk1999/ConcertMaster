package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.enums.request.ActionType;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

public class Request implements JSONObjectEntity {
    private ActionType _actionType;
    private List<Pair> _parameterKeyValueList;

    @JsonGetter("action_type")
    public ActionType getActionType() {
        return _actionType;
    }

    @JsonGetter("parameter_key_value")
    public List<Pair> getParameterValue() {
        return _parameterKeyValueList;
    }

    @JsonSetter("action_type")
    public void setActionType(ActionType actionType) {
        _actionType = actionType;
    }

    @JsonSetter("parameter_key_value")
    public void setParameterKey(List<Pair> parameterKeyValueList) {
        _parameterKeyValueList = parameterKeyValueList;
    }
}
