package team_f.client.helper.converter;

import team_f.jsonconnector.entities.Instrument;
import team_f.jsonconnector.enums.InstrumentType;

public class InstrumentConverter {
    public static Instrument convertToJSON(team_f.domain.entities.Instrument instrument) {
        Instrument result = new Instrument();
        result.setInstrumentID(instrument.getInstrumentID());
        result.setBrand(instrument.getBrand());
        result.setModel(instrument.getModel());
        result.setInstrumentType(InstrumentType.valueOf(String.valueOf(instrument.getInstrumentType())));

        return result;
    }
}
