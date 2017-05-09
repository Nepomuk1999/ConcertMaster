package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDutyList implements JSONObjectEntity {
    private List<EventDuty> _eventDutyList;

    @JsonGetter("event_duties")
    public List<EventDuty> getEventDutyList() {
        return _eventDutyList;
    }

    @JsonSetter("event_duties")
    public void setEventDutyList(List<EventDuty> eventDutyList) {
        _eventDutyList = eventDutyList;
    }
}
