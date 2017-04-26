package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.EventDuty;
import team_f.domain.enums.EventDutyProperty;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import team_f.domain.helper.DateTimeHelper;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.helper.StringHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

import static team_f.domain.enums.EventDutyProperty.*;

public class EventDutyLogic implements EntityLogic<EventDuty, EventDutyProperty> {
    protected EventDutyLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(EventDuty eventDuty, EventDutyProperty... eventDutyproperty) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        for (EventDutyProperty property : eventDutyproperty) {

            switch (property) {
                case ID:
                    if (!IntegerHelper.isValidId(eventDuty.getEventDutyId())) {
                        resultList.add(new Pair<>(String.valueOf(ID), "is not in the correct range"));
                    }
                    break;

                case START_DATE:
                    if(eventDuty.getStartTime() != null) {
                        if (!DateTimeHelper.liesInFuture(eventDuty.getStartTime())) {
                            resultList.add(new Pair<>(String.valueOf(START_DATE), "is bygone"));
                        }
                    } else {
                        resultList.add(new Pair<>(String.valueOf(START_DATE), "is empty"));
                    }
                    break;

                case END_DATE:
                    if(eventDuty.getEndTime() != null) {
                        if(!DateTimeHelper.liesInFuture(eventDuty.getEndTime())){
                            resultList.add(new Pair<>(String.valueOf(END_DATE), "is bygone"));
                        }
                        if(!DateTimeHelper.compareDates(eventDuty.getStartTime(),eventDuty.getEndTime())){
                            resultList.add(new Pair<>(String.valueOf(END_DATE), "is before Starttime"));
                        }
                    } else {
                        //TODO: if empty then default value 3h
                        resultList.add(new Pair<>(String.valueOf(END_DATE), "is empty"));
                    }
                    break;

                case DEFAULT_POINTS:
                    if (!IntegerHelper.isPositiveDefaultPoint(eventDuty.getDefaultPoints())) {
                        resultList.add(new Pair<>(String.valueOf(DEFAULT_POINTS), "Only positive Points possible"));
                    }
                    break;

                case NAME:
                    if (eventDuty.getName() == null && !StringHelper.isNotEmpty(eventDuty.getName())) {
                        resultList.add(new Pair<>(String.valueOf(NAME), "is empty"));
                    }
                    break;

                case LOCATION:
                    if (eventDuty.getLocation() == null && !StringHelper.isNotEmpty(eventDuty.getLocation())) {
                        resultList.add(new Pair<>(String.valueOf(LOCATION), "is empty"));
                    }
                    break;

                case CONDUCTOR:
                    if (eventDuty.getConductor() == null && !StringHelper.isNotEmpty(eventDuty.getConductor())) {
                        resultList.add(new Pair<>(String.valueOf(CONDUCTOR), "is empty"));
                    }
                    break;

                case EVENT_STATUS:
                    if (eventDuty.getEventStatus() == null) {
                        resultList.add(new Pair<>(String.valueOf(EVENT_STATUS), "is empty"));
                    } else {
                        boolean isValid = false;
                        for (EventStatus eventStatus : EventStatus.values()) {
                            if (String.valueOf(eventStatus).equals(eventDuty.getEventStatus())) {
                                isValid = true;
                            }
                        }

                        if (!isValid) {
                            resultList.add(new Pair<>(String.valueOf(EVENT_STATUS), "is not valid"));
                        }
                    }

                    break;

                case EVENT_TYPE:
                    if (eventDuty.getEventType() == null) {
                        resultList.add(new Pair<>(String.valueOf(EVENT_TYPE), "is empty"));
                    } else {
                        boolean isValid = false;
                        for (EventType eventType : EventType.values()) {
                            if (String.valueOf(eventType).equals(String.valueOf(eventDuty.getEventType()))) {
                                isValid = true;
                            }
                        }
                        //TODO: for second Sprint: Validate if Event_type==opera, concert,....
                        //are all one-day events, except Tournee
                        // if(eventDuty.getEventType().toString().equals(EventType.Concert))

                        if (!isValid) {
                            resultList.add(new Pair<>(String.valueOf(EVENT_TYPE), "is not valid"));
                        }

                    }
                    break;

                case REHEARSAL_FOR:
                  //TODO:Check reheasal Date and Eventdate
                    }
                break;







    }




                    /*@TODO: Validation for
                        INSTRUMENTATION,
                        MUSICAL_WORK_LIST,

                        DISPOSITION_LIST,
                        SECTION_DUTY_ROSTER_LIST,
                        REQUEST_LIST
                        */


            return resultList;
        }


    @Override
    public List<Pair<String, String>> validate(EventDuty eventDuty) {
        List<Pair<String, String>> result = new LinkedList<>();

        return validate(eventDuty, EventDutyProperty.values());
    }


}
