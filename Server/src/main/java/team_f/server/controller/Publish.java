package team_f.server.controller;

import org.json.JSONArray;
import team_f.application.EventApplication;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.ErrorList;
import team_f.jsonconnector.helper.ReadHelper;
import team_f.jsonconnector.helper.WriteHelper;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = {URIList.publish})
public class Publish extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (MediaType.APPLICATION_JSON.equals(contentType)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(MediaType.APPLICATION_JSON.equals(contentType)) {
            team_f.jsonconnector.entities.Publish publish = (team_f.jsonconnector.entities.Publish) ReadHelper.readJSONObject(req.getReader(), team_f.jsonconnector.entities.Publish.class);

            if(publish != null) {
                EventApplication facade = new EventApplication();
                boolean isSuccessful = false;

                switch (publish.getPublishType()) {
                    case PUBLISH:
                        isSuccessful = facade.publishEventsByMonth(publish.getMonth(), publish.getYear());
                        break;
                    case UNPUBLISH:
                        isSuccessful = facade.unpublishEventsByMonth(publish.getMonth(), publish.getYear());
                        break;
                }

                ErrorList errorList = new ErrorList();
                ArrayList<Error> errors = new ArrayList<>();
                Error error;

                if(!isSuccessful) {
                    error = new Error();
                    error.setKey("successful");
                    error.setValue("false");
                    errors.add(error);
                }

                errorList.setValue(errors);
                resp.setContentType(MediaType.APPLICATION_JSON);
                WriteHelper.writeJSONObject(resp.getWriter(), errorList);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
