package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.InstrumentTypeEntity;
import team_f.domain.enums.InstrumentType;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class InstrumentTypeFacade extends BaseDatabaseFacade {
    public InstrumentTypeFacade() {
        super();
    }

    public InstrumentTypeFacade(EntityManager session) {
        super(session);
    }

    /**
     * Function to get all InstrumentTypes. Returns a List of InstrumentTypes
     *
     * @return instrumentTypes      List<InstrumentType>         returns a list of instrument types
     */
    public List<InstrumentType> getAllInstrumentTypes() {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from InstrumentTypeEntity");

        List<InstrumentTypeEntity> instrumentTypeEntities = query.getResultList();
        List<InstrumentType> instrumentTypes = new ArrayList<>(instrumentTypeEntities.size());
        InstrumentType instrument;

        for (InstrumentTypeEntity entity : instrumentTypeEntities) {
            instrument = convertToInstrumentType(entity);
            instrumentTypes.add(instrument);
        }

        return instrumentTypes;
    }

    public InstrumentType getInstrumentTypeById(int id) {
        EntityManager session = getCurrentSession();
        Query query = session.createQuery("from InstrumentTypeEntity where instrumentTypeId = :id");
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<InstrumentTypeEntity> instrumentTypeEntities = query.getResultList();
        // @TODO: should be possible
        InstrumentType instrumentType = null;
        //InstrumentType instrumentType = new InstrumentType();

        if (instrumentTypeEntities.size() > 0) {
            InstrumentTypeEntity entity = instrumentTypeEntities.get(0);
            instrumentType = convertToInstrumentType(entity);
        }

        return instrumentType;
    }

    /**
     * Function to convert an InstrumentTypeEntity object to an InstrumentType. Returns the InstrumentType after creating and setting information from InstrumentTypeEntity object.
     * @return instrumentType      InstrumentType        returns a instrument type object
     */
    protected InstrumentType convertToInstrumentType(InstrumentTypeEntity instrumentTypeEntity) {
        // @TODO: should not be an enum: read the values from the DB
        // InstrumentType instrumentType = new InstrumentType();
        //InstrumentType.setInstrumentTypeID(instrumentTypeEntity.getInstrumentTypeId());

        InstrumentType instrumentType = InstrumentType.valueOf(instrumentTypeEntity.getInstrumentType());

        return instrumentType;
    }
}