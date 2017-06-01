package team_f.server.controller;

import javafx.util.Pair;
import org.json.JSONArray;
import team_f.application.PersonApplication;
import team_f.application.RequestApplication;
import team_f.domain.enums.RequestType;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.common.URIList;
import team_f.server.helper.request.CalendarRequest;
import team_f.server.helper.request.RequestRequest;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {URIList.request})
public class Request extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if (contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            CalendarRequest.getAndSetParameters(req, true);
            setAttributes(req);

            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/request.jsp").include(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        resp.setCharacterEncoding("UTF-8");

        if(contentType != null && contentType.startsWith(MediaType.APPLICATION_JSON)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            RequestApplication facade = RequestApplication.getInstance();
            RequestRequest requestRequest = new RequestRequest(req);

            RequestType requestType = null;

            try {
                requestType = RequestType.valueOf(String.valueOf(requestRequest.getRequestType()));
            } catch (Exception e) {
            }

            Pair<DomainEntity, List<Pair<String, String>>> result = facade.add(requestRequest.getEventId(),
                    requestRequest.getPersonId(), requestType, requestRequest.getDescription());

            if(result.getValue().size() > 0) {
                resp.setContentType(MediaType.TEXT_HTML);
                CommonResponse.setErrorParameters(req, resp, result, "request");
                setAttributes(req);

                req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/request.jsp").include(req, resp);
            } else {
                resp.setContentType(MediaType.TEXT_HTML);
            }
        }
    }

    private void setAttributes(HttpServletRequest request) {
        PersonApplication personFacade = PersonApplication.getInstance();
        request.setAttribute("PERSON_LIST", personFacade.getList());
    }
}