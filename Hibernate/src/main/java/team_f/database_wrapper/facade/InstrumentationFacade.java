package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.*;
import team_f.domain.entities.Instrumentation;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class InstrumentationFacade extends BaseDatabaseFacade<InstrumentationEntity, Instrumentation> {
    public InstrumentationFacade() {
        super();
    }

    public InstrumentationFacade(EntityManager session) {
        super(session);
    }


    public Integer addInstrumentation(Instrumentation instrumentation) {

        EntityManager session = getCurrentSession();
        session.getTransaction().begin();

        // create new Instrumentation entities
        InstrumentationEntity instrumentationEntity = new InstrumentationEntity();
        StringInstrumentationEntity stringInstrumentationEntity = getStringInstrumentationEntityFromInstrumentation(instrumentation);
        WoodInstrumentationEntity woodInstrumentationEntity = getWoodInstrumentationEntityFromInstrumentation(instrumentation);
        BrassInstrumentationEntity brassInstrumentationEntity = getBrassInstrumentationEntityFromInstrumentation(instrumentation);
        PercussionInstrumentationEntity percussionInstrumentationEntity = getPercussionInstrumentationEntityFromInstrumentation(instrumentation);

        // store all child entries in the transaction and get the id
        session.persist(stringInstrumentationEntity);
        session.persist(brassInstrumentationEntity);
        session.persist(woodInstrumentationEntity);
        session.persist(percussionInstrumentationEntity);

        // set the received ids
        instrumentationEntity.setStringInstrumentation(stringInstrumentationEntity.getStringInstrumentationId());
        instrumentationEntity.setBrassInstrumentation(brassInstrumentationEntity.getBrassInstrumentationId());
        instrumentationEntity.setWoodInstrumentation(woodInstrumentationEntity.getWoodInstrumentationId());
        instrumentationEntity.setPercussionInstrumentation(percussionInstrumentationEntity.getPercussionInstrumentationId());

        session.persist(instrumentationEntity);

        try {
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return instrumentationEntity.getInstrumentationId();
    }

    public Instrumentation getInstrumentationByID(int id) {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from InstrumentationEntity where id = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<InstrumentationEntity> instrumentations = query.getResultList();
        InstrumentationEntity instrumentationEntity;
        Instrumentation instrumentation = new Instrumentation();

        if (instrumentations.size() > 0) {
            instrumentationEntity = instrumentations.get(0);
            instrumentation = convertToInstrumentation(instrumentationEntity);
        }

        return instrumentation;
    }

    public List<Instrumentation> getInstrumentations() {
        EntityManager session = getCurrentSession();

        Query query = session.createQuery("from InstrumentationEntity");
        List<InstrumentationEntity> instrumentationEntities = query.getResultList();
        List<Instrumentation> instrumentations = new ArrayList<>();

        for (InstrumentationEntity ie : instrumentationEntities) {
            instrumentations.add(convertToInstrumentation(ie));
        }

        return instrumentations;
    }

    /**
     * Function to convert InstrumentationEntity Object to Instrumentation. Returns the Instrumentation after creating and setting Informations from InstrumentationEntity object.
     * @return instrumentation    Instrumentation        returns Instrumentation Object
     */
    protected Instrumentation convertToInstrumentation(InstrumentationEntity entity) {
        Instrumentation instrumentation = new Instrumentation();
        instrumentation.setInstrumentationID(entity.getInstrumentationId());

        BrassInstrumentationEntity bie = getBrassInstrumentationEntity(entity.getBrassInstrumentation());
        WoodInstrumentationEntity wie = getWoodInstrumentationEntity(entity.getWoodInstrumentation());
        StringInstrumentationEntity sie = getStringInstrumentationEntity(entity.getStringInstrumentation());
        PercussionInstrumentationEntity pie = getPercussionInstrumentationEntity(entity.getPercussionInstrumentation());
        List<SpecialInstrumentationEntity> speiList = getSpecialInstrumentationEntities(entity.getInstrumentationId());

        instrumentation.setFlute(wie.getFlute());
        instrumentation.setOboe(wie.getOboe());
        instrumentation.setBassoon(wie.getBassoon());
        instrumentation.setClarinet(wie.getClarinet());

        instrumentation.setTube(bie.getTube());
        instrumentation.setHorn(bie.getHorn());
        instrumentation.setTrombone(bie.getTrombone());
        instrumentation.setTrumpet(bie.getTrumpet());

        instrumentation.setViolin1(sie.getViolin1());
        instrumentation.setViolin2(sie.getViolin2());
        instrumentation.setViola(sie.getViola());
        instrumentation.setViolincello(sie.getViolincello());
        instrumentation.setDoublebass(sie.getDoublebass());

        instrumentation.setKettledrum(pie.getKettledrum());
        instrumentation.setHarp(pie.getHarp());
        instrumentation.setPercussion(pie.getPercussion());

        // @TODO: special instrumentations
        /*
        if (speiList != null) {
            for (SpecialInstrumentationEntity spei : speiList) {
                i.addToSpecial(spei.getSpecialInstrument(), spei.getSpecialInstrumentationNumber(), spei.getSectionType().toString());
            }
        }*/

        return instrumentation;
    }

    private WoodInstrumentationEntity getWoodInstrumentationEntityFromInstrumentation (Instrumentation instrumentation) {
        WoodInstrumentationEntity woodInstrumentationEntity = new WoodInstrumentationEntity();

        woodInstrumentationEntity.setFlute(instrumentation.getFlute());
        woodInstrumentationEntity.setOboe(instrumentation.getOboe());
        woodInstrumentationEntity.setClarinet(instrumentation.getClarinet());
        woodInstrumentationEntity.setBassoon(instrumentation.getBassoon());

        return woodInstrumentationEntity;
    }

    private StringInstrumentationEntity getStringInstrumentationEntityFromInstrumentation (Instrumentation instrumentation) {
        StringInstrumentationEntity stringInstrumentationEntity = new StringInstrumentationEntity();

        stringInstrumentationEntity.setViolin1(instrumentation.getViolin1());
        stringInstrumentationEntity.setViolin2(instrumentation.getViolin2());
        stringInstrumentationEntity.setViola(instrumentation.getViola());
        stringInstrumentationEntity.setViolincello(instrumentation.getViolincello());
        stringInstrumentationEntity.setDoublebass(instrumentation.getDoublebass());

        return stringInstrumentationEntity;
    }

    private BrassInstrumentationEntity getBrassInstrumentationEntityFromInstrumentation (Instrumentation instrumentation) {
        BrassInstrumentationEntity brassInstrumentationEntity = new BrassInstrumentationEntity();

        brassInstrumentationEntity.setHorn(instrumentation.getHorn());
        brassInstrumentationEntity.setTrumpet(instrumentation.getTrumpet());
        brassInstrumentationEntity.setTrombone(instrumentation.getTrombone());
        brassInstrumentationEntity.setTube(instrumentation.getTube());

        return brassInstrumentationEntity;
    }

    private PercussionInstrumentationEntity getPercussionInstrumentationEntityFromInstrumentation (Instrumentation instrumentation) {
        PercussionInstrumentationEntity percussionInstrumentationEntity = new PercussionInstrumentationEntity();

        percussionInstrumentationEntity.setKettledrum(instrumentation.getKettledrum());
        percussionInstrumentationEntity.setPercussion(instrumentation.getPercussion());
        percussionInstrumentationEntity.setHarp(instrumentation.getHarp());

        return percussionInstrumentationEntity;
    }

    private BrassInstrumentationEntity getBrassInstrumentationEntity(int brassInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from BrassInstrumentationEntity where brassInstrumentationId = :id");
        query.setParameter("id", brassInstrumentationID);
        query.setMaxResults(1);

        List<BrassInstrumentationEntity> bie = query.getResultList();

        BrassInstrumentationEntity entity = new BrassInstrumentationEntity();

        if (bie.size() > 0) {
            entity = bie.get(0);
        }

        return entity;
    }

    private WoodInstrumentationEntity getWoodInstrumentationEntity(int woodInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from WoodInstrumentationEntity where woodInstrumentationId = :id");
        query.setParameter("id", woodInstrumentationID);
        query.setMaxResults(1);

        List<WoodInstrumentationEntity> wie = query.getResultList();

        WoodInstrumentationEntity entity = new WoodInstrumentationEntity();

        if (wie.size() > 0) {
            entity = wie.get(0);
        }

        return entity;
    }

    private StringInstrumentationEntity getStringInstrumentationEntity(int stringInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from StringInstrumentationEntity where stringInstrumentationId = :id");
        query.setParameter("id", stringInstrumentationID);
        query.setMaxResults(1);

        List<StringInstrumentationEntity> sie = query.getResultList();

        StringInstrumentationEntity entity = new StringInstrumentationEntity();

        if (sie.size() > 0) {
            entity = sie.get(0);
        }

        return entity;
    }

    private PercussionInstrumentationEntity getPercussionInstrumentationEntity(int percussionInstrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from PercussionInstrumentationEntity where percussionInstrumentationId = :id");
        query.setParameter("id", percussionInstrumentationID);
        query.setMaxResults(1);

        List<PercussionInstrumentationEntity> pie = query.getResultList();

        PercussionInstrumentationEntity entity = new PercussionInstrumentationEntity();

        if (pie.size() > 0) {
            entity = pie.get(0);
        }

        return entity;
    }

    private List<SpecialInstrumentationEntity> getSpecialInstrumentationEntities(int instrumentationID) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from SpecialInstrumentationEntity where specialInstrumentationId = :id");
        query.setParameter("id", instrumentationID);

        List<SpecialInstrumentationEntity> spei = query.getResultList();

        return spei;
    }

    @Override
    public int add(Instrumentation value) {
        return addInstrumentation(value);
    }

    @Override
    public int update(Instrumentation value) {
        return addInstrumentation(value);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
