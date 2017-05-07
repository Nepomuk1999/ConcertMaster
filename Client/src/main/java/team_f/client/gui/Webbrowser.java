package team_f.client.gui;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.internal.PrintDialogModel;
import com.teamdev.jxbrowser.chromium.javafx.DefaultPrintHandler;
import com.teamdev.jxbrowser.chromium.swing.internal.PrintDialog;

import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Webbrowser {
    public static Browser getBrowser(URL initialURL) {
        Browser browser = new Browser();

        addListeners(browser);
        browser.loadURL(initialURL.toExternalForm());

        return browser;
    }

    private static void addListeners(Browser browser) {
        // @TODO: add feature
        /*browser.setPrintHandler(printJob -> {
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
