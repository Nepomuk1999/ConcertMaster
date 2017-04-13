package team_f.database_wrapper.helper;

import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;
import java.util.Map;

public class ModelLogic {
    /**
     * validates the object
     * @return a list with keys (column name) and the error messages for the user
     *         is never null!
     */
    public static List<Map.Entry<String, String>> validate() {
        throw new NotImplementedException();
    }

    /**
     * creates a JSON object
     * @return object is null: returns an empty JSON object
     *         object is not null: returns a JSON object with the column name as the key and the values which respond to them
     */
    public static JSONObject toJSONObject(Object object) {
        throw new NotImplementedException();
    }
}
