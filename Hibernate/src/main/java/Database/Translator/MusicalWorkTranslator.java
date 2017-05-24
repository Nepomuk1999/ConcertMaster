package Database.Translator;

import Database.Entity.MusicalWorkEntity;
import Database.Facade.DatabaseFacade;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
public class MusicalWorkTranslator {

    public static MusicalWorkEntity transformMusicalWorkToEntity(MusicalWorkDomainInterface e, DatabaseFacade facade) {
        MusicalWorkEntity mwe;
        if (e.getId() == 0) {
            mwe = new MusicalWorkEntity();
        } else {
            mwe = facade.getMusicalWorkEntityForId(e.getId());
        }
        mwe.setName(e.getName());
        mwe.setComposer(e.getComposer());
        mwe.setInstrumentationId(InstrumentationTranslator.transformInstrumentationToEntity(e.getInstrumentation(), facade));
        facade.saveMusicalWork(mwe);
        return mwe;
    }

    public static List<MusicalWorkEntity> transformMusicalWorksToEntity(List<MusicalWorkDomainInterface> musicalWorks, DatabaseFacade facade) {
        List<MusicalWorkEntity> musicalWorkEntities = new LinkedList<>();
        for (MusicalWorkDomainInterface musicalWork : musicalWorks) {
            musicalWorkEntities.add(transformMusicalWorkToEntity(musicalWork, facade));
        }
        return musicalWorkEntities;
    }

    public static List<MusicalWorkDomainInterface> transformMusicalWorksToInterface(List<MusicalWorkEntity> musicalWorks, DatabaseFacade facade) {
        List<MusicalWorkDomainInterface> mWorks = new LinkedList<>();
        for (MusicalWorkEntity entity : musicalWorks) {
            mWorks.add(transformMusicalWorkToInterface(entity, facade));
        }
        return mWorks;
    }

    public static MusicalWorkDomainInterface transformMusicalWorkToInterface(MusicalWorkEntity musicalWork, DatabaseFacade facade) {
        MusicalWorkDomainObject musicalWorkDomainObject = new MusicalWorkDomainObject();
        musicalWorkDomainObject.setId(musicalWork.getMusicalWorkId());
        musicalWorkDomainObject.setComposer(musicalWork.getComposer());
        musicalWorkDomainObject.setName(musicalWork.getName());
        musicalWorkDomainObject.setInstrumentation(InstrumentationTranslator.transformInstrumentationToInterface(musicalWork.getInstrumentationId(), facade));
        return musicalWorkDomainObject;
    }
}
