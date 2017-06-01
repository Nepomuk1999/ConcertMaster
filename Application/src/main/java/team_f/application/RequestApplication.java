package team_f.application;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import team_f.application.interfaces.BaseApplicationFacade;
import team_f.database_wrapper.facade.RequestFacade;
import team_f.domain.entities.EventDuty;
import team_f.domain.entities.Person;
import team_f.domain.entities.Request;
import team_f.domain.enums.RequestType;
import team_f.domain.interfaces.DomainEntity;
import java.util.List;

import javafx.util.Pair;

public class RequestApplication  extends BaseApplicationFacade<Request> {
    private static RequestApplication _instance;
    private RequestFacade requestFacade = new RequestFacade();

    private RequestApplication() { }

    public static RequestApplication getInstance() {
        if(_instance == null) {
            _instance = new RequestApplication();
        }

        return _instance;
    }

    @Override
    public void closeSession() {
        requestFacade.closeSession();
    }

    public Pair<DomainEntity, List<Pair<String, String>>> add(int eventID, int personID, RequestType requestType,
                                                                   String description) {
        Request request = new Request();
        request.setDescription(description);

        EventDuty eventDuty = new EventDuty();
        eventDuty.setEventDutyID(eventID);

        request.setEventDuty(eventDuty);

        Person person = new Person();
        person.setPersonID(personID);

        request.setPerson(person);
        request.setRequestType(requestType);

        List<Pair<String, String>> errorList = request.validate();

        if (errorList.size() > 0) {
            return new Pair<>(request, errorList);
        }

        Integer resultID = requestFacade.add(request);

        if(resultID <= 0) {
            errorList.add(new Pair<>("SERVER_ERROR", "internal server error"));
        }

        return new Pair<>(request, errorList);
    }

    public List<Request> getRequestByEventAndPersonId(int eventId, int personId) {
        return requestFacade.getRequestByEventAndPersonId(eventId, personId);
    }

    @Override
    public Request getByID(int id) {
        throw new NotImplementedException();
    }

    @Override
    public List<Request> getList() {
        throw new NotImplementedException();
    }
}