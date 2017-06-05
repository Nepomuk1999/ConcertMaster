package team_f.client.singletons;

import team_f.application.PersonApplication;
import team_f.client.configuration.Configuration;
import team_f.client.helper.converter.PersonConverter;
import team_f.client.pages.PageAction;
import team_f.client.pages.musicianmanagement.MusicianManagement;
import team_f.client.pages.musicianmanagement.PersonParameter;
import team_f.client.pages.musicianmanagement.PersonReturnValue;
import team_f.domain.enums.AllInstrumentTypes;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.errorlist.PersonErrorList;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MusiciansTableSingleton {
    private static MusicianManagement _musicianTable;
    private static PersonApplication _facade = PersonApplication.getInstance();
    private static Configuration _configuration;

    private MusiciansTableSingleton() {
    }

    public static MusicianManagement getInstance(Configuration configuration) {
        if (_musicianTable == null) {
            _configuration = configuration;
            _musicianTable = new MusicianManagement();

            _musicianTable.setOnLoad(new PageAction<PersonReturnValue, PersonParameter>() {
                @Override
                public PersonReturnValue doAction(PersonParameter value) {
                    return null;
                }
            });

            _musicianTable.setOnLoadList(new PageAction<List<Person>, PersonParameter>() {
                @Override
                public List<Person> doAction(PersonParameter value) {
                    if(value != null) {
                        List<team_f.domain.entities.Person> personEntityList = _facade.getList();
                        List<team_f.jsonconnector.entities.Person> personList = new LinkedList<>();
                        team_f.jsonconnector.entities.Person person;

                        for(team_f.domain.entities.Person item : personEntityList) {
                            person = PersonConverter.convertToJSON(item);
                            personList.add(person);
                        }

                        return personList;
                    }

                    return null;
                }
            });

            _musicianTable.setOnCreate(new PageAction<PersonErrorList, Person>() {
                @Override
                public PersonErrorList doAction(Person value) {
                    return addEdit(value);
                }
            });

            _musicianTable.setOnEdit(new PageAction<PersonErrorList, Person>() {
                @Override
                public PersonErrorList doAction(Person value) {
                    if(value != null) {
                        return addEdit(value);
                    }

                    return null;
                }
            });

            _musicianTable.setOnDelete(new PageAction<PersonErrorList, Person>() {
                @Override
                public PersonErrorList doAction(Person value) {
                    // @TODO: implement
                    return null;
                }
            });
        }

        return _musicianTable;
    }

    private static PersonErrorList addEdit(Person person) {
        team_f.domain.enums.PersonRole personRole = null;
        String username = null;
        team_f.domain.enums.AccountRole accountRole = null;
        javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> tmpErrorList;

        try {
            personRole = team_f.domain.enums.PersonRole.valueOf(String.valueOf(person.getPersonRole()));
        } catch (Exception e) {
        }

        if(person.getAccount() != null) {
            username = person.getAccount().getUsername();
            accountRole = team_f.domain.enums.AccountRole.valueOf(String.valueOf(person.getAccount().getRole()));
        }

                    /*List<Integer> instrumentTypeList = new ArrayList<>();

                    if(person.getInstrumentTypeList() != null) {
                        for(team_f.jsonconnector.entities.InstrumentType instrumentType : person.getInstrumentTypeList()) {
                            if(instrumentType != null) {
                                instrumentTypeList.add(instrumentType.getID());
                            }
                        }
                    }*/

        List<AllInstrumentTypes> instrumentTypeList = new ArrayList<>();

        if(person.getInstrumentTypeList() != null) {
            for(team_f.jsonconnector.enums.InstrumentType instrumentType : person.getInstrumentTypeList()) {
                try {
                    instrumentTypeList.add(AllInstrumentTypes.valueOf(String.valueOf(instrumentType)));
                } catch (Exception e) {
                }
            }
        }

        tmpErrorList = _facade.add(person.getPersonID(), person.getFirstname(), person.getLastname(), String.valueOf(person.getGender()), person.getAddress(), person.getEmail(),
                person.getPhoneNumber(), 0, personRole, username, accountRole, instrumentTypeList);

        PersonErrorList personErrorList = new PersonErrorList();
        Error error;
        List<Error> errors = new LinkedList<>();

        if(tmpErrorList != null) {
            for(javafx.util.Pair<String, String> item : tmpErrorList.getValue()) {
                error = new Error();
                error.setKey(item.getKey());
                error.setValue(item.getValue());

                errors.add(error);
            }
        }

        List<team_f.jsonconnector.entities.Pair<Person, List<Error>>> list = new LinkedList<>();
        team_f.jsonconnector.entities.Pair pair = new team_f.jsonconnector.entities.Pair();
        pair.setKey(PersonConverter.convertToJSON((team_f.domain.entities.Person) tmpErrorList.getKey()));
        pair.setValue(errors);
        list.add(pair);
        personErrorList.setErrorList(list);

        return personErrorList;
    }
}


