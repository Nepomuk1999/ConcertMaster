package Database.Facade.helper.converter;

import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.DutyDomainInterface;
import Enums.DutyDispositionStatus;
import team_f.jsonconnector.entities.DutyDisposition;

public class DutyConverter {
    public static DutyDomainInterface convert(DutyDisposition dutyDisposition) {
        DutyDispositionDomainObject dutyDispositionDomain = new DutyDispositionDomainObject();
        dutyDispositionDomain.setStatus(DutyDispositionStatus.valueOf(String.valueOf(dutyDisposition.getDutyDispositionStatus())));
        dutyDispositionDomain.setPoints(dutyDisposition.getPoints());
        dutyDispositionDomain.setMusician(PersonConverter.convert(dutyDisposition.getPerson()));
        dutyDispositionDomain.setEvent(EventDutyConverter.convert(dutyDisposition.getEventDuty()));
        dutyDispositionDomain.setDescription(dutyDisposition.getDescription());

        // nothing to do, because it is not implemented in the code from team E

        return dutyDispositionDomain;
    }
}
