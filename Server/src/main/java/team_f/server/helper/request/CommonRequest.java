package team_f.server.helper.request;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

public class CommonRequest {
    public static JSONObject getParametersAsJSON(HttpServletRequest request) {
        Set<Map.Entry<String, String[]>> parameterKeys = request.getParameterMap().entrySet();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;

        for (Map.Entry<String, String[]> item : parameterKeys) {
            if(item.getValue().length == 1) {
                jsonObject.put(item.getKey(), item.getValue()[0]);
            } else if(item.getValue().length > 1) {
                jsonArray = new JSONArray();

                for(String value : item.getValue()) {
                    jsonArray.put(value);
                }

                jsonObject.put(item.getKey(), jsonArray);
            }
        }

        return jsonObject;
    }
}
