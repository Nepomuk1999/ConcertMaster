package team_f.client.singletons;

import team_f.client.pages.musicalwork.MusicalWorkManagement;
import team_f.jsonconnector.entities.MusicalWork;

import java.net.URL;

/**
 * Created by dominik on 04.05.17.
 */
public class MusicalWorkSingleton {
    private static MusicalWorkManagement _musicalWork;

    private MusicalWorkSingleton() {
    }

    public static MusicalWorkManagement getInstance() {
        if (_musicalWork == null) {
            _musicalWork = new MusicalWorkManagement();
        }

        return _musicalWork;
    }
}
