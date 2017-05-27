package Database.Facade;

import Database.Facade.helper.converter.DutyDispositionConverter;
import Database.Facade.helper.converter.EventDutyConverter;
import Database.Facade.helper.converter.MusicalWorkConverter;
import Database.Facade.helper.converter.PersonConverter;
import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.SectionDutyRosterDomainObject;
import Domain.Event.EventDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.Person.PersonDomainObject;
import Enums.*;
import javafx.scene.layout.Pane;
import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.list.DutyDispositionList;
import team_f.jsonconnector.entities.list.EventDutyList;
import team_f.jsonconnector.entities.list.MusicalWorkList;
import team_f.jsonconnector.entities.list.PersonList;
import team_f.jsonconnector.entities.special.errorlist.DutyDispositionErrorList;
import team_f.jsonconnector.entities.special.errorlist.EventDutyErrorList;
import team_f.jsonconnector.entities.special.request.DutyDispositionRequest;
import team_f.jsonconnector.entities.special.request.EventDutyRequest;
import team_f.jsonconnector.enums.PublishType;
import team_f.jsonconnector.enums.request.ActionType;
import team_f.jsonconnector.enums.request.DutyDispositionParameter;
import team_f.jsonconnector.enums.request.EventDutyParameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static team_f.client.helper.AlertHelper.showTryAgainLaterErrorMessage;

/**
 * Implements an abstraction layer (proxy) to the server which handles the request.
 * The GUI does therefore not recognize where the data comes from.
 *
 * Through to the complexity of the pages we abstract only the store and load layer to prevent unnecessary refactoring of the fxml and controller files.
 * This class can be loaded through reflection or replaced in the given compiled or not compiled files. This is only possible through to the nature of Java and other managed languages.
 */
public class DatabaseFacade {
    private static Configuration _configuration;
    private static Pane _pane;

    public DatabaseFacade() {
    }

    public static void initialize(Configuration configuration) {
        _configuration = configuration;
    }

    public static void setPane(Pane pane) {
        _pane = pane;
    }

    public List<EventDomainInterface> getAllEvents() {
        Request request = new Request();
        request.setActionType(ActionType.GET_ALL);

        EventDutyList eventDutyList = (EventDutyList) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDutyList.class);

