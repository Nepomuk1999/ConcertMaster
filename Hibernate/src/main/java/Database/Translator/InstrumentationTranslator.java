package Database.Translator;

import Enums.SectionType;
import team_f.database_wrapper.entities.*;
import Database.Facade.DatabaseFacade;
import Domain.Instrumentation.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
public class InstrumentationTranslator {
    public static InstrumentationDomainInterface transformInstrumentationToInterface(InstrumentationEntity instrumentation, DatabaseFacade facade) {
        InstrumentationDomainObject instrumentationInterface = new InstrumentationDomainObject();
        instrumentationInterface.setId(instrumentation.getInstrumentationId());
        instrumentationInterface.setBrassInstrumentation(transformBrassInstrumentationToInterface(instrumentation.getBrassInstrumentationByBrassInstrumentation()));
        instrumentationInterface.setPercussionInstrumentation(transformPercussionInstrumentationToInterface(instrumentation.getPercussionInstrumentationByPercussionInstrumentation()));
        instrumentationInterface.setStringInstrumentation(transformStringInstrumentationToInterface(instrumentation.getStringInstrumentationByStringInstrumentation()));
        instrumentationInterface.setWoodInstrumentation(transformWoodInstrumentationToInterface(instrumentation.getWoodInstrumentationByWoodInstrumentation()));
        instrumentationInterface.setSpecialInstrumentation(transformSpecialInstrumentationToInterface(instrumentationInterface, facade.getSpecialInstrumentationEntityForInstrumentation(instrumentation)));
        return instrumentationInterface;
    }

    private static WoodInstrumentationDomainInterface transformWoodInstrumentationToInterface(WoodInstrumentationEntity wood) {
        WoodInstrumentationDomainObject woodInterface = new WoodInstrumentationDomainObject();
        woodInterface.setId(wood.getWoodInstrumentationId());
        woodInterface.setBassoon(wood.getBassoon());
        woodInterface.setClarinet(wood.getClarinet());
        woodInterface.setFlute(wood.getFlute());
        woodInterface.setOboe(wood.getOboe());
        return woodInterface;
    }

    private static BrassInstrumentationDomainInterface transformBrassInstrumentationToInterface(BrassInstrumentationEntity brass) {
        BrassInstrumentationDomainObject brassInterface = new BrassInstrumentationDomainObject();
        brassInterface.setId(brass.getBrassInstrumentationId());
        brassInterface.setHorn(brass.getHorn());
        brassInterface.setTrombone(brass.getTrombone());
        brassInterface.setTrumpet(brass.getTrumpet());
        brassInterface.setTube(brass.getTube());
        return brassInterface;
    }

    private static StringInstrumentationDomainInterface transformStringInstrumentationToInterface(StringInstrumentationEntity string) {
        StringInstrumentationDomainObject stringInterface = new StringInstrumentationDomainObject();
        stringInterface.setId(string.getStringInstrumentationId());
        stringInterface.setViolin1(string.getViolin1());
        stringInterface.setViolin2(string.getViolin2());
        stringInterface.setViola(string.getViola());
        stringInterface.setViolincello(string.getViolincello());
        stringInterface.setDoublebass(string.getDoublebass());
        return stringInterface;
    }

    private static PercussionInstrumentationDomainInterface transformPercussionInstrumentationToInterface(PercussionInstrumentationEntity percussion) {
        PercussionInstrumentationDomainObject percussionInterface = new PercussionInstrumentationDomainObject();
        percussionInterface.setId(percussion.getPercussionInstrumentationId());
        percussionInterface.setHarp(percussion.getHarp());
        percussionInterface.setKettledrum(percussion.getKettledrum());
        percussionInterface.setPercussion(percussion.getPercussion());
        percussionInterface.setPercussionDescription(percussion.getPercussionDescription());
        return percussionInterface;
    }

