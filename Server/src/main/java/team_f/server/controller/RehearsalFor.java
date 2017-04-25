package team_f.server.controller;

import org.json.JSONArray;
import team_f.server.helper.request.CalendarRequest;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@WebServlet(urlPatterns = {"/RehearsalFor"})
public class RehearsalFor extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (MediaType.APPLICATION_JSON.equals(contentType)) {
            CommonResponse.writeJSONObject(resp, new JSONArray());
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            CalendarRequest.getAndSetParameters(req);
            setAttributes(req);

            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/event_type.jsp").include(req, resp);
        }
    }

    private void setAttributes(HttpServletRequest request) {
        request.setAttribute("IS_REHEARSAL_FOR", true);
    }
}
