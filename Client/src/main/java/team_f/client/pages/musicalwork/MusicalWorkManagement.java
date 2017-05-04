package team_f.client.pages.musicalwork;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dominik on 04.05.17.
 */
public class MusicalWorkManagement extends BorderPane {
    private final TextField _nameField;
    private final TextField _composerField;
    private TableView<MusicalWorkTestData> table;


    public MusicalWorkManagement(){
        //Name, Komponist, Instrumentation, Alternativ
        _nameField = new TextField();
        _composerField = new TextField();

        table = new TableView<>(MusicalWorkHelper.getMusicalWorkList());
        table.setEditable(false);

        TableView.TableViewSelectionModel<MusicalWorkTestData> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);


        table.getColumns().addAll(MusicalWorkHelper.getIdColumn(), MusicalWorkHelper.getMusicalWorkNameColumn(),
                MusicalWorkHelper.getComposerColumn(), MusicalWorkHelper.getInstrumentationColumn());


        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        GridPane newDataPane = getNewMusicalWorkDataPane();

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(e -> deleteMusicalWork());

        VBox root = new VBox();
        root.getChildren().addAll(newDataPane, deleteButton, table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        setCenter(root);
    }

    public GridPane getNewMusicalWorkDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(7);


        pane.addRow(0, new Label("Name:"), _nameField);
        pane.addRow(0, new Label("Composer:"), _composerField);

        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(_nameField);
        fields.add(_composerField);

        Button addButton = new Button("Add");
        Button resetButton = new Button("Reset");

        resetButton.setOnAction(e -> {
            for (TextField field : fields) {
                field.setText(null);
                field.setStyle(null);
            }
        });

        addButton.setOnAction(e -> {

            if (_nameField.getText().isEmpty() || _composerField.getText().isEmpty()) {

                validate(fields);

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Values Missing");
                alert.setHeaderText("Please fill in all the information in the form.");
                alert.setContentText("You can not save this MusicalWork. Please fill in missing data!");
                alert.showAndWait();

            } else {
                addMusicalWork();
            }
        });

        pane.add(addButton, 6, 0);
        pane.add(resetButton, 7, 0);

        return pane;
    }

    private void validate(ArrayList<TextField> fields) {
        for (TextField textField : fields) {
            if (textField.getText().isEmpty()) {
                textField.setStyle("-fx-border-color: red");
            } else {
                textField.setStyle("-fx-border-color: green");
            }
        }
    }


    public void addMusicalWork() {

    }

    public void deleteMusicalWork(){

    }
}
