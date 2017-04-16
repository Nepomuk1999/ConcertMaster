package team_f.server.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeHelper {
    private static final DateTimeFormatter _formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    public static LocalDateTime getLocalDateTime(String startDate, String startTime) {
        LocalDateTime result = null;

        try {
            result = LocalDateTime.parse(startDate + " " + startTime, _formatter);
        } catch (DateTimeParseException e) {
        }

        return result;
    }
}
