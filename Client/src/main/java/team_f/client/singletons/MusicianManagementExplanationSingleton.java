
package team_f.client.singletons;

import team_f.client.pages.musicianManagementExplanation.MusicianManagementExplanationPage;

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