package team_f.client.singletons;

import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import team_f.client.gui.Webbrowser;

import java.net.URL;

public class BrowserSingleton {
    private static BrowserView _browserView;

    private BrowserSingleton() {
    }

    public static BrowserView getInstance(URL initialURL) {
        if (_browserView == null) {
            _browserView = new BrowserView(Webbrowser.getBrowser(initialURL));
        }

        return _browserView;
    }
}


