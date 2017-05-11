package team_f.client.gui;

import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import team_f.client.configuration.Configuration;
import team_f.client.pages.BasePage;
import team_f.client.pages.home.TodoListHome;
import team_f.client.controls.sidebar.Sidebar;
import team_f.client.helper.WebHelper;
import team_f.client.pages.legende.LegendTable;
import team_f.client.pages.musicianManagementExplanation.MusicianManagementExplanationPage;
import team_f.client.singletons.BrowserSingleton;
import team_f.client.singletons.HomeScreenSingleton;
import team_f.client.singletons.LegendSingleton;
import team_f.client.singletons.MusicianManagementExplanationSingleton;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;

public class Client extends Application {
    private final static String _version = "1.0.0";
    private Configuration _configuration;

    public static void main(String[] args) {
        launch(args);
    }

    /*@Override
     public void init() throws Exception {
        // On Mac OS X Chromium engine must be initialized in non-UI thread.
        if (Environment.isMac()) {
            BrowserCore.initialize();
        }
    } */

    @Override
    public void start(Stage primaryStage) throws Exception {
        _configuration = AppConfiguration.getConfiguration(this);

        // @TODO: add i18n support and then set the correct language
        Locale.setDefault(Locale.ENGLISH);

        if (_configuration == null) {
            Common.closeApp(primaryStage, _configuration);
        }

        // load the webbrowser instance at the startup to avoid unnecessary lags for the user
        BrowserSingleton.getInstance(new URL(_configuration.getStartURI()));

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
                    WebHelper.openInWebbrowser(new URI(_configuration.getStartURI()));
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
            menuItem = new MenuItem("Show Schedule Explanation");
            menuItem.setOnAction(actionEvent -> {
                LegendTable legendTablePage = LegendSingleton.getInstance();
                legendTablePage.initialize();
                legendTablePage.load();
            });

            menuHelp.getItems().add(menuItem);

            //LegendTabel-äquivalent machen mit einfach Text (= auch singelton "kopieren")

            menuItem = new MenuItem("Show Musician Management Explanation"); //start
            menuItem.setOnAction(actionEvent -> {
                MusicianManagementExplanationPage mmep = MusicianManagementExplanationSingleton.getInstance();
                mmep.initialize();
                mmep.load();
                /*
                LegendTable legendTablePage = LegendSingleton.getInstance();
                legendTablePage.initialize();
                legendTablePage.load();  */
            });

            menuHelp.getItems().add(menuItem); //ende?





            menuItem = new MenuItem("Info");
            menuItem.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText(_configuration.getAppName());
                alert.setContentText("Version: " + _version);
                alert.show();
            });

            menuHelp.getItems().add(menuItem);

            Menu menuTodo = new Menu("Todo-List");
            menuItem = new MenuItem("Show Todo-List");
            menuItem.setOnAction(actionEvent -> {
                BasePage todoListPage = new TodoListHome();
                todoListPage.initialize();
                todoListPage.load();
            });
            menuTodo.getItems().add(menuItem);

            menuBar.getMenus().addAll(menuFile, menuTodo, menuHelp);
            content.setTop(menuBar);
        }

        content.setCenter(mainContent);

        // set the sidebar
        NavigationBar navigationBar = new NavigationBar(content, _configuration);
        Sidebar sidebar = navigationBar.getNavigationBar();
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
        primaryStage.setMaximized(true);

        primaryStage.show();
    }
}
