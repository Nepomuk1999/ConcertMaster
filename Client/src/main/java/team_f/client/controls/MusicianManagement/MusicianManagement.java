package team_f.client.controls.MusicianManagement;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MusicianManagement extends BorderPane {

    private final TextField firstNameField;
    private final TextField lastNameField;
    private final TextField streetField;
    private final TextField zipCodeField;
    private final TextField cityField;
    private final TextField sectionField;
    private final TextField instrumentField;
    private TableView<PersonTestData> table;


    public MusicianManagement() {
        firstNameField = new TextField();
        lastNameField = new TextField();
        streetField = new TextField();
        zipCodeField = new TextField();
        cityField = new TextField();
        sectionField = new TextField();
        instrumentField = new TextField();

        table = new TableView<>(MusicianTableHelper.getPersonList());
        table.setEditable(false);

        TableViewSelectionModel<PersonTestData> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);


        table.getColumns().addAll(MusicianTableHelper.getIdColumn(), MusicianTableHelper.getFirstNameColumn(),
                MusicianTableHelper.getLastNameColumn(), MusicianTableHelper.getStreetColumn(),
                MusicianTableHelper.getZipCodeColumn(), MusicianTableHelper.getCityColumn(), MusicianTableHelper.getSectionColumn(), MusicianTableHelper.getInstrumentColumn());


        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        GridPane newDataPane = this.getNewPersonDataPane();

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(e -> deletePerson());

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

    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);

        pane.addRow(0, new Label("First Name:"), firstNameField);
        pane.addRow(0, new Label("Last Name:"), lastNameField);
        pane.addRow(1, new Label("Street:"), streetField);
        pane.addRow(1, new Label("Zip Code:"), zipCodeField);
        pane.addRow(1, new Label("City:"), cityField);
        pane.addRow(2, new Label("Section:"), sectionField);
        pane.addRow(2, new Label("Instruments:"), instrumentField);

        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(firstNameField);
        fields.add(lastNameField);
        fields.add(streetField);
        fields.add(zipCodeField);
        fields.add(cityField);
        fields.add(sectionField);
        fields.add(instrumentField);

        Button addButton = new Button("Add");
        Button resetButton = new Button("Reset");

        resetButton.setOnAction(e -> {
            for (TextField field : fields) {
                field.setText(null);
                field.setStyle(null);
            }
        });

        addButton.setOnAction(e -> {
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || streetField.getText().isEmpty()
                    || zipCodeField.getText().isEmpty() || cityField.getText().isEmpty() || sectionField.getText().isEmpty()
                    || instrumentField.getText().isEmpty()) {
                validate(fields);

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Values Missing");
                alert.setHeaderText("Please fill in all the information in the form.");
                alert.setContentText("You can not save the Person. Please fill in missing data!");
                alert.showAndWait();

            } else {
                addPerson();
            }
        });

        pane.add(addButton, 6, 2);
        pane.add(resetButton, 7, 2);

        return pane;
    }

    public void addPerson() {
        Integer currentId = 0;

        // Get the next ID
        for (PersonTestData p : table.getItems()) {
            if (p.getId() > currentId) {
                currentId = p.getId();
            }
        }


        PersonTestData person = new PersonTestData(currentId + 1, firstNameField.getText(), lastNameField.getText(), streetField.getText(),
                zipCodeField.getText(), cityField.getText(), sectionField.getText(), instrumentField.getText());

        table.getItems().add(person);

        firstNameField.setText(null);
        lastNameField.setText(null);
        streetField.setText(null);
        zipCodeField.setText(null);
        cityField.setText(null);
        sectionField.setText(null);
        instrumentField.setText(null);
    }

    public void deletePerson() {

        TableViewSelectionModel<PersonTestData> tsm = table.getSelectionModel();

        // Check, if any rows are selected
        if (tsm.isEmpty()) {
            System.out.println("Select a row to delete!");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete");
        alert.setHeaderText("The following Person will be deleted");
        alert.setContentText(tsm.getSelectedItem().getFirstName() + " " + tsm.getSelectedItem().getLastName());

        ButtonType buttonTypeOne = new ButtonType("Delete");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeCancel) {
            alert.close();
            return;
        }


        // Get all selected row indices in an array
        ObservableList<Integer> list = tsm.getSelectedIndices();

        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        Arrays.sort(selectedIndices);

        for (int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }

    public void validate(ArrayList<TextField> fields) {
        for (TextField textField : fields) {
            if (textField.getText().isEmpty()) {
                textField.setStyle("-fx-border-color: red");
            } else {
                textField.setStyle("-fx-border-color: green");
            }
        }

    }
}
