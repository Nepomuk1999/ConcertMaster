package Database.Translator;

import Enums.EventStatus;
import team_f.database_wrapper.entities.*;
import Database.Facade.DatabaseFacade;
import Domain.Duty.DutyViewInterface;
import Domain.Event.*;
import team_f.database_wrapper.enums.EventType;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
public class EventTranslator {

    public static EventDomainInterface transformEventToInterface(EventDutyEntity event, DatabaseFacade facade) {
        EventDomainInterface e = null;

        // @TODO: crashed sometimes: here was a wrong logic
        if(event == null) {
            return null;
        }

        switch (event.getEventType()) {
            case Rehearsal:
                RehearsalEventDomain rehearsalEventDomain = new RehearsalEventDomain();
                rehearsalEventDomain.setRehearsalFor(transformEventToInterface(event.getEventDutyByRehearsalFor(), facade));
                rehearsalEventDomain.setId(event.getEventDutyId());
                e = rehearsalEventDomain;
                break;
            case NonMusicalEvent:
                NonMusicalEventDomain nonMusicalEventDomain = new NonMusicalEventDomain();
                nonMusicalEventDomain.setDescription(event.getDescription());
                nonMusicalEventDomain.setId(event.getEventDutyId());
                e = nonMusicalEventDomain;
                break;
            case Hofkapelle:
                HofkapelleEventDomain hofkapelleEventDomain = new HofkapelleEventDomain();
                hofkapelleEventDomain.setDescription(event.getDescription());
                hofkapelleEventDomain.setConductor(event.getConductor());
                hofkapelleEventDomain.setMusicalWorks(MusicalWorkTranslator.transformMusicalWorksToInterface(facade.getMusicalWorksForEvent(event), facade));
                hofkapelleEventDomain.setId(event.getEventDutyId());
                e = hofkapelleEventDomain;
                break;
            case Concert:
                ConcertEventDomain concertEventDomain = new ConcertEventDomain();
                concertEventDomain.setDescription(event.getDescription());
                concertEventDomain.setConductor(event.getConductor());
                concertEventDomain.setMusicalWorks(MusicalWorkTranslator.transformMusicalWorksToInterface(facade.getMusicalWorksForEvent(event), facade));
                concertEventDomain.setId(event.getEventDutyId());
                e = concertEventDomain;
                break;
            case Opera:
                OperaEventDomain operaEventDomain = new OperaEventDomain();
                operaEventDomain.setDescription(event.getDescription());
                operaEventDomain.setConductor(event.getConductor());
                operaEventDomain.setMusicalWorks(MusicalWorkTranslator.transformMusicalWorksToInterface(facade.getMusicalWorksForEvent(event), facade));
                operaEventDomain.setId(event.getEventDutyId());
                e = operaEventDomain;
                break;
            case Tour:
                TourEventDomain tourEventDomain = new TourEventDomain();
                tourEventDomain.setDescription(event.getDescription());
                tourEventDomain.setConductor(event.getConductor());
                tourEventDomain.setMusicalWorks(MusicalWorkTranslator.transformMusicalWorksToInterface(facade.getMusicalWorksForEvent(event), facade));
                tourEventDomain.setId(event.getEventDutyId());
                e = tourEventDomain;
                break;
        }
        e.setName(event.getName());
        e.setStartDate(event.getStarttime().toLocalDate());
        e.setStartTime(event.getStarttime().toLocalTime());
        e.setEndDate(event.getEndtime().toLocalDate());
        e.setEndTime(event.getEndtime().toLocalTime());
        e.setLocation(event.getLocation());
        e.setDefaultPoints(Math.round((float) event.getDefaultPoints()));
        e.setEventStatus(EventStatus.valueOf(String.valueOf(event.getEventStatus())));
        List<DutyViewInterface> duties = new LinkedList<>();
        duties.addAll(DutyTranslator.transformDutyForEventToDomainObject(facade, facade.getDutyEntitiesForEvent(event), e));
        e.setDuties(duties);
        return e;
    }

    public static EventDutyEntity transformEventToEntity(EventDomainInterface e, DatabaseFacade facade) {
        EventDutyEntity event;
        if (e.getId() != 0) {
            event = facade.getEventEntityForId(e.getId());
        } else {
            event = new EventDutyEntity();
        }
        event.setName(e.getName());
        event.setDescription(e.getDescription());
        event.setStarttime(LocalDateTime.of(e.getStartDate(), e.getStartTime()));
        event.setEndtime(LocalDateTime.of(e.getEndDate(), e.getEndTime()));
        event.setLocation(e.getLocation());
        event.setConductor(e.getConductor());
        event.setDefaultPoints(e.getDefaultPoints());
        if (e.getRehearsalFor() != null) {
            EventDomainInterface rehearsalFor = (EventDomainInterface) e.getRehearsalFor();
            event.setEventDutyByRehearsalFor(transformEventToEntity(rehearsalFor, facade));
        }
        event.setEventStatus(team_f.database_wrapper.enums.EventStatus.valueOf(String.valueOf(e.getEventStatus())));
        event.setEventType(EventType.valueOf(String.valueOf(e.getEventType())));
        event.setEventStatus(team_f.database_wrapper.enums.EventStatus.valueOf(String.valueOf(e.getEventStatus())));
        return event;
    }
}
