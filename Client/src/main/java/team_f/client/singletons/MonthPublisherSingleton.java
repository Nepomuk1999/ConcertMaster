package team_f.client.singletons;

import team_f.client.controls.MonthPublish.MonthPublisher;
import java.net.URL;

public class MonthPublisherSingleton {
    private static MonthPublisher _monthPublisher;

    private MonthPublisherSingleton() {
    }

    public static MonthPublisher getInstance(URL baseURL) {
        if(_monthPublisher == null) {
            _monthPublisher = new MonthPublisher(baseURL);
        }

        return _monthPublisher;
    }
}

