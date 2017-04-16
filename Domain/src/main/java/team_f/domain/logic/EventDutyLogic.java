package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.EventDuty;
import team_f.domain.enums.EventDutyProperty;
import team_f.domain.enums.EventStatus;
import team_f.domain.enums.EventType;
import team_f.domain.enums.TransactionType;
import team_f.domain.helper.DateTimeHelper;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.helper.StringHelper;
import team_f.domain.interfaces.EntityLogic;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static team_f.domain.enums.EventDutyProperty.*;

public class EventDutyLogic implements EntityLogic<EventDuty, EventDutyProperty> {
    protected EventDutyLogic() {
    }


    @Override
    public List<Pair<EventDutyProperty, String>> validate(EventDuty eventDuty, EventDutyProperty... eventDutyproperty) {
        List<Pair<EventDutyProperty, String>> resultList = new LinkedList<>();

        for (EventDutyProperty property : eventDutyproperty) {

            switch (property) {
                case ID:
                    if (!IntegerHelper.isValidId(eventDuty.getEventDutyId())) {
                        resultList.add(new Pair<>(ID, "is not in the correct range"));
                    }
                    break;

                case START_TIME:
                    if(eventDuty.getStarttime() != null) {
                        if (!DateTimeHelper.liesInFuture(eventDuty.getStarttime())) {
                            resultList.add(new Pair<>(START_TIME, "is bygone"));
                        }
                    } else {
                        resultList.add(new Pair<>(START_TIME, "is empty"));
                    }
                    break;

                case END_TIME:
                    if(eventDuty.getEndtime() != null) {
                        if(!DateTimeHelper.liesInFuture(eventDuty.getEndtime())){
                            resultList.add(new Pair<>(END_TIME, "is bygone"));
                        }
                        if(!DateTimeHelper.compareDates(eventDuty.getStarttime(),eventDuty.getEndtime())){
                            resultList.add(new Pair<>(END_TIME, "is before Starttime"));
                        }
                    }else{
                        resultList.add(new Pair<>(END_TIME, "is empty"));
                    }
                    break;

                case DEFAULT_POINTS:
                    if (!IntegerHelper.isPositiveDefaultPoint(eventDuty.getDefaultPoints())) {
                            resultList.add(new Pair<>(DEFAULT_POINTS, "Only positive Points possible"));
                        }
                    break;

                case NAME:
                    if(eventDuty.getName() == null&&!StringHelper.isNotEmpty(eventDuty.getName())) {
                        resultList.add(new Pair<>(NAME, "is empty"));
                    }
                    break;

                case LOCATION:
                    if(eventDuty.getLocation() == null&&!StringHelper.isNotEmpty(eventDuty.getLocation())) {
                        resultList.add(new Pair<>(LOCATION, "is empty"));
                    }
                    break;

                case CONDUCTOR:
                    if(eventDuty.getConductor() == null&&!StringHelper.isNotEmpty(eventDuty.getConductor())) {
                        resultList.add(new Pair<>(CONDUCTOR, "is empty"));
                    }
                    break;

                case EVENT_STATUS:
                    if(eventDuty.getEventStatus() == null) {
                        resultList.add(new Pair<>(EVENT_STATUS, "is empty"));
                    }
                    else{
                        boolean isValid = false;
                        for(EventStatus eventStatus : EventStatus.values()) {
                            if(String.valueOf(eventStatus).equals(eventDuty.getEventStatus())) {
                                isValid = true;
                            }
                        }

                        if(!isValid) {
                            resultList.add(new Pair<>(EVENT_STATUS, "is not valid"));
                        }
                    }

                    break;

                case EVENT_TYPE:
                    if(eventDuty.getEventType() == null) {
                        resultList.add(new Pair<>(EVENT_TYPE, "is empty"));
                    }
                    else{
                        boolean isValid = false;
                        for(EventType eventType : EventType.values()) {
                            if(String.valueOf(eventType).equals(eventDuty.getEventType())) {
                                isValid = true;
                            }
                        }

                        if(!isValid) {
                            resultList.add(new Pair<>(EVENT_TYPE, "is not valid"));
                        }
                    }

                    break;



                    /*@TODO: Validation for
                        INSTRUMENTATION,
                        MUSICAL_WORK_LIST,

                        DISPOSITION_LIST,
                        SECTION_DUTY_ROSTER_LIST,
                        REQUEST_LIST
                        */





            }}


            return resultList;
        }


    }
