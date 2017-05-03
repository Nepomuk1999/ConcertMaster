package team_f.server.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import team_f.application.EventApplication;
import team_f.application.helper.EventDutyHelper;
import team_f.domain.entities.EventDuty;
import team_f.jsonconnector.common.URIList;
import team_f.server.helper.CSSHelper;
import team_f.server.helper.response.CommonResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {URIList.calendar})
public class Calendar extends HttpServlet {
    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();

        if(MediaType.APPLICATION_JSON.equals(contentType)) {
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
                EventApplication facade = new EventApplication();
                List<EventDuty> eventList = facade.getEventsByMonth(startDate.getMonthValue() +1, startDate.getYear());

                for(EventDuty event : eventList) {
                    jsonObject = new JSONObject();
                    jsonObject.put("id", event.getEventDutyID());
                    jsonObject.put("start", event.getStartTime());
                    jsonObject.put("end", event.getEndTime());
                    jsonObject.put("title","(" + EventDutyHelper.getEventTypeCode(event.getEventType()) + ")" +"\t"+event.getName());
                    jsonObject.put("color", CSSHelper.getColor(EventDutyHelper.getColor(event.getEventType())));
                    //jsonObject.put("className", "");
                    //jsonObject.put("backgroundColor", "");
                    //jsonObject.put("borderColor", "");
                    //jsonObject.put("textColor", "");

                    jsonArray.put(jsonObject);
                }
            }

            CommonResponse.writeJSONObject(resp, jsonArray);
        } else {
            resp.setContentType(MediaType.TEXT_HTML);
            req.getRequestDispatcher(getServletContext().getContextPath() + "/views/pages/plan_overview.jsp").include(req, resp);
        }
    }
}
