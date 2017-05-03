package team_f.client.singletons;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

public class BrowserSingleton {
    private static BrowserView _browserView;

    private BrowserSingleton() {
    }

    public static BrowserView getInstance() {
        if (_browserView == null) {
            _browserView = new BrowserView(new Browser());
        }

        return _browserView;
    }
}


