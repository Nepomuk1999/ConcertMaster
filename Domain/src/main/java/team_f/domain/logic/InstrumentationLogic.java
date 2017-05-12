package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Instrumentation;
import team_f.domain.enums.InstrumentType;
import team_f.domain.enums.InstrumentationProperty;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.interfaces.EntityLogic;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Christoph on 09.05.2017.
 */
public class InstrumentationLogic implements EntityLogic<Instrumentation, InstrumentationProperty> {

    @Override
    public List<Pair<String, String>> validate(Instrumentation instrumentation, InstrumentationProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        boolean allZero = true;

        for (InstrumentationProperty property : properties) {
            if (property == null) {
                resultList.add(new Pair<>(String.valueOf(InstrumentType.valueOf(property.toString())), "is empty"));
            } else {
                if (IntegerHelper.isBiggerThanZero(instrumentation.getByInstrumentType(InstrumentType.valueOf(property.toString())))) {
                    allZero = false;
                }
            }
        }

        if (allZero == true) {
            resultList.add(new Pair<>("", "All inputs are 0!"));
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Instrumentation instrumentation) {
        return validate(instrumentation, InstrumentationProperty.values());
    }
}
