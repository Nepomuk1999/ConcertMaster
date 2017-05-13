package team_f.client.pages.musicianmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import team_f.client.converter.PersonConverter;
import team_f.client.entities.KeyValuePair;
import team_f.client.helper.ErrorMessageHelper;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.errorlist.PersonErrorList;
import team_f.jsonconnector.enums.*;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MusicianManagement extends BaseTablePage<PersonErrorList, Person, Person, PersonParameter> {
    private TextField _firstNameField;
    private TextField _lastNameField;
    private TextField _addressField;
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
    private Button _addButton;
    private Button _updateButton;
    private Button _editButton;
    private Button _deleteButton;
    private Button _cancelButton;

    public MusicianManagement() {
    }

    @Override
    public void initialize() {
        if (_initialize != null) {
            _initialize.doAction(null);
        }
        final URL Style = ClassLoader.getSystemResource("style/stylesheetMusicianManagement.css");
        getStylesheets().add(Style.toString());
        //textfields
        _firstNameField = new TextField();
        _lastNameField = new TextField();
        _addressField = new TextField();
        _emailField = new TextField();
        _phoneField = new TextField();
        _usernameField = new TextField();

        //comboboxes
        _comboBoxSectionType = new ComboBox<>(_sectionTypeList);
        _comboBoxInstrumentType = new ComboBox<>();
        _comboBoxRole = new ComboBox<>(_personRoleList);
        _comboBoxGender = new ComboBox<>(_genderList);
        _comboBoxAccountRole = new ComboBox<>(_accountRoleList);
        _comboBoxInstrumentType.setItems(MusicianTableHelper.getInstrumentTypeList((SectionType) _comboBoxSectionType.getItems().get(0).getValue()));

        _comboBoxSectionType.getSelectionModel().selectFirst();
        _comboBoxInstrumentType.getSelectionModel().selectFirst();
        _comboBoxGender.getSelectionModel().selectFirst();
        _comboBoxAccountRole.getSelectionModel().selectFirst();
        _comboBoxRole.getSelectionModel().selectFirst();


        _table = new TableView<>();
        _table.setEditable(false);
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _table.getColumns().addListener((ListChangeListener) change -> {
            change.next();
            if (change.wasReplaced()) {
                update();
            }
        });

        _table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _editButton.setDisable(false);
                _deleteButton.setDisable(false);
            } else {
                _editButton.setDisable(true);
                _deleteButton.setDisable(true);
            }
        });

        TableView.TableViewSelectionModel tsm = _table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);
        update();
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        _comboBoxSectionType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _comboBoxInstrumentType.setItems(MusicianTableHelper.getInstrumentTypeList((SectionType) newValue.getValue()));
                _comboBoxInstrumentType.getSelectionModel().selectFirst();
            }
        });

        _comboBoxGender.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });

        _comboBoxAccountRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });

        _comboBoxInstrumentType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });

        _comboBoxRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.getValue().equals(PersonRole.External_musician)) {
                    _usernameField.setDisable(true);
                    _comboBoxAccountRole.setDisable(true);
                    _usernameField.clear();
                    _usernameField.setStyle("-fx-border-color: transparent");
                } else {
                    _usernameField.setDisable(false);
                    _comboBoxAccountRole.setDisable(false);
                }
                if (newValue.getValue().equals(PersonRole.Manager) || newValue.getValue().equals(PersonRole.Music_librarian)
                        || newValue.getValue().equals(PersonRole.Orchestral_facility_manager)) {
                    _comboBoxInstrumentType.setDisable(true);
                    _comboBoxSectionType.setDisable(true);
                } else {
                    _comboBoxInstrumentType.setDisable(false);
                    _comboBoxSectionType.setDisable(false);
                }
            }

        });

        _editButton = new Button("Edit Selected Musician");
        _editButton.setDisable(true);
        _editButton.setMinWidth(125);
        _editButton.setOnAction(e -> {
            _table.setDisable(true);
            _addButton.setDisable(true);
            _updateButton.setDisable(false);
            _editButton.setDisable(true);
            _deleteButton.setDisable(true);
            _cancelButton.setText("Cancel Update");
            fillFields((Person) _table.getSelectionModel().getSelectedItem());
        });

        _deleteButton = new Button("Delete Selected Musician");
        _deleteButton.setDisable(true);
        _deleteButton.setMinWidth(125);
        _deleteButton.setOnAction(e -> editPerson());

        HBox buttonsBox = new HBox(_editButton, _deleteButton);
        buttonsBox.setSpacing(10);
        VBox root = new VBox();
        GridPane newDataPane = getNewPersonDataPane();
        root.getChildren().addAll(newDataPane, _table, buttonsBox);
        root.setSpacing(5);
        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderPane");
        borderPane.setCenter(root);
        setCenter(borderPane);
    }

    @Override
    public void load() {
        if (_load != null) {
        }

        loadList();
    }

    @Override
    public void update() {
        _table.getColumns().clear();
        _table.getColumns().addAll(MusicianTableHelper.getIdColumn(), MusicianTableHelper.getFirstNameColumn(),
                MusicianTableHelper.getLastNameColumn(), MusicianTableHelper.getStreetColumn(),
                MusicianTableHelper.getZipCodeColumn(), MusicianTableHelper.getPhonenumberColumn(),
                MusicianTableHelper.getRoleColumn(), MusicianTableHelper.getInstrumentColumn());
    }

    @Override
    public void exit() {
        if (_exit != null) {
            _exit.doAction(null);
        }
    }

    @Override
    public void dispose() {
    }

    public GridPane getNewPersonDataPane() {

        Label titleMusician = new Label("Create Musician");
        titleMusician.setId("titleMusician");

        GridPane pane = new GridPane();
        pane.gridLinesVisibleProperty().set(false);
        pane.getColumnConstraints().addAll(new ColumnConstraints(160), new ColumnConstraints(160),
                new ColumnConstraints(160), new ColumnConstraints(160), new ColumnConstraints(160));
        pane.setHgap(15);
        pane.setVgap(10);

        pane.addRow(0,titleMusician);
        pane.add(new Label("Role:"), 0, 1);
        pane.add(_comboBoxRole, 0, 2);
        pane.add(new Label("Section:"), 1, 1);
        pane.add(_comboBoxSectionType, 1, 2);
        pane.add(new Label("Instruments:"), 2, 1);
        pane.add(_comboBoxInstrumentType, 2, 2);

        pane.add(new Label("Gender:"), 0, 3);
        pane.add(_comboBoxGender, 0, 4);
        pane.add(new Label("First Name:"), 1, 3);
        pane.add(_firstNameField, 1, 4);
        pane.add(new Label("Last Name:"), 2, 3);
        pane.add(_lastNameField, 2, 4);

        pane.add(new Label("Street:"), 0, 5);
        pane.add(_addressField, 0, 6);
        pane.add(new Label("Phone Number:"), 1, 5);
        pane.add(_phoneField, 1, 6);
        pane.add(new Label("Email:"), 2, 5);
        pane.add(_emailField, 2, 6);

        pane.add(new Label("Username:"), 3, 3);
        pane.add(_usernameField, 3, 4);
        pane.add(new Label("Account Role:"), 3, 5);
        pane.add(_comboBoxAccountRole, 3, 6);

        pane.addRow(7, new Label(" "));
        pane.addRow(8, new Label(" "));

        _updateButton = new Button("Update Musician");
        _updateButton.setDisable(true);
        _updateButton.setOnAction(e -> {
            _table.setDisable(false);
            editPerson();
            reset();
        });

        _addButton = new Button("Add Musician");
        _addButton.setMinWidth(125);
        //Todo: usernameField should also be validated if Musician is not an external one!!!
        _addButton.setOnAction(e -> {
            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(_firstNameField);
            fields.add(_lastNameField);
            fields.add(_addressField);
            fields.add(_emailField);
            fields.add(_phoneField);

            if (_firstNameField.getText().isEmpty() || _lastNameField.getText().isEmpty() || _addressField.getText().isEmpty()
                    || _emailField.getText().isEmpty() || _phoneField.getText().isEmpty()) {
                if (!(_usernameField.isDisable())) {
                    fields.add(_usernameField);
                }
                validate(fields);
                showValuesMissingMessage();
            } else {
                addPerson();
            }
        });

        _cancelButton = new Button("Reset");
        _cancelButton.setMinWidth(125);
        _cancelButton.setOnAction(e -> {
            _table.setDisable(false);
            reset();
        });
        pane.add(_addButton, 4, 6);
        pane.add(_updateButton, 4, 7);
        pane.add(_cancelButton, 0, 7);
        return pane;
    }

    private void reset() {
        _firstNameField.clear();
        _firstNameField.setStyle("-fx-border-color: transparent");
        _lastNameField.clear();
        _lastNameField.setStyle("-fx-border-color: transparent");
        _addressField.clear();
        _addressField.setStyle("-fx-border-color: transparent");
        _emailField.clear();
        _emailField.setStyle("-fx-border-color: transparent");
        _phoneField.clear();
        _phoneField.setStyle("-fx-border-color: transparent");
        _usernameField.clear();
        _usernameField.setStyle("-fx-border-color: transparent");
        _addButton.setDisable(false);
        _editButton.setDisable(true);
        _deleteButton.setDisable(true);
        _updateButton.setDisable(true);
        _cancelButton.setText("Reset");
        _comboBoxRole.getSelectionModel().selectFirst();
        _comboBoxAccountRole.getSelectionModel().selectFirst();
        _comboBoxGender.getSelectionModel().selectFirst();
        _comboBoxSectionType.getSelectionModel().selectFirst();
        _comboBoxInstrumentType.getSelectionModel().selectFirst();
        _comboBoxAccountRole.getSelectionModel().selectFirst();
        _table.getSelectionModel().clearSelection();
    }

    public void addPerson() {
        if (_create != null) {
            Person person = new Person();
            setPerson(person, true);

            PersonErrorList resultPersonErrorList = _create.doAction(person);

            if (resultPersonErrorList != null && resultPersonErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = PersonConverter.getAbstractList(resultPersonErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultPersonErrorList.getKeyValueList().size() == 1 && resultPersonErrorList.getKeyValueList().get(0).getKey() != null && resultPersonErrorList.getKeyValueList().get(0).getKey().getPersonID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _table.getItems().add(resultPersonErrorList.getKeyValueList().get(0).getKey());
                    update();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }

    }

    public void editPerson() {
        if (_edit != null) {
            Person person = _table.getSelectionModel().getSelectedItem();
            setPerson(person, false);

            PersonErrorList resultPersonErrorList = _edit.doAction(person);

            if (resultPersonErrorList != null && resultPersonErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = PersonConverter.getAbstractList(resultPersonErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultPersonErrorList.getKeyValueList().size() == 1) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _table.getItems().remove(person);
                    _table.getItems().add(resultPersonErrorList.getKeyValueList().get(0).getKey());
                    update();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    private void loadList() {
        if (_loadList != null) {
            PersonParameter personParameter = new PersonParameter();
            List<Person> personList = _loadList.doAction(personParameter);

            if (personList != null) {
                _table.setItems(FXCollections.observableList(personList));
                update();
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }


    public void fillFields(Person person) {
        if (person.getFirstname() != null) {
            _firstNameField.setText(person.getFirstname());
        }
        if (person.getLastname() != null) {
            _lastNameField.setText(person.getLastname());
        }
        if (person.getAddress() != null) {
            _addressField.setText(person.getAddress());
        }
        if (person.getEmail() != null) {
            _emailField.setText(person.getEmail());
        }
        if (person.getPhoneNumber() != null) {
            _phoneField.setText(person.getPhoneNumber());
        }
        if (person.getAccount() != null && person.getAccount().getUsername() != null) {
            _usernameField.setText(person.getAccount().getUsername());
        }
        if (person.getPersonRole() != null) {
            if (getRolePos(person.getPersonRole()) >= 0) {
                _comboBoxRole.getSelectionModel().select(getRolePos(person.getPersonRole()));
                if (person.getPersonRole().equals(PersonRole.External_musician)) {
                    _comboBoxAccountRole.setDisable(true);
                }
                if (person.getPersonRole().equals(PersonRole.Manager) || person.getPersonRole().equals(PersonRole.Orchestral_facility_manager) || person.getPersonRole().equals(PersonRole.Music_librarian)) {
                    _comboBoxInstrumentType.setDisable(true);
                    _comboBoxSectionType.setDisable(true);
                } else {
                    _comboBoxInstrumentType.setDisable(false);
                    _comboBoxSectionType.setDisable(false);
                }
            }
        }

        if (person.getInstrumentType() != null) {
            int[] positions = getSectionPos(person.getInstrumentType());
            if (positions[0] >= 0 && (positions[1] >= 0)) {
                _comboBoxSectionType.getSelectionModel().select(positions[0]);
                _comboBoxInstrumentType.getSelectionModel().select(positions[1]);
            }
        }

        if (person.getGender() != null) {
            if (getGenderPos(person.getGender()) >= 0) {
                _comboBoxGender.getSelectionModel().select(getGenderPos(person.getGender()));
            }
        }

        if (person.getAccount() != null && person.getAccount().getRole() != null) {
            if (getAccountPos(person.getAccount().getRole()) >= 0) {
                _comboBoxAccountRole.getSelectionModel().select(getAccountPos(person.getAccount().getRole()));
            }
        }

    }

    //TODO: integrate Code to Helperclass
    public int getRolePos(PersonRole personRole) {
        int pos = 0;
        for (KeyValuePair role : _comboBoxRole.getItems()) {
            if (role.getValue().equals(personRole)) {
                return pos;
            }
            pos++;
        }
        return -1;

    }

    public int getGenderPos(Gender personGender) {
        int pos = 0;
        for (KeyValuePair gender : _comboBoxGender.getItems()) {
            if (gender.getValue().equals(personGender)) {
                return pos;
            }
            pos++;
        }
        return -1;

    }

    public int[] getSectionPos(InstrumentType instrumentType) {
        List sections = MusicianTableHelper.getSectionInstrumentPos();
        int sectionPos = -1;
        int instrumentPos = -1;
        for (int i = 0; i < sections.size(); i++) {
            List instruments = (List) sections.get(i);
            for (int j = 0; j < instruments.size(); j++) {
                if (instruments.get(j).equals(instrumentType)) {
                    sectionPos = i;
                    instrumentPos = j;
                }
            }
        }
        return new int[]{sectionPos, instrumentPos};

    }

    public int getAccountPos(AccountRole accountRole) {
        int pos = 0;
        for (KeyValuePair role : _comboBoxAccountRole.getItems()) {
            if (role.getValue().equals(accountRole)) {
                return pos;
            }
            pos++;
        }
        return -1;

    }

    private void setPerson(Person person, boolean createAccount) {
        person.setFirstname(_firstNameField.getText());
        person.setLastname(_lastNameField.getText());
        person.setAddress(_addressField.getText());
        person.setEmail(_emailField.getText());
        person.setPhoneNumber(_phoneField.getText());
        person.setGender((Gender) _comboBoxGender.getSelectionModel().getSelectedItem().getValue());
        person.setInstrumentType((InstrumentType) _comboBoxInstrumentType.getSelectionModel().getSelectedItem().getValue());
        //person.setInstruments();
        //person.setInitials();
        person.setPersonRole((PersonRole) _comboBoxRole.getSelectionModel().getSelectedItem().getValue());

        if(createAccount) {
            Account account = new Account();
            person.setAccount(account);
        }

        person.getAccount().setUsername(_usernameField.getText());
        person.getAccount().setRole((AccountRole) _comboBoxAccountRole.getSelectionModel().getSelectedItem().getValue());
    }
}

