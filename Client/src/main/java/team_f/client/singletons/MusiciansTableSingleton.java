package team_f.client.singletons;

import team_f.client.pages.musicianmanagement.MusicianManagement;

public class MusiciansTableSingleton {
    private static MusicianManagement _musicianTable;

    private MusiciansTableSingleton() {
    }

    public static MusicianManagement getInstance() {
        if (_musicianTable == null) {
            _musicianTable = new MusicianManagement();
        }

        return _musicianTable;
    }
}


