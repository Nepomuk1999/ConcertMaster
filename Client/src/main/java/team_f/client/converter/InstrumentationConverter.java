package team_f.client.converter;

import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import team_f.jsonconnector.entities.Error;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominik on 12.05.17.
 */
public class InstrumentationConverter {
    public static List<Pair<JSONObjectEntity, List<Error>>> getAbstractList(List<Pair<Instrumentation, List<Error>>> errorList) {
        List<Pair<JSONObjectEntity, List<Error>>> resultErrorList = new ArrayList<>(errorList.size());
        Pair<JSONObjectEntity, List<Error>> tmpPair;

        for(Pair<Instrumentation, List<Error>> item : errorList) {
            tmpPair = new Pair<>();
            tmpPair.setKey(item.getKey());
            tmpPair.setValue(item.getValue());
            resultErrorList.add(tmpPair);
        }

        return resultErrorList;
    }
}
