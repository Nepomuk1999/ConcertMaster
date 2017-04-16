package team_f.domain.logic;

import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.EntityLogic;

public class DomainEntityManager {
    private static EventDutyLogic eventDutyLogic = new EventDutyLogic();

    public DomainEntityManager() {
    }

    public static EntityLogic getLogic(EntityType entity) {
        switch (entity) {
            case EVENT_DUTY:
                return eventDutyLogic;
            default:
                return null;
        }
    }
}
