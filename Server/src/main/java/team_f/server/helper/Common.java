package team_f.server.helper;

import org.json.HTTP;
import org.json.JSONObject;
import java.io.BufferedReader;

public class Common {
    public static String readRequestBody(BufferedReader reader) {
        StringBuffer buffer = new StringBuffer();
        String line = null;

        try {
            while ((line = reader.readLine()) != null)
                buffer.append(line);
        } catch (Exception e) {
        }

        return buffer.toString();
    }

    public static JSONObject readJSONObject(BufferedReader reader) {
        String body = readRequestBody(reader);

        return HTTP.toJSONObject(body);
    }
}
