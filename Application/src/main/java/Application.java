import enums.EventStatus;
import enums.EventType;

import java.sql.Timestamp;

/**
 * Created by Christoph on 13.04.2017.
 */
public class Application {

    public Application (){}

    Facade facade = new Facade();

    public Integer addEvent(String name, String description, Timestamp start, Timestamp end, EventType eventType,
                    EventStatus status, String conductor, String location, double defaultPoints, int instrumentation) {

    }
}