    public static InstrumentationEntity transformInstrumentationToEntity(InstrumentationDomainInterface instrumentation, DatabaseFacade facade) {
        InstrumentationEntity entity;
        if (instrumentation.getId() != 0) {
            entity = facade.getInstrumentationEntityForId(instrumentation.getId());
            checkBrassInstrumentation(entity, instrumentation, facade);
            checkPercussionInstrumentation(entity, instrumentation, facade);
            checkStringInstrumentation(entity, instrumentation, facade);
            checkWoodInstrumentation(entity, instrumentation, facade);
        } else {
            entity = new InstrumentationEntity();
            checkBrassInstrumentation(entity, instrumentation, facade);
            checkPercussionInstrumentation(entity, instrumentation, facade);
            checkStringInstrumentation(entity, instrumentation, facade);
            checkWoodInstrumentation(entity, instrumentation, facade);
        }
        facade.saveInstrumentation(entity);
        return entity;
    }

    private static void checkWoodInstrumentation(InstrumentationEntity entity, InstrumentationDomainInterface instrumentation, DatabaseFacade facade) {
        WoodInstrumentationEntity woodEntity;
        if (instrumentation.getWoodInstrumentation().getId() == 0) {
            woodEntity = transformWoodInstrumentationToEntity(instrumentation.getWoodInstrumentation());
        } else {
            woodEntity = facade.getWoodInstrumentationEntityForId(instrumentation.getWoodInstrumentation().getId());
        }
        entity.setWoodInstrumentationByWoodInstrumentation(woodEntity);
        facade.saveWoodInstrumentation(woodEntity);
    }

    private static void checkStringInstrumentation(InstrumentationEntity entity, InstrumentationDomainInterface instrumentation, DatabaseFacade facade) {
        StringInstrumentationEntity stringEntity;
        if (instrumentation.getStringInstrumentation().getId() == 0) {
            stringEntity = transformStringInstrumentationToEntity(instrumentation.getStringInstrumentation());
        } else {
            stringEntity = facade.getStringInstrumentationEntityForId(instrumentation.getPercussionInstrumentation().getId());
        }
        entity.setStringInstrumentationByStringInstrumentation(stringEntity);
        facade.saveStringInstrumentation(stringEntity);
    }

    private static void checkPercussionInstrumentation(InstrumentationEntity entity, InstrumentationDomainInterface instrumentation, DatabaseFacade facade) {
        PercussionInstrumentationEntity percussionEntity;
        if (instrumentation.getPercussionInstrumentation().getId() == 0) {
            percussionEntity = transformPercussionInstrumentationToEntity(instrumentation.getPercussionInstrumentation());
        } else {
            percussionEntity = facade.getPercussionInstrumentationEntityForId(instrumentation.getPercussionInstrumentation().getId());
        }
        entity.setPercussionInstrumentationByPercussionInstrumentation(percussionEntity);
        facade.savePercussionInstrumentation(percussionEntity);
    }

    private static void checkBrassInstrumentation(InstrumentationEntity entity, InstrumentationDomainInterface instrumentation, DatabaseFacade facade) {
        BrassInstrumentationEntity brassEntity;
        if (instrumentation.getBrassInstrumentation().getId() == 0) {
            brassEntity = transformBrassInstrumentationToEntity(instrumentation.getBrassInstrumentation());
        } else {
            brassEntity = facade.getBrassInstrumentationEntityForId(instrumentation.getBrassInstrumentation().getId());
        }
        entity.setBrassInstrumentationByBrassInstrumentation(brassEntity);
        facade.saveBrassInstrumentation(brassEntity);
    }

    private static PercussionInstrumentationEntity transformPercussionInstrumentationToEntity(PercussionInstrumentationDomainInterface percussionInstrumentation) {
        PercussionInstrumentationEntity entity = new PercussionInstrumentationEntity();
        entity.setHarp(percussionInstrumentation.getHarp());
        entity.setKettledrum(percussionInstrumentation.getKettledrum());
        entity.setPercussion(percussionInstrumentation.getPercussion());
        entity.setPercussionDescription(percussionInstrumentation.getPercussionDescription());
        return entity;
    }

