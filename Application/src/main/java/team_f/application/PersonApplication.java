package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.AccountFacade;
import team_f.database_wrapper.facade.PersonFacade;
import team_f.domain.entities.Account;
import team_f.domain.entities.Instrument;
import team_f.domain.entities.Person;
import team_f.domain.enums.*;
import team_f.domain.interfaces.DomainEntity;
import team_f.domain.logic.AccountLogic;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.PersonLogic;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

public class PersonApplication {
    private PersonFacade personFacade = new PersonFacade();
    private AccountFacade accountFacade = new AccountFacade();

    public PersonApplication() {
    }

    public void closeSession() {
        personFacade.closeSession();
    }

    public List<Person> getAllMusicians() {
        return personFacade.getAllMusicians();
    }

    public List<Pair<InstrumentType, List<Person>>> getMusicianListByPlayedInstrumentType(List<Person> persons) {
        List<Pair<InstrumentType, List<Person>>> list = new LinkedList<>();
        List<Person> instrumentList = new LinkedList<>();

        for (InstrumentType instrumentType : InstrumentType.values()) {
            Pair<InstrumentType, List<Person>> pair = new Pair<>(instrumentType, instrumentList);

            for (Person person : persons) {
                for(Instrument instrument : person.getInstruments()) {
                    if(instrument.getInstrumentType() == instrumentType) {
                        instrumentList.add(person);
                    }
                }
            }

            list.add(pair);
        }

        return list;
    }

    public Pair<DomainEntity, List<Pair<String, String>>> register(String firstname, String lastname, String gender, String address,
                                                                   String email, String phoneNumber, PersonRole personRole, String username,
                                                                   AccountRole accountRole, List<InstrumentType> instrumentTypeList) {

        Person person = new Person();
        person.setFirstname(firstname);
        person.setLastname(lastname);
        person.setGender(gender);
        person.setAddress(address);
        person.setEmail(email);
        person.setPhoneNumber(phoneNumber);
        person.setPersonRole(personRole);
        //person.addInstrument(instrument);
        person.setPlayedInstruments(instrumentTypeList);

        if(firstname != null && lastname != null && firstname.length() > 0 && lastname.length() > 0) {
            person.setInitials("" + firstname.charAt(0) + lastname.charAt(0));
        }

        Account account = new Account();
        account.setUsername(username);
        account.setRole(accountRole);

        SecureRandom random = new SecureRandom();
        // @TODO: save a hashed value to the DB
        account.setPassword(new BigInteger(130, random).toString(32));

        person.setAccount(account);

        PersonLogic personLogic = (PersonLogic) DomainEntityManager.getLogic(EntityType.PERSON);

        List<Account> accountList= accountFacade.getAllUserNames();
        List<Person> personList = getAllMusicians();


        AccountLogic accountLogic = (AccountLogic) DomainEntityManager.getLogic((EntityType.ACCOUNT));

        List<Pair<String, String>> errorList = accountLogic.validate(account);
        errorList.addAll(personLogic.validate(person));

        for(Person p : personList){
            if(p.getFirstname().equals(firstname) && p.getLastname().equals(lastname) && p.getGender().equals(gender) && p.getAddress().equals(address)
                    && p.getEmail().equals(email) && p.getPhoneNumber().equals(phoneNumber) && p.getPersonRole().equals(personRole) && p.getPlayedInstruments().equals(instrumentTypeList)){
                errorList.add(new Pair<>(String.valueOf(PersonProperty.FIRSTNAME), "this musician already exists"));
            }
        }

        for(Account ac : accountList){
            if(ac.getUsername().equals(username)){
                errorList.add(new Pair<>(String.valueOf(AccountProperty.USERNAME), "username already exists"));
            }
        }


        if (errorList.size() > 0) {
            return new Pair<>(person, errorList);
        }

        Integer id = personFacade.register(person);
        person.setPersonID(id);

        return new Pair<>(person, new LinkedList<>());
    }
}
