package team_f.client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sun.awt.OSInfo;
import team_f.client.configuration.Configuration;
import team_f.client.configuration.ConfigurationManager;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Client extends Application {
    private final static String _version = "1.0.0";
    private final static String _configName = "config";
    private final static String _configDelimiter = "\t";
    private Configuration _configuration;
    private Stage _primaryStage;
    private File _configFile;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Path configDir = Paths.get(System.getProperty("user.home"), ".ConcertMasterClient");
        // load the config file
        _configFile = new File(configDir.toString(), _configName);

        try {
            configDir.toFile().mkdirs();
            _configuration = ConfigurationManager.loadConfiguration(_configFile, "\t");
        } catch (IOException e) {
            // create new default Configuration
            _configuration = new Configuration();

            try {
                ConfigurationManager.saveConfiguration(_configuration, _configDelimiter, _configFile, true);
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("configuration could not be saved");
            }
        }

        // check if the webbrowser instead of this application should be started
        if(_configuration.getOpenInWebbrowser()) {
            if(openInWebbrowser(new URI(_configuration.getStartURI()))) {
                closeApp();
            }
        }

        _primaryStage = primaryStage;

        primaryStage.setTitle(_configuration.getAppName());
        primaryStage.getIcons().add(getAppIcon());

        // create webview
        WebView browser = new WebView();
        browser.setContextMenuEnabled(false);
        browser.setPrefSize(_configuration.getWidth(), _configuration.getHeight());
        WebEngine engine = browser.getEngine();
        engine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == Worker.State.FAILED) {
                        engine.loadContent("<!DOCTYPE html>\n" +
                                "<html>\n" +
                                "<head>\n" +
                                "    <meta charset=\"utf-8\">\n" +
                                "    <title>Fehler</title>\n" +
                                "</head>\n" +
                                "\n" +
                                "<body>\n" +
                                "    <h1>Der Dienst ist derzeit leider nicht erreichbar.</h1>\n" +
                                "<p>Sie können versuchen die Seite neuzuladen.</p>" +
                                "    <a href=\"" + _configuration.getStartURI() + "\">Nochmals versuchen</a>\n" +
                                "</body>\n" +
                                "</html>");
                    }
                });
        engine.load(_configuration.getStartURI());

        // set the webview
        BorderPane content = new BorderPane();
        content.setCenter(browser);

        // set the menubar
        if(_configuration.getShowMenuBar()) {
            MenuBar menuBar = new MenuBar();

            Menu menuFile = new Menu("Datei");

            MenuItem menuItem;

            menuItem = new MenuItem("Startseite");
            menuItem.setOnAction(actionEvent -> engine.load(_configuration.getStartURI()));
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            menuItem = new MenuItem("In Webbrowser öffen");
            menuItem.setOnAction(actionEvent -> {
                try {
                    openInWebbrowser(new URI(_configuration.getStartURI()));
                } catch (URISyntaxException e) {
                }
            });
            menuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
            menuFile.getItems().add(menuItem);

            menuItem = new MenuItem("Beenden");
            menuItem.setOnAction(actionEvent -> closeAppWithWarning(actionEvent));
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
        primaryStage.setOnCloseRequest(t -> closeAppWithWarning(t));

        primaryStage.show();
    }

    private void closeAppWithWarning(Event event) {
        boolean close = true;

        if(_configuration.getShowCloseWarning()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Beenden");
            alert.setHeaderText("Beenden");
            alert.setContentText("Wollen Sie die Anwendung wirklich schließen?");

            ButtonType result = alert.showAndWait().get();

             if(result == ButtonType.OK) {
                 close = true;
             } else {
                 close = false;
             }
        }

        if(close) {
            closeApp();
        } else {
            if(event != null) {
                event.consume();
            }
        }
    }

    private void closeApp() {
        // save config
        if(_primaryStage != null) {
            _configuration.setWidth((int) _primaryStage.getWidth());
            _configuration.setHeight((int) _primaryStage.getHeight());
        }

        try {
            ConfigurationManager.saveConfiguration(_configuration, _configDelimiter, _configFile, true);
        } catch (IOException e) {
        }

        Platform.exit();
        System.exit(0);
    }

    private boolean openInWebbrowser(URI uri) {
        if(OSInfo.getOSType() == OSInfo.OSType.LINUX) {
            try {
                new ProcessBuilder("x-www-browser", uri.toURL().toExternalForm()).start();
                return true;
            } catch (IOException e) {
            }
        } else if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(uri);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        return false;
    }

    private Image getAppIcon() {
        Image anotherIcon = new Image(getClass().getResourceAsStream("/icon.png"));

        return anotherIcon;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
