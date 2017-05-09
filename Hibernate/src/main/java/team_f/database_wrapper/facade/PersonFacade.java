package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.AccountEntity;
import team_f.database_wrapper.entities.MusicianPartEntity;
import team_f.database_wrapper.entities.PersonEntity;
import team_f.domain.entities.Account;
import team_f.domain.entities.Person;
import team_f.domain.enums.InstrumentType;
import team_f.domain.enums.PersonRole;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PersonFacade extends BaseDatabaseFacade {
    private static AccountFacade _accountFacade = new AccountFacade();
    private static InstrumentTypeFacade _instrumentTypeFacade = new InstrumentTypeFacade();

    public PersonFacade() {
        super();
    }

    public PersonFacade(EntityManager session) {
        super(session);
    }

    /**
     * Function to get all Musicians. Returns a List of Persons
     *
     * @return musicians      List<Person>         returns a list of persons
     */
    public List<Person> getAllMusicians() {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from PersonEntity");

        List<PersonEntity> musicianEntities = query.getResultList();
        List<Person> musicians = new ArrayList<>();
        Person person;

        for (PersonEntity entity : musicianEntities) {
            person = convertToPerson(entity);

            Collection<String> playedInstruments = _instrumentTypeFacade.getPlayedInstrumentsByPersonId(entity.getPersonId());

            if(playedInstruments != null && playedInstruments.size() > 0) {
                // set only the first item because musicians cannot play multiple instruments in the orchestra (is only a feature for the future)
                for (String instrumentType: playedInstruments) {
                    try {
                        person.addPlayedInstrument(InstrumentType.valueOf(instrumentType.replace(" ", "").toUpperCase()));
                    } catch (Exception e) {
                    }
                }
            }

            musicians.add(person);
        }

        return musicians;
    }

    public Integer register(Person person) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        Account account = person.getAccount();
        AccountEntity accountEntity = _accountFacade.convertToAccountEntity(account);
        session.persist(accountEntity);
        int accountId = accountEntity.getAccountId();


        PersonEntity personEntity = convertToPersonEntity(person);
        personEntity.setAccount(accountId);

        session.persist(personEntity);

        List<MusicianPartEntity> musicianPartEntities = getMusicianPartEntityFromPerson(person);

        for ( MusicianPartEntity musicianPartEntity: musicianPartEntities) {
            musicianPartEntity.setMusician(personEntity.getPersonId());
            session.persist(musicianPartEntity);
        }

        try {
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return personEntity.getPersonId();
    }

    public static boolean contains(String test) {

        for (InstrumentType c : InstrumentType.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }

    protected PersonEntity convertToPersonEntity(Person person) {
        PersonEntity entity = new PersonEntity();

        entity.setPersonId(person.getPersonID());
        entity.setAddress(person.getAddress());
        entity.setEmail(person.getEmail());
        entity.setFirstname(person.getFirstname());
        entity.setLastname(person.getLastname());
        entity.setInitials(person.getInitials());
        entity.setGender(person.getGender());
        entity.setPhoneNumber(person.getPhoneNumber());
        entity.setPersonRole(team_f.database_wrapper.enums.PersonRole.valueOf(String.valueOf(person.getPersonRole())));

        AccountEntity account = _accountFacade.convertToAccountEntity(person.getAccount());
        entity.setAccountByAccount(account);

        return entity;
    }

    protected List<MusicianPartEntity> getMusicianPartEntityFromPerson (Person person) {
        List<MusicianPartEntity> musicianPartEntities = new LinkedList();

        for (InstrumentType instrumentType : person.getPlayedInstruments()) {
            MusicianPartEntity musicianPartEntity = new MusicianPartEntity();
            musicianPartEntity.setPart(_instrumentTypeFacade.getPartIdByPlayedInstrument(instrumentType));
            musicianPartEntities.add(musicianPartEntity);
        }

        return musicianPartEntities;
    }



    /**
     * Function to convert a PersonEntity object to a Person. Returns the Person after creating and setting information from PersonyEntity object.
     * @return person      Person        returns a person object
     */
    private Person convertToPerson(PersonEntity pe) {
        Person person = new Person();

        person.setPersonID(pe.getPersonId());
        person.setFirstname(pe.getFirstname());
        person.setLastname(pe.getLastname());
        person.setAddress(pe.getAddress());
        person.setEmail(pe.getEmail());
        person.setGender(pe.getGender());
        person.setInitials(pe.getInitials());
        person.setPhoneNumber(pe.getPhoneNumber());
        person.setPersonRole(PersonRole.valueOf(pe.getPersonRole().toString()));

        AccountEntity accountEntity = pe.getAccountByAccount();

        if(accountEntity != null) {
            person.setAccount(_accountFacade.convertToAccount(accountEntity));
        }

        return person;
    }
}
