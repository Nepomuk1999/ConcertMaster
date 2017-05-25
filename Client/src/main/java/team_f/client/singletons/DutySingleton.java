package team_f.client.singletons;

import Database.Facade.DatabaseFacade;
import team_f.client.pages.duty.Duty;

public class DutySingleton {
    private static Duty _duty;

    private DutySingleton() {
    }

    public static Duty getInstance() {
        if (_duty == null) {
            _duty = new Duty();
        } else {
            _duty.initialize();
        }

        DatabaseFacade.setPane(_duty);

        return _duty;
    }
}


