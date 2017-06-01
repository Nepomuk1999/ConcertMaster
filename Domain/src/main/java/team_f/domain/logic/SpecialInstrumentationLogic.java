package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.SpecialInstrumentation;
import team_f.domain.enums.properties.SpecialInstrumentationProperty;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.interfaces.EntityLogic;

import java.util.LinkedList;
import java.util.List;
import static team_f.domain.enums.properties.SpecialInstrumentationProperty.*;

public class SpecialInstrumentationLogic implements EntityLogic<SpecialInstrumentation, SpecialInstrumentationProperty> {
    protected SpecialInstrumentationLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(SpecialInstrumentation specialInstrumentation, SpecialInstrumentationProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (SpecialInstrumentationProperty property : properties) {
            switch (property) {
                case ID:
                    if (!IntegerHelper.isValidId(specialInstrumentation.getID())) {
                        resultList.add(new Pair<>(String.valueOf(ID), "is not in the correct range"));
                    }

                    break;
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(SpecialInstrumentation specialInstrumentation) {
        return validate(specialInstrumentation, SpecialInstrumentationProperty.values());
    }
}
