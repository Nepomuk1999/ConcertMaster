package team_f.application;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team_f.application.interfaces.BaseApplicationFacade;
import team_f.database_wrapper.facade.*;
import team_f.domain.entities.InstrumentType;
import javax.persistence.EntityManager;
import java.util.List;

public class InstrumentTypeApplication extends BaseApplicationFacade<InstrumentType> {
    private static InstrumentTypeApplication _instance;
    private EntityManager session = SessionFactory.getSession();
    private InstrumentTypeFacade instrumentTypeFacade = new InstrumentTypeFacade(session);

    private InstrumentTypeApplication() { }

    public static InstrumentTypeApplication getInstance() {
        if(_instance == null) {
            _instance = new InstrumentTypeApplication();
        }

        return _instance;
    }

    @Override
    public void closeSession() {
        instrumentTypeFacade.closeSession();
    }

    @Override
    public InstrumentType getByID(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<InstrumentType> getList() {
        // @TODO: implement and fix issues
        //return instrumentTypeFacade.getAllInstrumentTypes();
        throw new NotImplementedException();
    }
}