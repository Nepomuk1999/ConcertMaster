package team_f.application;

import javafx.util.Pair;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team_f.application.interfaces.BaseApplicationFacade;
import team_f.database_wrapper.facade.*;
import team_f.domain.entities.*;
import team_f.domain.enums.*;
import team_f.domain.interfaces.DomainEntity;
import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;

public class DutyDispositionApplication extends BaseApplicationFacade<DutyDisposition> {
    private static DutyDispositionApplication _instance;
    private EntityManager session = SessionFactory.getSession();
    private EventFacade eventFacade = new EventFacade(session);
    private DutyDispositionFacade dutyDispositionFacade = new DutyDispositionFacade(session);

    private DutyDispositionApplication() { }

    public static DutyDispositionApplication getInstance() {
        if(_instance == null) {
            _instance = new DutyDispositionApplication();
        }

        return _instance;
    }

    @Override
    public void closeSession() {
        eventFacade.closeSession();
    }

    public double getDutyDispositionByMonthAndPersonID(int personID, int month, int year) {
        return dutyDispositionFacade.getPointsForPersonAndMonth(personID, month, year);
    }

    public List<DutyDisposition> getDutyDispositionByPersonID(int personID) {
        return dutyDispositionFacade.getDutyDispositionsForPersonID(personID);
    }

    public List<DutyDisposition> getDutyDispositionByEventID(int eventID) {
        return dutyDispositionFacade.getDutyDispositionsForEventID(eventID);
    }

    public Pair<DomainEntity, List<Pair<String, String>>> addDutyDisposition(int eventDutyID, int personID, double points, String description,
                                                                             DutyDispositionStatus dutyDispositionStatus) {
        DutyDisposition dutyDisposition = new DutyDisposition();

        EventDuty eventDuty = eventFacade.getByID(eventDutyID);
        dutyDisposition.setEventDuty(eventDuty);

        //Person person = personFacade.getPersonByID(personID);
        Person person = new Person();
        person.setPersonID(personID);
        dutyDisposition.setMusician(person);

        dutyDisposition.setPoints(points);
        dutyDisposition.setDescription(description);
        dutyDisposition.setDutyDispositionStatus(dutyDispositionStatus);

        int resultID = dutyDispositionFacade.add(dutyDisposition);
        List<Pair<String, String>> errorList = new LinkedList<>();

        if(resultID <= 0) {
            errorList.add(new Pair<>("SERVER_ERROR", "internal server error"));
        }

        return new Pair<>(dutyDisposition, errorList);
    }

    public boolean delete(int eventDutyID, int musicianID) {
        return dutyDispositionFacade.delete(eventDutyID, musicianID);
    }

    @Override
    public DutyDisposition getByID(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<DutyDisposition> getList() {
        throw new NotImplementedException();
    }
}