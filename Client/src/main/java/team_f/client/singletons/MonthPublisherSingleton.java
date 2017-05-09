package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.client.pages.PageAction;
import team_f.client.pages.monthpublish.MonthPublishParameter;
import team_f.client.pages.monthpublish.MonthPublisher;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.ErrorList;
import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.Publish;
import java.net.MalformedURLException;
import java.net.URL;
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

            _monthPublisher.setOnUpdate(new PageAction<Boolean, Publish>() {
                @Override
                public Boolean doAction(Publish value) {
                    boolean isSuccessful = false;

                    if(value != null) {
                        ErrorList requestPublish = (ErrorList) RequestResponseHelper.writeAndReadJSONObject(getPublishURL(), value, ErrorList.class);

                        if (requestPublish != null && requestPublish.getKeyValueList() != null && requestPublish.getKeyValueList().size() == 0) {
                            isSuccessful = true;
                        } else {
                            isSuccessful = false;
                        }

                        switch (value.getPublishType()) {
                            case UNPUBLISH:
                                break;
                            case PUBLISH:
                                break;
                        }

                    }

                    return isSuccessful;
                }
            });

            _monthPublisher.setOnLoadList(new PageAction<List<EventDuty>, MonthPublishParameter>() {
                @Override
                public List<EventDuty> doAction(MonthPublishParameter value) {
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
}

