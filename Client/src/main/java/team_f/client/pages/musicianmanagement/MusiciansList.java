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
import java.util.List;


public class MusiciansList extends BaseTablePage<Person, Person, Person, PersonParameter> {
    private Label _ID;
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
        _ID = new Label();
        _firstname = new Label();
        _lastname = new Label();
        _username = new Label();
        _phonenumber = new Label();
        _address = new Label();
        _email = new Label();
        _personrole = new Label();
        _accountrole = new Label();
        _instruments=new Label();
        _initials=new Label();
        _section=new Label();
        _gender=new Label();
        _table = new TableView();
        _selectedPath = new TextField();



        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        _table.setMinHeight(450);
        _table.setEditable(false);
        _table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _table.getColumns().addAll(MusicianTableHelper.getFirstNameColumn(), MusicianTableHelper.getLastNameColumn());
        _table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                _ID.setText(String.valueOf(_table.getSelectionModel().getSelectedItem().getPersonID()));
                _firstname.setText(_table.getSelectionModel().getSelectedItem().getFirstname());
                _lastname.setText(_table.getSelectionModel().getSelectedItem().getLastname());
                _username.setText(_table.getSelectionModel().getSelectedItem().getAccount().getUsername());
                _phonenumber.setText(_table.getSelectionModel().getSelectedItem().getPhoneNumber());
                _address.setText(_table.getSelectionModel().getSelectedItem().getAddress());
                _personrole.setText(_table.getSelectionModel().getSelectedItem().getPersonRole().toString());
                _accountrole.setText(_table.getSelectionModel().getSelectedItem().getAccount().getRole().toString());
                _instruments.setText(_table.getSelectionModel().getSelectedItem().getInstrumentType().name());
                _initials.setText(_table.getSelectionModel().getSelectedItem().getInitials().toString());
                _section.setText(_table.getSelectionModel().getSelectedItem().getInstrumentType().name());
                _gender.setText(_table.getSelectionModel().getSelectedItem().getGender().toString());

            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinHeight(450);
        gridPane.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: grey;");
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(160), new ColumnConstraints(160));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        gridPane.add(new Label("ID"), 0, 1);
        gridPane.add(new Label("Initials:"), 0, 3);
        gridPane.add(new Label("Gender:"), 0, 5);
        gridPane.add(new Label("First Name:"), 0, 7);
        gridPane.add(new Label("Last Name:"), 0, 9);
        gridPane.add(new Label("Username:"), 0, 11);
        gridPane.add(new Label("Address:"), 0, 13);
        gridPane.add(new Label("Phone Number:"), 0, 15);
        gridPane.add(new Label("Email:"), 0, 17);
        gridPane.add(new Label("Account Role:"), 0, 19);
        gridPane.add(new Label("Person Role:"), 0, 21);
        gridPane.add(new Label("Section:"), 0, 23);
        gridPane.add(new Label("Instruments:"), 0, 25);

        gridPane.add(_ID, 1, 1);
        gridPane.add(_initials, 1, 3);
        gridPane.add(_gender, 1, 5);
        gridPane.add(_firstname, 1, 7);
        gridPane.add(_lastname, 1, 9);
        gridPane.add(_username, 1, 11);
        gridPane.add(_address, 1, 13);
        gridPane.add(_phonenumber, 1, 15);
        gridPane.add(_email, 1, 17);
        gridPane.add(_accountrole, 1, 19);
        gridPane.add(_personrole, 1, 21);
        gridPane.add(_section, 1, 23);
        gridPane.add(_instruments, 1, 25);

        Button MusicianToPdfButton = new Button("Convert Musician to PDF");
        MusicianToPdfButton.setDisable(true);
        MusicianToPdfButton.setMinWidth(180);
        MusicianToPdfButton.setStyle("-fx-font: 14 arial;");
        MusicianToPdfButton.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                Person selected = _table.getSelectionModel().getSelectedItem();
                try {
                    MusicianPDFGenerator main = new MusicianPDFGenerator(selected, selectedFile.getAbsolutePath());
                } catch (Exception e) {
                }
                }else{
                return;
            }
        });

        Button ListToPdfButton = new Button("Convert Musician List to PDF");
        ListToPdfButton.setMinWidth(180);
        ListToPdfButton.setStyle("-fx-font: 14 arial;");
        ListToPdfButton.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                _selectedPath.setText(selectedFile.getAbsolutePath());
                List<Person> items = _table.getItems();
                try {
                    //PublisherPDFGenerator main = new PublisherPDFGenerator(items, selectedValues, _selectedPath.getText());
                } catch (Exception e) {
                }
            }
        });

        _table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
            MusicianToPdfButton.setDisable(false);
            }else{
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
        titleMusician.setStyle(" -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        Label titleList = new Label("Musician List");
        titleList.setStyle(" -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        VBox zoomTool = new VBox();
        zoomTool.setStyle("-fx-padding: 10;");
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

}

