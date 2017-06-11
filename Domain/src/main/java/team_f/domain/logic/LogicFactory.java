package team_f.domain.logic;

import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.EntityLogic;
import java.util.HashMap;

public class LogicFactory {
    private static HashMap<EntityType, EntityLogic> _logicList;

    private LogicFactory() {
    }

    public static EntityLogic getLogic(EntityType entity) {
        if(_logicList == null) {
            initialize();
        }

        return _logicList.get(entity);
    }

    private static void initialize() {
        _logicList = new HashMap<>();
        _logicList.put(EntityType.EVENT_DUTY, new EventDutyLogic());
        _logicList.put(EntityType.PERSON, new PersonLogic());
        _logicList.put(EntityType.ACCOUNT, new AccountLogic());
        _logicList.put(EntityType.INSTRUMENTATION, new InstrumentationLogic());
        _logicList.put(EntityType.MUSICALWORK, new MusicalWorkLogic());
        _logicList.put(EntityType.REQUEST, new RequestLogic());
        _logicList.put(EntityType.DUTY_DISPOSITION, new DutyDispositionLogic());
        _logicList.put(EntityType.INSTRUMENT, new InstrumentLogic());
        _logicList.put(EntityType.INSTRUMENT_TYPE, new InstrumentTypeLogic());
    }
}
