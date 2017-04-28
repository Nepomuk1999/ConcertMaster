package team_f.database_wrapper.facade;

import team_f.database_wrapper.database.MusicianPartEntity;
import team_f.database_wrapper.database.PartEntity;
import team_f.database_wrapper.database.PartTypeEntity;
import team_f.database_wrapper.database.PersonEntity;
import team_f.domain.entities.Person;
import team_f.domain.enums.PersonRole;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dominik on 28.04.17.
 */
public class PersonFacade {
    EntityManager _session;

    public void closeSession() {
        _session.close();
        _session = null;
    }

    protected EntityManager getCurrentSession(){
        return _session;
    }

    /**
     * Function to get all Musicians. Returns a List of Persons
     * @return musicians      List<Person>         returns a list of persons
     */
    public List<Person> getAllMusicians() {

        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query query = session.createQuery("from PersonEntity");

        List<PersonEntity> musicianEntities = query.getResultList();
        List<Person> musicians = new ArrayList<>();

        for (PersonEntity entity : musicianEntities) {
            Person person = new Person();

            person = convertToPerson(entity);
            person.setInstruments(getPlayedInstrumentsByPersonId(person.getPersonId()));

            musicians.add(person);
        }

        return musicians;
    }

    private Person convertToPerson(PersonEntity pe) {
        Person person = new Person();

        person.setPersonId(pe.getPersonId());
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

    private List<String> getPlayedInstrumentsByPersonId(int id){
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query musicianPartQuery = session.createQuery("from MusicianPartEntity where musician = :id");
        musicianPartQuery.setParameter("id", id);

        List<MusicianPartEntity> musicianPartEntities = musicianPartQuery.getResultList();
        List<String> parts = new ArrayList<>();

        for(MusicianPartEntity musicianpartEntity : musicianPartEntities) {
            Query partQuery = session.createQuery("from PartEntity where partId = :id");
            partQuery.setParameter("id", musicianpartEntity.getPart());

            List<PartEntity> partEntities = partQuery.getResultList();

            if(!(partEntities.isEmpty())) {
                Query partTypeQuery = session.createQuery("from PartTypeEntity where partTypeId = :id");
                partTypeQuery.setParameter("id", partEntities.get(0).getPartType());
                List<PartTypeEntity> partTypeEntities = partTypeQuery.getResultList();

                if(!partTypeEntities.isEmpty()) {
                    parts.add(partTypeEntities.get(0).getPartType());
                }
            }
        }

        return parts;
    }

}
