package team_f.server.controller;

import team_f.application.EventApplication;
import team_f.jsonconnector.common.URIList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@WebServlet(urlPatterns = {URIList.root})
public class Root extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaType.TEXT_HTML);
        resp.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher(getServletContext().getContextPath() + "/views/pages/plan_overview.jsp").include(req, resp);
    }

    @Override
    public void destroy() {
        EventApplication facade = new EventApplication();
        facade.closeSession();
    }
}
