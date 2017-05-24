package Database.Facade.helper.converter;

import Domain.Instrumentation.*;
import Enums.SectionType;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.SpecialInstrumentation;
import java.util.ArrayList;
import java.util.List;

public class InstrumentationConverter {
    public static InstrumentationDomainInterface convert(Instrumentation instrumentation) {
        InstrumentationDomainObject instrumentationDomain = new InstrumentationDomainObject();
        instrumentationDomain.setId(instrumentation.getInstrumentationID());

        BrassInstrumentationDomainObject brassInstrumentationDomainObject = new BrassInstrumentationDomainObject();
        brassInstrumentationDomainObject.setId(instrumentation.getInstrumentationID());
        brassInstrumentationDomainObject.setHorn(instrumentation.getHorn());
        brassInstrumentationDomainObject.setTrombone(instrumentation.getTrombone());
        brassInstrumentationDomainObject.setTrumpet(instrumentation.getTrumpet());
        brassInstrumentationDomainObject.setTube(instrumentation.getTube());

        instrumentationDomain.setBrassInstrumentation(brassInstrumentationDomainObject);

        PercussionInstrumentationDomainObject percussionInstrumentationDomainObject = new PercussionInstrumentationDomainObject();
        percussionInstrumentationDomainObject.setId(instrumentation.getInstrumentationID());
        percussionInstrumentationDomainObject.setHarp(instrumentation.getHarp());
        percussionInstrumentationDomainObject.setKettledrum(instrumentation.getKettledrum());
        percussionInstrumentationDomainObject.setPercussion(instrumentation.getPercussion());
        percussionInstrumentationDomainObject.setPercussionDescription(instrumentation.getPercussionInstrumentation());

        instrumentationDomain.setPercussionInstrumentation(percussionInstrumentationDomainObject);

        if(instrumentation.getSpecialInstrumentation() != null) {
            List<SpecialInstrumentationDomainInterface> specialInstrumentationDomainObjectList = new ArrayList<>(instrumentation.getSpecialInstrumentation().size());
            SpecialInstrumentationDomainObject specialInstrumentationDomainObject;

            for(SpecialInstrumentation specialInstrumentation : instrumentation.getSpecialInstrumentation()) {
                specialInstrumentationDomainObject = new SpecialInstrumentationDomainObject();
                specialInstrumentationDomainObject.setId(specialInstrumentation.getSpecialInstrumentationID());
                specialInstrumentationDomainObject.setSectionType(SectionType.valueOf(String.valueOf(specialInstrumentation.getSectionType())));
                specialInstrumentationDomainObject.setSpecialInstrument(specialInstrumentation.getSpecialInstrumentation());
                specialInstrumentationDomainObject.setSpecialInstrumentationNumber(specialInstrumentation.getSpecialInstrumentCount());
                //specialInstrumentationDomainObject.setInstrumentation();
                specialInstrumentationDomainObjectList.add(specialInstrumentationDomainObject);
            }

            instrumentationDomain.setSpecialInstrumentation(specialInstrumentationDomainObjectList);
        }

        StringInstrumentationDomainObject stringInstrumentationDomainObject = new StringInstrumentationDomainObject();
        stringInstrumentationDomainObject.setId(instrumentation.getInstrumentationID());
        stringInstrumentationDomainObject.setDoublebass(instrumentation.getDoublebass());
        stringInstrumentationDomainObject.setViola(instrumentation.getViola());
        stringInstrumentationDomainObject.setViolin1(instrumentation.getViolin1());
        stringInstrumentationDomainObject.setViolin2(instrumentation.getViolin2());
        stringInstrumentationDomainObject.setViolincello(instrumentation.getViolincello());

        instrumentationDomain.setStringInstrumentation(stringInstrumentationDomainObject);

        WoodInstrumentationDomainObject woodInstrumentationDomainObject = new WoodInstrumentationDomainObject();
        woodInstrumentationDomainObject.setId(instrumentation.getInstrumentationID());
        woodInstrumentationDomainObject.setBassoon(instrumentation.getBassoon());
        woodInstrumentationDomainObject.setClarinet(instrumentation.getClarinet());
        woodInstrumentationDomainObject.setFlute(instrumentation.getFlute());
        woodInstrumentationDomainObject.setOboe(instrumentation.getOboe());

        instrumentationDomain.setWoodInstrumentation(woodInstrumentationDomainObject);

        return instrumentationDomain;
    }
}
