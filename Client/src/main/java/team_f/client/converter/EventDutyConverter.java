package team_f.client.converter;

import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

import java.util.ArrayList;
import java.util.List;

public class EventDutyConverter {
    public static List<Pair<JSONObjectEntity, List<Error>>> getAbstractList(List<Pair<EventDuty, List<Error>>> errorList) {
        List<Pair<JSONObjectEntity, List<Error>>> resultErrorList = new ArrayList<>(errorList.size());
        Pair<JSONObjectEntity, List<Error>> tmpPair;

        for(Pair<EventDuty, List<Error>> item : errorList) {
            tmpPair = new Pair<>();
            tmpPair.setKey(item.getKey());
            tmpPair.setValue(item.getValue());
            resultErrorList.add(tmpPair);
        }
        return resultErrorList;
    }
}
