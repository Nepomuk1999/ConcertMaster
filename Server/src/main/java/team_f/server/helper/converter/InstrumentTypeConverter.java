package team_f.server.helper.converter;

import team_f.jsonconnector.entities.InstrumentType;

public class InstrumentTypeConverter {
    public static InstrumentType convertToJSON(team_f.domain.entities.InstrumentType instrumentType) {
        InstrumentType result = new InstrumentType();
        result.setID(instrumentType.getID());
        result.setName(instrumentType.getName());

        return result;
    }
}
