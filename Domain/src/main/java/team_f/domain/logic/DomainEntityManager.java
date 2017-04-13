package team_f.domain.logic;

import team_f.database_wrapper.facade.Facade;
import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.EntityLogic;

public class DomainEntityManager {
    private Facade _facade;

    public DomainEntityManager() {
        _facade = new Facade();
    }

    public EntityLogic getLogic(EntityType entity) {
        switch (entity) {
            case EVENT_DUTY:
                return new EventDutyLogic(_facade);
            default:
                return null;
        }
    }
}
