package team_f.jsonconnector.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.io.IOException;
import java.io.Writer;

public class WriteHelper {
    public static boolean writeJSONObject(Writer writer, JSONObjectEntity jsonObjectEntity) {
        if(writer != null && jsonObjectEntity != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            try {
                mapper.writeValue(writer, jsonObjectEntity);
                return true;
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        return false;
    }

    public static String writeJSONObjectAsString(JSONObjectEntity jsonObjectEntity) {
        if(jsonObjectEntity != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            try {
                return mapper.writeValueAsString(jsonObjectEntity);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        return null;
    }
}
