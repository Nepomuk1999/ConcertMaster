package team_f.server.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(urlPatterns = {"/Calendar"})
public class Calendar extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(MediaType.APPLICATION_JSON.equals(contentType)) {
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding("UTF-8");

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject;

            // get the parameters
            String startParameter = req.getParameter("start");
            String endParameter = req.getParameter("end");

            LocalDate startDate = null;
            LocalDate endDate = null;

            try {
                startDate = LocalDate.parse(startParameter);
                endDate = LocalDate.parse(endParameter);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(startDate != null && endDate != null) {
                // add the data from the team_f.database_wrapper.database

                Application facade = new Application();
                facade.
                List<EventDuty> eventList = (startDate.getMonthValue() +1, startDate.getYear());

                for(EventDutyEntity event : eventList) {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", event.getEventDutyId());
                    jsonObject.put("start", event.getStarttime());
                    jsonObject.put("end", event.getEndtime());
                    jsonObject.put("title", event.getName());
                    //jsonObject.put("className", "");
                    //jsonObject.put("color", "");
                    //jsonObject.put("backgroundColor", "");
                    //jsonObject.put("borderColor", "");
                    //jsonObject.put("textColor", "");

                    jsonArray.put(jsonObject);
                }*/

                /*event = new JSONObject();
                event.put("start", "2017-04-01");
                event.put("title", "All Day Event Duty");
                jsonArray.put(event);

                event = new JSONObject();
                event.put("title", "Long Event Duty");
                event.put("start", LocalDateTime.now());
                event.put("end", ZonedDateTime.now().plusDays(2).format(DateTimeFormatter.ISO_INSTANT));
                jsonArray.put(event);

                event = new JSONObject();
                event.put("title", "Long Event Duty with id");
                event.put("id", 500);
                event.put("start", LocalDateTime.now());
                event.put("end", ZonedDateTime.now().plusDays(2).format(DateTimeFormatter.ISO_INSTANT));
                jsonArray.put(event);*/
            }

            PrintWriter writer = resp.getWriter();
            writer.write(jsonArray.toString());

            writer.flush();
            writer.close();
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/pages/plan_overview.jsp").include(req, resp);
        }
    }
}
