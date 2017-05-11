package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.MusicalWorkFacade;
import team_f.domain.entities.Instrumentation;
import team_f.domain.entities.MusicalWork;
import team_f.domain.interfaces.DomainEntity;

import java.util.LinkedList;
import java.util.List;

public class MusicalWorkApplication {
    private MusicalWorkFacade musicalworkfacade = new MusicalWorkFacade();

    public MusicalWorkApplication(){
    }

    public void closeSession() {
        musicalworkfacade.closeSession();
    }

    // @TODO: the servlet layer should not create a Instrumentation
    public Pair<DomainEntity, List<Pair<String, String>>> addMusicalWork(String name, String composer, Integer violin1, Integer violin2, Integer viola, Integer violincello,
                                                                         Integer doublebass, Integer flute, Integer oboe, Integer clarinet, Integer bassoon, Integer horn,
                                                                         Integer trumpet, Integer trombone, Integer tube, Integer kettledrum, Integer percussion, Integer harp) {
        MusicalWork musicalWork = new MusicalWork();
        Instrumentation instrumentation = new Instrumentation();

        instrumentation.setViolin1(violin1);
        instrumentation.setViolin2(violin2);
        instrumentation.setViola(viola);
        instrumentation.setViolincello(violincello);
        instrumentation.setDoublebass(doublebass);
        instrumentation.setFlute(flute);
        instrumentation.setOboe(oboe);
        instrumentation.setClarinet(clarinet);
        instrumentation.setBassoon(bassoon);
        instrumentation.setHorn(horn);
        instrumentation.setTrumpet(trumpet);
        instrumentation.setTrombone(trombone);
        instrumentation.setTube(tube);
        instrumentation.setKettledrum(kettledrum);
        instrumentation.setPercussion(percussion);
        instrumentation.setHarp(harp);

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
