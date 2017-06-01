package Domain.Person;

import Domain.Account.AccountDomainObject;
import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.DutyViewInterface;
import Domain.Event.EventViewInterface;
import Enums.PersonRole;

import java.util.List;

/**
 * @author Julian
 */
public interface PersonViewInterface {
    int getId();

    String getInitials();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getGender();

    String getAddress();

    String getPhoneNumber();

    PersonRole getPersonRole();

    AccountDomainObject getAccount();

    List<PartDomainObject> getParts();

    List<DutyViewInterface> getDuties();

    boolean isAvailable(EventViewInterface event);

    boolean playsInstrument(String instrument);
}
