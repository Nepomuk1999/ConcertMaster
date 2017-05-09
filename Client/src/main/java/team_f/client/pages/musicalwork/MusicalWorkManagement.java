package team_f.client.pages.musicalwork;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.MusicalWork;
import java.util.ArrayList;
import java.util.List;

public class MusicalWorkManagement extends BaseTablePage<MusicalWork, MusicalWork, MusicalWork, MusicalWorkParameter> {
    private TextField _nameField;
    private TextField _composerField;
    private TableView<MusicalWork> _table;

    private int[] _stringinstrumentation;
    private TextField _firstViolinField;
    private TextField _secondViolinField;
    private TextField _violaField;
    private TextField _violoncelloField;
    private TextField _contrabassField;

    private int[] _woodInstrumentation;
    private TextField _fluteField;
    private TextField _oboeField;
    private TextField _clarinetField;
    private TextField _bassoonField;

    private int[] _brassInstrumentation;
    private TextField _hornField;
    private TextField _trumpetField;
    private TextField _tromboneField;
    private TextField _tubaField;

    private int[] _percussionInstrumentation;
    private TextField _kettledrumField;
    private TextField _percussionField;
    private TextField _harpField;

    private TextField _specialInstrumentation;

    public MusicalWorkManagement(){
    }

    @Override
    public void initialize() {
        if(_initialize != null) {
            _initialize.doAction(null);
        }

        //Name, Komponist, Instrumentation, Alternativ
        _nameField = new TextField();
        _composerField = new TextField();

        _stringinstrumentation = new int[4];
        _firstViolinField = new TextField();
        _firstViolinField.setMaxWidth(40.0);
        _secondViolinField = new TextField();
        _secondViolinField.setMaxWidth(40.0);
        _violaField = new TextField();
        _violaField.setMaxWidth(40.0);
        _violoncelloField = new TextField();
        _violoncelloField.setMaxWidth(40.0);
        _contrabassField = new TextField();
        _contrabassField.setMaxWidth(40.0);

        _woodInstrumentation = new int[3];
        _fluteField = new TextField();
        _fluteField.setMaxWidth(40.0);
        _oboeField = new TextField();
        _oboeField.setMaxWidth(40.0);
        _clarinetField = new TextField();
        _clarinetField.setMaxWidth(40.0);
        _bassoonField = new TextField();
        _bassoonField.setMaxWidth(40.0);

        _brassInstrumentation = new int[3];
        _hornField = new TextField();
        _hornField.setMaxWidth(40.0);
        _trumpetField = new TextField();
        _trumpetField.setMaxWidth(40.0);
        _tromboneField = new TextField();
        _tromboneField.setMaxWidth(40.0);
        _tubaField = new TextField();
        _tubaField.setMaxWidth(40.0);

        _percussionInstrumentation = new int[2];
        _kettledrumField = new TextField();
        _kettledrumField.setMaxWidth(40.0);
        _percussionField = new TextField();
        _percussionField.setMaxWidth(40.0);
        _harpField = new TextField();
        _harpField.setMaxWidth(40.0);

        _specialInstrumentation = new TextField();


        _table = new TableView<>();
        _table.setEditable(false);

        TableView.TableViewSelectionModel<MusicalWork> tsm = _table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);

        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        GridPane newDataPane = getNewMusicalWorkDataPane();

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(e -> deleteMusicalWork());

        VBox root = new VBox();
        root.getChildren().addAll(newDataPane, _table, deleteButton);
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
        if(_load != null) {
            List<MusicalWork> resultMusicalWorkList = _load.doAction(null);
            _table.setItems(FXCollections.observableList(resultMusicalWorkList));


            // @TODO:
            _table.getColumns().addAll(MusicalWorkHelper.getIdColumn(), MusicalWorkHelper.getMusicalWorkNameColumn(),
            MusicalWorkHelper.getComposerColumn(), MusicalWorkHelper.getInstrumentationColumn());

            _table.refresh();
        }
    }

    @Override
    public void exit() {
    }

    @Override
    public void dispose() {
    }

    public void addMusicalWork() {
        if(_create != null) {
            MusicalWork musicalWork = new MusicalWork();
            musicalWork.setName(_nameField.getText());
            musicalWork.setComposer(_composerField.getText());
            musicalWork.setInstrumentation(new Instrumentation());

            MusicalWork resultMusicalWork = _create.doAction(musicalWork);

            if(resultMusicalWork != null && resultMusicalWork.getMusicalWorkID() > 0) {
                _table.getItems().add(resultMusicalWork);
                _nameField.setText(null);
                _composerField.setText(null);
                _firstViolinField.setText(null);
                _secondViolinField.setText(null);
                _violaField.setText(null);
                _violoncelloField.setText(null);
                _contrabassField.setText(null);
                _fluteField.setText(null);
                _oboeField.setText(null);
                _clarinetField.setText(null);
                _bassoonField.setText(null);
                _hornField.setText(null);
                _trumpetField.setText(null);
                _tromboneField.setText(null);
                _tubaField.setText(null);
                _kettledrumField.setText(null);
                _percussionField.setText(null);
                _harpField.setText(null);
                _specialInstrumentation.setText(null);
            }
        }
    }

    public void deleteMusicalWork() {
        if(_delete != null) {
            MusicalWork musicalWork = new MusicalWork();

            MusicalWork resultMusicalWork = _create.doAction(musicalWork);

            if(resultMusicalWork == null) {
                _table.getItems().remove(resultMusicalWork);
                _nameField.setText(null);
                _composerField.setText(null);
            }
        }
    }

    private GridPane getNewMusicalWorkDataPane() {
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

        pane.addRow(3, _specialInstrumentation);

        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(_nameField);
        fields.add(_composerField);


        Button addButton = new Button("Add MusicalWork");

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

        return pane;
    }
}
