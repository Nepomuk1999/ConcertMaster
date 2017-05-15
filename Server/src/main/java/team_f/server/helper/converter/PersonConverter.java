package team_f.server.helper.converter;

import team_f.jsonconnector.entities.Person;
import team_f.jsonconnector.enums.Gender;
import team_f.jsonconnector.enums.InstrumentType;
import team_f.jsonconnector.enums.PersonRole;

public class PersonConverter {
    public static Person convertToJSON(team_f.domain.entities.Person person) {
        Person result = new Person();
        result.setPersonID(person.getPersonID());
        result.setFirstname(person.getFirstname());
        result.setLastname(person.getLastname());
        result.setPhoneNumber(person.getPhoneNumber());
        result.setInitials(person.getInitials());

        if (!person.getPersonRole().equals(team_f.domain.enums.PersonRole.External_musician)) {
            if (person.getAccount() != null) {
                result.setAccount(AccountConverter.convertToJSON(person.getAccount()));
            }
        }

        result.setPersonRole(PersonRole.valueOf(String.valueOf(person.getPersonRole())));

        if(person.getGender().equals("m")) {
            result.setGender(Gender.MALE);
        } else if(person.getGender().equals("w")){
            result.setGender(Gender.FEMALE);
        }

        result.setEmail(person.getEmail());
        result.setAddress(person.getAddress());

        if(person.getPlayedInstruments() != null) {
            // use only the first one
            if(person.getPlayedInstruments().size() > 0) {
                result.setInstrumentType(InstrumentType.valueOf(String.valueOf(person.getPlayedInstruments().get(0))));
            }
        }

        return result;
    }
}
