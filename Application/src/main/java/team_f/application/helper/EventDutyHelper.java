package team_f.application.helper;

import javafx.scene.paint.Color;
import team_f.domain.entities.EventDuty;

public class EventDutyHelper {
    public static Color getColor(EventDuty eventDuty) {
        if(eventDuty != null) {
            switch (eventDuty.getEventType()) {
                case Concert:
                    break;
                default:
                break;
            }
        }

        return null;
    }
}
