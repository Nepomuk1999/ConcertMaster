package team_f.client.helper.converter;

import team_f.jsonconnector.entities.SpecialInstrumentation;

public class SpecialInstrumentationConverter {
    public static SpecialInstrumentation convertToJSON(team_f.domain.entities.SpecialInstrumentation specialInstrumentation) {
        SpecialInstrumentation result = new SpecialInstrumentation();
        result.setSpecialInstrumentationID(specialInstrumentation.getSpecialInstrumentationID());
        result.setSpecialInstrumentationCount(specialInstrumentation.getSpecialInstrumentCount());
        result.setSpecialInstrumentation(specialInstrumentation.getSpecialInstrument());
        result.setSectionType(specialInstrumentation.getSectionType());

        return result;
    }
}