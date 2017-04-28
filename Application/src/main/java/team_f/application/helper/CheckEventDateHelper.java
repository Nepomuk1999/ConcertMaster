package team_f.application.helper;

import team_f.application.EventApplication;
import team_f.domain.entities.EventDuty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by w7pro on 28.04.2017.
 */
public class CheckEventDateHelper {
    public static Boolean checkEvents (EventDuty eventDuty) {
        EventApplication facade = new EventApplication();
        List<EventDuty> eventDuties = facade.getDateEventList(eventDuty.getStartTime());
        List<EventDuty> rehearsals = new ArrayList<>();
        for(EventDuty event : eventDuties){
            if(event.getRehearsalFor()!=null&&eventDuty.getRehearsalFor().getEventDutyId()==event.getRehearsalFor().getEventDutyId()){
                rehearsals.add(event);
            }
        }
        if(rehearsals.size()>0){
            for(EventDuty equalRehearsal:rehearsals){
            //Probe darf nicht gleichzeitig beginnen oder überschneiden
            }
        }
        return true;
    }

    //TODO:
    public static boolean checkDates(LocalDateTime starttimeEvent,LocalDateTime endtimeRehearsal) {
        if((!endtimeRehearsal.isAfter(starttimeEvent))){
            return true;
        }

        return false;
    }
}
