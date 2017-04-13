package team_f.domain.helper;

import team_f.domain.enums.EventDutyProperty;

public class EnumHelper {
    public static EventDutyProperty[] getEventDutys() {
        return EventDutyProperty.values();
    }

    /*
    public static EventType[] getEventTypes() {
        return EventType.values();
    }

    public static EventStatus[] getEventStatus() {
        return EventStatus.values();
    }*/
}
