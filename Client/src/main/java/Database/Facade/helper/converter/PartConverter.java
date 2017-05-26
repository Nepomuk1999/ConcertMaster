package Database.Facade.helper.converter;

import Domain.Person.PartDomainObject;
import Domain.Person.PartTypeDomainObject;
import Enums.SectionType;
import team_f.jsonconnector.enums.InstrumentType;

import java.util.Arrays;
import java.util.List;

public class PartConverter {
    public static PartDomainObject convert(team_f.jsonconnector.enums.InstrumentType instrumentType) {
        PartDomainObject partDomain = new PartDomainObject();
        //partDomain.setId();
        PartTypeDomainObject partTypeDomainObject = new PartTypeDomainObject();
        //partTypeDomainObject.setId();

        // The description has to start with an uppercase letter. All others have to be lowercase.
        if(instrumentType != null) {
            String description = String.valueOf(instrumentType);

            if(description.length() > 0) {
                description = description.substring(0, 1).toUpperCase() + description.substring(1, description.length()).toLowerCase();
            }

            partTypeDomainObject.setDescription(description);
        }

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
        } else {
            // @TODO:
            sectionType = SectionType.Percussion;
        }

        List<InstrumentType> brass= Arrays.asList(InstrumentType.HORN,InstrumentType.TROMBONE, InstrumentType.TRUMPET,InstrumentType.TUBE,
                InstrumentType.CIMBASSO, InstrumentType.WAGNERTUBA, InstrumentType.EUPHONIUM, InstrumentType.SAXOPHONE);
        List<InstrumentType> viola= Arrays.asList(InstrumentType.VIOLA);
        List<InstrumentType> violin1= Arrays.asList(InstrumentType.FIRSTVIOLIN);
        List<InstrumentType> violin2= Arrays.asList(InstrumentType.SECONDVIOLIN);
        List<InstrumentType> woodwind= Arrays.asList(InstrumentType.FLUTE,InstrumentType.OBOE, InstrumentType.CLARINET,InstrumentType.BASSOON,
                InstrumentType.CONTRABASSOON, InstrumentType.HECKELPHONE, InstrumentType.BASSCLARINET, InstrumentType.PICCOLO, InstrumentType.CONTRABASSTROMBONE,
                InstrumentType.BASSTROMBONE, InstrumentType.BASSETHORN, InstrumentType.FRENCHHORN, InstrumentType.ENGLISHHORN);
        List<InstrumentType> doublebass= Arrays.asList(InstrumentType.DOUBLEBASS);
        List<InstrumentType> percussion= Arrays.asList(InstrumentType.PERCUSSION,InstrumentType.HARP, InstrumentType.KETTLEDRUM, InstrumentType.PIANO,
                InstrumentType.CELESTA, InstrumentType.ORGAN, InstrumentType.CEMBALO, InstrumentType.ACCORDEON, InstrumentType.KEYBOARD, InstrumentType.BANDONEON,
                InstrumentType.GUITAR, InstrumentType.MANDOLIN);
        List<InstrumentType> violincello= Arrays.asList(InstrumentType.VIOLONCELLO);

        partDomain.setSectionType(sectionType);
        return partDomain;
    }

    public static team_f.jsonconnector.enums.InstrumentType convert(PartDomainObject instrumentType) {
        // @TODO: skip now the conversion, but fix it in the future, when possible
        //return team_f.jsonconnector.enums.InstrumentType.valueOf(instrumentType.getPartType().getDescription());
        return InstrumentType.TUBE;
    }
}
