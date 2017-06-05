package team_f.application;

import javafx.util.Pair;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team_f.application.interfaces.BaseApplicationFacade;
import team_f.database_wrapper.facade.AccountFacade;
import team_f.database_wrapper.facade.PersonFacade;
import team_f.domain.entities.Account;
import team_f.domain.entities.Person;
import team_f.domain.enums.AccountRole;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.InstrumentType;
import team_f.domain.enums.PersonRole;
import team_f.domain.enums.properties.AccountProperty;
import team_f.domain.enums.AllInstrumentTypes;
import team_f.domain.enums.properties.PersonProperty;
import team_f.domain.interfaces.DomainEntity;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class PersonApplication extends BaseApplicationFacade<Person> {
    private static PersonApplication _instance;
    private PersonFacade personFacade = new PersonFacade();
    private AccountFacade accountFacade = new AccountFacade();

    private PersonApplication() {
    }

    public static PersonApplication getInstance() {
        if(_instance == null) {
            _instance = new PersonApplication();
        }

        return _instance;
    }

    @Override
    public void closeSession() {
        personFacade.closeSession();
    }

    /** Function adds persons who play the specific instrument for every instrumentType and returns this list of
     *                  InstrumentType,List<Person>>-pairs
     * bzw
     * Function to get a list of pairs of instrumentTypes and a list of lists of persons playing them  for a list of persons
     *
     * @param persons
     * @return list     List<Pair<InstrumentType, List<Person>>>    returns list of pairs of
     *                                                              instrumentTypes with persons playing them
     */
    protected List<Pair<InstrumentType, List<Person>>> getMusicianListByPlayedInstrumentType(List<Person> persons) {
        List<Pair<InstrumentType, List<Person>>> list = new LinkedList<>();
        List<Person> instrumentList = new LinkedList<>();

        for (InstrumentType instrumentType : InstrumentType.values()) {
            Pair<InstrumentType, List<Person>> pair = new Pair<>(instrumentType, instrumentList);

            for (Person person : persons) {
                List<InstrumentType> standardInstruments = new LinkedList<>();
                for (AllInstrumentTypes instrument: person.getPlayedInstruments()) {
                    if (standardInstruments.contains(instrument.toString())) {
                        standardInstruments.add(InstrumentType.valueOf(instrument.toString()));
                    }
                }
                for(InstrumentType instrument : standardInstruments) {
                    if(instrument == instrumentType) {
                        instrumentList.add(person);
                    }
                }
            }

            list.add(pair);
        }

        return list;
    }

    /**
     * Creates domain entity Person object and sets its data.  The instruments played by the person (instrumentTypeList)
     *                      is only set for the correct PersonRole (not Manager, Music_librarian, Orchestral_facility_manager)
     *                      sets the persons initials
     *                      creates domain entity Account object, sets its values and sets them to person
     *                      validates and creates errorList
     *                      updating persons is permitted (=changing their data)
     *                      checks if person with same data already exists and gives back errorList
     *                      checks if person that is not an External_musician has already used username
     *                      checks if person plays instrument
     *                      if one or more errors occurred an errorList ist returned
     *                      if no errors occur person is added with PersonFacade and the ID is set
     *
     * @param id
     * @param firstname
     * @param lastname
     * @param gender
     * @param address
     * @param email
     * @param phoneNumber
     * @param accountID
     * @param personRole
     * @param username
     * @param accountRole
     * @param instrumentTypeList
     * @return      Pair<>(person, errorList)    or     Pair<>(person, new LinkedList<>())  returns a pair of person and a list
     *                                                          if errors occured the list is the errorlist

     */
    public Pair<DomainEntity, List<Pair<String, String>>> add(int id, String firstname, String lastname, String gender, String address,
                                                                   String email, String phoneNumber, int accountID, PersonRole personRole, String username,
                                                                   AccountRole accountRole, List<AllInstrumentTypes> instrumentTypeList) {
        Person person = new Person();
        person.setPersonID(id);
        person.setFirstname(firstname);
        person.setLastname(lastname);
        person.setGender(gender);
        person.setAddress(address);
        person.setEmail(email);
        person.setPhoneNumber(phoneNumber);
        person.setPersonRole(personRole);
        //person.addInstrument(instrument);
        if (!(person.getPersonRole().equals(PersonRole.Manager)||
                person.getPersonRole().equals(PersonRole.Music_librarian))||
                person.getPersonRole().equals(PersonRole.Orchestral_facility_manager)) {
            // @TODO: use the id instead of the string
            person.setPlayedInstruments(instrumentTypeList);
        }

        if(firstname != null && lastname != null && firstname.length() > 0 && lastname.length() > 0) {
            person.setInitials("" + firstname.charAt(0) + lastname.charAt(0));
        }

        Account account = new Account();
        account.setAccountID(accountID);
        account.setUsername(username);
        account.setRole(accountRole);

        SecureRandom random = new SecureRandom();
        // @TODO: save a hashed value to the DB
        account.setPassword(new BigInteger(130, random).toString(32));

        person.setAccount(account);

        List<Account> accountList= accountFacade.getAllUserNames();
        List<Person> personList = getList();

        List<Pair<String, String>> errorList = person.validate();

        if (!personRole.equals(PersonRole.External_musician)) {
            List<Pair<String, String>> errorList2 = account.validate();
            errorList.addAll(errorList2);
        }


        if(person.getPersonID() <= 0) {
            for(Person p : personList){
                if(Objects.equals(p.getFirstname(), firstname.trim()) && Objects.equals(p.getLastname(), lastname.trim()) &&
                        Objects.equals(p.getGender(), gender) & Objects.equals(p.getAddress(), address.trim()) &&
                        Objects.equals(p.getEmail(), email.trim()) && Objects.equals(p.getPhoneNumber(), phoneNumber.trim()) &&
                        Objects.equals(p.getPersonRole(), personRole) && Objects.equals(p.getPlayedInstruments(), instrumentTypeList)) {
                    errorList.add(new Pair<>(String.valueOf(PersonProperty.FIRSTNAME), "this musician already exists"));
                }
            }
            if (!personRole.equals(PersonRole.External_musician)) {
                for (Account ac : accountList) {
                    if (ac.getUsername().equals(username.trim())) {
                        errorList.add(new Pair<>(String.valueOf(AccountProperty.USERNAME), "username already exists"));
                    }
                }
            }
        }

        if(instrumentTypeList == null){
            errorList.add(new Pair<>(String.valueOf(PersonProperty.FIRSTNAME), "no instrument selected"));
        }

        if (errorList.size() > 0) {
            return new Pair<>(person, errorList);
        }

        Integer resultID = personFacade.add(person);
        person.setPersonID(resultID);

        return new Pair<>(person, new LinkedList<>());
    }

    @Override
    public Person getByID(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Person> getList() {
        return personFacade.getAllMusicians();
    }
}
