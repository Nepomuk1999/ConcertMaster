package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.EventApplication;
import team_f.domain.entities.EventDuty;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import team_f.server.helper.converter.EventDutyConverter;
import team_f.server.helper.response.CommonResponse;
import team_f.server.helper.response.JsonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {URIList.publish})
public class Publish extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            team_f.jsonconnector.entities.Publish publish = (team_f.jsonconnector.entities.Publish) ReadHelper.readJSONObject(req.getReader(), team_f.jsonconnector.entities.Publish.class);

            if(publish != null) {
                EventApplication facade = new EventApplication();
                List<javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>>> tmpErrorList = null;

                switch (publish.getPublishType()) {
                    case PUBLISH:
                        tmpErrorList = facade.publishEventsByMonth(publish.getMonth(), publish.getYear());
                        break;
                    case UNPUBLISH:
                        tmpErrorList = facade.unpublishEventsByMonth(publish.getMonth(), publish.getYear());
                        break;
                }

                List<javafx.util.Pair<JSONObjectEntity, List<javafx.util.Pair<String, String>>>> resultErrorList = new ArrayList<>(tmpErrorList.size());

                for(Pair<DomainEntity, List<javafx.util.Pair<String, String>>> item : tmpErrorList) {
                    resultErrorList.add(new Pair<>(EventDutyConverter.convertToJSON((EventDuty) item.getKey()), item.getValue()));
                }

                ErrorList errorList = JsonResponse.prepareErrorMessages(resultErrorList);
                resp.setContentType(MediaType.APPLICATION_JSON);
                resp.setCharacterEncoding("UTF-8");
                WriteHelper.writeJSONObject(resp.getWriter(), errorList);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}