package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Instrument;
import team_f.domain.enums.properties.InstrumentProperty;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

public class InstrumentLogic implements EntityLogic<Instrument, InstrumentProperty> {
    protected InstrumentLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(Instrument instrument, InstrumentProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (InstrumentProperty property : properties) {

            switch (property) {
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Instrument instrument) {
        return validate(instrument, InstrumentProperty.values());
    }
}
