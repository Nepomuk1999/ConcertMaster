package team_f.client.pages.musicianmanagement;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import team_f.client.entities.KeyValuePair;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.enums.AccountRole;
import team_f.jsonconnector.enums.Gender;
import team_f.jsonconnector.enums.PersonRole;
import team_f.jsonconnector.enums.SectionType;

import java.util.ArrayList;

public class MusicianManagement extends BaseTablePage<Person, Person, Person, PersonParameter> {
    private TextField _firstNameField;
    private TextField _lastNameField;
    private TextField _streetField;
    private TextField _emailField;
    private TextField _phoneField;
    private TableView<Person> _table;
    private ComboBox<KeyValuePair> _comboBoxSectionType;
    private ComboBox<KeyValuePair> _comboBoxInstrumentType;
    private ComboBox<KeyValuePair> _comboBoxRole;
    private ComboBox<KeyValuePair> _comboBoxGender;
    private TextField _usernameField;
    private ComboBox<KeyValuePair> _comboBoxAccountRole;
    // private final TextField instrumentField;
    private final ObservableList<KeyValuePair> _personRoleList = MusicianTableHelper.getPersonRoleList();
    private final ObservableList<KeyValuePair> _accountRoleList = MusicianTableHelper.getAccountRoleList();
    private final ObservableList<KeyValuePair> _genderList = MusicianTableHelper.getGenderList();
    private final ObservableList<KeyValuePair> _sectionTypeList = MusicianTableHelper.getSectionTypeList();

    public MusicianManagement() {
    }