        return convertToEventDutyList(eventDutyList);
    }

    public int saveEventDuty(EventDomainInterface event) {
        if(event != null) {
            EventDutyRequest request = new EventDutyRequest();

            if(event.getId() > 0) {
                request.setActionType(ActionType.UPDATE);
            } else {
                request.setActionType(ActionType.CREATE);
            }

            request.setEntity(EventDutyConverter.convert(event));

            EventDutyErrorList eventDuty = (EventDutyErrorList) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDutyErrorList.class);

            if(eventDuty != null && eventDuty.getKeyValueList() != null && eventDuty.getKeyValueList().size() > 0 &&
                    eventDuty.getKeyValueList().get(0).getKey() != null && eventDuty.getKeyValueList().get(0).getValue() != null && eventDuty.getKeyValueList().get(0).getValue().size() == 0) {
                return eventDuty.getKeyValueList().get(0).getKey().getEventDutyID();
            } else {
                showTryAgainLaterErrorMessage(_pane);
            }
        } else {
            showTryAgainLaterErrorMessage(_pane);
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
            showTryAgainLaterErrorMessage(_pane);
        }

        return new LinkedList<>();
    }

    public EventDomainInterface getEventForId(int id) {
        Request request = new Request();
        request.setActionType(ActionType.GET_BY_PARAMETER);

        List<Pair<String, String>> keyValueList = new LinkedList<>();
        Pair<String, String> pair;

        pair = new Pair<>();
        pair.setKey(String.valueOf(EventDutyParameter.ID));
        pair.setValue("" + id);
        keyValueList.add(pair);

        request.setParameterKeyList(keyValueList);

        EventDuty eventDuty = (EventDuty) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDuty.class);

        if(eventDuty != null) {
            return EventDutyConverter.convert(eventDuty, _configuration, 3);
        } else {
            showTryAgainLaterErrorMessage(_pane);
        }

        return null;
    }

    public void saveMusicalWorksForEvent(EventDomainInterface event, List<MusicalWorkDomainInterface> musicalWorksList) {
        EventDuty eventDuty = EventDutyConverter.convert(event);

        if(musicalWorksList != null) {
            List<MusicalWork> musicalWorkList = new ArrayList(musicalWorksList.size());

            for(MusicalWorkDomainInterface musicalWork : musicalWorksList) {
                musicalWorkList.add(MusicalWorkConverter.convert(musicalWork));
            }

            eventDuty.setMusicalWorkList(musicalWorkList);
        }

        EventDutyRequest request = new EventDutyRequest();

        if(eventDuty.getEventDutyID() > 0) {
            request.setActionType(ActionType.UPDATE);
        } else {
            request.setActionType(ActionType.CREATE);
        }

        request.setEntity(eventDuty);

        EventDutyErrorList response = (EventDutyErrorList) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDutyErrorList.class);

        if(response != null) {
        } else {
            showTryAgainLaterErrorMessage(_pane);
        }
    }

    public List<EventDomainInterface> getUnpublishedEventsForMonth(YearMonth month) {
        // get the entire month instead of only unpublished ones

        Request request = new Request();
        request.setActionType(ActionType.GET_BY_PARAMETER);
        Pair<String, String> tmpPair;
        List<Pair<String, String>> parameterList = new LinkedList<>();

        tmpPair = new Pair<>();
        tmpPair.setKey(String.valueOf(EventDutyParameter.MONTH));
        tmpPair.setValue(String.valueOf(month.getMonthValue()));
        parameterList.add(tmpPair);

        tmpPair = new Pair<>();
        tmpPair.setKey(String.valueOf(EventDutyParameter.YEAR));
        tmpPair.setValue(String.valueOf(month.getYear()));
        parameterList.add(tmpPair);

        request.setParameterKeyList(parameterList);

        EventDutyList eventDutyList = (EventDutyList) RequestResponseHelper.writeAndReadJSONObject(getEventURL(), request, EventDutyList.class);

        return convertToEventDutyList(eventDutyList);
    }

    public void publishScheduleForMonth(YearMonth month) {
        Publish request = new Publish();
        request.setMonth(month.getMonthValue());
        request.setYear(month.getYear());
        request.setPublishType(PublishType.PUBLISH);

        EventDutyErrorList eventDutyErrorList = (EventDutyErrorList) RequestResponseHelper.writeAndReadJSONObject(getPublishURL(), request, EventDutyErrorList.class);

        if(eventDutyErrorList == null) {
            showTryAgainLaterErrorMessage(_pane);
        }
    }

    public List<PersonDomainObject> getMusicians() {
        Request request = new Request();
        request.setActionType(ActionType.GET_ALL);

        PersonList personList = (PersonList) RequestResponseHelper.writeAndReadJSONObject(getPersonURL(), request, PersonList.class);

        if(personList != null) {
            return convertToPersonList(personList);
        }

        return new LinkedList<>();
    }

    public double getPointsForPersonAndMonth(PersonDomainObject person, Month month) {
        Request request = new Request();
        request.setActionType(ActionType.GET_BY_PARAMETER);

        List<Pair<String, String>> keyValueList = new LinkedList<>();
        Pair<String, String> pair;

        pair = new Pair<>();
        pair.setKey(String.valueOf(DutyDispositionParameter.PERSON_ID));
        pair.setValue("" + person.getId());
        keyValueList.add(pair);

        pair = new Pair<>();
        pair.setKey(String.valueOf(DutyDispositionParameter.MONTH));
        pair.setValue("" + month.getValue());
        keyValueList.add(pair);

        pair = new Pair<>();
        pair.setKey(String.valueOf(DutyDispositionParameter.YEAR));
        LocalDateTime now = LocalDateTime.now();
        int year;

        // we have to guess the year
        if(now.getMonthValue() > month.getValue()) {
            year = now.getYear() -1;
        } else {
            year = now.getYear();
        }

        pair.setValue("" + year);
        keyValueList.add(pair);

        request.setParameterKeyList(keyValueList);

        DutyDispositionList dutyDisposition = (DutyDispositionList) RequestResponseHelper.writeAndReadJSONObject(getDutyDispositionURL(), request, DutyDispositionList.class);

        if(dutyDisposition != null) {
            return dutyDisposition.getPoints();
        } else {
            showTryAgainLaterErrorMessage(_pane);
        }

        return -1;
    }

    public void saveDutyDisposition(DutyDispositionDomainObject duty) {
        DutyDisposition dutyDisposition = DutyDispositionConverter.convert(duty);

        DutyDispositionRequest request = new DutyDispositionRequest();
        request.setActionType(ActionType.CREATE);
        request.setEntity(dutyDisposition);

        DutyDispositionErrorList response = (DutyDispositionErrorList) RequestResponseHelper.writeAndReadJSONObject(getDutyDispositionURL(), request, DutyDispositionErrorList.class);

        if(response != null) {
        } else {
            showTryAgainLaterErrorMessage(_pane);
        }
    }

    public List<SectionDutyRosterDomainObject> getSectionDutyRoastersBySection(SectionType type) {
        // not required
        return new LinkedList<>();
    }

    public void removeDutyDisposition(DutyDispositionDomainObject dutyDomain) {
        DutyDisposition dutyDisposition = DutyDispositionConverter.convert(dutyDomain);

        DutyDispositionRequest request = new DutyDispositionRequest();
        request.setActionType(ActionType.DELETE);
        request.setEntity(dutyDisposition);

        DutyDisposition response = (DutyDisposition) RequestResponseHelper.writeAndReadJSONObject(getDutyDispositionURL(), request, DutyDisposition.class);

        if(response != null) {
        } else {
            showTryAgainLaterErrorMessage(_pane);
        }
    }

    private List<EventDomainInterface> convertToEventDutyList(EventDutyList eventDutyList) {
        if(eventDutyList != null) {
            List<EventDuty> tmpEventDuties = eventDutyList.getEventDutyList();

            if(tmpEventDuties != null) {
                List<EventDomainInterface> resultList = new ArrayList<>(tmpEventDuties.size());

                for(EventDuty eventDuty : tmpEventDuties) {
                    resultList.add(EventDutyConverter.convert(eventDuty, _configuration, 3));
                }

                return resultList;
            }
        } else {
            showTryAgainLaterErrorMessage(_pane);
        }

        return new LinkedList<>();
    }

    private List<PersonDomainObject> convertToPersonList(PersonList personList) {
        if(personList != null) {
            List<Person> tmpPersons = personList.getPersonList();

            if(tmpPersons != null) {
                List<PersonDomainObject> resultList = new ArrayList<>(tmpPersons.size());

                for(Person person : tmpPersons) {
                    resultList.add(PersonConverter.convert(person, _configuration));
                }

                return resultList;
            }
        } else {
            showTryAgainLaterErrorMessage(_pane);
        }

        return new LinkedList<>();
    }

    private URL getEventURL(){
        try {
            return new URL(new URL(_configuration.getRootURI()), URIList.event);
        } catch (MalformedURLException e) {
        }
        return null;
    }

    private URL getMusicalWorkURL(){
        try {
            return new URL(new URL(_configuration.getRootURI()), URIList.musicalWork);
        } catch (MalformedURLException e) {
        }
        return null;
    }

    private static URL getPublishURL() {
        try {
            return new URL(new URL(_configuration.getRootURI()), URIList.publish);
        } catch (MalformedURLException e) {
        }

        return null;
    }

    private static URL getPersonURL() {
        try {
            return new URL(new URL(_configuration.getRootURI()), URIList.person);
        } catch (MalformedURLException e) {
        }

        return null;
    }

    private static URL getDutyDispositionURL() {
        try {
            return new URL(new URL(_configuration.getRootURI()), URIList.dutyDisposition);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}