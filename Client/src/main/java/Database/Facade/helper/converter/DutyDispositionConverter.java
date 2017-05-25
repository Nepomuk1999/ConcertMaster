package Database.Facade.helper.converter;

import Domain.Duty.DutyDispositionDomainObject;
import Enums.DutyDispositionStatus;
import team_f.jsonconnector.entities.DutyDisposition;

public class DutyDispositionConverter {
    public static DutyDispositionDomainObject convert(DutyDisposition dutyDisposition) {
        DutyDispositionDomainObject dutyDispositionDomain = new DutyDispositionDomainObject();
        dutyDispositionDomain.setDescription(dutyDisposition.getDescription());
        dutyDispositionDomain.setPoints(dutyDisposition.getPoints());
        dutyDispositionDomain.setStatus(DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getDutyDispositionStatus())));

        if(dutyDisposition.getEventDuty() != null) {
            dutyDispositionDomain.setEvent(EventDutyConverter.convert(dutyDisposition.getEventDuty()));
        }

        if(dutyDisposition.getPerson() != null) {
            dutyDispositionDomain.setMusician(PersonConverter.convert(dutyDisposition.getPerson()));
        }

        return dutyDispositionDomain;
    }

    public static DutyDisposition convert(DutyDispositionDomainObject dutyDisposition) {
        DutyDisposition result = new DutyDisposition();
        result.setDescription(dutyDisposition.getDescription());
        result.setPoints(dutyDisposition.getPoints());
        result.setDutyDispositionStatus(team_f.jsonconnector.enums.DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getStatus())));

        if(dutyDisposition.getEvent() != null) {
            result.setEventDuty(EventDutyConverter.convert(dutyDisposition.getEvent()));
        }

        if(dutyDisposition.getMusician() != null) {
            result.setPerson(PersonConverter.convert(dutyDisposition.getMusician()));
        }

        return result;
    }
}
