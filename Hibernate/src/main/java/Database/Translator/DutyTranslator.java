package Database.Translator;

import Database.Entity.DutyDispositionEntity;
import Database.Entity.EventDutySectionDutyRosterEntity;
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
        dutyDomain.setEvent(EventTranslator.transformEventToInterface(entity.getEventDuty(), facade));
        dutyDomain.setDescription(entity.getDescription());
        dutyDomain.setPoints(entity.getPoints());
        dutyDomain.setMusician(PersonTranslator.transformPersonToDomainObject(entity.getMusician(), facade));
        dutyDomain.setStatus(entity.getDutyDispositionStatus());
        return dutyDomain;
    }

    public static DutyDispositionEntity transformDutyToEntity(DutyDispositionDomainObject duty, DatabaseFacade facade) {
        DutyDispositionEntity entity = new DutyDispositionEntity();
        entity.setEventDuty(EventTranslator.transformEventToEntity(duty.getEvent(), facade));
        entity.setDescription(duty.getDescription());
        entity.setPoints(duty.getPoints());
        entity.setMusician(PersonTranslator.transformPersonToEntity(facade, duty.getMusician()));
        entity.setDutyDispositionStatus(duty.getStatus());
        return entity;
    }

    public static SectionDutyRosterDomainObject transformSectionDutyRosterToDomainObject(DatabaseFacade facade, EventDutySectionDutyRosterEntity entity) {
        SectionDutyRosterDomainObject domain = new SectionDutyRosterDomainObject();
        domain.setEvent(EventTranslator.transformEventToInterface(entity.getEventDuty(), facade));
        domain.setSectionType(entity.getSectionDutyRoster().getSectionType());
        domain.setStatus(entity.getSectionDutyRoster().getDutyRosterStatus());
        return domain;
    }

    static List<DutyDispositionDomainObject> transformDutyForPersonToDomainObject(DatabaseFacade facade, List<DutyDispositionEntity> entities, PersonDomainObject person) {
        List<DutyDispositionDomainObject> dutyDomains = new LinkedList<>();
        for (DutyDispositionEntity entity : entities) {
            DutyDispositionDomainObject dutyDomain = new DutyDispositionDomainObject();
            dutyDomain.setEvent(EventTranslator.transformEventToInterface(entity.getEventDuty(), facade));
            dutyDomain.setDescription(entity.getDescription());
            dutyDomain.setPoints(entity.getPoints());
            dutyDomain.setMusician(person);
            dutyDomain.setStatus(entity.getDutyDispositionStatus());
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
            dutyDomain.setMusician(PersonTranslator.transformPersonToDomainObjectForEvent(entity.getMusician(), event, facade));
            dutyDomain.setStatus(entity.getDutyDispositionStatus());
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
            dutyDomain.setStatus(entity.getDutyDispositionStatus());
            dutyDomains.add(dutyDomain);
        }
        return dutyDomains;
    }
}
