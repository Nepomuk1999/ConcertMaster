package team_f.server.helper;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeHelper {
    private static final DateTimeFormatter _formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
    private static final String _iCalDateTimeFormat = "yyyy-dd-mm'T'HH:mm";
    private static final TimeZoneRegistry _timeZoneRegistry = TimeZoneRegistryFactory.getInstance().createRegistry();

    public static LocalDateTime getLocalDateTime(String startDate, String startTime) {
        LocalDateTime result = null;

        try {
            result = LocalDateTime.parse(startDate + " " + startTime, _formatter);
        } catch (DateTimeParseException e) {
        }

        return result;
    }

    public static DateTime convertToICalDateTime(LocalDateTime dateTime) {
        DateTime result = null;

        try {
            result = new DateTime(dateTime.toString(), _iCalDateTimeFormat, _timeZoneRegistry.getTimeZone("UTC"));
        } catch (ParseException e) {
        }

        return result;
    }
}