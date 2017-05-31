package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.EventDutyEntity;
import team_f.database_wrapper.entities.PersonEntity;
import team_f.database_wrapper.entities.RequestEntity;
import team_f.database_wrapper.enums.RequestType;
import team_f.database_wrapper.helper.StoreHelper;
import team_f.domain.entities.Request;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RequestFacade extends BaseDatabaseFacade<Request> {
    private static PersonFacade _personFacade = new PersonFacade();
    private static EventFacade _eventFacade = new EventFacade();

    public RequestFacade() {
        super();
    }

    public RequestFacade(EntityManager session) {
        super(session);
    }

    public List<Request> getRequestByEventAndPersonId(int eventId, int personId) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from RequestEntity where eventDuty = :eventId and musician = :personId");
        query.setParameter("eventId", eventId);
        query.setParameter("personId", personId);

        List<RequestEntity> requestEntities = query.getResultList();
        List<Request> requests = new ArrayList<>();

        for (RequestEntity requestEntity : requestEntities) {
            Request event = convertToRequest(requestEntity);

            requests.add(event);
        }

        return requests;
    }

    @Override
    public int add(Request request) {
        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        RequestEntity requestEntity = convertToRequestEntity(request);

        session.persist(requestEntity);
        StoreHelper.storeEntities(session);

        return 1;
    }

    @Override
    public int update(Request value) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    protected Request convertToRequest(RequestEntity entity) {
        Request request = new Request();
        request.setRequestType(team_f.domain.enums.RequestType.valueOf(String.valueOf(entity.getRequestType())));

        PersonEntity personEntity = entity.getPersonByMusician();
        request.setPerson(_personFacade.convertToPerson(personEntity));

        EventDutyEntity eventDutyEntity = entity.getEventDutyByEventDuty();
        request.setEventDuty(_eventFacade.convertToEventDuty(eventDutyEntity));

        request.setDescription(entity.getDescription());

        return request;
    }

    protected RequestEntity convertToRequestEntity(Request request) {
        RequestEntity entity = new RequestEntity();
        entity.setDescription(request.getDescription());

        if(request.getEventDuty() != null) {
            entity.setEventDuty(request.getEventDuty().getEventDutyID());
        }

        if(request.getPerson() != null) {
            entity.setMusician(request.getPerson().getPersonID());
        }

        entity.setRequestType(RequestType.valueOf(String.valueOf(request.getRequestType())));

        return entity;
    }
}
