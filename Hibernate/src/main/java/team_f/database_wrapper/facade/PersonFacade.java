package team_f.database_wrapper.facade;

import team_f.database_wrapper.database.*;
import team_f.domain.entities.Person;
import team_f.domain.enums.InstrumentType;
import team_f.domain.enums.PersonRole;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonFacade {
    EntityManager _session;

    public void closeSession() {
        _session.close();
        _session = null;
    }

    public PersonFacade() {
        _session = SessionFactory.getSession();
    }

    public PersonFacade(EntityManager session) {
        _session = session;
    }

    protected EntityManager getCurrentSession() {
        return _session;
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

            Collection<InstrumentEntity> instruments = entity.getInstrumentsByPersonId();

            if(instruments != null && instruments.size() > 0) {
                // set only the first item because musicians cannot play multiple instruments in the orchestra (is only a feature for the future)
                InstrumentFacade instrumentFacade = new InstrumentFacade(getCurrentSession());
                person.addInstrument(instrumentFacade.convertToInstrument(instruments.iterator().next()));
            }

            musicians.add(person);
        }

        return musicians;
    }

    public static boolean contains(String test) {

        for (InstrumentType c : InstrumentType.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
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

        return person;
    }

    /*private List<String> getPlayedInstrumentsByPersonId(int id) {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query musicianPartQuery = session.createQuery("from MusicianPartEntity where musician = :id");
        musicianPartQuery.setParameter("id", id);

        List<MusicianPartEntity> musicianPartEntities = musicianPartQuery.getResultList();
        List<String> parts = new ArrayList<>();

        for (MusicianPartEntity musicianpartEntity : musicianPartEntities) {
            Query partQuery = session.createQuery("from PartEntity where partId = :id");
            partQuery.setParameter("id", musicianpartEntity.getPart());

            List<PartEntity> partEntities = partQuery.getResultList();

            if (!(partEntities.isEmpty())) {
                Query partTypeQuery = session.createQuery("from PartTypeEntity where partTypeId = :id");
                partTypeQuery.setParameter("id", partEntities.get(0).getPartType());
                List<PartTypeEntity> partTypeEntities = partTypeQuery.getResultList();

                if (!partTypeEntities.isEmpty()) {
                    parts.add(partTypeEntities.get(0).getPartType());
                }
            }
        }

        return parts;
    }*/
}
