package Database.Facade.helper.converter;

import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainObject;
import Domain.MusicalWork.MusicalWorkViewInterface;
import team_f.jsonconnector.entities.MusicalWork;

public class MusicalWorkConverter {
    public static MusicalWorkDomainInterface convert(MusicalWork musicalWork) {
        MusicalWorkDomainObject musicalWorkDomain = new MusicalWorkDomainObject();
        musicalWorkDomain.setId(musicalWork.getMusicalWorkID());
        musicalWorkDomain.setComposer(musicalWork.getComposer());
        musicalWorkDomain.setInstrumentation(InstrumentationConverter.convert(musicalWork.getInstrumentation()));
        musicalWorkDomain.setName(musicalWork.getName());

        return musicalWorkDomain;
    }

    public static MusicalWork convert(MusicalWorkViewInterface musicalWork) {
        MusicalWork result = new MusicalWork();
        result.setMusicalWorkID(musicalWork.getId());
        result.setComposer(musicalWork.getComposer());
        result.setInstrumentation(InstrumentationConverter.convert(musicalWork.getInstrumentation()));
        result.setName(musicalWork.getName());

        return result;
    }
}
