package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.PersonFacade;
import team_f.domain.entities.Instrument;
import team_f.domain.entities.Person;
import team_f.domain.enums.InstrumentType;
import java.util.LinkedList;
import java.util.List;

public class PersonApplication {
    private PersonFacade personFacade = new PersonFacade();

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
}
