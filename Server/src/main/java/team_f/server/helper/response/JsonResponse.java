package team_f.server.helper.response;

import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.ErrorList;
import team_f.jsonconnector.entities.Pair;
import java.util.LinkedList;
import java.util.List;

public class JsonResponse {
    public static ErrorList prepareErrorMessages(List<javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>>> errorList) {
        ErrorList resultErrorList = new ErrorList();
        javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> entityErrorList;
        List<Pair<Integer, List<Error>>> errorItemList = new LinkedList<>();
        Pair<Integer, List<Error>> tmpPair;

        if(errorList != null) {
            for (int i = 0; i < errorList.size(); i++) {
                entityErrorList = errorList.get(i);

                if(entityErrorList != null && entityErrorList.getValue() != null) {
                    tmpPair = getErrorList(entityErrorList.getKey(), entityErrorList.getValue());
                    errorItemList.add(tmpPair);
                }
            }
        }

        resultErrorList.setValue(errorItemList);
        return resultErrorList;
    }

    public static ErrorList prepareErrorMessage(DomainEntity entity, List<javafx.util.Pair<String, String>> errorList) {
        ErrorList resultErrorList = new ErrorList();

        Pair<Integer, List<Error>> resultPair = getErrorList(entity, errorList);
        List<Pair<Integer, List<Error>>> list = new LinkedList<>();
        list.add(resultPair);
        resultErrorList.setValue(list);

        return resultErrorList;
    }

    private static Pair<Integer, List<Error>> getErrorList(DomainEntity entity, List<javafx.util.Pair<String, String>> errorList) {
        Error error;
        List<Error> errors = new LinkedList<>();

        if(errorList != null) {
            for(javafx.util.Pair<String, String> item : errorList) {
                error = new Error();
                error.setKey(item.getKey());
                error.setValue(item.getValue());

                errors.add(error);
            }
        }

        Integer key;

        if(entity != null) {
            key = entity.getID();
        } else {
            key = 0;
        }

        Pair<Integer, List<Error>> resultPair = new Pair<>();
        resultPair.setKey(key);
        resultPair.setValue(errors);

        return resultPair;
    }
}
