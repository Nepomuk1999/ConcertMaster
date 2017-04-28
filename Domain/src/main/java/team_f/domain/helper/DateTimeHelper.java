package team_f.domain.helper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateTimeHelper {
    public static boolean takesPlaceInFuture(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();

        if(!time.isBefore(now)){
            return true;
        }

       return false;
    }

    public static boolean compareDates(LocalDateTime starttime, LocalDateTime endtime) {
        if(!endtime.isBefore(starttime)){
            return true;
        }

        return false;
    }

    public static boolean compareRehearsalDate(LocalDateTime starttimeEvent,LocalDateTime endtimeRehearsal) {
        if((!endtimeRehearsal.isAfter(starttimeEvent))){
            return true;
        }

        return false;
    }

    public static Boolean periodExpired(LocalDateTime starttime,int limitMonths) {
        LocalDateTime fromDateTime = starttime;
        LocalDateTime toDateTime = LocalDateTime.now();

        if((fromDateTime.getMonthValue()-toDateTime.getMonthValue())>=limitMonths){
            return true;
        }else{
            return false;
        }

}
}


