package Domain.Duty;

import Domain.Event.EventDomainInterface;
import Domain.Person.PersonDomainObject;
import Enums.DutyDispositionStatus;

/**
 * @author Drazen Lukic
 */
public interface DutyViewInterface {
    EventDomainInterface getEvent();

    String getDescription();

    double getPoints();

    PersonDomainObject getMusician();

    DutyDispositionStatus getStatus();
}
