package team_f.client.singletons;

import team_f.client.controls.Home.HomeScreen;

public class HomeScreenSingleton {
    private static HomeScreen _homescreen;

    private HomeScreenSingleton() {
    }

    public static HomeScreen getInstance() {
        if(_homescreen == null) {
            _homescreen = new HomeScreen();
        }

        return _homescreen;
    }
}


