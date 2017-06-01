package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.DutyDisposition;
import team_f.domain.enums.properties.DutyDispositionProperty;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

import static team_f.domain.enums.properties.EventDutyProperty.ID;

public class DutyDispositionLogic implements EntityLogic<DutyDisposition, DutyDispositionProperty> {
    protected DutyDispositionLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(DutyDisposition dutyDisposition, DutyDispositionProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (DutyDispositionProperty property : properties) {

            switch (property) {
                case ID:
                    if (!IntegerHelper.isValidId(dutyDisposition.getID())) {
                        resultList.add(new Pair<>(String.valueOf(ID), "is not in the correct range"));
                    }

                    break;
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(DutyDisposition dutyDisposition) {
        return validate(dutyDisposition, DutyDispositionProperty.values());
    }
}
