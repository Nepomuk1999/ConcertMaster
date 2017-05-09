package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Instrumentation;
import team_f.domain.enums.InstrumentationProperty;
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

        LOOP:
        for (InstrumentationProperty property : properties) {

            switch (property) {
                // @TODO: validate the cases
                // use AccountLogic for the account specific logic
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Instrumentation instrumentation) {
        List<Pair<String, String>> result = new LinkedList<>();

        return validate(instrumentation, InstrumentationProperty.values());
    }
}
