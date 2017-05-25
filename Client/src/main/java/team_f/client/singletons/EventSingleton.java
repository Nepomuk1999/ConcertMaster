package team_f.client.singletons;

import Database.Facade.DatabaseFacade;
import team_f.client.pages.event.Event;

public class EventSingleton {
    private static Event _event;

    private EventSingleton() {
    }

    public static Event getInstance() {
        if (_event == null) {
            _event = new Event();
        } else {
            _event.initialize();
        }

        DatabaseFacade.setPane(_event);

        return _event;
    }
}


