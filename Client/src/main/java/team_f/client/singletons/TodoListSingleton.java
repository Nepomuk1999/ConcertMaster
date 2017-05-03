package team_f.client.singletons;

import team_f.client.pages.home.TodolistHome;

public class TodoListSingleton {

    private static TodolistHome _todoListHome;

    private TodoListSingleton() {
    }

    public static TodolistHome getInstance() {
        if (_todoListHome == null) {
            _todoListHome = new TodolistHome();
        }

        return _todoListHome;
    }
}

