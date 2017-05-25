package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.AccountEntity;
import team_f.database_wrapper.entities.MusicianPartEntity;
import team_f.database_wrapper.entities.PersonEntity;
import team_f.database_wrapper.helper.StoreHelper;
import team_f.domain.entities.Account;
import team_f.domain.entities.DutyDisposition;
import team_f.domain.entities.Person;
import team_f.domain.enums.AllInstrumentTypes;
import team_f.domain.enums.InstrumentType;
import team_f.domain.enums.PersonRole;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PersonFacade extends BaseDatabaseFacade<Person> {
    private static AccountFacade _accountFacade = new AccountFacade();
    private static InstrumentTypeFacade _instrumentTypeFacade = new InstrumentTypeFacade();
    private static DutyDispositionFacade _dutyDispositionFacade = new DutyDispositionFacade();

    public PersonFacade() {
        super();
    }

    public PersonFacade(EntityManager session) {
        super(session);
    }

    /**
     * Function to get all Musicians. Returns a List of Persons
     *
     * creates a List<PeronEntity> from the database and from this a list of all musicians with the PersonFacades convertToPerson-method.
     * Also adds instrumentType to the person if he plays an instrument
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
                    //person.addPlayedInstrument(InstrumentType.valueOf(instrumentType));
                    try {
                        person.addPlayedInstrument(AllInstrumentTypes.valueOf(instrumentType.replace(" ", "").toUpperCase()));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }

            List<DutyDisposition> dutyDispositions = _dutyDispositionFacade.getDutyDispositionsForPersonID(entity.getPersonId());
            person.setDutyDispositions(dutyDispositions);

            musicians.add(person);
        }

        return musicians;
    }

    /** Function converts a domain entity Person object into a database_wrapper PersonEntity object
     *  then sets the account for this person with the accountEntitys ID
     *  then persists the account entity of this person
     *
     * gets the persons musicianPartEntities and sets the musician to them by id and then persists.
     * returns the personId of the personEntity
     *

     * @param person
     * @return personEntity.getPersonId()
     */
    private Integer register(Person person) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        PersonEntity personEntity = convertToPersonEntity(person);

        if (!person.getPersonRole().equals(PersonRole.External_musician)) {
            if (person.getID() > 0) {
                Query query = session.createQuery("from AccountEntity where username = :user");
                query.setParameter("user", person.getAccount().getUsername());
                query.setMaxResults(1);

                List<AccountEntity> accounts = query.getResultList();
                AccountEntity accountEntity = accounts.get(0);
                personEntity.setAccount(accountEntity.getAccountId());

            } else {
                Account account = person.getAccount();
                AccountEntity accountEntity = _accountFacade.convertToAccountEntity(account);
                session.persist(accountEntity);
                personEntity.setAccount(accountEntity.getAccountId());
            }
        }

        StoreHelper.storeEntities(session);
        session.getTransaction().begin();

        if (personEntity.getPersonId() > 0){
            session.merge(personEntity);
        } else {
            session.persist(personEntity);
            session.flush();
        }

        StoreHelper.storeEntities(session);
        session.getTransaction().begin();

        List<MusicianPartEntity> musicianPartEntities = getMusicianPartEntityFromPerson(person);

        Query query = session.createQuery("delete MusicianPartEntity where musician = :ID");
        query.setParameter("ID", personEntity.getPersonId());
        query.executeUpdate();

        for (MusicianPartEntity musicianPartEntity: musicianPartEntities) {
            musicianPartEntity.setMusician(personEntity.getPersonId());
            session.persist(musicianPartEntity);
        }

        StoreHelper.storeEntities(session);
        return personEntity.getPersonId();
    }

    //this method is not used
    public static boolean contains(String test) {

        for (InstrumentType c : InstrumentType.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }

    /** Function to convert a domain entity Person object into a database_wrapper PersonEntity object
     *
     * @param person
     * @return  entity      returns a database_wrapper PersonEntity object
     */
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

        Account account = person.getAccount();

        if(account != null) {
            entity.setAccount(account.getAccountID());
        }

        return entity;
    }

    /**Function to return the Parts a musician plays from the DB with the getPlayedInstruments method of the Person
     * domain entity object
     *
     * @param person
     * @return      musicianPartEntities        returns list of MusicianPartEntities
     */
    protected List<MusicianPartEntity> getMusicianPartEntityFromPerson (Person person) {
        List<MusicianPartEntity> musicianPartEntities = new LinkedList();

        for (AllInstrumentTypes instrumentType : person.getPlayedInstruments()) {
            MusicianPartEntity musicianPartEntity = new MusicianPartEntity();
            musicianPartEntity.setPart(_instrumentTypeFacade.getPartIdByPlayedInstrument(instrumentType));
            musicianPartEntities.add(musicianPartEntity);
        }

        return musicianPartEntities;
    }

    /**
     * Function to convert a PersonEntity object to a Person. Returns the Person after creating and setting information
     * from PersonEntity object.
     * @return person      Person        returns a person object
     */
    protected Person convertToPerson(PersonEntity pe) {
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

    @Override
    public int add(Person value) {
        return register(value);
    }

    @Override
    public int update(Person value) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
