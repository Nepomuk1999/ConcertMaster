package team_f.client.pages.musicianmanagement;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import team_f.client.entities.KeyValuePair;
import team_f.client.helper.RequestResponseHelper;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.Account;
import team_f.jsonconnector.entities.ErrorList;
import team_f.jsonconnector.entities.Person;
import team_f.jsonconnector.enums.AccountRole;
import team_f.jsonconnector.enums.Gender;
import team_f.jsonconnector.enums.PersonRole;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MusicianManagement extends BorderPane {
    private TextField _firstNameField;
    private TextField _lastNameField;
    private TextField _streetField;
    private TextField _emailField;
    private TextField _phoneField;
    private TableView<Person> _table;
    private ComboBox<String> _comboBoxSection;
    private ComboBox<String> _comboBoxInstrument;
    private ComboBox<KeyValuePair> _comboBoxRole;
    private ComboBox<KeyValuePair> _comboBoxGender;
    private TextField _usernameField;
    private ComboBox<KeyValuePair> _comboBoxAccountRole;
    // private final TextField instrumentField;
    private URL _baseURL;
    private final ObservableList<KeyValuePair> _personRoleList = MusicianTableHelper.getPersonRoleList();
    private final ObservableList<KeyValuePair> _accountRoleList = MusicianTableHelper.getAccountRoleList();
    private final ObservableList<KeyValuePair> _genderList = MusicianTableHelper.getGenderList();


    public MusicianManagement(URL baseURL) {
        _baseURL = baseURL;
        _firstNameField = new TextField();
        _lastNameField = new TextField();
        _streetField = new TextField();
        _emailField = new TextField();
        _phoneField = new TextField();

        _comboBoxSection = new ComboBox<>();
        _comboBoxInstrument = new ComboBox<>();
        _comboBoxRole = new ComboBox<>(_personRoleList);
        _comboBoxGender = new ComboBox<>(_genderList);

        _usernameField = new TextField();
        _comboBoxAccountRole = new ComboBox<>(_accountRoleList);

        _table = new TableView<>(MusicianTableHelper.getPersonList());
        _table.setEditable(false);

        TableViewSelectionModel<Person> tsm = _table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);

        _table.getColumns().addAll(MusicianTableHelper.getIdColumn(), MusicianTableHelper.getFirstNameColumn(),
                                   MusicianTableHelper.getLastNameColumn(), MusicianTableHelper.getStreetColumn(),
                                   MusicianTableHelper.getZipCodeColumn(), MusicianTableHelper.getPhonenumberColumn(),
                                   MusicianTableHelper.getRoleColumn(), MusicianTableHelper.getSectionColumn(),
                                   MusicianTableHelper.getInstrumentColumn());


        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        root.getChildren().addAll(newDataPane, deleteButton, _table);
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
        pane.setVgap(7);

        pane.addRow(0, new Label("Role:"), _comboBoxRole);
        pane.addRow(0, new Label("Section:"), _comboBoxSection);
        pane.addRow(0, new Label("Instruments:"), _comboBoxInstrument);
        pane.addRow(1, new Label("Gender:"), _comboBoxGender);
        pane.addRow(1, new Label("First Name:"), _firstNameField);
        pane.addRow(1, new Label("Last Name:"), _lastNameField);
        pane.addRow(2, new Label("Street:"), _streetField);
        pane.addRow(2, new Label("Email:"), _emailField);
        pane.addRow(2, new Label("Phone Number:"), _phoneField);

        pane.add(new Label("Username"),12, 1);
        pane.add(_usernameField,13, 1);
        pane.add(new Label("Accountrole"),12, 2);
        pane.add(_comboBoxAccountRole,13, 2);

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

        pane.add(addButton, 6, 3);
        pane.add(resetButton, 7, 3);

        return pane;
    }

    public void addPerson() {
        // _table.getItems().add(person);

        Person person = new Person();
        person.setFirstname(_firstNameField.getText());
        person.setLastname(_lastNameField.getText());
        person.setAddress(_streetField.getText());
        person.setEmail(_emailField.getText());
        person.setPhoneNumber(_phoneField.getText());
        person.setGender((Gender) _comboBoxGender.getSelectionModel().getSelectedItem().getValue());
        //person.setInstruments();
        //person.setInitials();
        person.setPersonRole((PersonRole) _comboBoxRole.getSelectionModel().getSelectedItem().getValue());

        Account account = new Account();
        account.setUsername(_usernameField.getText());
        account.setRole((AccountRole) _comboBoxAccountRole.getSelectionModel().getSelectedItem().getValue());

        person.setAccount(account);

        ErrorList request = (ErrorList) RequestResponseHelper.writeAndReadJSONObject(getFullURL(), person, ErrorList.class);

        boolean isSuccessful;

        if (request != null && request.getKeyValueList() != null && request.getKeyValueList().size() == 0) {
            isSuccessful = true;
        } else {
            isSuccessful = false;
        }

        // @TODO: show error message

        // @todo: save the entity and add it to the list

        reset();
    }

    public void deletePerson() {
        TableViewSelectionModel<Person> tsm = _table.getSelectionModel();

        // Check, if any rows are selected
        if (tsm.isEmpty()) {
            System.out.println("Select a row to delete!");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete");
        alert.setHeaderText("The following Person will be deleted");
        alert.setContentText(tsm.getSelectedItem().getFirstname() + " " + tsm.getSelectedItem().getLastname());

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
            _table.getItems().remove(selectedIndices[i].intValue());
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

    private void reset() {
        _firstNameField.setText(null);
        _lastNameField.setText(null);
        _streetField.setText(null);
        _emailField.setText(null);
        _phoneField.setText(null);
       /* _initials.setText(null);
        sectionField.setText(null);
        instrumentField.setText(null);*/
    }

    private URL getFullURL() {
        try {
            return new URL(_baseURL, URIList.register);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}
