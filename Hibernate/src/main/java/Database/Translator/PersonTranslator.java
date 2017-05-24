package Database.Translator;

import Database.Entity.PartEntity;
import Database.Entity.PartTypeEntity;
import Database.Entity.PersonEntity;
import Database.Facade.DatabaseFacade;
import Domain.Duty.DutyViewInterface;
import Domain.Event.EventDomainInterface;
import Domain.Person.PartDomainObject;
import Domain.Person.PartTypeDomainObject;
import Domain.Person.PersonDomainObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Drazen
 */
public class PersonTranslator {

    public static PersonDomainObject transformPersonToDomainObject(PersonEntity personEntity, DatabaseFacade facade) {

        PersonDomainObject personDomainObject = new PersonDomainObject();

        personDomainObject.setId(personEntity.getPersonId());
        personDomainObject.setAccount(null);
        personDomainObject.setAddress(personEntity.getAddress());
        personDomainObject.setEmail(personEntity.getEmail());
        personDomainObject.setGender(personEntity.getGender());
        personDomainObject.setFirstName(personEntity.getFirstname());
        personDomainObject.setInitials(personEntity.getInitials());
        personDomainObject.setLastName(personEntity.getLastname());
        personDomainObject.setPersonRole(personEntity.getPersonRole());
        personDomainObject.setPhoneNumber(personEntity.getPhoneNumber());
        personDomainObject.setParts(PersonTranslator.transformPartsToDomainObject(facade.getPartsEntityForPerson(personEntity)));
        List<DutyViewInterface> dutyViews = new LinkedList<>();
        dutyViews.addAll(DutyTranslator.transformDutyForPersonToDomainObject(facade, facade.getDutyEntitiesForPerson(personEntity), personDomainObject));
        personDomainObject.setDuties(dutyViews);
        return personDomainObject;
    }

    private static List<PartDomainObject> transformPartsToDomainObject(List<PartEntity> partsForPerson) {

        List<PartDomainObject> partDomainObjectList = new LinkedList<>();

        for (PartEntity p : partsForPerson) {

            partDomainObjectList.add(PersonTranslator.transformPartToDomainObject(p));

        }
        return partDomainObjectList;
    }

    private static PartDomainObject transformPartToDomainObject(PartEntity p) {
        PartDomainObject partDomainObject = new PartDomainObject();

        partDomainObject.setId(p.getPartId());
        partDomainObject.setPartType(PersonTranslator.transformPartTypeToDomainObject(p.getPartType()));
        partDomainObject.setSectionType(p.getSectionType());

        return partDomainObject;
    }

    private static PartTypeDomainObject transformPartTypeToDomainObject(PartTypeEntity partType) {
        PartTypeDomainObject partTypeDomainObject = new PartTypeDomainObject();
        partTypeDomainObject.setDescription(partType.getPartType());
        partTypeDomainObject.setId(partType.getPartTypeId());

        return partTypeDomainObject;
    }

    public static PersonEntity transformPersonToEntity(DatabaseFacade facade, PersonDomainObject personDomainObject) {

        PersonEntity personEntity;

        if (personDomainObject.getId() == 0) {
            personEntity = new PersonEntity();

        } else {
            personEntity = facade.getPersonEntityForId(personDomainObject.getId());
        }
        //ToDo Accounts
        personEntity.setAccount(null);
        personEntity.setAddress(personDomainObject.getAddress());
        personEntity.setEmail(personDomainObject.getEmail());
        personEntity.setAddress(personDomainObject.getAddress());
        personEntity.setGender(personDomainObject.getGender());
        personEntity.setFirstname(personDomainObject.getFirstName());
        personEntity.setInitials(personDomainObject.getInitials());
        personEntity.setLastname(personDomainObject.getLastName());
        personEntity.setPersonRole(personDomainObject.getPersonRole());
        personEntity.setPhoneNumber(personDomainObject.getPhoneNumber());

        return personEntity;
    }


    public static PersonDomainObject transformPersonToDomainObjectForEvent(PersonEntity personEntity, EventDomainInterface event, DatabaseFacade facade) {
        PersonDomainObject personDomainObject = new PersonDomainObject();

        personDomainObject.setId(personEntity.getPersonId());
        personDomainObject.setAccount(null);
        personDomainObject.setAddress(personEntity.getAddress());
        personDomainObject.setEmail(personEntity.getEmail());
        personDomainObject.setGender(personEntity.getGender());
        personDomainObject.setFirstName(personEntity.getFirstname());
        personDomainObject.setInitials(personEntity.getInitials());
        personDomainObject.setLastName(personEntity.getLastname());
        personDomainObject.setPersonRole(personEntity.getPersonRole());
        personDomainObject.setPhoneNumber(personEntity.getPhoneNumber());
        personDomainObject.setParts(PersonTranslator.transformPartsToDomainObject(facade.getPartsEntityForPerson(personEntity)));
        List<DutyViewInterface> dutyViews = new LinkedList<>();
        dutyViews.addAll(DutyTranslator.transformDutyForPersonAndEventToDomainObject(facade, facade.getDutyEntitiesForPerson(personEntity), event, personDomainObject));
        personDomainObject.setDuties(dutyViews);
        return personDomainObject;
    }
}
