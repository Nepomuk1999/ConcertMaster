package team_f.domain.logic;

import team_f.domain.enums.EntityType;
import team_f.domain.interfaces.EntityLogic;

public class DomainEntityManager {
    private static EventDutyLogic eventDutyLogic = new EventDutyLogic();
    private static PersonLogic personLogic = new PersonLogic();
    private static AccountLogic accountLogic = new AccountLogic();
    private static InstrumentationLogic instrumentationLogic = new InstrumentationLogic();
    private static MusicalWorkLogic musicalWorkLogic = new MusicalWorkLogic();
    private static RequestLogic requestLogic = new RequestLogic();

    public DomainEntityManager() {
    }

    public static EntityLogic getLogic(EntityType entity) {
        switch (entity) {
            case EVENT_DUTY:
                return eventDutyLogic;
            case PERSON:
                return personLogic;
            case ACCOUNT:
                return accountLogic;
            case INSTRUMENTATION:
                return instrumentationLogic;
            case MUSICALWORK:
                return musicalWorkLogic;
            case REQUEST:
                return requestLogic;
            default:
                return null;
        }
    }
}
