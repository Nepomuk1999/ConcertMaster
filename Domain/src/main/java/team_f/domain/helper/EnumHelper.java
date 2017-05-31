package team_f.domain.helper;

import team_f.domain.enums.RequestType;
import team_f.domain.enums.properties.EventDutyProperty;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import java.util.LinkedList;
import java.util.List;

public class EnumHelper {
    // save the values
    private static EventType[] _eventTypeList;
    private static RequestType[] _requestTypeList;
    private static EventDutyProperty[] _eventDutyList;

    public static EventStatus[] getEventStatusList() {
        return EventStatus.values();
    }

    public static EventType[] getEventTypeList() {
        return EventType.values();
    }

    public static EventType[] getBasicEventTypeList() {
        if (_eventTypeList == null) {
            List<EventType> eventTypes = new LinkedList();

            for (EventType item : EventType.values()) {
                // skip unnecessary event types
                if (item == EventType.Rehearsal) {
                    continue;
                }

                eventTypes.add(item);
            }

            EventType[] list = new EventType[eventTypes.size()];
            _eventTypeList = eventTypes.toArray(list);
        }

        return _eventTypeList;
    }

    public static RequestType[] getRequestTypeList() {
        if (_requestTypeList == null) {
            _requestTypeList = RequestType.values();
        }

        return _requestTypeList;
    }

    public static EventDutyProperty[] getEventDutys() {
        if(_eventDutyList != null) {
            _eventDutyList = EventDutyProperty.values();
        }

        return _eventDutyList;
    }
}
