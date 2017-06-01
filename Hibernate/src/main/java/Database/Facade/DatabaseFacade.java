package Database.Facade;

import Enums.DutyRosterStatus;
import Enums.EventStatus;
import Enums.EventType;
import Enums.PersonRole;
import Enums.SectionType;
import team_f.database_wrapper.entities.*;
import Database.Translator.*;
import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.SectionDutyRosterDomainObject;
import Domain.Event.EventDomainInterface;
import Domain.Instrumentation.InstrumentationDomainInterface;
import Domain.Instrumentation.SpecialInstrumentationDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.Person.PartDomainObject;
import Domain.Person.PersonDomainObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Benjamin Grabherr
 */
@Transactional
public class DatabaseFacade {
    private final SessionFactory factory;

    public DatabaseFacade() {
        Configuration config = new Configuration();
        config.configure();
        factory = config.buildSessionFactory();
    }

    public List<EventDomainInterface> getAllEvents() {
        try (Session session = factory.openSession()) {
            Query<EventDutyEntity> query = session.createQuery("FROM EventDutyEntity", EventDutyEntity.class);
            List<EventDutyEntity> events = query.list();
            List<EventDomainInterface> refreshed = new LinkedList<>();
            for (EventDutyEntity event : events) {
                session.refresh(event);
                refreshed.add(EventTranslator.transformEventToInterface(event, this));
            }
            session.close();
            return refreshed;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public int saveEventDuty(EventDomainInterface event) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            EventDutyEntity eventDutyEntity = EventTranslator.transformEventToEntity(event, this);
            session.saveOrUpdate(eventDutyEntity);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
            return eventDutyEntity.getEventDutyId();
        } catch (RuntimeException re) {
            re.printStackTrace();
            return -1;
        }
    }

