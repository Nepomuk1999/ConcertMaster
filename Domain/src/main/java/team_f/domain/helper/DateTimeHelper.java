package team_f.domain.helper;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeHelper {
    public static boolean takesPlaceInFuture(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        if (!time.isBefore(now)) {
            return true;
        }

        return false;
    }

    public static boolean compareDates(LocalDateTime starttime, LocalDateTime endtime) {
        if (!endtime.isBefore(starttime)) {
            return true;
        }

        return false;
    }

    public static boolean compareRehearsalDate(LocalDateTime starttimeEvent, LocalDateTime endtimeRehearsal) {
        if ((!endtimeRehearsal.isAfter(starttimeEvent))) {
            return true;
        }

        return false;
    }

   public static Boolean periodExpired(LocalDateTime starttime, int limitDays) {
        LocalDateTime fromDateTime = starttime;
        LocalDateTime toDateTime = LocalDateTime.now();

       Instant instant1 = toDateTime.atZone(ZoneId.systemDefault()).toInstant();
       Date dateTo = Date.from(instant1);

       Instant instant2 = starttime.atZone(ZoneId.systemDefault()).toInstant();
       Date dateFrom = Date.from(instant2);

       long diff = dateFrom.getTime() - dateTo.getTime();

       if((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)>limitDays)){
            return true;
        } else {
            return false;
        }

    }


    }



