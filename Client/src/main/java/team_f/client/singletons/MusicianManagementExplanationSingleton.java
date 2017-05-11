
package team_f.client.singletons;

import team_f.client.pages.musicianManagementExplanation.MusicianManagementExplanationPage;

/**
 * Created by paul on 10.05.2017.
 */
public class MusicianManagementExplanationSingleton {


    private static MusicianManagementExplanationPage _musicianManagementExplanationPage;

    private  MusicianManagementExplanationSingleton() {
    }

    public static MusicianManagementExplanationPage getInstance() {
        if (_musicianManagementExplanationPage == null) {
            _musicianManagementExplanationPage = new MusicianManagementExplanationPage();
        }

        return _musicianManagementExplanationPage;
    }
}