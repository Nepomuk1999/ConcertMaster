package interfaces;

import java.util.List;
import java.util.Map;

public interface Validatable {
    /**
     * validates the object
     * @return a list with keys (column name) and the error messages for the user
     */
    public List<Map.Entry<String, String>> validate();
}
