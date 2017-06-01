package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.pages.duty.Duty;

public class DutySingleton {
    private static Duty _duty;
    private static Configuration _configuration;

    private DutySingleton() {
    }

    public static Duty getInstance(Configuration configuration) {
        if (_duty == null) {
            _configuration = configuration;
            _duty = new Duty();
        } else {
            _duty.initialize();
        }

        return _duty;
    }
}


