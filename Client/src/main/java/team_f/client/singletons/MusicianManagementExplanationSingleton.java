
package team_f.client.singletons;

import team_f.client.pages.musicianManagementExplanation.MMEPTable;

public class MusicianManagementExplanationSingleton {


    private static MMEPTable _mMEPTable;

    private  MusicianManagementExplanationSingleton() {
    }

    public static MMEPTable getInstance() {
        if (_mMEPTable == null) {
            _mMEPTable = new MMEPTable();
        }

        return _mMEPTable;
    }
}