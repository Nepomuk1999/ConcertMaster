package team_f.client.singletons;

import team_f.client.controls.MusicianManagement.MusicianManagement;

/**
 * Created by w7pro on 28.04.2017.
 */
public class MusiciansTableSingleton {
    private static MusicianManagement _legendTable;

    private MusiciansTableSingleton() {
    }

    public static MusicianManagement getInstance() {
        if(_legendTable == null) {
            _legendTable = new MusicianManagement();
        }

        return _legendTable;
    }
}


