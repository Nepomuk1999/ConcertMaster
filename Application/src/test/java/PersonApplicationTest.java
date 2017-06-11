import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import team_f.application.PersonApplication;
import team_f.database_wrapper.facade.PersonFacade;
import team_f.domain.entities.Account;
import team_f.domain.entities.Person;
import team_f.domain.enums.AccountRole;
import team_f.domain.enums.AllInstrumentTypes;
import team_f.domain.enums.PersonRole;

import java.util.ArrayList;
import java.util.List;

public class PersonApplicationTest {

    private PersonApplication personApplication;
    private PersonFacade personFacade;
    private int deleteId = 0;


    @Before
    public void setUp() {
        personApplication = PersonApplication.getInstance();
        personFacade = new PersonFacade();
    }

    @After
    public void clearfromDB() {
        personFacade.delete(deleteId);
    }

    @Test
    public void registerPerson() {
        Person person = new Person();
        person.setPersonID(0);
        person.setFirstname("John");
        person.setLastname("Doe");
        person.setGender("m");
        person.setAddress("Beispielstraße 123");
        person.setPhoneNumber("06641238741");
        person.setEmail("john.doe@testmail.com");
        person.setInitials("JD");
        person.setPersonRole(PersonRole.Concert_Master);

        Account account = new Account();
        account.setUsername("jodo123");
        account.setAccountID(0);
        account.setRole(AccountRole.Musician);

        person.setAccount(account);

        ArrayList<AllInstrumentTypes> instrumentTypeList = new ArrayList<>();
        instrumentTypeList.add(AllInstrumentTypes.FIRSTVIOLIN);
        person.setPlayedInstruments(instrumentTypeList);

        personApplication.add(person.getPersonID(), person.getFirstname(), person.getLastname(), person.getGender(), person.getAddress(),
                person.getEmail(), person.getPhoneNumber(), person.getAccount().getAccountID(), person.getPersonRole(),
                person.getAccount().getUsername(),person.getAccount().getRole(), instrumentTypeList);

        deleteId = person.getPersonID();

        List<Person> personArrayList = personApplication.getList();
        Person newPerson = personArrayList.get(personArrayList.size()-1);

        deleteId = newPerson.getPersonID();

        Assert.assertEquals(person.getFirstname(), newPerson.getFirstname());
        Assert.assertEquals(person.getLastname(), newPerson.getLastname());
        Assert.assertEquals(person.getAccount().getUsername(), newPerson.getAccount().getUsername());
        Assert.assertEquals(person.getAccount().getPassword(), newPerson.getAccount().getPassword());
        Assert.assertEquals(person.getAccount().getRole(), newPerson.getAccount().getRole());
        Assert.assertEquals(person.getGender(), newPerson.getGender());
        Assert.assertEquals(person.getAddress(), newPerson.getAddress());
        Assert.assertEquals(person.getEmail(), newPerson.getEmail());
        Assert.assertEquals(person.getPhoneNumber(), newPerson.getPhoneNumber());
        Assert.assertEquals(person.getPersonRole(), newPerson.getPersonRole());
        Assert.assertEquals(person.getInitials(), newPerson.getInitials());
        Assert.assertEquals(person.getPlayedInstruments(), newPerson.getPlayedInstruments());
        return;
    }
}