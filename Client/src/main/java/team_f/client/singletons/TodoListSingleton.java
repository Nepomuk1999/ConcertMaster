package team_f.client.singletons;

import team_f.client.pages.home.TodoListHome;

public class TodoListSingleton {

    private static TodoListHome _todoListHome;

    private TodoListSingleton() {
    }

    public static TodoListHome getInstance() {
        if (_todoListHome == null) {
            _todoListHome = new TodoListHome();
        }

        return _todoListHome;
    }
}

