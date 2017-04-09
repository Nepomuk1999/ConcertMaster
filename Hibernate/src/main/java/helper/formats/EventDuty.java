package helper.formats;

import database.EventDutyEntity;
import helper.JSON;
import helper.OutputFormat;
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
