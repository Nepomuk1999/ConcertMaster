package helper;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JSON {
    public static String getJSONDate(Timestamp timestamp) {
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC"));
        return utcDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }
}
