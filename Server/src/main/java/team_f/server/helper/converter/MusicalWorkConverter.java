package team_f.server.helper.converter;

import team_f.jsonconnector.entities.MusicalWork;

public class MusicalWorkConverter {
    public static MusicalWork convertToJSON(team_f.domain.entities.MusicalWork musicalWork) {
        MusicalWork result = new MusicalWork();
        result.setMusicalWorkID(musicalWork.getMusicalWorkID());
        result.setName(musicalWork.getName());

        if(musicalWork.getAlternativeInstrumentation() != null) {
            result.setAlternativeInstrumentationId(InstrumentationConverter.convertToJSON(musicalWork.getAlternativeInstrumentation()));
        }

        if(musicalWork.getInstrumentation() != null) {
            result.setInstrumentation(InstrumentationConverter.convertToJSON(musicalWork.getInstrumentation()));
        }

        result.setComposer(musicalWork.getComposer());

        return result;
    }
}
