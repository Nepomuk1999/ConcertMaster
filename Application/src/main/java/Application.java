import enums.EventStatus;
import enums.EventType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
        event.setStartTime(start);
        event.setEndTime(end);
        event.setEventType(eventType);
        event.setEventStatus(status);
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
