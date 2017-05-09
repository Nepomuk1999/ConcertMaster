package team_f.client.singletons;

import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.scene.web.WebView;
import team_f.client.gui.Webbrowser;

import java.net.URL;

public class BrowserSingleton {
    //private static BrowserView _browserView;
    private static WebView _browserView;

    private BrowserSingleton() {
    }

    /*
    public static BrowserView getInstance(URL initialURL) {
        if (_browserView == null) {
            _browserView = new BrowserView(Webbrowser.getBrowser(initialURL));
        }

        return _browserView;
    }*/

    public static WebView getInstance(URL initialURL) {
        if (_browserView == null) {
            _browserView = new WebView();
            _browserView.getEngine().load(initialURL.toExternalForm());
        }

        return _browserView;
    }
}


