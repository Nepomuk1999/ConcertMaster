package team_f.application;

import javafx.util.Pair;
import team_f.database_wrapper.facade.InstrumentationFacade;
import team_f.database_wrapper.facade.SessionFactory;
import team_f.domain.entities.Instrumentation;
import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.DomainEntity;
import team_f.domain.logic.DomainEntityManager;
import team_f.domain.logic.InstrumentationLogic;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;

public class InstrumentationApplication {
    private EntityManager session = SessionFactory.getSession();
    private InstrumentationFacade instrumentationFacade = new InstrumentationFacade(session);

    public InstrumentationApplication() {
    }

    public void closeSession() {
        instrumentationFacade.closeSession();
    }

    public List<Instrumentation> getInstrumentationList() {
        return instrumentationFacade.getInstrumentations();
    }

    public Pair<DomainEntity, List<Pair<String, String>>> addInstrumentation (Integer violin1, Integer violin2, Integer viola, Integer violincello,
                                                                              Integer doublebass, Integer flute, Integer oboe, Integer clarinet, Integer bassoon, Integer horn, Integer trumpet,
                                                                              Integer trombone, Integer tube, Integer kettledrum, Integer percussion, Integer harp) {
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

        InstrumentationLogic instrumentationLogic = (InstrumentationLogic) DomainEntityManager.getLogic(EntityType.INSTRUMENTATION);

        List<Pair<String, String>> errorList = instrumentationLogic.validate(instrumentation);

        if (errorList.size() > 0) {
            return new Pair<>(instrumentation, errorList);
        }

        instrumentationFacade.addInstrumentation(instrumentation);

        return new Pair<>(instrumentation, new LinkedList<>());
    }
}
