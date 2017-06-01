package team_f.client.singletons;

import team_f.application.EventApplication;
import team_f.client.configuration.Configuration;
import team_f.client.helper.converter.EventDutyConverter;
import team_f.client.pages.PageAction;
import team_f.client.pages.monthpublish.MonthPublishParameter;
import team_f.client.pages.monthpublish.MonthPublisher;
import team_f.domain.interfaces.DomainEntity;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.errorlist.EventDutyErrorList;
import java.util.LinkedList;
import java.util.List;

public class MonthPublisherSingleton {
    private static MonthPublisher _monthPublisher;
    private static EventApplication _facade = EventApplication.getInstance();
    private static Configuration _configuration;

    private MonthPublisherSingleton() {
    }

    public static MonthPublisher getInstance(Configuration configuration) {
        if (_monthPublisher == null) {
            _configuration = configuration;
            _monthPublisher = new MonthPublisher();

            _monthPublisher.setOnUpdate(new PageAction<EventDutyErrorList, Publish>() {
                @Override
                public EventDutyErrorList doAction(Publish value) {
                    EventDutyErrorList eventDutyErrorList = null;
                    List<javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>>> tmpErrorList = null;

                    if(value != null) {
                        // do some specific tasks
                        switch (value.getPublishType()) {
                            case UNPUBLISH:
                                tmpErrorList = _facade.unpublishEventsByMonth(value.getMonth(), value.getYear());

                                break;
                            case PUBLISH:
                                tmpErrorList = _facade.publishEventsByMonth(value.getMonth(), value.getYear());

                                break;
                        }

                        Error error;
                        List<Error> errors;
                        List<team_f.jsonconnector.entities.Pair<EventDuty, List<Error>>> list;
                        team_f.jsonconnector.entities.Pair pair;

                        if(tmpErrorList != null) {
                            list = new LinkedList<>();

                            for(javafx.util.Pair<DomainEntity, List<javafx.util.Pair<String, String>>> item : tmpErrorList) {
                                errors = new LinkedList<>();

                                pair = new team_f.jsonconnector.entities.Pair();
                                pair.setKey(EventDutyConverter.convertToJSON((team_f.domain.entities.EventDuty) item.getKey()));

                                if(item.getValue() != null) {
                                    for(javafx.util.Pair<String, String> val : item.getValue()) {
                                        error = new Error();
                                        error.setKey(val.getKey());
                                        error.setValue(val.getValue());

                                        errors.add(error);
                                    }
                                }

                                pair.setValue(errors);
                                list.add(pair);
                            }

                            eventDutyErrorList.setErrorList(list);

                            return eventDutyErrorList;
                        }
                    }

                    return eventDutyErrorList;
                }
            });

            _monthPublisher.setOnLoadList(new PageAction<List<EventDuty>, MonthPublishParameter>() {
                @Override
                public List<EventDuty> doAction(MonthPublishParameter value) {
                    if(value != null) {
                        List<team_f.domain.entities.EventDuty> eventDutyEntityList = _facade.getList();
                        List<team_f.jsonconnector.entities.EventDuty> eventDutyList = new LinkedList<>();
                        EventDuty eventDuty;

                        for(team_f.domain.entities.EventDuty item : eventDutyEntityList) {
                            eventDuty = EventDutyConverter.convertToJSON(item);
                            eventDutyList.add(eventDuty);
                        }

                        return eventDutyList;
                    }

                    return null;
                }
            });
        }

        return _monthPublisher;
    }
}

