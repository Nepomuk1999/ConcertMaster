package team_f.database_wrapper.facade;

import team_f.database_wrapper.entities.InstrumentTypeEntity;
import team_f.database_wrapper.entities.MusicianPartEntity;
import team_f.database_wrapper.entities.PartEntity;
import team_f.database_wrapper.entities.PartTypeEntity;
import team_f.domain.enums.AllInstrumentTypes;
import team_f.domain.enums.InstrumentType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class InstrumentTypeFacade extends BaseDatabaseFacade<InstrumentType> {
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
        InstrumentType instrumentType;

        for (InstrumentTypeEntity entity : instrumentTypeEntities) {
            instrumentType = convertToInstrumentType(entity);
            instrumentTypes.add(instrumentType);
        }

        return instrumentTypes;
    }

    /** Function to get a list of database_wrapper entity instrumentTypeEntities objects from the DB with the
     *  specific Id to convert the first of them to a domain entity InstrumentType object.
     *
     * @param id
     * @return  instrumentType  InstrumentType
     */
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

    /** Function to get the parts a musician can play by his ID.
     *
     * @param id
     * @return parts    List<String>    returns a list of the parts the musician plays
     */
    protected List<String> getPlayedInstrumentsByPersonId(int id) {
        EntityManager session = getCurrentSession();

        // prevent SQL injections
        Query musicianPartQuery = session.createQuery("from MusicianPartEntity where musician = :id");
        musicianPartQuery.setParameter("id", id);

        List<MusicianPartEntity> musicianPartEntities = musicianPartQuery.getResultList();
        List<String> parts = new ArrayList<>();

        for (MusicianPartEntity musicianpartEntity : musicianPartEntities) {
            Query partQuery = session.createQuery("from PartEntity where partId = :id");
            partQuery.setParameter("id", musicianpartEntity.getPart());

            List<PartEntity> partEntities = partQuery.getResultList();

            if (!(partEntities.isEmpty())) {
                Query partTypeQuery = session.createQuery("from PartTypeEntity where partTypeId = :id");
                partTypeQuery.setParameter("id", partEntities.get(0).getPartType());
                List<PartTypeEntity> partTypeEntities = partTypeQuery.getResultList();

                if (!partTypeEntities.isEmpty()) {
                    parts.add(partTypeEntities.get(0).getPartType());
                }
            }
        }

        return parts;
    }

    /** Function to return the ID of the part by checking the InstrumentType
     *
     * @param instrumentType
     * @return  partId  Integer     returns the partId
     */
    protected Integer getPartIdByPlayedInstrument (AllInstrumentTypes instrumentType) {
        Integer partId;

        if (instrumentType.equals(AllInstrumentTypes.FIRSTVIOLIN)) {
            partId = 1;
        } else if (instrumentType.equals(AllInstrumentTypes.SECONDVIOLIN)) {
            partId = 2;
        } else if (instrumentType.equals(AllInstrumentTypes.VIOLA)) {
            partId = 3;
        } else if (instrumentType.equals(AllInstrumentTypes.VIOLONCELLO)) {
            partId = 4;
        } else if (instrumentType.equals(AllInstrumentTypes.DOUBLEBASS)) {
            partId = 5;
        } else if (instrumentType.equals(AllInstrumentTypes.ENGLISHHORN)) {
            partId = 6;
        } else if (instrumentType.equals(AllInstrumentTypes.FRENCHHORN)) {
            partId = 7;
        } else if (instrumentType.equals(AllInstrumentTypes.BASSETHORN)) {
            partId = 8;
        } else if (instrumentType.equals(AllInstrumentTypes.TRUMPET)) {
            partId = 9;
        } else if (instrumentType.equals(AllInstrumentTypes.TROMBONE)) {
            partId = 10;
        } else if (instrumentType.equals(AllInstrumentTypes.BASSTROMBONE)) {
            partId = 11;
        } else if (instrumentType.equals(AllInstrumentTypes.CONTRABASSTROMBONE)) {
            partId = 12;
        } else if (instrumentType.equals(AllInstrumentTypes.TUBE)) {
            partId = 13;
        } else if (instrumentType.equals(AllInstrumentTypes.KETTLEDRUM)) {
            partId = 14;
        } else if (instrumentType.equals(AllInstrumentTypes.PERCUSSION)) {
            partId = 15;
        } else if (instrumentType.equals(AllInstrumentTypes.HARP)) {
            partId = 16;
        } else if (instrumentType.equals(AllInstrumentTypes.FLUTE)) {
            partId = 17;
        } else if (instrumentType.equals(AllInstrumentTypes.PICCOLO)) {
            partId = 18;
        } else if (instrumentType.equals(AllInstrumentTypes.OBOE)) {
            partId = 19;
        } else if (instrumentType.equals(AllInstrumentTypes.CLARINET)) {
            partId = 20;
        } else if (instrumentType.equals(AllInstrumentTypes.BASSCLARINET)) {
            partId = 21;
        } else if (instrumentType.equals(AllInstrumentTypes.BASSOON)) {
            partId = 22;
        } else if (instrumentType.equals(AllInstrumentTypes.HECKELPHONE)) {
            partId = 23;
        } else if (instrumentType.equals(AllInstrumentTypes.CONTRABASSOON)) {
            partId = 24;
        } else if (instrumentType.equals(AllInstrumentTypes.SAXOPHONE)) {
            partId = 25;
        } else if (instrumentType.equals(AllInstrumentTypes.HORN)) {
            partId = 26;
        } else if (instrumentType.equals(AllInstrumentTypes.EUPHONIUM)) {
            partId = 27;
        } else if (instrumentType.equals(AllInstrumentTypes.WAGNERTUBA)) {
            partId = 28;
        } else if (instrumentType.equals(AllInstrumentTypes.CIMBASSO)) {
            partId = 29;
        } else if (instrumentType.equals(AllInstrumentTypes.PIANO)) {
            partId = 30;
        } else if (instrumentType.equals(AllInstrumentTypes.CELESTA)) {
            partId = 31;
        } else if (instrumentType.equals(AllInstrumentTypes.ORGAN)) {
            partId = 32;
        } else if (instrumentType.equals(AllInstrumentTypes.CEMBALO)) {
            partId = 33;
        } else if (instrumentType.equals(AllInstrumentTypes.KEYBOARD)) {
            partId = 34;
        } else if (instrumentType.equals(AllInstrumentTypes.ACCORDEON)) {
            partId = 35;
        } else if (instrumentType.equals(AllInstrumentTypes.BANDONEON)) {
            partId = 36;
        } else if (instrumentType.equals(AllInstrumentTypes.GUITAR)) {
            partId = 37;
        } else if (instrumentType.equals(AllInstrumentTypes.MANDOLIN)) {
            partId = 38;
        } else {
            partId = -1;
        }

        return partId;
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

        /*InstrumentType instrumentType = new InstrumentType();
        instrumentType.setID(instrumentTypeEntity.getInstrumentTypeId());
        instrumentType.setName(instrumentTypeEntity.getInstrumentType());*/

        return instrumentType;
    }

    @Override
    public int add(InstrumentType value) {
        return 0;
    }

    @Override
    public int update(InstrumentType value) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}