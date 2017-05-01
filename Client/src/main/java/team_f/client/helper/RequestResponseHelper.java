package team_f.client.helper;

import sun.net.www.http.PosterOutputStream;
import team_f.jsonconnector.entities.JSONObjectEntity;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestResponseHelper {
    public static <T extends JSONObjectEntity> JSONObjectEntity writeAndReadJSONObject(URL url, JSONObjectEntity requestJSONObject, Class<T> responseJSONObjectClass) {
        StringBuffer sb = new StringBuffer();

        if (url != null && requestJSONObject != null && responseJSONObjectClass != null) {
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.setRequestProperty();
                connection.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
                connection.setRequestProperty("Content-Language", "en-US");

                connection.setRequestMethod(HttpMethod.POST);
                connection.setDoOutput(true);
                connection.setDoInput(true);

                PosterOutputStream os = (PosterOutputStream) connection.getOutputStream();
                os.write(WriteHelper.writeJSONObjectAsString(requestJSONObject).getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                InputStream is = connection.getInputStream();
                JSONObjectEntity entity = ReadHelper.readJSONObject(new InputStreamReader(is), responseJSONObjectClass);
                connection.disconnect();

                return entity;
            } catch (IOException e) {
            }
        }

        return null;
    }
}
