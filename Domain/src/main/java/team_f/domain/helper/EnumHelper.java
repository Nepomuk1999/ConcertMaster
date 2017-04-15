package team_f.domain.helper;

import team_f.domain.enums.EventDutyProperty;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;

public class EnumHelper {
    public static EventStatus[] getEventStatusList() {
        return EventStatus.values();
    }
    public static EventType[] getEventTypeList() {
        return EventType.values();
    }

    public static EventDutyProperty[] getEventDutys() {
        return EventDutyProperty.values();
    }
}
