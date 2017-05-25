package team_f.client.singletons;

import Database.Facade.DatabaseFacade;
import team_f.client.configuration.Configuration;
import team_f.client.pages.event.Event;

public class EventSingleton {
    private static Event _event;
    private static Configuration _configuration;

    private EventSingleton() {
    }

    public static Event getInstance(Configuration configuration) {
        if (_event == null) {
            _configuration = configuration;
            DatabaseFacade.initialize(configuration);
            _event = new Event();
        } else {
            _event.initialize();
        }

        DatabaseFacade.setPane(_event);

        return _event;
    }
}


