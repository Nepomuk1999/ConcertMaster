package team_f.domain.entities;

import javafx.util.Pair;
import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.DomainEntity;
import team_f.domain.interfaces.DomainEntityProperty;
import team_f.domain.interfaces.EntityLogic;
import team_f.domain.interfaces.Validate;
import team_f.domain.logic.DomainEntityManager;
import java.util.List;

public abstract class BaseDomainEntity<P extends DomainEntityProperty> implements DomainEntity, Validate<P> {
    private static EntityLogic _logic;

    public BaseDomainEntity(EntityType entityType) {
        _logic = DomainEntityManager.getLogic(entityType);
    }

    @Override
    public List<Pair<String, String>> validate() {
        return _logic.validate(this);
    }
}
