
package team_f.client.singletons;

import team_f.client.pages.musicalWorkManagementExplanation.MWMETable;

public class MusicalWorkManagementExplanationSingleton {


    private static MWMETable _mWMETable;

    private  MusicalWorkManagementExplanationSingleton() {
    }

    public static MWMETable getInstance() {
        if (_mWMETable == null) {
            _mWMETable = new MWMETable();
        }

        return _mWMETable;
    }
}