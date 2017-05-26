package Database.Facade.helper.converter;

import Domain.Duty.DutyDispositionDomainObject;
import Enums.DutyDispositionStatus;
import team_f.client.configuration.Configuration;
import team_f.jsonconnector.entities.DutyDisposition;

public class DutyDispositionConverter {
    public static DutyDispositionDomainObject convert(DutyDisposition dutyDisposition, Configuration configuration, int level) {
        DutyDispositionDomainObject dutyDispositionDomain = new DutyDispositionDomainObject();
        dutyDispositionDomain.setDescription(dutyDisposition.getDescription());
        dutyDispositionDomain.setPoints(dutyDisposition.getPoints());
        dutyDispositionDomain.setStatus(DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getStatus())));

        if(dutyDisposition.getEventDuty() != null && level > 0) {
            dutyDispositionDomain.setEvent(EventDutyConverter.convert(dutyDisposition.getEventDuty(), configuration, level -1));
        }

        if(dutyDisposition.getPerson() != null) {
            dutyDispositionDomain.setMusician(PersonConverter.convert(dutyDisposition.getPerson(), configuration));
        }

        return dutyDispositionDomain;
    }

    public static DutyDisposition convert(DutyDispositionDomainObject dutyDisposition) {
        DutyDisposition result = new DutyDisposition();
        result.setDescription(dutyDisposition.getDescription());
        result.setPoints(dutyDisposition.getPoints());
        result.setStatus(team_f.jsonconnector.enums.DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getStatus())));

        if(dutyDisposition.getEvent() != null) {
            result.setEventDuty(EventDutyConverter.convert(dutyDisposition.getEvent()));
        }

        if(dutyDisposition.getMusician() != null) {
            result.setPerson(PersonConverter.convert(dutyDisposition.getMusician()));
        }

        return result;
    }
}
