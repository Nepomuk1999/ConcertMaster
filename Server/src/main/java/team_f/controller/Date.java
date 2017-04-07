package team_f.controller;

import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/Date"})
public class Date extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if (MediaType.APPLICATION_JSON.equals(contentType)) {
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding("UTF-8");

            JSONArray jsonArray = new JSONArray();

            PrintWriter writer = resp.getWriter();
            writer.write(jsonArray.toString());

            writer.flush();
            writer.close();
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/date.jsp").include(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(MediaType.APPLICATION_JSON.equals(contentType)) {
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding("UTF-8");

            JSONArray jsonArray = new JSONArray();

            PrintWriter writer = resp.getWriter();
            writer.write(jsonArray.toString());

            writer.flush();
            writer.close();
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/modals/date.jsp").include(req, resp);
        }
    }
}
