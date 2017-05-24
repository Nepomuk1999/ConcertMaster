package Database.Facade.helper.converter;

import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainObject;
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
}
