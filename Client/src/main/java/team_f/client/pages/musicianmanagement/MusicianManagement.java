package team_f.client.pages.musicianmanagement;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MusicianManagement extends BorderPane {

    private final TextField _firstNameField;
    private final TextField _lastNameField;
    private final TextField _streetField;
    private final TextField _emailField;
    private final TextField _phoneField;
    private TableView<PersonTestData> table;
    private ComboBox<String> _comboBoxSection;
    private ComboBox<String> _comboBoxInstrument;
    private ComboBox<String> _comboBoxRole;
    private ComboBox<String> _comboBoxGender;

    private final TextField _usernameField;
    private ComboBox<String> _comboBoxAccountRole;


    public MusicianManagement() {


        _firstNameField = new TextField();
        _lastNameField = new TextField();
        _streetField = new TextField();
        _emailField = new TextField();
        _phoneField = new TextField();


        _comboBoxSection=new ComboBox<>();
        _comboBoxInstrument=new ComboBox<>();
        _comboBoxRole=new ComboBox<>();
        _comboBoxGender=new ComboBox<>();


        _usernameField = new TextField();
        _comboBoxAccountRole=new ComboBox<>();


        table = new TableView<>(MusicianTableHelper.getPersonList());
        table.setEditable(false);

        TableViewSelectionModel<PersonTestData> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);


        table.getColumns().addAll(MusicianTableHelper.getIdColumn(), MusicianTableHelper.getFirstNameColumn(),
                MusicianTableHelper.getLastNameColumn(), MusicianTableHelper.getStreetColumn(),
                MusicianTableHelper.getZipCodeColumn(), MusicianTableHelper.getPhonenumberColumn(), MusicianTableHelper.getRoleColumn(), MusicianTableHelper.getSectionColumn(), MusicianTableHelper.getInstrumentColumn());


        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        _comboBoxSection.setStyle("-fx-font: 14 arial;");
        _comboBoxSection.getSelectionModel().selectFirst();
        _comboBoxSection.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                System.out.println("selected");
            }
        });


        _comboBoxInstrument.setStyle("-fx-font: 14 arial;");
        _comboBoxInstrument.getSelectionModel().selectFirst();
        _comboBoxInstrument.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                System.out.println("selected");
            }
        });

        _comboBoxRole.setStyle("-fx-font: 14 arial;");
        _comboBoxRole.getSelectionModel().selectFirst();
        _comboBoxRole.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                System.out.println("selected");
            }
        });

        _comboBoxGender.setStyle("-fx-font: 14 arial;");
        _comboBoxGender.getSelectionModel().selectFirst();
        _comboBoxGender.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                System.out.println("selected");
            }
        });


        _comboBoxAccountRole.setStyle("-fx-font: 14 arial;");
        _comboBoxAccountRole.getSelectionModel().selectFirst();
        _comboBoxAccountRole.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                System.out.println("selected");
            }
        });


        GridPane newDataPane = getNewPersonDataPane();

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(e -> deletePerson());

        VBox root = new VBox();
        root.getChildren().addAll(newDataPane,table,deleteButton );
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
        pane.setVgap(10);

        pane.addRow(0, new Label("Role:"), _comboBoxSection);
        pane.addRow(0, new Label("Section:"), _comboBoxInstrument);
        pane.addRow(0, new Label("Instruments:"), _comboBoxRole);
        pane.addRow(1, new Label("Gender:"), _comboBoxGender);
        pane.addRow(1, new Label("First Name:"), _firstNameField);
        pane.addRow(1, new Label("Last Name:"), _lastNameField);
        pane.addRow(2, new Label("Street:"), _streetField);
        pane.addRow(2, new Label("Email:"), _emailField);
        pane.addRow(2, new Label("Phonenumber:"), _phoneField);
        pane.addRow(3, new Label(""));
        pane.addRow(4, new Label(""));
        pane.add(new Label("Username"),15, 1);
        pane.add(_usernameField,16, 1);
        pane.add(new Label("Accountrole"),15, 2);
        pane.add(_comboBoxAccountRole,16, 2);


        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(_firstNameField);
        fields.add(_lastNameField);
        fields.add(_streetField);
        fields.add(_emailField);
        fields.add(_phoneField);


        Button addButton = new Button("Add");
        Button resetButton = new Button("Reset");

        resetButton.setOnAction(e -> {
            for (TextField field : fields) {
                field.setText(null);
                field.setStyle(null);
            }
        });

        addButton.setOnAction(e -> {

            if (_firstNameField.getText().isEmpty() || _lastNameField.getText().isEmpty() || _streetField.getText().isEmpty()
                    || _emailField.getText().isEmpty() || _phoneField.getText().isEmpty()) {

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

        pane.add(addButton, 0, 3);
        pane.add(resetButton, 1, 3);

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


       // PersonTestData person = new PersonTestData(currentId + 1, _firstNameField.getText(), _lastNameField.getText(), _streetField.getText(),
          //     _emailField.getText(), _phoneField.getText());

        // table.getItems().add(person);

        _firstNameField.setText(null);
        _lastNameField.setText(null);
        _streetField.setText(null);
        _emailField.setText(null);
        _phoneField.setText(null);

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
