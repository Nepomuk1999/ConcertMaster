package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

public class EventDutyList implements JSONObjectEntity {
    private List<EventDuty> _eventDutyList;

    @JsonGetter("eventDuties")
    public List<EventDuty> getEventDutyList() {
        return _eventDutyList;
    }

    @JsonSetter("eventDuties")
    public void setEventDutyList(List<EventDuty> eventDutyList) {
        _eventDutyList = eventDutyList;
    }
}
