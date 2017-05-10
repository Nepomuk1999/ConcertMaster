package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.client.pages.PageAction;
import team_f.client.pages.monthpublish.MonthPublishParameter;
import team_f.client.pages.monthpublish.MonthPublisher;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.enums.request.ActionType;
import team_f.jsonconnector.enums.request.EventDutyParameter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MonthPublisherSingleton {
    private static MonthPublisher _monthPublisher;
    private static Configuration _configuration;

    private MonthPublisherSingleton() {
    }

    public static MonthPublisher getInstance(Configuration configuration) {
        if (_monthPublisher == null) {
            _configuration = configuration;
            _monthPublisher = new MonthPublisher();

            _monthPublisher.setOnLoad(new PageAction<List<EventDuty>, MonthPublishParameter>() {
                @Override
                public List<EventDuty> doAction(MonthPublishParameter value) {
                    return null;
                }
            });

            _monthPublisher.setOnUpdate(new PageAction<EventDutyErrorList, Publish>() {
                @Override
                public EventDutyErrorList doAction(Publish value) {
                    EventDutyErrorList eventDutyErrorList = null;

                    if(value != null) {
                        eventDutyErrorList = (EventDutyErrorList) RequestResponseHelper.writeAndReadJSONObject(getPublishURL(), value, EventDutyErrorList.class);

                        // do some specific tasks
                        switch (value.getPublishType()) {
                            case UNPUBLISH:
                                break;
                            case PUBLISH:
                                break;
                        }

                    }

                    return eventDutyErrorList;
                }
            });

            _monthPublisher.setOnLoadList(new PageAction<List<EventDuty>, MonthPublishParameter>() {
                @Override
                public List<EventDuty> doAction(MonthPublishParameter value) {
                    if(value != null) {
                        Request request = new Request();
                        request.setActionType(ActionType.GET_BY_PARAMETER);
                        Pair<String, String> tmpPair;
                        List<Pair<String, String>> parameterList = new LinkedList<>();

                        tmpPair = new Pair<>();
                        tmpPair.setKey(String.valueOf(EventDutyParameter.MONTH));
                        tmpPair.setValue(String.valueOf(value.getMonth()));
                        parameterList.add(tmpPair);

                        tmpPair = new Pair<>();
                        tmpPair.setKey(String.valueOf(EventDutyParameter.YEAR));
                        tmpPair.setValue(String.valueOf(value.getYear()));
                        parameterList.add(tmpPair);

                        request.setParameterKeyList(parameterList);

                        EventDutyList eventDuty = (EventDutyList) RequestResponseHelper.writeAndReadJSONObject(getEventDutyURL(), request, EventDutyList.class);

                        if(eventDuty != null) {
                            return eventDuty.getEventDutyList();
                        } else {
                            // @TODO: show error
                        }
                    }

                    return null;
                }
            });
        }

        return _monthPublisher;
    }

    private static URL getPublishURL() {
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.publish);
        } catch (MalformedURLException e) {
        }

        return null;
    }

    private static URL getEventDutyURL() {
        try {
            return new URL(new URL(_configuration.getStartURI()), URIList.event);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}

