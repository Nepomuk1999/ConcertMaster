package team_f.application;

import team_f.database_wrapper.facade.PersonFacade;
import team_f.domain.entities.Person;

import java.util.List;

/**
 * Created by dominik on 28.04.17.
 */
public class PersonApplication {
    private PersonFacade personFacade = new PersonFacade();

    public PersonApplication(){}

    public void closeSession() {
        personFacade.closeSession();
    }

    public List<Person> getAllMusicians(){
        return personFacade.getAllMusicians();
    }

}
