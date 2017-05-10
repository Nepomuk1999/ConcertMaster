package team_f.client.helper;

import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

import java.util.List;

public class ErrorMessageHelper {
    public static String getErrorMessage(List<Pair<JSONObjectEntity, List<Error>>> errorList) {
        StringBuilder errorText = new StringBuilder();

        for(Pair<JSONObjectEntity, List<Error>> item : errorList) {
            if(item.getValue() != null && item.getValue().size() > 0) {
                if(item.getKey() != null) {
                    errorText.append(item.getKey().getEntityName());
                }

                for(Error error : item.getValue()) {
                    errorText.append(error.getKey() + ": " + error.getValue());
                }

                errorText.append("\n");
            }
        }

        return errorList.toString();
    }
}
