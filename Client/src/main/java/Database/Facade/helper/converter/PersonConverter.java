package Database.Facade.helper.converter;

import Domain.Duty.DutyViewInterface;
import Domain.Person.PartDomainObject;
import Domain.Person.PersonDomainObject;
import Enums.PersonRole;
import team_f.client.configuration.Configuration;
import team_f.jsonconnector.entities.DutyDisposition;
import team_f.jsonconnector.entities.Person;
import team_f.jsonconnector.enums.Gender;
import team_f.jsonconnector.enums.InstrumentType;
import java.util.ArrayList;
import java.util.List;

public class PersonConverter {
    public static PersonDomainObject convert(Person person, Configuration configuration) {
        PersonDomainObject personDomain = new PersonDomainObject();
        personDomain.setId(person.getPersonID());
        personDomain.setAddress(person.getAddress());
        personDomain.setEmail(person.getEmail());
        personDomain.setFirstName(person.getFirstname());
        personDomain.setLastName(person.getLastname());
        personDomain.setGender(String.valueOf(person.getGender()));
        personDomain.setInitials(person.getInitials());

        personDomain.setPersonRole(PersonRole.valueOf(String.valueOf(person.getPersonRole())));
        personDomain.setPhoneNumber(person.getPhoneNumber());

        if(person.getAccount() != null) {
            personDomain.setAccount(AccountConverter.convert(person.getAccount()));
        }

        if(person.getInstrumentTypeList() != null) {
            List<PartDomainObject> partDomainList = new ArrayList<>(person.getInstrumentTypeList().size());

            for (team_f.jsonconnector.enums.InstrumentType part : person.getInstrumentTypeList()) {
                partDomainList.add(PartConverter.convert(part));
            }

            partDomainList.add(PartConverter.convert(InstrumentType.BASSOON));

            personDomain.setParts(partDomainList);
        }

        if(person.getDutyDispositionList() != null) {
            List<DutyViewInterface> eventDutyList = new ArrayList<>(person.getDutyDispositionList().size());

            for(DutyDisposition dutyDisposition : person.getDutyDispositionList()) {
                eventDutyList.add(DutyConverter.convert(dutyDisposition, configuration));
            }

            personDomain.setDuties(eventDutyList);
        }

        return personDomain;
    }

    public static Person convert(PersonDomainObject person) {
        Person result = new Person();
        result.setPersonID(person.getId());
        result.setAddress(person.getAddress());
        result.setEmail(person.getEmail());
        result.setFirstname(person.getFirstName());
        result.setLastname(person.getLastName());

        switch (person.getGender()) {
            case "m":
                result.setGender(Gender.MALE);
                break;
            case "w":
                result.setGender(Gender.FEMALE);
                break;
        }

        result.setInitials(person.getInitials());

        result.setPersonRole(team_f.jsonconnector.enums.PersonRole.valueOf(String.valueOf(person.getPersonRole())));
        result.setPhoneNumber(person.getPhoneNumber());

        if(person.getAccount() != null) {
            result.setAccount(AccountConverter.convert(person.getAccount()));
        }

        if(person.getParts() != null) {
            List<InstrumentType> instrumentTypeList = new ArrayList<>(person.getParts().size());

            for(PartDomainObject part : person.getParts()) {
                instrumentTypeList.add(PartConverter.convert(part));
            }

            result.setInstrumentTypeList(instrumentTypeList);
        }

        return result;
    }
}
