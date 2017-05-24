package Domain.Duty;

import Domain.Event.EventDomainInterface;
import Domain.Person.PersonDomainObject;
import Enums.DutyDispositionStatus;

/**
 * @author Drazen Lukic
 */
public interface DutyDomainInterface extends DutyViewInterface {
    void setEvent(EventDomainInterface event);

    void setDescription(String description);

    void setPoints(double points);

    void setMusician(PersonDomainObject musician);

    void setStatus(DutyDispositionStatus status);
}
