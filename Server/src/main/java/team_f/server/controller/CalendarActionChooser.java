package team_f.server.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import team_f.jsonconnector.common.URIList;
import team_f.server.helper.request.CommonRequest;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@WebServlet(urlPatterns = {URIList.calendarActionChooser})
public class CalendarActionChooser extends HttpServlet {
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
            JSONObject jsonObject = CommonRequest.getParametersAsJSON(req);
            req.setAttribute("eventData", jsonObject.toString());

            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/calendar_action_chooser.jsp").include(req, resp);
        }
    }
}
