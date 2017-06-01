package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Instrument;
import team_f.domain.enums.properties.InstrumentProperty;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;
import static team_f.domain.enums.properties.InstrumentProperty.*;


public class InstrumentLogic implements EntityLogic<Instrument, InstrumentProperty> {
    protected InstrumentLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(Instrument instrument, InstrumentProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (InstrumentProperty property : properties) {
            switch (property) {
                case ID:
                    if (!IntegerHelper.isValidId(instrument.getID())) {
                        resultList.add(new Pair<>(String.valueOf(ID), "is not in the correct range"));
                    }

                    break;
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Instrument instrument) {
        return validate(instrument, InstrumentProperty.values());
    }
}
