package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.DutyDisposition;
import team_f.domain.enums.properties.DutyDispositionProperty;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

public class DutyDispositionLogic implements EntityLogic<DutyDisposition, DutyDispositionProperty> {
    protected DutyDispositionLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(DutyDisposition dutyDisposition, DutyDispositionProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (DutyDispositionProperty property : properties) {

            switch (property) {
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(DutyDisposition dutyDisposition) {
        return validate(dutyDisposition, DutyDispositionProperty.values());
    }
}
