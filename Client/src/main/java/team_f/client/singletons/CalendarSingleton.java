package team_f.client.singletons;

import team_f.client.configuration.Configuration;
import team_f.client.pages.calendar.Calendar;

public class CalendarSingleton {
    private static Calendar _calendarView;
    private static Configuration _configuration;

    private CalendarSingleton() {
    }

    public static Calendar getInstance(Configuration configuration) {
        if (_calendarView == null) {
            _configuration = configuration;
            _calendarView = new Calendar(_configuration);
        }

        return _calendarView;
    }
}


