package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.InstrumentType;
import team_f.domain.enums.properties.InstrumentTypeProperty;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

import static team_f.domain.enums.properties.InstrumentProperty.ID;

public class InstrumentTypeLogic implements EntityLogic<InstrumentType, InstrumentTypeProperty> {
    protected InstrumentTypeLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(InstrumentType instrumentType, InstrumentTypeProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (InstrumentTypeProperty property : properties) {
            switch (property) {
                case ID:
                    if (!IntegerHelper.isValidId(instrumentType.getID())) {
                        resultList.add(new Pair<>(String.valueOf(ID), "is not in the correct range"));
                    }

                    break;
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(InstrumentType instrumentType) {
        return validate(instrumentType, InstrumentTypeProperty.values());
    }
}
