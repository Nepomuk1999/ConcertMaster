package team_f.server.helper.converter;

import team_f.jsonconnector.entities.DutyDisposition;
import team_f.jsonconnector.enums.DutyDispositionStatus;

public class DutyDispositionConverter {
    public static DutyDisposition convertToJSON(team_f.domain.entities.DutyDisposition dutyDisposition) {
        DutyDisposition result = new DutyDisposition();
        result.setDescription(dutyDisposition.getDescription());
        result.setDutyDispositionStatus(DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getDutyDispositionStatus())));
        result.setPoints(dutyDisposition.getPoints());

        if(dutyDisposition.getEventDuty() != null) {
            result.setEventDuty(EventDutyConverter.convertToJSON(dutyDisposition.getEventDuty()));
        }

        if(dutyDisposition.getMusician() != null) {
            result.setMusician(PersonConverter.convertToJSON(dutyDisposition.getMusician()));
        }

        return result;
    }
}
