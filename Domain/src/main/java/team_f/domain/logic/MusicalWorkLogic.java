package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.MusicalWork;
import team_f.domain.enums.EntityType;
import team_f.domain.enums.MusicalWorkProperty;
import team_f.domain.interfaces.EntityLogic;

import java.util.LinkedList;
import java.util.List;

public class MusicalWorkLogic implements EntityLogic<MusicalWork, MusicalWorkProperty> {

    @Override
    public List<Pair<String, String>> validate(MusicalWork object, MusicalWorkProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for( MusicalWorkProperty property : properties){
            switch (property){
                case CONDUCTOR:
                    if ( object.getComposer() == null) {
                        resultList.add(new Pair<>(String.valueOf(MusicalWorkProperty.CONDUCTOR), "is not inserted"));
                    }

                    break;

                case TITLE:
                    if ( object.getName() == null) {
                        resultList.add(new Pair<>(String.valueOf(MusicalWorkProperty.TITLE), "is not inserted"));
                    }

                    break;

                case INSTRUMENTAMENTATION:
                    InstrumentationLogic instrumentationLogic = (InstrumentationLogic) DomainEntityManager.getLogic(EntityType.INSTRUMENTATION);
                    List<Pair<String, String>> errorList = InstrumentationLogic.validate(object.getInstrumentation());

                    break;
            }
        }
        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(MusicalWork object) {
        return null;
    }
}