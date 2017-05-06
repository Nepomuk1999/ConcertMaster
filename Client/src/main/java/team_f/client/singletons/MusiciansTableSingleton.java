package team_f.client.singletons;

import team_f.client.pages.musicianmanagement.MusicianManagement;

import java.net.URL;

public class MusiciansTableSingleton {
    private static MusicianManagement _musicianTable;

    private MusiciansTableSingleton() {
    }

    public static MusicianManagement getInstance(URL baseURL) {
        if (_musicianTable == null) {
            _musicianTable = new MusicianManagement(baseURL);
        }

        return _musicianTable;
    }
}


