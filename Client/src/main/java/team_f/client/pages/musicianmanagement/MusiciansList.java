package team_f.client.pages.musicianmanagement;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.Person;

import java.util.List;


public class MusiciansList extends BaseTablePage<Person, Person, Person, PersonParameter> {
    private Label _firstname;
    private Label _lastname;
    private Label _username;
    private Label _phonenumber;
    private Label _street;
    private Label _email;
    private Label _personrole;
    private Label _accountrole;
    private TableView<Person> _table;


    @Override
    public void initialize() {
        _firstname = new Label();
        _lastname = new Label();
        _username = new Label();
        _phonenumber = new Label();
        _street = new Label();
        _email = new Label();
        _personrole = new Label();
        _accountrole = new Label();
        _table = new TableView();


        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        Label label = new Label("Musician List");
        label.setFont(new Font("Arial", 20));



        _table.setEditable(false);
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _table.getColumns().addAll(MusicianTableHelper.getFirstNameColumn(), MusicianTableHelper.getLastNameColumn());
        _table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                _firstname.setText("Karl");
                _lastname.setText("Müller");
                _username.setText("KRLM");

            }
        });

        HBox vbox = new HBox();
        vbox.setSpacing(5);

        borderPane.setTop(label);
        vbox.getChildren().addAll(_table);
        borderPane.setCenter(vbox);

        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(160), new ColumnConstraints(160));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(new Label("First Name:"), 0, 1);
        gridPane.add(new Label("Last Name:"), 0, 3);
        gridPane.add(new Label("Username:"), 0, 5);
        gridPane.add(new Label("Phone Number:"), 0, 7);
        gridPane.add(new Label("Street:"), 0, 9);
        gridPane.add(new Label("Email:"), 0, 11);
        gridPane.add(new Label("Person Role:"), 0, 13);
        gridPane.add(new Label("Account Role:"), 0, 15);
        gridPane.add(_firstname, 1, 1);
        gridPane.add(_lastname, 1, 3);
        gridPane.add(_username, 1, 5);
        gridPane.add(_phonenumber, 1, 7);
        gridPane.add(_street, 1, 9);
        gridPane.add(_email, 2, 11);
        gridPane.add(_personrole, 3, 13);
        gridPane.add(_accountrole, 4, 15);

        scrollPane.setContent(gridPane);
        vbox.getChildren().add(scrollPane);
        setCenter(borderPane);

    }

    @Override
    public void load() {
        if(_load != null) {
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
        if(_loadList != null) {
            PersonParameter personParameter = new PersonParameter();
            List<Person> personList = _loadList.doAction(personParameter);

            if(personList != null) {
                _table.setItems(FXCollections.observableList(personList));
                update();
            }
        }
    }

}

