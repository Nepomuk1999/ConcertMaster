package team_f.database_wrapper.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JSON {
    public static String getJSONDate(LocalDateTime timestamp) {
        ZonedDateTime utcDateTime = timestamp.atZone(ZoneId.of("UTC"));
        return utcDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }
}
