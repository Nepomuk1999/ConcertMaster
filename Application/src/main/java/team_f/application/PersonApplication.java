package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.PersonFacade;
import team_f.domain.entities.Person;
import team_f.domain.enums.InstrumentType;

import java.util.LinkedList;
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

    public List<Pair<InstrumentType, List<Person>>> getMusicianListByPlayedInstrument(List<Person> persons) {
        List<Pair<InstrumentType, List<Person>>> list = new LinkedList<>();

        for (InstrumentType instrumentType : InstrumentType.values()) {
            List<Person> instrumentList = new LinkedList<>();
            Pair pair = new Pair<>(instrumentType, instrumentList);

            for(Person person : persons) {
                for (String instrument : person.getInstruments())
                    if (instrumentType.equals(instrument)) {
                        instrumentList.add(person);
                    }
            }

            list.add(pair);
        }

        return list;
    }
}
