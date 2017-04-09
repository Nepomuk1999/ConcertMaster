package team_f.database_wrapper.helper.formats;

import team_f.database_wrapper.database.EventDutyEntity;
import team_f.database_wrapper.helper.JSON;
import team_f.database_wrapper.helper.OutputFormat;
import org.json.JSONObject;

public class EventDuty extends OutputFormat {
    public static JSONObject toJSONObject(EventDutyEntity eventDuty) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("eventDutyID", eventDuty.getEventDutyId());
        jsonObject.put("name", eventDuty.getName());
        jsonObject.put("description", eventDuty.getDescription());
        jsonObject.put("starttime", JSON.getJSONDate(eventDuty.getStarttime()));
        jsonObject.put("endtime", JSON.getJSONDate(eventDuty.getEndtime()));
        jsonObject.put("eventType", eventDuty.getEventType());
        jsonObject.put("eventStatus", eventDuty.getEventStatus());
        jsonObject.put("conductor", eventDuty.getConductor());
        jsonObject.put("location", eventDuty.getLocation());
        jsonObject.put("rehearsalFor", eventDuty.getRehearsalFor());
        jsonObject.put("defaultPoints", eventDuty.getDefaultPoints());
        jsonObject.put("instrumentation", eventDuty.getInstrumentation());

        return jsonObject;
    }
}
