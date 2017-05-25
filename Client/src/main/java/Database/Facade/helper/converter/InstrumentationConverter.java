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

    public static Instrumentation convert(InstrumentationViewInterface instrumentation) {
        Instrumentation result = new Instrumentation();
        result.setInstrumentationID(instrumentation.getId());

        BrassInstrumentationViewInterface brassInstrumentation = instrumentation.getBrassInstrumentation();

        if(brassInstrumentation != null) {
            result.setHorn(brassInstrumentation.getHorn());
            result.setTrombone(brassInstrumentation.getTrombone());
            result.setTrumpet(brassInstrumentation.getTrumpet());
            result.setTube(brassInstrumentation.getTube());
        }

        PercussionInstrumentationViewInterface percussionInstrumentation = instrumentation.getPercussionInstrumentation();

        if(percussionInstrumentation != null) {
            result.setHarp(percussionInstrumentation.getHarp());
            result.setKettledrum(percussionInstrumentation.getKettledrum());
            result.setPercussion(percussionInstrumentation.getPercussion());
        }

        List<SpecialInstrumentationViewInterface> specialInstrumentationList = instrumentation.getSpecialInstrumentation();

        if(specialInstrumentationList != null) {
            List<SpecialInstrumentation> tmpSpecialInstrumentationList = new ArrayList<>(specialInstrumentationList.size());
            SpecialInstrumentation tmpSpecialInstrumentation;

            for(SpecialInstrumentationViewInterface item : specialInstrumentationList) {
                tmpSpecialInstrumentation = new SpecialInstrumentation();
                tmpSpecialInstrumentation.setSpecialInstrumentationID(item.getId());
                tmpSpecialInstrumentation.setSectionType(String.valueOf(item.getSectionType()));
                tmpSpecialInstrumentation.setSpecialInstrumentation(item.getSpecialInstrument());
                tmpSpecialInstrumentation.setSpecialInstrumentationCount(item.getSpecialInstrumentationNumber());
            }

            result.setSpecialInstrumentation(tmpSpecialInstrumentationList);
        }

        StringInstrumentationViewInterface stringInstrumentation = instrumentation.getStringInstrumentation();

        if(stringInstrumentation != null) {
            result.setDoublebass(stringInstrumentation.getDoublebass());
            result.setViola(stringInstrumentation.getViola());
            result.setViolin1(stringInstrumentation.getViolin1());
            result.setViolin2(stringInstrumentation.getViolin2());
            result.setViolincello(stringInstrumentation.getViolincello());
        }

        WoodInstrumentationViewInterface woodInstrumentation = instrumentation.getWoodInstrumentation();

        if (woodInstrumentation != null) {
            result.setBassoon(woodInstrumentation.getBassoon());
            result.setClarinet(woodInstrumentation.getClarinet());
            result.setFlute(woodInstrumentation.getFlute());
            result.setOboe(woodInstrumentation.getOboe());
        }

        return result;
    }
}
