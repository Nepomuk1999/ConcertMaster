package team_f.client.helper.response;

import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

import java.util.LinkedList;
import java.util.List;

public class JsonResponse {
    public static ErrorList prepareErrorMessages(List<javafx.util.Pair<JSONObjectEntity, List<javafx.util.Pair<String, String>>>> errorList) {
        ErrorList resultErrorList = new ErrorList();
        javafx.util.Pair<JSONObjectEntity, List<javafx.util.Pair<String, String>>> entityErrorList;
        List<Pair<JSONObjectEntity, List<Error>>> errorItemList = new LinkedList<>();
        Pair<JSONObjectEntity, List<Error>> tmpPair;

        if(errorList != null) {
            for (int i = 0; i < errorList.size(); i++) {
                entityErrorList = errorList.get(i);

                if(entityErrorList != null && entityErrorList.getValue() != null) {
                    tmpPair = getErrorList(entityErrorList.getKey(), entityErrorList.getValue());
                    errorItemList.add(tmpPair);
                }
            }
        }

        resultErrorList.setErrorList(errorItemList);
        return resultErrorList;
    }

    public static ErrorList prepareErrorMessage(JSONObjectEntity entity, List<javafx.util.Pair<String, String>> errorList) {
        ErrorList resultErrorList = new ErrorList();

        Pair<JSONObjectEntity, List<Error>> resultPair = getErrorList(entity, errorList);
        List<Pair<JSONObjectEntity, List<Error>>> list = new LinkedList<>();
        list.add(resultPair);
        resultErrorList.setErrorList(list);

        return resultErrorList;
    }

    private static Pair<JSONObjectEntity, List<Error>> getErrorList(JSONObjectEntity entity, List<javafx.util.Pair<String, String>> errorList) {
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

        Pair<JSONObjectEntity, List<Error>> resultPair = new Pair<>();
        resultPair.setKey(entity);
        resultPair.setValue(errors);

        return resultPair;
    }
}
