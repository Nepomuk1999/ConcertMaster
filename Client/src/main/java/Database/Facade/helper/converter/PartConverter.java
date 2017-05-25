package Database.Facade.helper.converter;

import Domain.Person.PartDomainObject;
import Domain.Person.PartTypeDomainObject;
import Enums.SectionType;
import team_f.jsonconnector.enums.InstrumentType;

public class PartConverter {
    public static PartDomainObject convert(team_f.jsonconnector.enums.InstrumentType instrumentType) {
        PartDomainObject partDomain = new PartDomainObject();
        //partDomain.setId();
        PartTypeDomainObject partTypeDomainObject = new PartTypeDomainObject();
        //partTypeDomainObject.setId();
        partTypeDomainObject.setDescription(String.valueOf(instrumentType));
        partDomain.setPartType(partTypeDomainObject);

        SectionType sectionType = null;

        if(InstrumentType.FRENCHHORN.equals(instrumentType) || InstrumentType.BASSTROMBONE.equals(instrumentType) || InstrumentType.CONTRABASSTROMBONE.equals(instrumentType)
                || InstrumentType.EUPHONIUM.equals(instrumentType) || InstrumentType.WAGNERTUBA.equals(instrumentType) || InstrumentType.CIMBASSO.equals(instrumentType)) {
            sectionType = SectionType.Brass;
        } else if(InstrumentType.ENGLISHHORN.equals(instrumentType) || InstrumentType.BASSETHORN.equals(instrumentType) || InstrumentType.PICCOLO.equals(instrumentType)
                || InstrumentType.BASSCLARINET.equals(instrumentType) || InstrumentType.HECKELPHONE.equals(instrumentType) || InstrumentType.CONTRABASSOON.equals(instrumentType)
                || InstrumentType.SAXOPHONE.equals(instrumentType)) {
            sectionType = SectionType.Woodwind;
        } else if(InstrumentType.PIANO.equals(instrumentType) || InstrumentType.CELESTA.equals(instrumentType) || InstrumentType.ORGAN.equals(instrumentType)
                || InstrumentType.CEMBALO.equals(instrumentType) || InstrumentType.KEYBOARD.equals(instrumentType) || InstrumentType.ACCORDEON.equals(instrumentType)
                || InstrumentType.BANDONEON.equals(instrumentType) || InstrumentType.GUITAR.equals(instrumentType) || InstrumentType.MANDOLIN.equals(instrumentType)) {
            sectionType = SectionType.Percussion;
        }

        partDomain.setSectionType(sectionType);

        return partDomain;
    }

    public static team_f.jsonconnector.enums.InstrumentType convert(PartDomainObject instrumentType) {
        return InstrumentType.valueOf(instrumentType.getPartType().getDescription());
    }
}
