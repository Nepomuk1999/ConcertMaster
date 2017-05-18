package team_f.client.pages.musicianmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.Person;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MusiciansList extends BaseTablePage<Person, Person, Person, Person, PersonParameter> {
    private Label _id;
    private Label _firstname;
    private Label _lastname;
    private Label _username;
    private Label _section;
    private Label _phonenumber;
    private Label _address;
    private Label _email;
    private Label _personrole;
    private Label _initials;
    private Label _accountrole;
    private TextArea _instruments;
    private Label _gender;

    private TableView<Person> _musicianList;
    private List<Label> _labelList;

    private ObservableList<Person> _masterData = FXCollections.observableArrayList();
    private ObservableList<Person> _filteredData = FXCollections.observableArrayList();
    private TextField _filterField;

    @Override
    public void initialize() {
        final URL Style = ClassLoader.getSystemResource("style/stylesheetMusicianList.css");
        getStylesheets().add(Style.toString());

        _id = new Label();
        _firstname = new Label();
        _lastname = new Label();
        _username = new Label();
        _phonenumber = new Label();
        _address = new Label();
        _email = new Label();
        _personrole = new Label();
        _accountrole = new Label();
        _instruments = new TextArea();
        _initials = new Label();
        _section = new Label();
        _gender = new Label();

        _instruments.setEditable(false);
        _labelList = new ArrayList<Label>() {{
            add(_id);
            add(_firstname);
            add(_lastname);
            add(_username);
            add(_phonenumber);
            add(_address);
            add(_email);
            add(_personrole);
            add(_accountrole);
            add(_initials);
            add(_section);
            add(_gender);
        }};

        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderPane");

        _musicianList = new TableView();
        _musicianList.setMinHeight(450);
        _musicianList.setMinWidth(450);
        _musicianList.setEditable(false);
        _musicianList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _musicianList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _musicianList.getColumns().addAll(MusicianTableHelper.getFirstNameColumn(), MusicianTableHelper.getLastNameColumn());
        _musicianList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                reset();
                setFields(_musicianList.getSelectionModel().getSelectedItem());
            }
        });

        _filteredData.addAll(_masterData);
        _musicianList.setItems(_filteredData);
        _masterData.addListener((ListChangeListener<Person>) change -> updateFilteredData());

        GridPane gridPane = new GridPane();
        gridPane.setId("gridpane");
        gridPane.setMinHeight(450);

        gridPane.getColumnConstraints().addAll(new ColumnConstraints(80), new ColumnConstraints(160), new ColumnConstraints(80), new ColumnConstraints(160));
        gridPane.setVgap(10);


        gridPane.add(new Label("ID"), 0, 0);
        gridPane.add(new Label("Initials:"), 0, 2);
        gridPane.add(new Label("Sex:"), 2, 2);
        gridPane.add(new Label("First Name:"), 0, 4);
        gridPane.add(new Label("Last Name:"), 2, 4);
        gridPane.add(new Label("Username:"), 0, 6);
        gridPane.add(new Label("Address:"), 2, 6);
        gridPane.add(new Label("Phone:"), 0, 8);
        gridPane.add(new Label("Email:"), 2, 8);
        gridPane.add(new Label("Account Role:"), 0, 10);
        gridPane.add(new Label("Person Role:"), 2, 10);
        gridPane.add(new Label("Section:"), 0, 12);
        gridPane.add(new Label("Instruments:"), 2, 12);


        gridPane.add(_id, 1, 0);
        gridPane.add(_initials, 1, 2);
        gridPane.add(_gender, 3, 2);
        gridPane.add(_firstname, 1, 4);
        gridPane.add(_lastname, 3, 4);
        gridPane.add(_username, 1, 6);
        gridPane.add(_address, 3, 6);
        gridPane.add(_phonenumber, 1, 8);
        gridPane.add(_email, 3, 8);
        gridPane.add(_accountrole, 1, 10);
        gridPane.add(_personrole, 3, 10);
        gridPane.add(_section, 1, 12);
        gridPane.add(_instruments, 3, 12);

        Button MusicianToPdfButton = new Button("Convert Musician to PDF");
        MusicianToPdfButton.setDisable(true);
        MusicianToPdfButton.setMinWidth(180);
        MusicianToPdfButton.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {

                try {
                    MusicianPDFGenerator main = new MusicianPDFGenerator(_musicianList.getSelectionModel().getSelectedItem(),selectedFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button ListToPdfButton = new Button("Convert Musician List to PDF");
        ListToPdfButton.setMinWidth(180);
        ListToPdfButton.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                List<Person> items = _musicianList.getItems();
                try {
                   ListPDFGenerator main = new ListPDFGenerator(_musicianList.getItems(),selectedFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        _musicianList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                MusicianToPdfButton.setDisable(false);
            } else {
                MusicianToPdfButton.setDisable(true);
            }
        });

        Label titleMusician = new Label("Musician");
        titleMusician.setId("titleMusician");

        Label titleList = new Label("Musician List");
        titleList.setId("titleList");

        _filterField = new TextField();
        _filterField.setMaxWidth(200);
        _filterField.setPromptText("First- or Lastname");
        _filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.trim().isEmpty()) {
                updateFilteredData();
            }else{
                updateFilteredData();
            }
        });

        HBox searchField = new HBox(20);
        searchField.setAlignment(Pos.TOP_CENTER);
        searchField.getChildren().addAll(new Label("Search:"), _filterField);

        VBox listBox = new VBox(20);
        listBox.setAlignment(Pos.TOP_CENTER);
        listBox.getChildren().addAll(titleList, searchField, _musicianList, ListToPdfButton);

        TextField space = new TextField(" ");
        space.setVisible(false);

        VBox personBox = new VBox(20);
        personBox.setAlignment(Pos.TOP_CENTER);
        personBox.getChildren().addAll(titleMusician, space, gridPane, MusicianToPdfButton);

        HBox root = new HBox(20);
        root.getChildren().addAll(listBox, personBox);

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
        _musicianList.getColumns().clear();
        _musicianList.getColumns().addAll(MusicianTableHelper.getFirstNameColumn(), MusicianTableHelper.getLastNameColumn());
    }

    @Override
    public void exit() {

    }

    @Override
    public void dispose() {

    }

    private void loadList() {
        if (_loadList != null) {
            PersonParameter personParameter = new PersonParameter();
            List<Person> personList = _loadList.doAction(personParameter);

            if (personList != null) {
               // _musicianList.setItems(FXCollections.observableList(personList));
                _masterData.setAll(personList);
                update();
            }
        }
    }

    public void setFields(Person person) {

        if (person.getPersonID() >0) {
            _id.setText(String.valueOf(person.getPersonID()));
        }

        if (person.getFirstname() != null) {
            _firstname.setText(person.getFirstname());
        }
        if (person.getLastname() != null) {
            _lastname.setText(person.getLastname());
        }
        if (person.getAccount() != null && person.getAccount().getUsername() != null) {
            _username.setText(person.getAccount().getUsername());
        }
        if (person.getPhoneNumber() != null) {
            _phonenumber.setText(person.getPhoneNumber());
        }
        if (person.getAddress() != null) {
            _address.setText(person.getAddress());
        }
        if (person.getPersonRole() != null) {
            _personrole.setText(person.getPersonRole().toString());
        }
        if (person.getAccount() != null && person.getAccount().getRole() != null) {
            _accountrole.setText(person.getAccount().getRole().toString());
        }

        if (person.getListToString() != null) {
            _instruments.setText(person.getInstrumentsForList());
        }

        if (person.getInitials() != null) {
            _initials.setText(person.getInitials().toString());
        }

        if (person.getGender() != null) {
            _gender.setText(person.getGender().toString());
        }
        if (person.getEmail() != null) {
            _email.setText(person.getEmail().toString());
        }

    }

    private void reset(){
        for(Label label:_labelList){
            label.setText(" ");
        }
        _instruments.clear();
    }

    private void updateFilteredData() {
        _filteredData.clear();

        for (Person p : _masterData) {
            if (matchesFilter(p)) {
                _filteredData.add(p);
            }
        }
        _musicianList.setItems(_filteredData);
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
        ArrayList<TableColumn<Person, ?>> sortOrder = new ArrayList<>(_musicianList.getSortOrder());
        _musicianList.getSortOrder().clear();
        _musicianList.getSortOrder().addAll(sortOrder);
    }

}

