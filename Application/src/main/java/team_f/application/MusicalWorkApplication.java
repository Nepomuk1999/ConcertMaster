package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.MusicalWorkFacade;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.interfaces.DomainEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dominik on 09.05.17.
 */
public class MusicalWorkApplication {
    private MusicalWorkFacade musicalworkfacade = new MusicalWorkFacade();

    public MusicalWorkApplication(){

    }

    public void closeSession() {
        musicalworkfacade.closeSession();
    }

    public Pair<DomainEntity, List<Pair<String, String>>> addMusicalWork(int id, String name, String composer, Instrumentation instrumentation) {
        MusicalWork musicalWork = new MusicalWork();

       musicalWork.setMusicalWorkID(id);
       musicalWork.setName(name);
       musicalWork.setComposer(composer);
       musicalWork.setInstrumentation(instrumentation);

        musicalworkfacade.addMusicalWork(musicalWork);

        return new Pair<>(musicalWork, new LinkedList<>());
    }

    public List<MusicalWork> getMusicalWorkList(){
        return musicalworkfacade.getMusicalWorks();
    }
}
