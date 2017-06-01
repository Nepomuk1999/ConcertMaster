package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.MusicalWork;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.errors.InstrumentationError;
import team_f.domain.enums.properties.MusicalWorkProperty;
import team_f.domain.helper.StringHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;
import static team_f.domain.enums.properties.MusicalWorkProperty.*;

public class MusicalWorkLogic implements EntityLogic<MusicalWork, MusicalWorkProperty> {

    @Override
    public List<Pair<String, String>> validate(MusicalWork musicalWork, MusicalWorkProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        for( MusicalWorkProperty property : properties){
            switch (property){
                case CONDUCTOR:
                    if (musicalWork.getComposer() == null && !StringHelper.isNotEmpty(musicalWork.getComposer())) {
                        resultList.add(new Pair<>(String.valueOf(CONDUCTOR), "is empty"));
                    }

                case TITLE:
                    if (musicalWork.getName() == null && !StringHelper.isNotEmpty(musicalWork.getName())) {
                        resultList.add(new Pair<>(String.valueOf(TITLE), "is empty"));
                    }

                    break;

                case INSTRUMENTATION:
                    InstrumentationLogic instrumentationLogic = (InstrumentationLogic) DomainEntityManager.getLogic(EntityType.INSTRUMENTATION);
                    if (!(instrumentationLogic.validate(musicalWork.getInstrumentation()).isEmpty())) {
                        resultList.add(new Pair<>(String.valueOf(InstrumentationError.ALLZERO.toString()), " all inputs are null!"));
                    }
                    break;
            }
        }
        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(MusicalWork object) {
        return validate(object, MusicalWorkProperty.values());
    }
}