    public List<MusicalWorkDomainInterface> getMusicalWorksForEvent(EventDomainInterface event) {
        try (Session session = factory.openSession()) {
            Query<EventDutyMusicalWorkEntity> query = session.createQuery("FROM EventDutyMusicalWorkEntity e where e.eventDuty = :event", EventDutyMusicalWorkEntity.class);
            query.setParameter("event", event.getId());
            List<EventDutyMusicalWorkEntity> result = query.list();
            List<MusicalWorkEntity> musicalWorksEntity = new LinkedList<>();
            for (EventDutyMusicalWorkEntity eventDutyMusicalWorkEntity : result) {
                musicalWorksEntity.add(eventDutyMusicalWorkEntity.getMusicalWorkByMusicalWork());
            }
            session.close();
            return MusicalWorkTranslator.transformMusicalWorksToInterface(musicalWorksEntity, this);
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<MusicalWorkDomainInterface> getAllMusicalWorks() {
        try (Session session = factory.openSession()) {
            Query<MusicalWorkEntity> query = session.createQuery("FROM MusicalWorkEntity ", MusicalWorkEntity.class);
            List<MusicalWorkEntity> musicalWorks = query.list();
            List<MusicalWorkEntity> refreshed = new LinkedList<>();
            for (MusicalWorkEntity musicalWork : musicalWorks) {
                session.refresh(musicalWork);
                refreshed.add(musicalWork);
            }
            session.close();
            return MusicalWorkTranslator.transformMusicalWorksToInterface(refreshed, this);
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public EventDomainInterface getEventForId(int id) {
        try (Session session = factory.openSession()) {
            Query<EventDutyEntity> query = session.createQuery("FROM EventDutyEntity e where e.eventDutyId = :id", EventDutyEntity.class);
            query.setParameter("id", id);
            EventDutyEntity event = query.getSingleResult();
            session.refresh(event);
            session.close();
            return EventTranslator.transformEventToInterface(event, this);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void saveMusicalWorksForEvent(EventDomainInterface event, List<MusicalWorkDomainInterface> musicalWorksList) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("Delete from EventDutyMusicalWorkEntity e where e.eventDuty = :id");
            query.setParameter("id", event.getId());
            query.executeUpdate();
            List<MusicalWorkEntity> musicalWorks = MusicalWorkTranslator.transformMusicalWorksToEntity(musicalWorksList, this);
            EventDutyEntity eventDutyEntity = getEventEntityForId(event.getId());
            for (MusicalWorkEntity m : musicalWorks) {
                EventDutyMusicalWorkEntity eventDutyMusicalWorkEntity = new EventDutyMusicalWorkEntity();
                eventDutyMusicalWorkEntity.setEventDutyByEventDuty(eventDutyEntity);
                eventDutyMusicalWorkEntity.setMusicalWorkByMusicalWork(m);
                session.save(eventDutyMusicalWorkEntity);
            }
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public MusicalWorkDomainInterface getMusicalWorkForId(int id) {
        try (Session session = factory.openSession()) {
            Query<MusicalWorkEntity> query = session.createQuery("from MusicalWorkEntity m where musicalWorkId = :id", MusicalWorkEntity.class);
            query.setParameter("id", id);
            MusicalWorkEntity musicalWork = query.getSingleResult();
            session.refresh(musicalWork);
            session.close();
            return MusicalWorkTranslator.transformMusicalWorkToInterface(musicalWork, this);
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public void saveMusicalWork(MusicalWorkDomainInterface mwe) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(MusicalWorkTranslator.transformMusicalWorkToEntity(mwe, this));
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public void saveSpecialInstrumentationToInstrumentation(List<SpecialInstrumentationDomainInterface> specialInstrumentations, InstrumentationDomainInterface instrumentationDomain) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<SpecialInstrumentationEntity> entities = InstrumentationTranslator.transformSpecialInstrumentationToEntity(this, specialInstrumentations);
            for (SpecialInstrumentationEntity specialInstrumentationEntity : entities) {
                session.saveOrUpdate(specialInstrumentationEntity);
            }
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public List<EventDomainInterface> getUnpublishedEventsForMonth(YearMonth month) {
        try (Session session = factory.openSession()) {
            LocalDateTime startDate = LocalDateTime.of(month.atDay(1), LocalTime.MIDNIGHT);
            LocalDateTime endDate = LocalDateTime.of(month.atEndOfMonth(), LocalTime.MAX);
            Query<EventDutyEntity> query = session.createQuery("FROM EventDutyEntity e WHERE (e.starttime > :startDate) AND (e.starttime < :endDate) AND (e.eventStatus = :status)", EventDutyEntity.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            query.setParameter("status", EventStatus.Unpublished);
            List<EventDutyEntity> events = query.list();
            List<EventDomainInterface> refreshed = new LinkedList<>();
            for (EventDutyEntity event : events) {
                session.refresh(event);
                refreshed.add(EventTranslator.transformEventToInterface(event, this));
            }
            session.close();
            return refreshed;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public EventDutyEntity getEventEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<EventDutyEntity> query = session.createQuery("FROM EventDutyEntity e where e.eventDutyId = :id", EventDutyEntity.class);
            query.setParameter("id", id);
            EventDutyEntity event = query.getSingleResult();
            session.refresh(event);
            session.close();
            return event;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<MusicalWorkEntity> getMusicalWorksForEvent(EventDutyEntity event) {
        try (Session session = factory.openSession()) {
            Query<EventDutyMusicalWorkEntity> query = session.createQuery("FROM EventDutyMusicalWorkEntity e where e.eventDuty = :event", EventDutyMusicalWorkEntity.class);
            query.setParameter("event", event.getEventDutyId());
            List<EventDutyMusicalWorkEntity> list = query.list();
            List<MusicalWorkEntity> musicalWorksEntity = new LinkedList<>();
            for (EventDutyMusicalWorkEntity eventDutyMusicalWorkEntity : list) {
                musicalWorksEntity.add(eventDutyMusicalWorkEntity.getMusicalWorkByMusicalWork());
            }
            session.close();
            return musicalWorksEntity;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public MusicalWorkEntity getMusicalWorkEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<MusicalWorkEntity> query = session.createQuery("from MusicalWorkEntity e where e.musicalWorkId = :id", MusicalWorkEntity.class);
            query.setParameter("id", id);
            MusicalWorkEntity musicalWork = query.getSingleResult();
            session.refresh(musicalWork);
            session.close();
            return musicalWork;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public void saveMusicalWork(MusicalWorkEntity mwe) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(mwe);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public void saveSpecialInstrumentationEntitiesToInstrumentation(List<SpecialInstrumentationEntity> specialInstrumentationEntities) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            for (SpecialInstrumentationEntity specialInstrumentation : specialInstrumentationEntities) {
                session.saveOrUpdate(specialInstrumentation);
            }
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public void saveBrassInstrumentation(BrassInstrumentationEntity brass) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(brass);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public void saveStringInstrumentation(StringInstrumentationEntity string) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(string);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public void savePercussionInstrumentation(PercussionInstrumentationEntity percussion) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(percussion);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public void saveWoodInstrumentation(WoodInstrumentationEntity wood) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(wood);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public void saveInstrumentation(InstrumentationEntity instrumentation) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(instrumentation);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public InstrumentationEntity getInstrumentationEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<InstrumentationEntity> query = session.createQuery("from InstrumentationEntity i where i.instrumentationId = :id", InstrumentationEntity.class);

            query.setParameter("id", id);
            InstrumentationEntity instrumentation = query.getSingleResult();
            session.refresh(instrumentation);
            session.close();
            return instrumentation;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public BrassInstrumentationEntity getBrassInstrumentationEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<BrassInstrumentationEntity> query = session.createQuery("from BrassInstrumentationEntity b where b.brassInstrumentationId = :id", BrassInstrumentationEntity.class);
            query.setParameter("id", id);
            BrassInstrumentationEntity brassInstrumentation = query.getSingleResult();
            session.refresh(brassInstrumentation);
            session.close();
            return brassInstrumentation;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public PercussionInstrumentationEntity getPercussionInstrumentationEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<PercussionInstrumentationEntity> query = session.createQuery("from PercussionInstrumentationEntity p where p.percussionInstrumentationId = :id", PercussionInstrumentationEntity.class);
            query.setParameter("id", id);
            PercussionInstrumentationEntity percussionInstrumentation = query.getSingleResult();
            session.refresh(percussionInstrumentation);
            session.close();
            return percussionInstrumentation;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public StringInstrumentationEntity getStringInstrumentationEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<StringInstrumentationEntity> query = session.createQuery("from StringInstrumentationEntity s where s.stringInstrumentationId = :id", StringInstrumentationEntity.class);
            query.setParameter("id", id);
            StringInstrumentationEntity stringInstrumentation = query.getSingleResult();
            session.refresh(stringInstrumentation);
            session.close();
            return stringInstrumentation;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public WoodInstrumentationEntity getWoodInstrumentationEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<WoodInstrumentationEntity> query = session.createQuery("from WoodInstrumentationEntity w where w.woodInstrumentationId = :id", WoodInstrumentationEntity.class);
            query.setParameter("id", id);
            WoodInstrumentationEntity woodInstrumentationEntity = query.getSingleResult();
            session.refresh(woodInstrumentationEntity);
            session.close();
            return woodInstrumentationEntity;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public SpecialInstrumentationEntity getSpecialInstrumentationEntityForId(int id) {
        try (Session session = factory.openSession()) {
            Query<SpecialInstrumentationEntity> query = session.createQuery("from SpecialInstrumentationEntity s where s.specialInstrumentationId = :id", SpecialInstrumentationEntity.class);
            query.setParameter("id", id);
            SpecialInstrumentationEntity specialInstrumentation = query.getSingleResult();
            session.refresh(specialInstrumentation);
            session.close();
            return specialInstrumentation;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<SpecialInstrumentationEntity> getSpecialInstrumentationEntityForInstrumentation(InstrumentationEntity instrumentation) {
        try (Session session = factory.openSession()) {
            Query<SpecialInstrumentationEntity> query = session.createQuery("from SpecialInstrumentationEntity s where s.instrumentationId = :instrumentation", SpecialInstrumentationEntity.class);

            query.setParameter("instrumentation", instrumentation.getInstrumentationId());
            List<SpecialInstrumentationEntity> specialInstrumentation = query.list();
            List<SpecialInstrumentationEntity> refresh = new LinkedList<>();
            for (SpecialInstrumentationEntity s : specialInstrumentation) {
                session.refresh(s);
                refresh.add(s);
            }
            session.close();
            return refresh;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public void publishScheduleForMonth(YearMonth month) {
        try (Session session = factory.openSession()) {
            LocalDateTime startDate = LocalDateTime.of(month.atDay(1), LocalTime.MIDNIGHT);
            LocalDateTime endDate = LocalDateTime.of(month.atEndOfMonth(), LocalTime.MAX);
            Transaction transaction = session.beginTransaction();
            Query<EventDutyEntity> query = session.createQuery("FROM EventDutyEntity e WHERE (e.starttime > :startDate) AND (e.starttime < :endDate) AND (e.eventStatus = :status)", EventDutyEntity.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            query.setParameter("status", EventStatus.Unpublished);
            List<EventDutyEntity> events = query.list();
            for (EventDutyEntity event : events) {
                session.refresh(event);
                event.setEventStatus(team_f.database_wrapper.enums.EventStatus.valueOf(String.valueOf(EventStatus.Published)));
                session.update(event);
            }
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public PersonEntity getPersonEntityForId(int id) {

        try (Session session = factory.openSession()) {
            Query<PersonEntity> query = session.createQuery("from PersonEntity p where p.personId = :id", PersonEntity.class);

            query.setParameter("id", id);
            PersonEntity personEntity = query.getSingleResult();
            session.refresh(personEntity);
            session.close();
            return personEntity;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<PartEntity> getPartsEntityForPerson(PersonEntity personEntity) {
        try (Session session = factory.openSession()) {
            Query<MusicianPartEntity> query = session.createQuery("from MusicianPartEntity m where m.musician = :musician", MusicianPartEntity.class);

            query.setParameter("musician", personEntity.getPersonId());
            List<MusicianPartEntity> musicianPartEntities = query.list();
            List<PartEntity> refresh = new LinkedList<>();
            for (MusicianPartEntity m : musicianPartEntities) {
                session.refresh(m);
                refresh.add(m.getPartByPart());
            }
            session.close();
            return refresh;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<PersonDomainObject> getMusicians() {

        try (Session session = factory.openSession()) {
            Query<PersonEntity> query = session.createQuery("FROM PersonEntity p where p.personRole = :role", PersonEntity.class);
            query.setParameter("role", PersonRole.Musician);
            List<PersonEntity> persons = query.list();
            List<PersonDomainObject> refreshed = new LinkedList<>();
            for (PersonEntity person : persons) {
                session.refresh(person);
                refreshed.add(PersonTranslator.transformPersonToDomainObject(person, this));
            }
            session.close();
            return refreshed;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<DutyDispositionDomainObject> getDutiesForEvent(EventDomainInterface eventInterface) {
        try (Session session = factory.openSession()) {
            EventDutyEntity event = EventTranslator.transformEventToEntity(eventInterface, this);
            Query<DutyDispositionEntity> query = session.createQuery("FROM DutyDispositionEntity d where d.eventDuty = :event", DutyDispositionEntity.class);
            query.setParameter("event", event.getEventDutyId());
            List<DutyDispositionEntity> duties = query.list();
            List<DutyDispositionDomainObject> refreshed = new LinkedList<>();
            for (DutyDispositionEntity duty : duties) {
                session.refresh(duty);
                refreshed.add(DutyTranslator.transformDutyToDomainObject(duty, this));
            }
            session.close();
            return refreshed;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public double getPointsForPersonAndMonth(PersonDomainObject person, Month month) {
        try (Session session = factory.openSession()) {
            PersonEntity personEntity = PersonTranslator.transformPersonToEntity(this, person);
            Query<DutyDispositionEntity> query = session.createQuery("FROM DutyDispositionEntity d where d.musician = :musician", DutyDispositionEntity.class);
            query.setParameter("musician", personEntity.getPersonId());
            List<DutyDispositionEntity> duties = query.list();
            double pointsForMonth = 0;
            for (DutyDispositionEntity duty : duties) {
                session.refresh(duty);
                if (duty.getEventDutyByEventDuty().getStarttime().getMonth().equals(month)) {
                    if (duty.getEventDutyByEventDuty().getEventType().equals(EventType.Tour)) {
                        LocalDateTime startTime = duty.getEventDutyByEventDuty().getStarttime();
                        LocalDateTime endTime = duty.getEventDutyByEventDuty().getEndtime();
                        long days = startTime.until(endTime, ChronoUnit.DAYS);
                        if (!(endTime.minusDays(days).getDayOfMonth() == startTime.getDayOfMonth())) {
                            days++;
                        }
                        pointsForMonth += (duty.getPoints() * days);
                    } else {
                        pointsForMonth += duty.getPoints();
                    }
                }
            }
            session.close();
            return pointsForMonth;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return 0;
        }
    }

    public List<PersonDomainObject> getMusiciansForInstrument(String instrument) {
        List<PersonDomainObject> persons = getMusicians();
        List<PersonDomainObject> availableMusicians = new LinkedList<>();
        for (PersonDomainObject person : persons) {
            boolean correctInstrument = false;
            for (PartDomainObject part : person.getParts()) {
                if (part.getPartType().getDescription().equals(instrument)) {
                    correctInstrument = true;
                }
            }
            if (correctInstrument) {
                availableMusicians.add(person);
            }
        }
        return availableMusicians;
    }

    public void saveDutyDisposition(DutyDispositionDomainObject duty) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            DutyDispositionEntity dutyDispositionEntity = DutyTranslator.transformDutyToEntity(duty, this);
            session.saveOrUpdate(dutyDispositionEntity.getPersonByMusician());
            session.saveOrUpdate(dutyDispositionEntity.getEventDutyByEventDuty());
            Query<Long> query = session.createQuery("select count(*) from EventDutySectionDutyRosterEntity e where  e.eventDuty = :event and e.sectionDutyRosterBySectionDutyRoster.sectionType = :sectionType", Long.class);
            query.setParameter("event", dutyDispositionEntity.getEventDuty());
            query.setParameter("sectionType", duty.getMusician().getParts().get(0).getSectionType());
            int count = Math.toIntExact(query.getSingleResult());
            if (count == 0) {
                SectionDutyRosterEntity sectionDuty = new SectionDutyRosterEntity();
                sectionDuty.setDutyRosterStatus(team_f.database_wrapper.enums.DutyRosterStatus.valueOf(String.valueOf(DutyRosterStatus.Unpublished)));
                sectionDuty.setSectionType(team_f.database_wrapper.enums.SectionType.valueOf(String.valueOf(duty.getMusician().getParts().get(0).getSectionType())));
                session.saveOrUpdate(sectionDuty);
                EventDutySectionDutyRosterEntity eventDuty = new EventDutySectionDutyRosterEntity();
                eventDuty.setEventDuty(dutyDispositionEntity.getEventDuty());
                eventDuty.setSectionDutyRosterBySectionDutyRoster(sectionDuty);
                session.saveOrUpdate(eventDuty);
            }
            session.saveOrUpdate(dutyDispositionEntity);
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public List<DutyDispositionDomainObject> getDutiesForPerson(PersonDomainObject person) {
        try (Session session = factory.openSession()) {
            PersonEntity personEntity = PersonTranslator.transformPersonToEntity(this, person);
            Query<DutyDispositionEntity> query = session.createQuery("FROM DutyDispositionEntity d where d.musician = :musician", DutyDispositionEntity.class);
            query.setParameter("musician", personEntity.getPersonId());
            List<DutyDispositionEntity> duties = query.list();
            List<DutyDispositionDomainObject> dutyDomains = new LinkedList<>();
            for (DutyDispositionEntity duty : duties) {
                session.refresh(duty);
                dutyDomains.add(DutyTranslator.transformDutyToDomainObject(duty, this));
            }
            session.close();
            return dutyDomains;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<SectionDutyRosterDomainObject> getSectionDutyRoastersBySection(SectionType type) {
        try (Session session = factory.openSession()) {
            Query<EventDutySectionDutyRosterEntity> query = session.createQuery("FROM EventDutySectionDutyRosterEntity s where s.sectionDutyRosterBySectionDutyRoster.sectionType = :type", EventDutySectionDutyRosterEntity.class);
            query.setParameter("type", type);
            List<EventDutySectionDutyRosterEntity> duties = query.list();
            List<SectionDutyRosterDomainObject> dutyRosterDomains = new LinkedList<>();
            for (EventDutySectionDutyRosterEntity duty : duties) {
                session.refresh(duty);
                dutyRosterDomains.add(DutyTranslator.transformSectionDutyRosterToDomainObject(this, duty));
            }
            session.close();
            return dutyRosterDomains;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }
 private static PartDomainObject transformPartToDomainObject(PartEntity p) {
        PartDomainObject partDomainObject = new PartDomainObject();

        partDomainObject.setId(p.getPartId());
        partDomainObject.setPartType(PersonTranslator.transformPartTypeToDomainObject(p.getPartTypeByPartType()));
        // @TODO: crashs sometimes
        try {
            partDomainObject.setSectionType(SectionType.valueOf(String.valueOf(p.getSectionType())));
        } catch (Exception e) {
            //System.out.println(e);
        }

        return partDomainObject;
    }
    public void removeDutyDisposition(DutyDispositionDomainObject dutyDomain) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM DutyDispositionEntity d where d.eventDuty = :event and d.musician = :musician and d.dutyDispositionStatus = :status");
            EventDutyEntity eventDutyEntity = EventTranslator.transformEventToEntity(dutyDomain.getEvent(), this);
            session.saveOrUpdate(eventDutyEntity);
            query.setParameter("event", eventDutyEntity.getEventDutyId());
            PersonEntity personEntity = PersonTranslator.transformPersonToEntity(this, dutyDomain.getMusician());
            session.saveOrUpdate(personEntity);
            query.setParameter("musician", personEntity.getPersonId());
            query.setParameter("status", dutyDomain.getStatus());
            query.executeUpdate();
            session.flush();
            session.clear();
            transaction.commit();
            session.close();
        } catch (RuntimeException re) {
            re.printStackTrace();
        }
    }

    public List<DutyDispositionEntity> getDutyEntitiesForPerson(PersonEntity personEntity) {
        try (Session session = factory.openSession()) {
            Query<DutyDispositionEntity> query = session.createQuery("FROM DutyDispositionEntity d where d.musician = :musician", DutyDispositionEntity.class);
            query.setParameter("musician", personEntity.getPersonId());
            List<DutyDispositionEntity> duties = query.list();
            for (DutyDispositionEntity duty : duties) {
                session.refresh(duty);
            }
            session.close();
            return duties;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }

    public List<DutyDispositionEntity> getDutyEntitiesForEvent(EventDutyEntity event) {
        try (Session session = factory.openSession()) {
            Query<DutyDispositionEntity> query = session.createQuery("FROM DutyDispositionEntity d where d.eventDuty = :event", DutyDispositionEntity.class);
            query.setParameter("event", event.getEventDutyId());
            List<DutyDispositionEntity> duties = query.list();
            for (DutyDispositionEntity duty : duties) {
                session.refresh(duty);
            }
            session.close();
            return duties;
        } catch (RuntimeException re) {
            re.printStackTrace();
            return null;
        }
    }
}
