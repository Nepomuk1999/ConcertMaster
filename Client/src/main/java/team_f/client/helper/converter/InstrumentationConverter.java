package team_f.client.helper.converter;

import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.SpecialInstrumentation;

import java.util.ArrayList;
import java.util.List;

public class InstrumentationConverter {
    public static Instrumentation convertToJSON(team_f.domain.entities.Instrumentation instrumentation) {
        Instrumentation result = new Instrumentation();
        result.setInstrumentationID(instrumentation.getInstrumentationID());
        result.setBassoon(instrumentation.getBassoon());
        result.setClarinet(instrumentation.getClarinet());
        result.setDoublebass(instrumentation.getDoublebass());
        result.setFlute(instrumentation.getFlute());
        result.setHarp(instrumentation.getHarp());
        result.setHorn(instrumentation.getHorn());
        result.setKettledrum(instrumentation.getKettledrum());
        result.setOboe(instrumentation.getOboe());
        result.setPercussion(instrumentation.getPercussion());
        result.setTrombone(instrumentation.getTrombone());
        result.setTrumpet(instrumentation.getTrumpet());
        result.setTube(instrumentation.getTube());
        result.setViola(instrumentation.getViola());
        result.setViolin1(instrumentation.getViolin1());
        result.setViolin2(instrumentation.getViolin2());
        result.setViolincello(instrumentation.getViolincello());

        if(instrumentation.getSpecial() != null) {
            List<SpecialInstrumentation> specialInstrumentationList = new ArrayList<>(instrumentation.getSpecial().size());
            SpecialInstrumentation specialInstrumentation;

            for(team_f.domain.entities.SpecialInstrumentation item : instrumentation.getSpecial()) {
                specialInstrumentation = SpecialInstrumentationConverter.convertToJSON(item);
                specialInstrumentationList.add(specialInstrumentation);
            }

            result.setSpecialInstrumentation(specialInstrumentationList);
        }

        return result;
    }
}