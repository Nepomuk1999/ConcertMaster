package team_f.client.singletons;

import team_f.client.controls.MonthPublish.MonthPublisher;

/**
 * Created by w7pro on 30.04.2017.
 */
public class MonthPublisherSingleton {
    private static MonthPublisher _monthPublisher;

    private MonthPublisherSingleton() {
    }

    public static MonthPublisher getInstance() {
        if(_monthPublisher == null) {
            _monthPublisher = new MonthPublisher();
        }

        return _monthPublisher;
    }
}

