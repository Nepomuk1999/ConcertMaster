package team_f.domain.logic;

import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.EntityLogic;

public class DomainEntityManager {
    private static EventDutyLogic eventDutyLogic = new EventDutyLogic();
    private static PersonLogic personLogic = new PersonLogic();
    private static InstrumentationLogic instrumentationLogic = new InstrumentationLogic();

    public DomainEntityManager() {
    }

    public static EntityLogic getLogic(EntityType entity) {
        switch (entity) {
            case EVENT_DUTY:
                return eventDutyLogic;
            case PERSON:
                return personLogic;
            case INSTRUMENTATION:
                return instrumentationLogic;
            default:
                return null;
        }
    }
}
