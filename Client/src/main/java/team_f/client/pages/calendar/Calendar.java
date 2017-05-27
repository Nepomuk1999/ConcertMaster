package team_f.client.pages.calendar;
import javafx.scene.Parent;
import team_f.client.configuration.Configuration;
import team_f.client.gui.Webbrowser;
import team_f.client.pages.BasePage;
import team_f.jsonconnector.common.URIList;
import javax.lang.model.type.NullType;
import java.net.MalformedURLException;
import java.net.URL;

public class Calendar extends BasePage<Void, NullType, NullType, NullType> {
    private Parent _browser;
    private Configuration _configuration;

    public Calendar(Configuration configuration){
        _configuration = configuration;
    }

    @Override
    public void initialize() {
        if(_initialize != null) {
            _initialize.doAction(null);
        }

        _browser = Webbrowser.getBrowser(getCalendarURL(), _configuration.getUseJxBrowser());
        setCenter(_browser);
    }

    @Override
    public void load() {
        if(_load != null) {
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void exit() {
        if(_exit != null) {
            _exit.doAction(null);
        }
    }

    @Override
    public void dispose() {
    }

    private URL getCalendarURL() {
        try {
            return new URL(new URL(_configuration.getRootURI()), URIList.calendar);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}
