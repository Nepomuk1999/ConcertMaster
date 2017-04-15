package team_f.client.gui;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserCore;
import com.teamdev.jxbrowser.chromium.internal.Environment;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
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
import team_f.client.controls.Home.HomeScreen;
import team_f.client.controls.sidebar.Sidebar;
import team_f.client.helper.Web;
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
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        _configuration = AppConfiguration.getConfiguration(this);

        if(_configuration == null) {
            Common.closeApp(primaryStage, _configuration);
        }

        primaryStage.setTitle(_configuration.getAppName());
        primaryStage.getIcons().add(AppConfiguration.getAppIcon());

        // load the webbrowser
        Browser browser = Webbrowser.getBrowser(_configuration.getStartURI());
        BorderPane content = new BorderPane();
       // content.setCenter(new BrowserView(browser));
        content.setCenter(new HomeScreen());

        // set the menubar
        if(_configuration.getShowMenuBar()) {
            MenuBar menuBar = new MenuBar();

            Menu menuFile = new Menu("Datei");

            MenuItem menuItem;

            menuItem = new MenuItem("Startseite");
            menuItem.setOnAction(actionEvent -> browser.loadURL(_configuration.getStartURI()));
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            menuItem = new MenuItem("In Webbrowser öffen");
            menuItem.setOnAction(actionEvent -> {
                try {
                    Web.openInWebbrowser(new URI(_configuration.getStartURI()));
                } catch (URISyntaxException e) {
                }
            });
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            menuItem = new MenuItem("Beenden");
            menuItem.setOnAction(actionEvent -> Common.closeAppWithWarning(actionEvent, primaryStage, _configuration));
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            Menu menuHelp = new Menu("Hilfe");

            menuItem = new MenuItem("Info");
            menuItem.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText(_configuration.getAppName());
                alert.setContentText("Version: " + _version);
                alert.show();
            });
            menuHelp.getItems().add(menuItem);

            menuBar.getMenus().addAll(menuFile, menuHelp);
            content.setTop(menuBar);
        }

        // set the sidebar
        Sidebar sidebar = NavigationBar.getNavigationBar();
        content.setLeft(sidebar);

        // set window
        Scene scene = new Scene(content);

        if(_configuration.getWidth() < 0 || _configuration.getHeight() < 0) {
            primaryStage.setMaximized(true);
        }

        if(_configuration.getWidth() >= 0) {
            primaryStage.setWidth(_configuration.getWidth());
        }

        if(_configuration.getHeight() >= 0) {
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
