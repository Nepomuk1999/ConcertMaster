package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.SpecialInstrumentation;
import team_f.domain.enums.properties.SpecialInstrumentationProperty;
import team_f.domain.interfaces.EntityLogic;

import java.util.LinkedList;
import java.util.List;

public class SpecialInstrumentationLogic implements EntityLogic<SpecialInstrumentation, SpecialInstrumentationProperty> {
    protected SpecialInstrumentationLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(SpecialInstrumentation specialInstrumentation, SpecialInstrumentationProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (SpecialInstrumentationProperty property : properties) {

            switch (property) {
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(SpecialInstrumentation specialInstrumentation) {
        return validate(specialInstrumentation, SpecialInstrumentationProperty.values());
    }
}
