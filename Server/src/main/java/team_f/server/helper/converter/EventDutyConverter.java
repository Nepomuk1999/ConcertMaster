package team_f.server.helper.converter;

import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.MusicalWork;
import team_f.jsonconnector.enums.EventStatus;
import team_f.jsonconnector.enums.EventType;
import java.util.ArrayList;
import java.util.List;

public class EventDutyConverter {
    public static EventDuty convertToJSON(team_f.domain.entities.EventDuty eventDuty) {
        EventDuty result = new EventDuty();
        result.setEventDutyID(eventDuty.getEventDutyID());
        result.setConductor(eventDuty.getConductor());
        result.setDefaultPoints(eventDuty.getDefaultPoints());
        result.setDescription(eventDuty.getDescription());
        result.setStartTime(eventDuty.getStartTime());
        result.setEndTime(eventDuty.getEndTime());
        result.setEventStatus(EventStatus.valueOf(String.valueOf(eventDuty.getEventStatus())));
        result.setEventType(EventType.valueOf(String.valueOf(eventDuty.getEventType())));
        result.setLocation(eventDuty.getLocation());

        if(eventDuty.getMusicalWorkList() != null) {
            List<MusicalWork> musicalWorks = new ArrayList<>(eventDuty.getMusicalWorkList().size());

            for(team_f.domain.entities.MusicalWork musicalWork : eventDuty.getMusicalWorkList()) {
                musicalWorks.add(MusicalWorkConverter.convertToJSON(musicalWork));
            }

            result.setMusicalWorkList(musicalWorks);
        }

        result.setName(eventDuty.getName());

        if(eventDuty.getRehearsalFor() != null) {
            result.setRehearsalFor(convertToJSON(eventDuty.getRehearsalFor()));
        }

        return result;
    }
}
