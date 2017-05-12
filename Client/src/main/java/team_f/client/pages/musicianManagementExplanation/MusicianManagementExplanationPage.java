package team_f.client.pages.musicianManagementExplanation;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import team_f.client.pages.BasePage;
import javax.lang.model.type.NullType;

public class MusicianManagementExplanationPage extends BasePage<Void, NullType, NullType, NullType> {
    public MusicianManagementExplanationPage(){

    }

    @Override
    public void initialize() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Musical Management Explanation");
        alert.setHeaderText("ConcertMaster Musical Management Explanation");

        Label label = new Label("This function is available to administrative personnel only.\n" +
                "\n" +
                "ConcertMaster allows you to manage the musicians of your orchestra. To manage musicians, click the " +
                "“Musician” button on the sidebar. The submenu that lists the available functions will appear. Choose " +
                "“Musician Management”.\n\n" +
                "You can now add musicians, external musicians, managers, musician librarians, facility managers and " +
                "substitutes by entering their personal information and what their role in the orchestra is, what section" +
                " they play in and what instrument they play. \n\n" +
                "On the bottom of the page you see a table that lists every person entered into the musician management. \n\n" +
                "To edit someone’s data press “Edit Selected Musician” after selecting an entry from the table. \n");
        label.setWrapText(true);
        label.setPrefWidth(300);
        label.setMaxHeight(Double.MAX_VALUE);

        //GridPane.setVgrow(table, Priority.ALWAYS);
        //GridPane.setHgrow(table, Priority.ALWAYS);
        //GridPane content = new GridPane();
        //content.setMinWidth(500);
        //content.setMinHeight(215);
        //content.add(label, 0, 0);
        //content.add(table, 0, 1);

        alert.getDialogPane().setContent(label);
        alert.showAndWait();

        //setCenter(table);
        //setVisible(true);
    }

    @Override
    public void load() {
    }

    @Override
    public void update() {
    }

    @Override
    public void exit() {
    }

    @Override
    public void dispose() {
    }

}
