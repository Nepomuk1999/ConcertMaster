package team_f.client.pages.musicalwork;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by dominik on 04.05.17.
 */
public class MusicalWorkManagement extends BorderPane {
    private final TextField _nameField;
    private final TextField _composerField;
    private TableView<MusicalWorkTestData> table;

    private int[] _stringinstrumentation;
    private final TextField _firstViolinField;
    private final TextField _secondViolinField;
    private final TextField _violaField;
    private final TextField _violoncelloField;
    private final TextField _contrabassField;

    private int[] _woodInstrumentation;
    private final TextField _fluteField;
    private final TextField _oboeField;
    private final TextField _clarinetField;
    private final TextField _bassoonField;


    private int[] _brassInstrumentation;
    private final TextField _hornField;
    private final TextField _trumpetField;
    private final TextField _tromboneField;
    private final TextField _tubaField;

    private int[] _percussionInstrumentation;
    private final TextField _kettledrumField;
    private final TextField _percussionField;
    private final TextField _harpField;

    public MusicalWorkManagement(){
        //Name, Komponist, Instrumentation, Alternativ
        _nameField = new TextField();
        _composerField = new TextField();

        _stringinstrumentation = new int[4];
        _firstViolinField = new TextField();
        _firstViolinField.setMaxWidth(50.0);
        _secondViolinField = new TextField();
        _secondViolinField.setMaxWidth(50.0);
        _violaField = new TextField();
        _violaField.setMaxWidth(50.0);
        _violoncelloField = new TextField();
        _violoncelloField.setMaxWidth(50.0);
        _contrabassField = new TextField();
        _contrabassField.setMaxWidth(50.0);

        _woodInstrumentation = new int[3];
        _fluteField = new TextField();
        _oboeField = new TextField();
        _clarinetField = new TextField();
        _bassoonField = new TextField();

        _brassInstrumentation = new int[3];
        _hornField = new TextField();
        _trumpetField = new TextField();
        _tromboneField = new TextField();
        _tubaField = new TextField();

        _percussionInstrumentation = new int[2];
        _kettledrumField = new TextField();
        _percussionField = new TextField();
        _harpField = new TextField();

        table = new TableView<>(MusicalWorkHelper.getMusicalWorkList());
        table.setEditable(false);

        TableView.TableViewSelectionModel<MusicalWorkTestData> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);


        table.getColumns().addAll(MusicalWorkHelper.getIdColumn(), MusicalWorkHelper.getMusicalWorkNameColumn(),
                MusicalWorkHelper.getComposerColumn(), MusicalWorkHelper.getInstrumentationColumn());


        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        GridPane newDataPane = getNewMusicalWorkDataPane();

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(e -> deleteMusicalWork());

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

    public GridPane getNewMusicalWorkDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(7);


        pane.addRow(0, new Label("Name:"), _nameField);
        pane.addRow(0, new Label("Composer:"), _composerField);

        //Strings
        pane.addRow(3, new Label("1. Violin:"), _firstViolinField);
        pane.addRow(4, new Label("2. Violin:"), _secondViolinField);
        pane.addRow(5, new Label("Viola:"), _violaField);
        pane.addRow(6, new Label("Violoncello:"), _violoncelloField);
        pane.addRow(7, new Label("Contrabass:"), _contrabassField);

        //Wood
        pane.addRow(3, new Label("Flute:"), _fluteField);
        pane.addRow(4, new Label("Oboe:"), _oboeField);
        pane.addRow(5, new Label("Clarinet:"), _clarinetField);
        pane.addRow(6, new Label("Bassoon:"), _bassoonField);


        //Brass
        pane.addRow(3, new Label("Horn:"), _hornField);
        pane.addRow(4, new Label("Trumpet:"), _trumpetField);
        pane.addRow(5, new Label("Trombone:"), _tromboneField);
        pane.addRow(6, new Label("Tuba:"), _tubaField);

        //Percussion
        pane.addRow(3, new Label("Kettledrum:"), _kettledrumField);
        pane.addRow(4, new Label("Percussion:"), _percussionField);
        pane.addRow(5, new Label("Harp:"), _harpField);




        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(_nameField);
        fields.add(_composerField);

        Button addButton = new Button("Add MusicalWork");
        Button resetButton = new Button("Reset");

        resetButton.setOnAction(e -> {
            for (TextField field : fields) {
                field.setText(null);
                field.setStyle(null);
            }
        });

        addButton.setOnAction(e -> {

            if (_nameField.getText().isEmpty() || _composerField.getText().isEmpty()) {

                validate(fields);

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Values Missing");
                alert.setHeaderText("Please fill in all the information in the form.");
                alert.setContentText("You can not save this MusicalWork. Please fill in missing data!");
                alert.showAndWait();

            } else {
                addMusicalWork();
            }
        });

        pane.add(new Label("Instrumentation:"), 0,1);
        pane.add(new Label("String:"), 0,2);
        pane.add(new Label("Wood:"),2,2);
        pane.add(new Label("Brass:"),4, 2);
        pane.add(new Label("Percussion:"),6, 2);
        pane.add(new Label("Special Instruments:"),8,2);
        pane.add(addButton, 9, 7);
        pane.add(resetButton, 10, 7);

        return pane;
    }

    private void validate(ArrayList<TextField> fields) {
        for (TextField textField : fields) {
            if (textField.getText().isEmpty()) {
                textField.setStyle("-fx-border-color: red");
            } else {
                textField.setStyle("-fx-border-color: green");
            }
        }
    }


    public void addMusicalWork() {
        Integer currentId = 0;

        // Get the next ID
        for (MusicalWorkTestData mw : table.getItems()) {
            if (mw.get_id() > currentId) {
                currentId = mw.get_id();
            }
        }

        MusicalWorkTestData musicalwork = new MusicalWorkTestData(currentId + 1, _nameField.getText(), _composerField.getText(), "");

        table.getItems().add(musicalwork);

        _nameField.setText(null);
        _composerField.setText(null);
    }

    public void deleteMusicalWork(){

    }
}