    private static BrassInstrumentationEntity transformBrassInstrumentationToEntity(BrassInstrumentationDomainInterface brassInstrumentation) {
        BrassInstrumentationEntity entity = new BrassInstrumentationEntity();
        entity.setHorn(brassInstrumentation.getHorn());
        entity.setTrombone(brassInstrumentation.getTrombone());
        entity.setTrumpet(brassInstrumentation.getTrumpet());
        entity.setTube(brassInstrumentation.getTube());
        return entity;
    }

    private static StringInstrumentationEntity transformStringInstrumentationToEntity(StringInstrumentationDomainInterface stringInstrumentation) {
        StringInstrumentationEntity entity = new StringInstrumentationEntity();
        entity.setDoublebass(stringInstrumentation.getDoublebass());
        entity.setViola(stringInstrumentation.getViola());
        entity.setViolin1(stringInstrumentation.getViolin1());
        entity.setViolin2(stringInstrumentation.getViolin2());
        entity.setViolincello(stringInstrumentation.getViolincello());
        return entity;
    }

    private static WoodInstrumentationEntity transformWoodInstrumentationToEntity(WoodInstrumentationDomainInterface woodInstrumentation) {
        WoodInstrumentationEntity entity = new WoodInstrumentationEntity();
        entity.setBassoon(woodInstrumentation.getBassoon());
        entity.setClarinet(woodInstrumentation.getClarinet());
        entity.setFlute(woodInstrumentation.getFlute());
        entity.setOboe(woodInstrumentation.getOboe());
        return entity;
    }

    private static List<SpecialInstrumentationDomainInterface> transformSpecialInstrumentationToInterface(InstrumentationDomainInterface instrumentation, List<SpecialInstrumentationEntity> specialInstrumentationForInstrumentation) {
        List<SpecialInstrumentationDomainInterface> returnList = new LinkedList<>();
        for (SpecialInstrumentationEntity entity : specialInstrumentationForInstrumentation) {
            SpecialInstrumentationDomainObject specialInstrumentationDomainInterface = new SpecialInstrumentationDomainObject();
            specialInstrumentationDomainInterface.setId(entity.getSpecialInstrumentationId());
            specialInstrumentationDomainInterface.setInstrumentation(instrumentation);
            specialInstrumentationDomainInterface.setSectionType(SectionType.valueOf(String.valueOf(entity.getSectionType())));
            specialInstrumentationDomainInterface.setSpecialInstrument(entity.getSpecialInstrument());
            specialInstrumentationDomainInterface.setSpecialInstrumentationNumber(entity.getSpecialInstrumentationNumber());
            returnList.add(specialInstrumentationDomainInterface);
        }
        return returnList;
    }

    public static List<SpecialInstrumentationEntity> transformSpecialInstrumentationToEntity(DatabaseFacade facade, List<SpecialInstrumentationDomainInterface> specialInstrumentationForInstrumentation) {
        List<SpecialInstrumentationEntity> returnList = new LinkedList<>();
        for (SpecialInstrumentationDomainInterface instrumentationInterface : specialInstrumentationForInstrumentation) {
            SpecialInstrumentationEntity specialInstrumentationEntity;
            if (instrumentationInterface.getId() != 0) {
                specialInstrumentationEntity = facade.getSpecialInstrumentationEntityForId(instrumentationInterface.getId());
            } else {
                specialInstrumentationEntity = new SpecialInstrumentationEntity();
            }
            specialInstrumentationEntity.setInstrumentationByInstrumentationId(InstrumentationTranslator.transformInstrumentationToEntity(instrumentationInterface.getInstrumentation(), facade));
            specialInstrumentationEntity.setSectionType(team_f.database_wrapper.enums.SectionType.valueOf(String.valueOf(instrumentationInterface.getSectionType())));
            specialInstrumentationEntity.setSpecialInstrument(instrumentationInterface.getSpecialInstrument());
            specialInstrumentationEntity.setSpecialInstrumentationNumber(instrumentationInterface.getSpecialInstrumentationNumber());
            returnList.add(specialInstrumentationEntity);
        }
        return returnList;
    }
}
