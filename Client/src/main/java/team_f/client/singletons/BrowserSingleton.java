package team_f.client.singletons;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;

public class BrowserSingleton {
    private static BrowserView _browserview;

    private BrowserSingleton() {
    }

    public static BrowserView getInstance() {
        if(_browserview == null) {
            _browserview = new BrowserView(new Browser());
        }

        return _browserview;
    }
}


