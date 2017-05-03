package team_f.application;

import team_f.database_wrapper.facade.InstrumentationFacade;
import team_f.database_wrapper.facade.PersonFacade;
import team_f.database_wrapper.facade.SessionFactory;
import team_f.domain.entities.Instrumentation;
import javax.persistence.EntityManager;
import java.util.List;

public class InstrumentationApplication {
    private EntityManager session = SessionFactory.getSession();
    private PersonFacade personFacade = new PersonFacade(session);
    private InstrumentationFacade instrumentationFacade = new InstrumentationFacade(session);

    public InstrumentationApplication() {
    }

    public void closeSession() {
        personFacade.closeSession();
    }

    public List<Instrumentation> getInstrumentationList() {
        return instrumentationFacade.getInstrumentations();
    }
}
