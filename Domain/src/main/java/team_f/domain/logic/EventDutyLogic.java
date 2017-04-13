package team_f.domain.logic;

import javafx.util.Pair;
import team_f.database_wrapper.facade.Facade;
import team_f.domain.entities.EventDuty;
import team_f.domain.enums.EventDutyProperty;
import team_f.domain.enums.TransactionType;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.helper.StringHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

import static team_f.domain.enums.EventDutyProperty.DESCRIPTION;
import static team_f.domain.enums.EventDutyProperty.NAME;

public class EventDutyLogic implements EntityLogic<EventDuty, EventDutyProperty> {
    private Facade _facade;

    protected EventDutyLogic(Facade facade) {
        _facade = facade;
    }

    @Override
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
    }

    @Override
    public List<Pair<EventDutyProperty, String>> validate(EventDuty eventDuty) {
        List<Pair<EventDutyProperty, String>> resultList = new LinkedList<>();

        if(eventDuty != null) {
            /*resultList.addAll(validate(new Pair(EventDutyProperty., user.getUserId()),
                                       new Pair(EventDutyProperty.FIRST_NAME, user.getFirstName()),
                                       new Pair(EventDutyProperty.LAST_NAME, user.getLastName()),
                                       new Pair(EventDutyProperty.EMAIL, user.getEmail()),
                                       new Pair(EventDutyProperty.PASSWORD, user.getPassword()),
                                       new Pair(EventDutyProperty.CATEGORIES, user.getCategories())
                              ));*/
        }

        return resultList;
    }

    @Override
    public List<Pair<EventDutyProperty, String>> update(TransactionType transactionType, EventDuty object) {
        return new LinkedList<>();
    }

    @Override
    public List<Pair<EventDutyProperty, String>> create(TransactionType transactionType, EventDuty object) {
        return new LinkedList<>();
    }

    @Override
    public List<Pair<EventDutyProperty, String>> delete(TransactionType transactionType, int id) {
        return new LinkedList<>();
    }

    @Override
    public List<EventDuty> getList(Pair<EventDutyProperty, Object> ... entries) {
        return new LinkedList<>();
    }

    @Override
    public EventDuty getEntity(Pair<EventDutyProperty, Object> ... entries) {
        return null;
    }
}