package team_f.domain.logic;

import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.EntityLogic;

public class DomainEntityManager {
    public DomainEntityManager() {
    }

    public EntityLogic getLogic(EntityType entity) {
        switch (entity) {
            case EVENT_DUTY:
                return new EventDutyLogic();
            default:
                return null;
        }
    }
}
