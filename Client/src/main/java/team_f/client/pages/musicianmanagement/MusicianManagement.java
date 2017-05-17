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
import team_f.jsonconnector.entities.Account;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.Person;
import team_f.jsonconnector.entities.special.errorlist.PersonErrorList;
import team_f.jsonconnector.enums.*;
import team_f.jsonconnector.enums.properties.AccountProperty;
import team_f.jsonconnector.enums.properties.PersonProperty;
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
    private TextField _usernameField;

    private TableView<Person> _musicianTable;

    private ComboBox<KeyValuePair> _comboBoxSectionType;
    private ComboBox<KeyValuePair> _comboBoxInstrumentType;
    private ComboBox<KeyValuePair> _comboBoxRole;
    private ComboBox<KeyValuePair> _comboBoxGender;
    private ComboBox<KeyValuePair> _comboBoxAccountRole;

    private final ObservableList<KeyValuePair> _personRoleList = MusicianTableHelper.getPersonRoleList();
    private final ObservableList<KeyValuePair> _accountRoleList = MusicianTableHelper.getAccountRoleList();
    private final ObservableList<KeyValuePair> _genderList = MusicianTableHelper.getGenderList();
    private final ObservableList<KeyValuePair> _sectionTypeList = MusicianTableHelper.getSectionTypeList();

    private Button _addButton;
    private Button _updateButton;
    private Button _editButton;
    private Button _deleteButton;
    private Button _cancelButton;

    private List<TextField> _fieldsList;
    private List<ComboBox> _comboboxList;

    private ObservableList<Person> _masterData = FXCollections.observableArrayList();
    private ObservableList<Person> _filteredData = FXCollections.observableArrayList();
    private TextField _filterField;

    @Override
    public void initialize() {
        if (_initialize != null) {
            _initialize.doAction(null);
        }
        final URL Style = ClassLoader.getSystemResource("style/stylesheetMusicianManagement.css");
        getStylesheets().add(Style.toString());

        _fieldsList = new ArrayList<>();
        //textfields
        _firstNameField = new TextField();
        _lastNameField = new TextField();
        _addressField = new TextField();
        _emailField = new TextField();
        _phoneField = new TextField();
        _usernameField = new TextField();

        _fieldsList = new ArrayList(){{
            add(_firstNameField);
            add(_lastNameField);
            add(_addressField);
            add(_emailField);
            add(_phoneField);
            add(_usernameField);
        }};

        addListener();

        //comboboxes
        _comboBoxSectionType = new ComboBox<>(_sectionTypeList);
        _comboBoxInstrumentType = new ComboBox<>();
        _comboBoxRole = new ComboBox<>(_personRoleList);
        _comboBoxGender = new ComboBox<>(_genderList);
        _comboBoxAccountRole = new ComboBox<>(_accountRoleList);
        _comboBoxInstrumentType.setItems(MusicianTableHelper.getInstrumentTypeList((SectionType) _comboBoxSectionType.getItems().get(0).getValue()));

        _comboboxList = new ArrayList(){{
            add(_comboBoxSectionType);
            add(_comboBoxAccountRole);
            add(_comboBoxGender);
            add(_comboBoxInstrumentType);
            add(_comboBoxRole);
        }};

        _comboBoxSectionType.getSelectionModel().selectFirst();
        _comboBoxInstrumentType.getSelectionModel().selectFirst();
        _comboBoxGender.getSelectionModel().selectFirst();
        _comboBoxAccountRole.getSelectionModel().selectFirst();
        _comboBoxRole.getSelectionModel().selectFirst();


        _musicianTable = new TableView<>();
        _musicianTable.setEditable(false);
        _musicianTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _musicianTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _musicianTable.getColumns().addListener((ListChangeListener) change -> {
            change.next();
            if (change.wasReplaced()) {
                update();
            }
        });

        _musicianTable.getItems().addListener((ListChangeListener<Person>) change -> updateFilteredData());
        _musicianTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _editButton.setDisable(false);
                _deleteButton.setDisable(false);
            } else {
                _editButton.setDisable(true);
                _deleteButton.setDisable(true);
            }
        });
        update();

        _filteredData.addAll(_masterData);
        _musicianTable.setItems(_filteredData);
        _masterData.addListener((ListChangeListener<Person>) change -> updateFilteredData());

        _musicianTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _editButton.setDisable(false);
                _deleteButton.setDisable(false);
            } else {
                _editButton.setDisable(true);
                _deleteButton.setDisable(true);
            }
        });

        _comboBoxSectionType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _comboBoxInstrumentType.setItems(MusicianTableHelper.getInstrumentTypeList((SectionType) newValue.getValue()));
                _comboBoxInstrumentType.getSelectionModel().selectFirst();
            }
        });

        //unused Listener
        _comboBoxGender.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });
        _comboBoxAccountRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });
        _comboBoxInstrumentType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        });

        //if Role equals External-Musician-->Account fields are disabled
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

        _editButton = new Button("Edit");
        _editButton.setDisable(true);
        _editButton.setMinWidth(125);
        _editButton.setOnAction(e -> {
            _musicianTable.setDisable(true);
            _addButton.setVisible(false);
            _updateButton.setVisible(true);
            _editButton.setDisable(true);
            _deleteButton.setDisable(true);
            _cancelButton.setText("Cancel");
            _filterField.setDisable(true);
            fillFields((Person) _musicianTable.getSelectionModel().getSelectedItem());
        });

        _deleteButton = new Button("Delete");
        _deleteButton.setDisable(true);
        _deleteButton.setMinWidth(125);
        _deleteButton.setOnAction(e -> editPerson());

        _filterField = new TextField();
        _filterField.setPromptText("First- or Lastname");
        _filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.trim().isEmpty()) {
                updateFilteredData();
            }else{
                updateFilteredData();
            }
        });

        HBox buttonsBox = new HBox(_editButton, _deleteButton,new Label("Search:"), _filterField);
        buttonsBox.setSpacing(10);
        VBox root = new VBox();
        GridPane newDataPane = getNewPersonDataPane();
        root.getChildren().addAll(newDataPane, _musicianTable, buttonsBox);
        root.setSpacing(5);
        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderPane");
        borderPane.setCenter(root);
        setCenter(borderPane);
    }

    public GridPane getNewPersonDataPane() {

        Label titleMusician = new Label("Add Employee");
        titleMusician.setId("titleMusician");
        titleMusician.setMinWidth(200);

        GridPane pane = new GridPane();
        pane.gridLinesVisibleProperty().set(false);
        pane.getColumnConstraints().addAll(new ColumnConstraints(160), new ColumnConstraints(160),
                new ColumnConstraints(160), new ColumnConstraints(160), new ColumnConstraints(160));
        pane.setHgap(15);
        pane.setVgap(10);

        pane.addRow(0,titleMusician);
        pane.add(new Label("Orchestra Role:*"), 0, 1);
        pane.add(_comboBoxRole, 0, 2);
        pane.add(new Label("Section:*"), 1, 1);
        pane.add(_comboBoxSectionType, 1, 2);
        pane.add(new Label("Instruments:*"), 2, 1);
        pane.add(_comboBoxInstrumentType, 2, 2);

        pane.add(new Label("Sex:*"), 0, 3);
        pane.add(_comboBoxGender, 0, 4);
        pane.add(new Label("First Name:*"), 1, 3);
        pane.add(_firstNameField, 1, 4);
        pane.add(new Label("Last Name:*"), 2, 3);
        pane.add(_lastNameField, 2, 4);

        pane.add(new Label("Street:*"), 0, 5);
        pane.add(_addressField, 0, 6);
        pane.add(new Label("Phone Number:*"), 1, 5);
        pane.add(_phoneField, 1, 6);
        pane.add(new Label("Email:*"), 2, 5);
        pane.add(_emailField, 2, 6);

        pane.add(new Label("Username:*"), 3, 3);
        pane.add(_usernameField, 3, 4);
        pane.add(new Label("Account Role:*"), 3, 5);
        pane.add(_comboBoxAccountRole, 3, 6);

        pane.addRow(7, new Label(" "));
        pane.addRow(8, new Label(" "));

        _updateButton = new Button("Update");
        _updateButton.setMinWidth(100);
        _updateButton.setVisible(false);
        _updateButton.setOnAction(e -> {
            _musicianTable.setDisable(false);
            editPerson();
        });

        _addButton = new Button("Add");
        _addButton.setMinWidth(100);
        _addButton.setVisible(true);
        _addButton.setOnAction(e -> {
            ArrayList<TextField> fields = new ArrayList<>();
            fields.add(_firstNameField);
            fields.add(_lastNameField);
            fields.add(_addressField);
            fields.add(_emailField);
            fields.add(_phoneField);

            if (_firstNameField.getText().trim().isEmpty() || _lastNameField.getText().trim().isEmpty() || _addressField.getText().trim().isEmpty()
                    || _emailField.getText().trim().isEmpty() || _phoneField.getText().trim().isEmpty()|| ((!_usernameField.isDisable())&&_usernameField.getText().trim().isEmpty())) {
                if ((!_usernameField.isDisable())) {
                    fields.add(_usernameField);
                }
                validate(fields);
                showValuesMissingMessage();
            } else {
                addPerson();
            }
        });

        _cancelButton = new Button("Clear");
        _cancelButton.setMinWidth(100);
        _cancelButton.setOnAction(e -> {
            _musicianTable.setDisable(false);
            reset();
        });

        pane.add(_addButton, 4, 7);
        pane.add(_updateButton, 4, 7);
        pane.add(_cancelButton, 0, 7);
        Label labelRequired = new Label("*...Required Fields");
        labelRequired.setMinWidth(100);
        pane.add(labelRequired, 0, 8);
        return pane;
    }


    public void addPerson() {
        if (_create != null) {
            Person person = new Person();
            if(_comboBoxRole.getSelectionModel().getSelectedItem().getValue().equals(PersonRole.External_musician)) {
                setPerson(person, false);
            }else {
                setPerson(person, true);
            }

            PersonErrorList resultPersonErrorList = _create.doAction(person);

            if (resultPersonErrorList != null && resultPersonErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = PersonConverter.getAbstractList(resultPersonErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultPersonErrorList.getKeyValueList().size() == 1 && resultPersonErrorList.getKeyValueList().get(0).getKey() != null && resultPersonErrorList.getKeyValueList().get(0).getKey().getPersonID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    //_musicianTable.getItems().add(resultPersonErrorList.getKeyValueList().get(0).getKey());
                    _masterData.add(resultPersonErrorList.getKeyValueList().get(0).getKey());
                    update();
                    reset();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                    markInvalidFields(errorList);
                    update();
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }

    }


    public void editPerson() {
        if (_edit != null) {
            Person person = _musicianTable.getSelectionModel().getSelectedItem();
            setPerson(person, false);

            PersonErrorList resultPersonErrorList = _edit.doAction(person);

            if (resultPersonErrorList != null && resultPersonErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = PersonConverter.getAbstractList(resultPersonErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultPersonErrorList.getKeyValueList().size() == 1) {
                    showSuccessMessage("Successful", tmpErrorText);

                    // _musicianTable.getItems().remove(person);
                    //_musicianTable.getItems().add(resultPersonErrorList.getKeyValueList().get(0).getKey());
                    _masterData.remove(person);
                    _masterData.add(resultPersonErrorList.getKeyValueList().get(0).getKey());
                    update();
                    reset();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                    markInvalidFields(errorList);
                    update();
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    private void setPerson(Person person, boolean createAccount) {
        person.setFirstname(_firstNameField.getText());
        person.setLastname(_lastNameField.getText());
        person.setAddress(_addressField.getText());
        person.setEmail(_emailField.getText());
        person.setPhoneNumber(_phoneField.getText());
        person.setGender((Gender) _comboBoxGender.getSelectionModel().getSelectedItem().getValue());
        if (!_comboBoxInstrumentType.isDisable()) {
            person.setInstrumentType((InstrumentType) _comboBoxInstrumentType.getSelectionModel().getSelectedItem().getValue());
        }
        person.setPersonRole((PersonRole) _comboBoxRole.getSelectionModel().getSelectedItem().getValue());

        if (!_comboBoxAccountRole.isDisable() && !_usernameField.isDisable()) {
            if (createAccount) {
                Account account = new Account();
                person.setAccount(account);
            }

            if (person.getAccount() != null) {
                person.getAccount().setUsername(_usernameField.getText());
                person.getAccount().setRole((AccountRole) _comboBoxAccountRole.getSelectionModel().getSelectedItem().getValue());
            }
        }

    }

    private void loadList() {
        if (_loadList != null) {
            PersonParameter personParameter = new PersonParameter();
            List<Person> personList = _loadList.doAction(personParameter);

            if (personList != null) {
                //_musicianTable.setItems(FXCollections.observableList(personList));
                _masterData.setAll(personList);
                if(_filterField!=null){
                    _filterField.clear();
                }
                update();
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    @Override
    public void load() {
        if (_load != null) {
        }

        loadList();
    }

    @Override
    public void update() {
        _musicianTable.getColumns().clear();
        _musicianTable.getColumns().addAll(MusicianTableHelper.getIdColumn(), MusicianTableHelper.getFirstNameColumn(),
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
            _usernameField.setText(person.getAccount().getUsername().toString());
        }

        if(person.getAccount()!=null&&person.getAccount().getRole()!=null){
            _comboBoxAccountRole.getSelectionModel().select(MusicianTableHelper.getAccountPos(person.getAccount().getRole()));
        }

        if (person.getPersonRole() != null) {
            if (MusicianTableHelper.getRolePos(person.getPersonRole()) >= 0) {
                _comboBoxRole.getSelectionModel().select(MusicianTableHelper.getRolePos(person.getPersonRole()));
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
            int[] positions = MusicianTableHelper.getSectionPos(person.getInstrumentType());
            if (positions[0] >= 0 && (positions[1] >= 0)) {
                _comboBoxSectionType.getSelectionModel().select(positions[0]);
                _comboBoxInstrumentType.getSelectionModel().select(positions[1]);
            }
        }

        if (person.getGender() != null) {
            if (MusicianTableHelper.getGenderPos(person.getGender()) >= 0) {
                _comboBoxGender.getSelectionModel().select(MusicianTableHelper.getGenderPos(person.getGender()));
            }
        }

        if (person.getAccount() != null && person.getAccount().getRole() != null) {
            if (MusicianTableHelper.getAccountPos(person.getAccount().getRole()) >= 0) {
                _comboBoxAccountRole.getSelectionModel().select(MusicianTableHelper.getAccountPos(person.getAccount().getRole()));
            }
        }

        _usernameField.setDisable(true);
    }

    private void markInvalidFields(List<Pair<JSONObjectEntity, List<Error>>> occuredErrors) {
        setBorder();
        String error;
        List<Error> errorList=occuredErrors.get(0).getValue();
        for(int x=0;x<errorList.size();x++) {
            error = errorList.get(x).getKey().toString();
            if (error.equals(AccountProperty.USERNAME.toString())) {
                _usernameField.setStyle("-fx-border-color: red");
            }
            if (error.equals(AccountProperty.ACCOUNT_ROLE.toString())) {
                _comboBoxAccountRole.setStyle("-fx-border-color: red");
            }
            if (error.equals(PersonProperty.FIRSTNAME.toString())) {
                _firstNameField.setStyle("-fx-border-color: red");
            }
            if (error.equals(PersonProperty.LASTNAME.toString())) {
                _lastNameField.setStyle("-fx-border-color: red");
            }
            if (error.equals(PersonProperty.ADDRESS.toString())) {
                _addressField.setStyle("-fx-border-color: red");
            }
            if (error.equals(PersonProperty.PHONE_NUMBER.toString())) {
                _phoneField.setStyle("-fx-border-color: red");
            }
            if (error.equals(PersonProperty.EMAIL.toString())) {
                _emailField.setStyle("-fx-border-color: red");
            }
            if (error.equals(PersonProperty.GENDER.toString())) {
                _comboBoxGender.setStyle("-fx-border-color: red");
            }
            if (error.equals(PersonProperty.PERSON_ROLE.toString())) {
                _comboBoxRole.setStyle("-fx-border-color: red");
            }
        }

    }

    private void setBorder() {
        for (TextField field : _fieldsList) {
            if(!field.isDisable()){
                field.setStyle("-fx-border-color: green");
            }
        }
        for (ComboBox comboBox : _comboboxList) {
            if(!comboBox.isDisable()){
                comboBox.setStyle("-fx-border-color: green");
            }
        }
    }
    private void reset() {
        for(TextField field:_fieldsList){
            field.clear();
            field.setStyle("-fx-border-color: transparent");
            field.setDisable(false);
        }
        for(ComboBox comboBox:_comboboxList){
            comboBox.setStyle("-fx-border-color: transparent");
            comboBox.getSelectionModel().selectFirst();
            comboBox.setDisable(false);
        }
        _addButton.setVisible(true);
        _editButton.setDisable(true);
        _deleteButton.setDisable(true);
        _updateButton.setVisible(false);
        _cancelButton.setText("Clear");
        _filterField.setDisable(false);
        _musicianTable.getSelectionModel().clearSelection();
    }

    private void addListener() {
        for (TextField field : _fieldsList) {
            field.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (field.getText().trim().isEmpty()) {
                    field.setStyle("-fx-border-color: red");
                } else {
                    field.setStyle("-fx-border-color: green");
                }
            });

            field.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                if (newValue1.trim().isEmpty()) {
                    field.setStyle("-fx-border-color: red");
                } else {
                    field.setStyle("-fx-border-color: green");
                }
            });
        }
    }

    private void updateFilteredData() {
        _filteredData.clear();

        for (Person p : _masterData) {
            if (matchesFilter(p)) {
                _filteredData.add(p);
            }
        }
        _musicianTable.setItems(_filteredData);
        reapplyTableSortOrder();
    }


    private boolean matchesFilter(Person person) {
        String filterString = _filterField.getText();
        if (filterString == null || filterString.isEmpty()) {
            // No filter --> Add all.
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();

        if (person.getFirstname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (person.getLastname().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false;
    }

    private void reapplyTableSortOrder() {
        ArrayList<TableColumn<Person, ?>> sortOrder = new ArrayList<>(_musicianTable.getSortOrder());
        _musicianTable.getSortOrder().clear();
        _musicianTable.getSortOrder().addAll(sortOrder);
    }


}

