package team_f.application;

import team_f.database_wrapper.facade.*;
import javax.persistence.EntityManager;
import java.util.List;

public class InstrumentTypeApplication {
    private static InstrumentTypeApplication _instance;
    private EntityManager session = SessionFactory.getSession();
    private InstrumentTypeFacade instrumentTypeFacade = new InstrumentTypeFacade(session);

    private InstrumentTypeApplication() { }

    public static InstrumentTypeApplication getInstance() {
        if(_instance != null) {
            _instance = new InstrumentTypeApplication();
        }

        return _instance;
    }

    public void closeSession() {
        instrumentTypeFacade.closeSession();
    }

    public List<team_f.domain.entities.InstrumentType> getInstrumentTypeList() {
        // @TODO: implement and fix issues
        //return instrumentTypeFacade.getAllInstrumentTypes();
        return null;
    }
}