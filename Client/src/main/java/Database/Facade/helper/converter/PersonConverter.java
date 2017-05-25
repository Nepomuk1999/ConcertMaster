package Database.Facade.helper.converter;

import Domain.Person.PartDomainObject;
import Domain.Person.PersonDomainObject;
import Enums.PersonRole;
import team_f.jsonconnector.entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonConverter {
    public static PersonDomainObject convert(Person person) {
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

            for(team_f.jsonconnector.enums.InstrumentType part : person.getInstrumentTypeList()) {
                PartConverter.convert(part);
            }

            personDomain.setParts(partDomainList);
        }

        // @TODO: required for our purpose?
        // personDomain.setDuties();

        return personDomain;
    }
}
