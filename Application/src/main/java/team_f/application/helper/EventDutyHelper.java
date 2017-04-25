package team_f.application.helper;

import javafx.scene.paint.Color;
import team_f.domain.enums.EventType;

public class EventDutyHelper {
    public static Color getColor(EventType eventType) {
        if(eventType != null) {
            switch (eventType) {
                case Concert:
                    return Color.SADDLEBROWN;
                case Tour:
                    return Color.CORNFLOWERBLUE;
                case Opera:
                    return Color.DODGERBLUE;
                case Rehearsal:
                    return Color.BURLYWOOD;
                case Hofkapelle:
                    return Color.ROSYBROWN;
                case NonMusicalEvent:
                    return Color.DARKGRAY;
            }
        }

        return null;
    }

    public static String getEventTypeText(EventType eventType) {
        if(eventType != null) {
            switch (eventType) {
                case NonMusicalEvent:
                    return "Non Musical Event";
                case Hofkapelle:
                    return "Hofkapelle";
                case Rehearsal:
                    return "Rehearsal";
                case Opera:
                    return "Opera";
                case Tour:
                    return "Tour";
                case Concert:
                    return "Concert";
            }
        }

        return null;
    }
}