    @Override
    public void initialize() {
        _firstNameField = new TextField();
        _lastNameField = new TextField();
        _streetField = new TextField();
        _emailField = new TextField();
        _phoneField = new TextField();

        _comboBoxSectionType = new ComboBox<>(_sectionTypeList);
        _comboBoxInstrumentType = new ComboBox<>();
        _comboBoxRole = new ComboBox<>(_personRoleList);
        _comboBoxGender = new ComboBox<>(_genderList);

        _usernameField = new TextField();
        _comboBoxAccountRole = new ComboBox<>(_accountRoleList);

        _table = new TableView<>(MusicianTableHelper.getPersonList());
        _table.setEditable(false);

        _comboBoxSectionType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            _comboBoxInstrumentType.setItems(MusicianTableHelper.getInstrumentTypeList((SectionType) newValue.getValue()));
            _comboBoxInstrumentType.getSelectionModel().selectFirst();
        });

        _comboBoxInstrumentType.getSelectionModel().selectFirst();

        _table.getColumns().addListener((ListChangeListener) change -> {
            change.next();
            if(change.wasReplaced()) {
                _table.getColumns().clear();
                getTableColumns();
            }
        });

        TableView.TableViewSelectionModel tsm = _table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);
        getTableColumns();
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        _comboBoxSectionType.setStyle("-fx-font: 14 arial;");
        _comboBoxSectionType.getSelectionModel().selectFirst();
        _comboBoxSectionType.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
            }
        });

        _comboBoxInstrumentType.setStyle("-fx-font: 14 arial;");
        _comboBoxInstrumentType.getSelectionModel().selectFirst();
        _comboBoxInstrumentType.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
            }
        });

        _comboBoxRole.setStyle("-fx-font: 14 arial;");
        _comboBoxRole.getSelectionModel().selectFirst();
        _comboBoxRole.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                if(arg2.getValue().equals(PersonRole.External_musician)){
                    _usernameField.setDisable(true);
                    _comboBoxAccountRole.setDisable(true);
                }else{
                    _usernameField.setDisable(false);
                    _comboBoxAccountRole.setDisable(false);
                }
            }
        });

        _comboBoxGender.setStyle("-fx-font: 14 arial;");
        _comboBoxGender.getSelectionModel().selectFirst();
        _comboBoxGender.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
            }
        });

        _comboBoxAccountRole.setStyle("-fx-font: 14 arial;");
        _comboBoxAccountRole.getSelectionModel().selectFirst();
        _comboBoxAccountRole.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
            }
        });

        GridPane newDataPane = getNewPersonDataPane();

        // Create the Delete Button and add Event-Handler
        Button editButton = new Button("Edit Selected Musician");
        editButton.setOnAction(e -> editPerson());

        VBox root = new VBox();
        root.getChildren().addAll(newDataPane, _table,editButton );
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        setCenter(root);
    }

    @Override
    public void load() {
    }

    @Override
    public void exit() {
    }

    @Override
    public void dispose() {
    }

    public GridPane getNewPersonDataPane() {

        //Title Label
        Label titleMusician=new Label("Create Musician");
        titleMusician.setStyle(" -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        Label titleAccount=new Label("Create Account");
        titleAccount.setStyle(" -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");


        GridPane pane = new GridPane();
        pane.setHgap(15);
        pane.setVgap(10);
        pane.add(titleMusician,0,0);
        pane.add(new Label("Role:"), 0,1);
        pane.add(_comboBoxRole, 0,2);
        pane.add(new Label("Section:"),1,1);
        pane.add(_comboBoxSection,1,2);
        pane.add(new Label("Instruments:"), 2,1);
        pane.add(_comboBoxInstrument,2,2);

        pane.add(new Label("Gender:"), 0,3);
        pane.add(_comboBoxGender,0,4);
        pane.add(new Label("First Name:"), 1,3);
        pane.add(_firstNameField,1,4);
        pane.add(new Label("Last Name:"), 2,3);
        pane.add(_lastNameField,2,4);

        pane.add(new Label("Street:"), 0,5);
        pane.add(_streetField,0,6);
        pane.add(new Label("Phone Number:"), 1,5);
        pane.add(_phoneField,1,6);
        pane.add(new Label("Email:"), 2,5);
        pane.add(_emailField,2,6);

        //pane.add(titleAccount,20,0);
        pane.add(new Label("Username:"),3, 3);
        pane.add(_usernameField,3, 4);
        pane.add(new Label("Account Role:"),3, 5);
        pane.add(_comboBoxAccountRole,3, 6);

        pane.addRow(7, new Label(" "));
        pane.addRow(8, new Label(" "));


/*
        pane.addRow(0, new Label("Role:"), _comboBoxRole);
        pane.addRow(0, new Label("Section:"), _comboBoxSectionType);
        pane.addRow(0, new Label("Instruments:"), _comboBoxInstrumentType);
        pane.addRow(1, new Label("Gender:"), _comboBoxGender);
        pane.addRow(1, new Label("First Name:"), _firstNameField);
        pane.addRow(1, new Label("Last Name:"), _lastNameField);
        pane.addRow(2, new Label("Street:"), _streetField);
        pane.addRow(2, new Label("Email:"), _emailField);
        pane.addRow(2, new Label("Phone Number:"), _phoneField);
        pane.addRow(3, new Label(""));
        pane.addRow(4, new Label(""));*/

        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(_firstNameField);
        fields.add(_lastNameField);
        fields.add(_streetField);
        fields.add(_emailField);
        fields.add(_phoneField);

        Button addButton = new Button("Add Musician");

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

        pane.add(addButton, 4, 6);
        return pane;
    }

    public void getTableColumns(){
        _table.getColumns().addAll(MusicianTableHelper.getIdColumn(), MusicianTableHelper.getFirstNameColumn(),
                MusicianTableHelper.getLastNameColumn(), MusicianTableHelper.getStreetColumn(),
                MusicianTableHelper.getZipCodeColumn(), MusicianTableHelper.getPhonenumberColumn(),
                MusicianTableHelper.getRoleColumn(), MusicianTableHelper.getSectionColumn(),
                MusicianTableHelper.getInstrumentColumn());
    }

    private void reset() {
        _firstNameField.setText(null);
        _lastNameField.setText(null);
        _streetField.setText(null);
        _emailField.setText(null);
        _phoneField.setText(null);
    }

    public void addPerson() {
        if(_create != null) {
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

            Person resultPerson = _create.doAction(person);

            if(resultPerson != null && resultPerson.getPersonID() > 0) {
                _table.getItems().add(resultPerson);
            }
        }

        reset();
    }

    public void editPerson() {
        if(_edit != null) {
            // @TODO: use setOnEdit instead of handling all the stuff in this class
            /*TableView.TableViewSelectionModel<Person> tsm = _table.getSelectionModel();

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
            }*/

            /*
            Person person = new Person();
            _edit.doAction(person);*/
        }
    }
}
