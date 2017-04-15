package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.EventDuty;
import team_f.domain.enums.EventDutyProperty;
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

    /*@Override
    public List<Pair<EventDutyProperty, String>> validate(Pair<EventDutyProperty, Object> ... entries) {
        List<Pair<EventDutyProperty, String>> resultList = new LinkedList<>();
        Object value;

        if(entries != null) {
            for (Pair<EventDutyProperty, Object> entry : entries) {
                if (entry != null) {
                    value = entry.getValue();

                    switch (entry.getKey()) {
                        case ID:
                            if (value instanceof Integer) {
                                Integer tmp = (Integer) value;

                                if (!IntegerHelper.isValidId(tmp)) {
                                    resultList.add(new Pair<>(EventDutyProperty.ID, "is not in the correct range"));
                                }
                            } else {
                                resultList.add(new Pair<>(EventDutyProperty.ID, "has not the correct type"));
                            }

                            break;
                        case NAME:
                            if (value instanceof String) {
                                String tmp = (String) value;
                                resultList.add(new Pair<>(NAME, StringHelper.check(tmp, 3, 15)));
                            }

                            break;
                        case DESCRIPTION:
                            if (value instanceof String) {
                                String tmp = (String) value;
                                resultList.add(new Pair<>(DESCRIPTION, StringHelper.check(tmp, 3, 15)));
                            }

                            break;
                        // @TODO: implement more
                        default:
                            break;
                    }
                } else {
                    break;
                }
            }
        }

        return resultList;
    }*/

    @Override
    public List<Pair<EventDutyProperty, String>> validate(EventDuty eventDuty, EventDutyProperty... eventDutyproperty) {
        List<Pair<EventDutyProperty, String>> resultList = new LinkedList<>();

        for (EventDutyProperty property : eventDutyproperty) {

            switch (property) {
                case ID:
                    //Todo: check if not null! necessary or not?

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
                    if(!DateTimeHelper.liesInFuture(eventDuty.getEndtime())){
                        resultList.add(new Pair<>(END_TIME, "is bygone"));
                    }

                    if(!DateTimeHelper.compareDates(eventDuty.getStarttime(),eventDuty.getEndtime())){
                        resultList.add(new Pair<>(END_TIME, "is before Starttime"));
                    }

                    break;

                case DEFAULT_POINTS:
                    if (!IntegerHelper.isPositiveDefaultPoint(eventDuty.getDefaultPoints())) {
                        resultList.add(new Pair<>(DEFAULT_POINTS, "Only positive Points possible"));
                    }


                    break;

            }}


            return resultList;
        }
    }
