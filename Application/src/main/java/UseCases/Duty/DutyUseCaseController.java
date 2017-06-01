package UseCases.Duty;

import Database.Facade.DatabaseFacade;
import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.SectionDutyRosterDomainObject;
import Domain.Event.EventDomainInterface;
import Domain.Event.EventViewInterface;
import Domain.Person.PersonDomainObject;
import Domain.Person.PersonViewInterface;
import Enums.DutyDispositionStatus;
import Enums.DutyRosterStatus;
import Enums.SectionType;
import javafx.collections.ObservableList;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
public class DutyUseCaseController {
    private final DatabaseFacade facade;
    private final List<PersonDomainObject> musicians;

    public DutyUseCaseController() {
        facade = new DatabaseFacade();
        musicians = facade.getMusicians();
    }

    public DutyUseCaseController(List<PersonViewInterface> musicians) {
        facade = new DatabaseFacade();
        this.musicians = new LinkedList<>();
        for(PersonViewInterface person : musicians) {
            this.musicians.add((PersonDomainObject) person);
        }
    }

    public List<PersonViewInterface> getAllMusicians() {
        List<PersonViewInterface> musicianViews = new LinkedList<>();
        musicianViews.addAll(facade.getMusicians());
        return musicianViews;
    }

    public double getPointsForMusicianAndDate(PersonViewInterface person, EventViewInterface event) {
        return facade.getPointsForPersonAndMonth(((PersonDomainObject) person), event.getStartDate().getMonth());
    }

    public List<PersonViewInterface> getAvailableMusicians(EventViewInterface event, String instrument) {
        List<PersonViewInterface> availableMusicians = new LinkedList<>();
        //musicians.addAll(facade.getMusiciansForInstrument(instrument));
        for (PersonDomainObject musician : musicians) {
            if (musician.isAvailable(event) && musician.playsInstrument(instrument)) {
                availableMusicians.add(musician);
            }
        }
        return availableMusicians;
    }

    public List<YearMonth> getUnpublishedDutyMonths(SectionType... types) {
        List<SectionDutyRosterDomainObject> dutyRosterDomain = new LinkedList<>();
        for (SectionType type : types) {
            dutyRosterDomain.addAll(facade.getSectionDutyRoastersBySection(type));
        }
        List<YearMonth> unpublishedMonths = new LinkedList<>();
        for (EventDomainInterface eventDomainInterface : facade.getAllEvents()) {
            YearMonth current = YearMonth.from(eventDomainInterface.getStartDate());
            if (current.isAfter(YearMonth.now()) && !unpublishedMonths.contains(current)) {
                unpublishedMonths.add(current);
            }
        }
        Iterator<YearMonth> iterator = unpublishedMonths.iterator();
        while (iterator.hasNext()) {
            YearMonth current = iterator.next();
            boolean published = false;
            for (int count = 0; !published && (count < dutyRosterDomain.size()); count++) {
                SectionDutyRosterDomainObject dutyRoster = dutyRosterDomain.get(count);
                if ((dutyRoster.getStatus().equals(DutyRosterStatus.Published)) && (current.equals(YearMonth.of(dutyRoster.getEvent().getStartDate().getYear(), dutyRoster.getEvent().getStartDate().getMonth())))) {
                    published = true;
                }
            }
            if (published) {
                iterator.remove();
            }
        }
        unpublishedMonths.sort(Comparator.naturalOrder());
        return unpublishedMonths;
    }

    public void createDuty(EventViewInterface event, List<PersonViewInterface> musicians, String description, int points) {
        for(PersonViewInterface musician : musicians) {
            DutyDispositionDomainObject duty = new DutyDispositionDomainObject();
            duty.setEvent((EventDomainInterface) event);
            duty.setDescription(description);
            duty.setPoints(points);
            duty.setMusician((PersonDomainObject) musician);
            duty.setStatus(DutyDispositionStatus.Normal);
            ((PersonDomainObject) musician).addDuty(duty);
            ((EventDomainInterface) event).addDuty(duty);
            facade.saveDutyDisposition(duty);
        }
    }

    public void createSpareDuty(EventViewInterface event, List<PersonViewInterface> musicians, String description, int points) {
        for(PersonViewInterface musician : musicians) {
            DutyDispositionDomainObject duty = new DutyDispositionDomainObject();
            duty.setEvent((EventDomainInterface) event);
            duty.setDescription(description);
            duty.setPoints(points);
            duty.setMusician((PersonDomainObject) musician);
            duty.setStatus(DutyDispositionStatus.Spare);
            ((PersonDomainObject) musician).addDuty(duty);
            ((EventDomainInterface) event).addDuty(duty);
            facade.saveDutyDisposition(duty);
        }
    }

    public void editSpareDuties(EventViewInterface event, List<PersonViewInterface> oldMusicians, ObservableList<PersonViewInterface> musicians, String description, int points) {
        for(PersonViewInterface oldMusician : oldMusicians){
            DutyDispositionDomainObject duty = new DutyDispositionDomainObject();
            duty.setMusician((PersonDomainObject) oldMusician);
            duty.setEvent((EventDomainInterface) event);
            duty.setStatus(DutyDispositionStatus.Spare);
            ((PersonDomainObject) oldMusician).removeDuty(duty);
            ((EventDomainInterface) event).removeDuty(duty);
            facade.removeDutyDisposition(duty);
        }
        createSpareDuty(event, musicians, description, points);
    }

    public void editDuties(EventViewInterface event, List<PersonViewInterface> oldMusicians, ObservableList<PersonViewInterface> musicians, String description, int points) {
        for(PersonViewInterface oldMusician : oldMusicians){
            DutyDispositionDomainObject duty = new DutyDispositionDomainObject();
            duty.setMusician((PersonDomainObject) oldMusician);
            duty.setEvent((EventDomainInterface) event);
            duty.setStatus(DutyDispositionStatus.Normal);
            ((EventDomainInterface) event).removeDuty(duty);
            ((PersonDomainObject) oldMusician).removeDuty(duty);
            facade.removeDutyDisposition(duty);
        }
        createDuty(event, musicians, description, points);
    }
}
