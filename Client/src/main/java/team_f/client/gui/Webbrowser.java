package team_f.client.gui;

import com.teamdev.jxbrowser.chromium.Browser;

public class Webbrowser {
    public static Browser getBrowser(String initialURL) {
        Browser browser = new Browser();
        browser.loadURL(initialURL);

        return browser;
    }
}
