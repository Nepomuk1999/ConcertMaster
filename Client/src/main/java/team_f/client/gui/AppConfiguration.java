package team_f.client.gui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import team_f.client.configuration.Configuration;
import team_f.client.configuration.ConfigurationManager;
import team_f.client.helper.Web;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfiguration {
    private final static String _configName = "config";
    private final static String _configDelimiter = "\t";
    private static File _configFile;

    public static Configuration getConfiguration(Application application) {
        Configuration _configuration;

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
        if (_configuration.getOpenInWebbrowser()) {
            try {
                if (Web.openInWebbrowser(new URI(_configuration.getStartURI()))) {
                }
            } catch (URISyntaxException e) {
            }
        }

        return _configuration;
    }

    public static void saveConfiguration(Stage primaryStage, Configuration configuration) {
        // save config
        if (primaryStage != null) {
            configuration.setWidth((int) primaryStage.getWidth());
            configuration.setHeight((int) primaryStage.getHeight());
        }

        try {
            ConfigurationManager.saveConfiguration(configuration, _configDelimiter, _configFile, true);
        } catch (IOException e) {
        }
    }

    public static Image getAppIcon() {
        Image anotherIcon = new Image(AppConfiguration.class.getResourceAsStream("/Logo Programm.png"));

        return anotherIcon;
    }
}
