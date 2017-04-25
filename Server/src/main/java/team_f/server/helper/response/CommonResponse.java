package team_f.server.helper.response;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.domain.interfaces.DomainEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CommonResponse {
    public static boolean writeJSONObject(HttpServletResponse response, JSONArray jsonArray) {
        boolean result = false;

        response.setContentType(MediaType.APPLICATION_JSON);
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = null;

        try {
            writer = response.getWriter();
            writer.write(jsonArray.toString());
            writer.flush();
            result = true;
        } catch (IOException e) {
        } finally {
            if(writer != null) {
                writer.close();
            }
        }

        return result;
    }

    public static void setErrorParameters(HttpServletRequest request, HttpServletResponse response, Pair<DomainEntity, List<Pair<String, String>>> result, String attributeKey) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        request.setAttribute(attributeKey, result.getKey());

        Object attributeContent;

        for(Pair<String, String> item : result.getValue()) {
            attributeContent = request.getAttribute(item.getKey());

            if(attributeContent != null) {
                request.setAttribute("PROBLEM_" + item.getKey(), ((String) attributeContent) + "<br>" + item.getValue());
            } else {
                request.setAttribute("PROBLEM_" + item.getKey(), item.getValue());
            }
        }
    }
}
