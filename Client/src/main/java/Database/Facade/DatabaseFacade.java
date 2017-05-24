package Database.Facade;

import Database.Facade.helper.converter.EventDutyConverter;
import Database.Facade.helper.converter.MusicalWorkConverter;
import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.SectionDutyRosterDomainObject;
import Domain.Event.EventDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.Person.PersonDomainObject;
import Enums.*;
import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.MusicalWork;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.Request;
import team_f.jsonconnector.entities.list.EventDutyList;
import team_f.jsonconnector.entities.list.MusicalWorkList;
import team_f.jsonconnector.enums.request.ActionType;
import team_f.jsonconnector.enums.request.EventDutyParameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static team_f.client.helper.AlertHelper.showTryAgainLaterErrorMessage;

/**
 * implements an abstraction layer to the server which handles the request
 */
public class DatabaseFacade {
    private static Configuration _configuration;

    public DatabaseFacade() {
    }

    public static void initialize(Configuration configuration) {
        _configuration = configuration;
    }

    public List<EventDomainInterface> getAllEvents() {
        Request request = new Request();
        request.setActionType(ActionType.GET_ALL);

        // @TODO: implement in server layer
        EventDutyList eventDutyList = (EventDutyList) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDutyList.class);

        if(eventDutyList != null) {
            List<EventDuty> tmpEventDuties = eventDutyList.getEventDutyList();

            if(tmpEventDuties != null) {
                List<EventDomainInterface> resultList = new ArrayList<>(tmpEventDuties.size());

                for(EventDuty eventDuty : tmpEventDuties) {
                    resultList.add(EventDutyConverter.convert(eventDuty));
                }

                return resultList;
            }
        } else {
            showTryAgainLaterErrorMessage(null);
        }

        return new LinkedList<>();
    }

    public int saveEventDuty(EventDomainInterface event) {
        if(event != null) {
            Request request = new Request();

            if(event.getId() > 0) {
                request.setActionType(ActionType.CREATE);
            } else {
                request.setActionType(ActionType.UPDATE);
            }

            EventDuty eventDuty = (EventDuty) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDuty.class);

            if(eventDuty != null) {
                return eventDuty.getEventDutyID();
            } else {
                showTryAgainLaterErrorMessage(null);
            }
        } else {
            showTryAgainLaterErrorMessage(null);
        }

        return -1;
    }

    public List<MusicalWorkDomainInterface> getAllMusicalWorks() {
        Request request = new Request();
        request.setActionType(ActionType.GET_ALL);

        MusicalWorkList musicalWorkList = (MusicalWorkList) RequestResponseHelper.writeAndReadJSONObject(getMusicalWorkURL(), request, MusicalWorkList.class);

        if(musicalWorkList != null) {
            List<MusicalWork> tmpMusicalWorks = musicalWorkList.getMusicalWorkList();

            if(tmpMusicalWorks != null) {
                List<MusicalWorkDomainInterface> resultList = new ArrayList<>(tmpMusicalWorks.size());

                for(MusicalWork musicalWork : tmpMusicalWorks) {
                    resultList.add(MusicalWorkConverter.convert(musicalWork));
                }

                return resultList;
            }
        } else {
            showTryAgainLaterErrorMessage(null);
        }

        return new LinkedList<>();
    }

    public EventDomainInterface getEventForId(int id) {
        Request request = new Request();
        request.setActionType(ActionType.GET_BY_PARAMETER);

        List<Pair<String, String>> keyValueList = new LinkedList<>();
        Pair<String, String> pair;

        pair = new Pair<>();
        // @TODO: implement in Server layer
        pair.setKey(String.valueOf(EventDutyParameter.ID));
        pair.setValue("" + id);
        keyValueList.add(pair);

        request.setParameterKeyList(keyValueList);

        EventDuty eventDuty = (EventDuty) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDuty.class);

        if(eventDuty != null) {
            return EventDutyConverter.convert(eventDuty);
        } else {
            showTryAgainLaterErrorMessage(null);
        }

        return null;
    }

    public void saveMusicalWorksForEvent(EventDomainInterface event, List<MusicalWorkDomainInterface> musicalWorksList) {
        // @TODO: implement
    }

    public List<EventDomainInterface> getUnpublishedEventsForMonth(YearMonth month) {
        // @TODO: implement
        return new LinkedList<>();
    }

    public void publishScheduleForMonth(YearMonth month) {
        // @TODO: implement
    }

    public List<PersonDomainObject> getMusicians() {
        // @TODO: implement
        return new LinkedList<>();
    }

    public double getPointsForPersonAndMonth(PersonDomainObject person, Month month) {
        // @TODO: implement
        return -1;
    }

    public void saveDutyDisposition(DutyDispositionDomainObject duty) {
        // @TODO: implement
    }

    public List<SectionDutyRosterDomainObject> getSectionDutyRoastersBySection(SectionType type) {
        // @TODO: implement
        return new LinkedList<>();
    }

    public void removeDutyDisposition(DutyDispositionDomainObject dutyDomain) {

    }

    private URL getEventURL(){
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.event);
        } catch (MalformedURLException e) {
        }
        return null;
    }

    private URL getMusicalWorkURL(){
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.musicalWork);
        } catch (MalformedURLException e) {
        }
        return null;
    }
}