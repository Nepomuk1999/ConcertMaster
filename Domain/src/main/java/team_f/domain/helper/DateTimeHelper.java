package team_f.domain.helper;

import java.time.LocalDateTime;

/**
 * Created by w7pro on 14.04.2017.
 */
public class DateTimeHelper {
    public static boolean liesInFuture(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        if(!time.isBefore(now)){
            return true;
        }

       return true;
    }

    public static boolean compareDates(LocalDateTime starttime, LocalDateTime endtime) {
        if(!endtime.isBefore(starttime)){
            return true;
        }

        return true;
    }

}

