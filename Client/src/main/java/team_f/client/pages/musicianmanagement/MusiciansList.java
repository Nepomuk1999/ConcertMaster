package team_f.client.pages.musicianmanagement;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.Person;

import java.io.File;
import java.net.URL;
import java.util.List;


public class MusiciansList extends BaseTablePage<Person, Person, Person, PersonParameter> {
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
    private Label _instruments;
    private Label _gender;
    private TableView<Person> _table;
    private TextField _selectedPath;

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
        _instruments = new Label();
        _initials = new Label();
        _section = new Label();
        _gender = new Label();
        _table = new TableView();
        _selectedPath = new TextField();


        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderPane");


        _table.setMinHeight(450);
        _table.setMinWidth(450);
        _table.setEditable(false);
        _table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _table.getColumns().addAll(MusicianTableHelper.getFirstNameColumn(), MusicianTableHelper.getLastNameColumn());
        _table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setFields(_table.getSelectionModel().getSelectedItem());
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setId("gridpane");
        gridPane.setMinHeight(450);

        gridPane.getColumnConstraints().addAll(new ColumnConstraints(160), new ColumnConstraints(160), new ColumnConstraints(160));
        gridPane.setVgap(5);
        gridPane.setHgap(10);


        gridPane.add(new Label("ID"), 0, 0);
        gridPane.add(new Label("Initials:"), 0, 2);
        gridPane.add(new Label("Gender:"), 2, 2);
        gridPane.add(new Label("First Name:"), 0, 4);
        gridPane.add(new Label("Last Name:"), 2, 4);
        gridPane.add(new Label("Username:"), 0, 6);
        gridPane.add(new Label("Address:"), 2, 6);
        gridPane.add(new Label("Phone Number:"), 0, 8);
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
                    MusicianPDFGenerator main = new MusicianPDFGenerator(_table.getSelectionModel().getSelectedItem(),selectedFile.getAbsolutePath());
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
                List<Person> items = _table.getItems();
                try {
                   ListPDFGenerator main=new ListPDFGenerator(_table.getItems(),selectedFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        _table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                MusicianToPdfButton.setDisable(false);
            } else {
                MusicianToPdfButton.setDisable(true);
            }
        });

        Slider mySlider = new Slider();
        mySlider.setMaxWidth(100);
        mySlider.setMin(0.5);
        mySlider.setMax(2);
        mySlider.setValue(1);
        mySlider.setShowTickLabels(true);
        mySlider.setShowTickMarks(true);
        mySlider.setMajorTickUnit(0.25);
        mySlider.setMinorTickCount(1);
        mySlider.setBlockIncrement(0.025);

        /*Scale scaleDefault = new Scale(0.8,1);
        scaleDefault.setPivotX(0);
        scaleDefault.setPivotY(0);
        borderPane.getTransforms().setAll(scaleDefault);*/

        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            Scale scale = new Scale(newValue.doubleValue() + 0.018, newValue.doubleValue() + 0.018);
            scale.setPivotX(0);
            scale.setPivotY(0);
            borderPane.getTransforms().setAll(scale);

        });

        Label titleMusician = new Label("Musician");
        titleMusician.setId("titleMusician");

        Label titleList = new Label("Musician List");
        titleList.setId("titleList");


        VBox zoomTool = new VBox();
        zoomTool.setId("zoomTool");
        zoomTool.getChildren().addAll(new Label("Zoom"), mySlider);
        setTop(zoomTool);

        VBox listBox = new VBox(20);
        listBox.setAlignment(Pos.TOP_CENTER);
        listBox.getChildren().addAll(titleList, _table, ListToPdfButton);

        VBox personBox = new VBox(20);
        personBox.setAlignment(Pos.TOP_CENTER);
        personBox.getChildren().addAll(titleMusician, gridPane, MusicianToPdfButton);


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
        _table.getColumns().clear();
        _table.getColumns().addAll(MusicianTableHelper.getFirstNameColumn(), MusicianTableHelper.getLastNameColumn());
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
                _table.setItems(FXCollections.observableList(personList));
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
        if (person.getInstrumentType() != null) {
            _instruments.setText(person.getInstrumentType().name());
        }
        if (person.getInitials() != null) {
            _initials.setText(person.getInitials().toString());
        }
        if (person.getInstrumentType() != null) {
            _section.setText(person.getInstrumentType().toString());
        }
        if (person.getGender() != null) {
            _gender.setText(person.getGender().toString());
        }


    }

}

