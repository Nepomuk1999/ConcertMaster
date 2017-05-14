package team_f.client.gui;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;

public class Webbrowser {
    private static boolean _initialized = false;

    public static Parent getBrowser(URL initialURL) {
        /*if(!_initialized) {
            if (Environment.isMac()) {
                BrowserCore.initialize();
                _initialized = true;
            }
        }*/

        WebView _browserView = new WebView();
        _browserView.getEngine().load(initialURL.toExternalForm());
        addListeners(_browserView.getEngine());
        _browserView.getEngine().load(initialURL.toExternalForm());

        return _browserView;
    }

    private static void addListeners(WebEngine engine) {
        // @TODO: add feature
        /*engine.setPrintHandler(printJob -> {
            PrintSettings printSettings = printJob.getPrintSettings();
            printSettings.setLandscape(true);
            printSettings.setPrintBackgrounds(false);
            //printSettings.setPrintToPDF(true);

            String homeDir = System.getProperty("user.home");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss");
            String dateTime = LocalDateTime.now().format(format);
            printSettings.setPDFFilePath(Paths.get(homeDir, "events_" + dateTime + ".pdf").toString());

            printJob.addPrintJobListener(event -> {
            });

            PrintDialogModel printDialogModel = new PrintDialogModel(printSettings);
            PrintDialog printDialog = new PrintDialog(null, printDialogModel); // set appropriate owner and size of the window
            printDialog.setVisible(true);



            return PrintStatus.CONTINUE;
        });*/
    }
}
