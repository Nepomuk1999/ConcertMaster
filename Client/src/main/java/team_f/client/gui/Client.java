package team_f.client.gui;

import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import team_f.client.configuration.Configuration;
import team_f.client.controls.Home.TodolistHome;
import team_f.client.controls.Legende.LegendTable;
import team_f.client.controls.sidebar.Sidebar;
import team_f.client.helper.Web;
import team_f.client.singletons.HomeScreenSingleton;
import team_f.client.singletons.TodoListSingleton;

import java.net.URI;
import java.net.URISyntaxException;

public class Client extends Application {
    private final static String _version = "1.0.0";
    private Configuration _configuration;

    @Override
    public void init() throws Exception {
        // On Mac OS X Chromium engine must be initialized in non-UI thread.
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }

        // load the webbrowser instance at the startup to avoid unnecessary lags for the user
        Webbrowser.getBrowser("/");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        _configuration = AppConfiguration.getConfiguration(this);

        if (_configuration == null) {
            Common.closeApp(primaryStage, _configuration);
        }

        primaryStage.setTitle(_configuration.getAppName());
        primaryStage.getIcons().add(AppConfiguration.getAppIcon());

        BorderPane content = new BorderPane();

        BorderPane mainContent = new BorderPane();
        mainContent.setCenter(HomeScreenSingleton.getInstance());

        // set the menubar
        if (_configuration.getShowMenuBar()) {
            MenuBar menuBar = new MenuBar();

            Menu menuFile = new Menu("File");

            MenuItem menuItem;

            menuItem = new MenuItem("Home");
            menuItem.setOnAction(event -> mainContent.setCenter(HomeScreenSingleton.getInstance()));
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            menuItem = new MenuItem("Open in Browser");
            menuItem.setOnAction(actionEvent -> {
                try {
                    Web.openInWebbrowser(new URI(_configuration.getStartURI()));
                } catch (URISyntaxException e) {
                }
            });
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            menuItem = new MenuItem("Exit");
            menuItem.setOnAction(actionEvent -> Common.closeAppWithWarning(actionEvent, primaryStage, _configuration));
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            Menu menuHelp = new Menu("Help");

            menuItem = new MenuItem("Info");
            menuItem.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText(_configuration.getAppName());
                alert.setContentText("Version: " + _version);
                alert.show();
            });
            menuHelp.getItems().add(menuItem);

            Menu menuTodo = new Menu("My TodoList");
            menuItem = new MenuItem("Show TodoList");
            menuItem.setOnAction(actionEvent -> {
               new TodolistHome();

            });
            menuTodo.getItems().add(menuItem);

            Menu menuLegend = new Menu("Help");
            menuItem = new MenuItem("Show Schedule Explanation");
            menuItem.setOnAction(actionEvent -> {
                new LegendTable();

            });
            menuLegend.getItems().add(menuItem);

            menuBar.getMenus().addAll(menuFile, menuHelp, menuTodo, menuLegend);
            content.setTop(menuBar);
        }

        content.setCenter(mainContent);

        // set the sidebar
        Sidebar sidebar = NavigationBar.getNavigationBar(mainContent, _configuration);
        content.setLeft(sidebar);

        // set window
        Scene scene = new Scene(content);

        if (_configuration.getWidth() < 0 || _configuration.getHeight() < 0) {
            primaryStage.setMaximized(true);
        }

        if (_configuration.getWidth() >= 0) {
            primaryStage.setWidth(_configuration.getWidth());
        }

        if (_configuration.getHeight() >= 0) {
            primaryStage.setHeight(_configuration.getHeight());
        }

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(t -> Common.closeAppWithWarning(t, primaryStage, _configuration));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
