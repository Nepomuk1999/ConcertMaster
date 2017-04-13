import enums.EventStatus;
import enums.EventType;
import team_f.database_wrapper.facade.Facade;
import team_f.domain.entities.EventDuty;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Christoph on 13.04.2017.
 */
public class Application {

    public Application (){}

    Facade facade = new Facade();

    public Integer addEvent(String name, String description, LocalDateTime start, LocalDateTime end, EventType eventType,
                            EventStatus status, String conductor, String location, double defaultPoints, int instrumentation) {

        EventDuty event = new EventDuty();

        event.setName(name);
        event.setDescription(description);
        event.setStarttime(start);
        event.setEndtime(end);
        event.setEventType(eventType.toString());
        event.setEventStatus(status.toString());
        event.setConductor(conductor);
        event.setLocation(location);
        event.setDefaultPoints(defaultPoints);
        event.setInstrumentation(instrumentation);

        return facade.addEvent(event);
    }

    public List<EventDuty> getEvents(int month, int year) {
        return facade.getEvents(month, year);
    }
}
