package team_f.client.helper.converter;

import team_f.domain.enums.AllInstrumentTypes;
import team_f.jsonconnector.entities.DutyDisposition;
import team_f.jsonconnector.entities.Person;
import team_f.jsonconnector.enums.Gender;
import team_f.jsonconnector.enums.InstrumentType;
import team_f.jsonconnector.enums.PersonRole;

import java.util.ArrayList;
import java.util.List;

public class PersonConverter {
    public static Person convertToJSON(team_f.domain.entities.Person person) {
        Person result = new Person();
        result.setPersonID(person.getPersonID());
        result.setFirstname(person.getFirstname());
        result.setLastname(person.getLastname());
        result.setPhoneNumber(person.getPhoneNumber());
        result.setInitials(person.getInitials());

        if (!String.valueOf(team_f.domain.enums.PersonRole.External_musician).equals(person.getPersonRole())) {
            if (person.getAccount() != null) {
                result.setAccount(AccountConverter.convertToJSON(person.getAccount()));
            }
        }

        if(person.getPersonRole() != null) {
            result.setPersonRole(PersonRole.valueOf(String.valueOf(person.getPersonRole())));
        }

        if(person.getGender() != null) {
            if(person.getGender().equals("m")) {
                result.setGender(Gender.MALE);
            } else if(person.getGender().equals("w")){
                result.setGender(Gender.FEMALE);
            }
        }

        result.setEmail(person.getEmail());
        result.setAddress(person.getAddress());

        if(person.getPlayedInstruments() != null) {
            List<InstrumentType> instrumentTypeList = new ArrayList<>(person.getPlayedInstruments().size());

            for(AllInstrumentTypes instrumentType : person.getPlayedInstruments()) {
                try {
                    instrumentTypeList.add(InstrumentType.valueOf(String.valueOf(instrumentType)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            result.setInstrumentTypeList(instrumentTypeList);
        }

        if(person.getDutyDispositionList() != null) {
            List<DutyDisposition> eventDutyList = new ArrayList<>(person.getDutyDispositionList().size());

            for(team_f.domain.entities.DutyDisposition dutyDisposition : person.getDutyDispositionList()) {
                eventDutyList.add(DutyDispositionConverter.convertToJSON(dutyDisposition));
            }

            result.setDutyDispositionList(eventDutyList);
        }

        return result;
    }
}
