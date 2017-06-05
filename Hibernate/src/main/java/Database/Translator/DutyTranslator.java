package Database.Translator;

import Enums.DutyDispositionStatus;
import Enums.DutyRosterStatus;
import Enums.SectionType;
import team_f.database_wrapper.entities.*;
import Database.Facade.DatabaseFacade;
import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.SectionDutyRosterDomainObject;
import Domain.Event.EventDomainInterface;
import Domain.Person.PersonDomainObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Drazen
 */
public class DutyTranslator {

    public static DutyDispositionDomainObject transformDutyToDomainObject(DutyDispositionEntity entity, DatabaseFacade facade) {
        DutyDispositionDomainObject dutyDomain = new DutyDispositionDomainObject();
        dutyDomain.setEvent(EventTranslator.transformEventToInterface(entity.getEventDutyByEventDuty(), facade));
        dutyDomain.setDescription(entity.getDescription());
        dutyDomain.setPoints(entity.getPoints());
        dutyDomain.setMusician(PersonTranslator.transformPersonToDomainObject(entity.getPersonByMusician(), facade));
        dutyDomain.setStatus(DutyDispositionStatus.valueOf(String.valueOf(entity.getDutyDispositionStatus())));
        return dutyDomain;
    }

    public static DutyDispositionEntity transformDutyToEntity(DutyDispositionDomainObject duty, DatabaseFacade facade) {
        DutyDispositionEntity entity = new DutyDispositionEntity();
        entity.setEventDutyByEventDuty(EventTranslator.transformEventToEntity(duty.getEvent(), facade));
        entity.setEventDuty(entity.getEventDutyByEventDuty().getEventDutyId());
        entity.setDescription(duty.getDescription());
        entity.setPoints(duty.getPoints());
        entity.setPersonByMusician(PersonTranslator.transformPersonToEntity(facade, duty.getMusician()));
        entity.setMusician(entity.getPersonByMusician().getPersonId());
        entity.setDutyDispositionStatus(team_f.database_wrapper.enums.DutyDispositionStatus.valueOf(String.valueOf(duty.getStatus())));
        return entity;
    }

    public static SectionDutyRosterDomainObject transformSectionDutyRosterToDomainObject(DatabaseFacade facade, EventDutySectionDutyRosterEntity entity) {
        SectionDutyRosterDomainObject domain = new SectionDutyRosterDomainObject();
        domain.setEvent(EventTranslator.transformEventToInterface(entity.getEventDutyByEventDuty(), facade));
        domain.setSectionType(SectionType.valueOf(String.valueOf(entity.getSectionDutyRosterBySectionDutyRoster().getSectionType())));
        domain.setStatus(DutyRosterStatus.valueOf(String.valueOf(entity.getSectionDutyRosterBySectionDutyRoster().getDutyRosterStatus())));
        return domain;
    }

    static List<DutyDispositionDomainObject> transformDutyForPersonToDomainObject(DatabaseFacade facade, List<DutyDispositionEntity> entities, PersonDomainObject person) {
        List<DutyDispositionDomainObject> dutyDomains = new LinkedList<>();
        for (DutyDispositionEntity entity : entities) {
            DutyDispositionDomainObject dutyDomain = new DutyDispositionDomainObject();
            dutyDomain.setEvent(EventTranslator.transformEventToInterface(entity.getEventDutyByEventDuty(), facade));
            dutyDomain.setDescription(entity.getDescription());
            dutyDomain.setPoints(entity.getPoints());
            dutyDomain.setMusician(person);
            dutyDomain.setStatus(DutyDispositionStatus.valueOf(String.valueOf(entity.getDutyDispositionStatus())));
            dutyDomains.add(dutyDomain);
        }
        return dutyDomains;
    }

    static List<DutyDispositionDomainObject> transformDutyForEventToDomainObject(DatabaseFacade facade, List<DutyDispositionEntity> entities, EventDomainInterface event) {
        List<DutyDispositionDomainObject> dutyDomains = new LinkedList<>();
        for (DutyDispositionEntity entity : entities) {
            DutyDispositionDomainObject dutyDomain = new DutyDispositionDomainObject();
            dutyDomain.setEvent(event);
            dutyDomain.setDescription(entity.getDescription());
            dutyDomain.setPoints(entity.getPoints());
            dutyDomain.setMusician(PersonTranslator.transformPersonToDomainObjectForEvent(entity.getPersonByMusician(), event, facade));
            dutyDomain.setStatus(DutyDispositionStatus.valueOf(String.valueOf(entity.getDutyDispositionStatus())));
            dutyDomains.add(dutyDomain);
        }
        return dutyDomains;
    }

    public static List<DutyDispositionDomainObject> transformDutyForPersonAndEventToDomainObject(DatabaseFacade facade, List<DutyDispositionEntity> dutyEntitiesForPerson, EventDomainInterface event, PersonDomainObject personDomainObject) {
        List<DutyDispositionDomainObject> dutyDomains = new LinkedList<>();
        for (DutyDispositionEntity entity : dutyEntitiesForPerson) {
            DutyDispositionDomainObject dutyDomain = new DutyDispositionDomainObject();
            dutyDomain.setEvent(event);
            dutyDomain.setDescription(entity.getDescription());
            dutyDomain.setPoints(entity.getPoints());
            dutyDomain.setMusician(personDomainObject);
            dutyDomain.setStatus(DutyDispositionStatus.valueOf(String.valueOf(entity.getDutyDispositionStatus())));
            dutyDomains.add(dutyDomain);
        }
        return dutyDomains;
    }
}
