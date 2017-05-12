package team_f.client.pages.instrumentationmanagement;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.BigDecimalField;
import team_f.client.controls.numberfield.NumberField;
import team_f.client.exceptions.NumberRangeException;
import team_f.client.helper.ErrorMessageHelper;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.special.InstrumentationErrorList;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dominik on 11.05.17.
 */
public class InstrumentationManagement extends BaseTablePage<InstrumentationErrorList, Instrumentation, Instrumentation, InstrumentationParameter> {
    private TextField _nameField;

    private TableView<Instrumentation> _table;

    //String
    private BigDecimalField _firstViolinField;
    private NumberField _secondViolinField;
    private NumberField _violaField;
    private NumberField _violoncelloField;
    private NumberField _doublebassField;

    //Wood
    private NumberField _fluteField;
    private NumberField _oboeField;
    private NumberField _clarinetField;
    private NumberField _bassoonField;

    //Brass
    private NumberField _hornField;
    private NumberField _trumpetField;
    private NumberField _tromboneField;
    private NumberField _tubeField;

    //Percussion
    private NumberField _kettledrumField;
    private NumberField _percussionField;
    private NumberField _harpField;

    //SpecialInstrumentation
    private TextField _specialInstrumentation;

    public InstrumentationManagement(){

    }

    @Override
    public void initialize() {
        if(_initialize != null) {
            _initialize.doAction(null);
        }

        _nameField = new TextField();
        try {
            _firstViolinField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _secondViolinField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _violaField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _violoncelloField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _doublebassField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);

            _fluteField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _oboeField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _clarinetField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _bassoonField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);

            _hornField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _trumpetField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _tromboneField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _tubeField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);

            _kettledrumField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _percussionField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
            _harpField = new NumberField((Integer) null, 0, Integer.MAX_VALUE);
        } catch (NumberRangeException e) {
        }

        _specialInstrumentation = new TextField();

        _table = new TableView<>();
        _table.setEditable(false);

        TableView.TableViewSelectionModel<Instrumentation> tsm = _table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);

        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        GridPane newDataPane = getNewInstrumentationDataPane();

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(e -> deleteInstrumentation());

        VBox root = new VBox();
        root.getChildren().addAll(newDataPane, _table, deleteButton);
        root.setSpacing(5);
        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        borderPane.setCenter(root);
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
        _table.getColumns().addAll(InstrumentationHelper.getIdColumn(), InstrumentationHelper.getInstrumentationColumn());
    }

    @Override
    public void exit() {
        if(_exit != null) {
            _exit.doAction(null);
        }
    }

    @Override
    public void dispose() {

    }

    public void addInstrumentation(){

    }

    public void deleteInstrumentation(){

    }

    private GridPane getNewInstrumentationDataPane(){
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        Label titleInstrumentation = new Label("Instrumentation");
        titleInstrumentation.setStyle(" -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        pane.addRow(0,titleInstrumentation);
        pane.addRow(1, new Label("Name"), _nameField);

        //Strings
        pane.addRow(4, new Label("1. Violin:"), _firstViolinField);
        pane.addRow(5, new Label("2. Violin:"), _secondViolinField);
        pane.addRow(6, new Label("Viola:"), _violaField);
        pane.addRow(7, new Label("Violoncello:"), _violoncelloField);
        pane.addRow(8, new Label("Doublebass:"), _doublebassField);

        //Wood
        pane.addRow(4, new Label("Flute:"), _fluteField);
        pane.addRow(5, new Label("Oboe:"), _oboeField);
        pane.addRow(6, new Label("Clarinet:"), _clarinetField);
        pane.addRow(7, new Label("Bassoon:"), _bassoonField);

        //Brass
        pane.addRow(4, new Label("Horn:"), _hornField);
        pane.addRow(5, new Label("Trumpet:"), _trumpetField);
        pane.addRow(6, new Label("Trombone:"), _tromboneField);
        pane.addRow(7, new Label("Tube:"), _tubeField);

        //Percussion
        pane.addRow(4, new Label("Kettledrum:"), _kettledrumField);
        pane.addRow(5, new Label("Percussion:"), _percussionField);
        pane.addRow(6, new Label("Harp:"), _harpField);

        pane.addRow(4, _specialInstrumentation);

        List<TextField> textFields = new LinkedList();
        textFields.add(_nameField);

        List<BigDecimalField> decimalFields = new LinkedList<>();
        decimalFields.add(_firstViolinField);
        decimalFields.add(_secondViolinField);
        decimalFields.add(_violaField);
        decimalFields.add(_violoncelloField);
        decimalFields.add(_doublebassField);

        decimalFields.add(_fluteField);
        decimalFields.add(_oboeField);
        decimalFields.add(_clarinetField);
        decimalFields.add(_bassoonField);

        decimalFields.add(_hornField);
        decimalFields.add(_trumpetField);
        decimalFields.add(_tromboneField);
        decimalFields.add(_tubeField);

        decimalFields.add(_kettledrumField);
        decimalFields.add(_percussionField);
        decimalFields.add(_harpField);


        Button addButton = new Button("Add Instrumentation");
        addButton.setMinWidth(130);

        pane.add(new Label("Instrumentation:"), 0,2);
        pane.add(new Label("String:"), 0,3);
        pane.add(new Label("Wood:"),2,3);
        pane.add(new Label("Brass:"),4, 3);
        pane.add(new Label("Percussion:"),6, 3);
        pane.add(new Label("Special Instruments:"),8,3);
        pane.add(addButton, 9, 8);
        return pane;
    }

    private void loadList() {
        if(_loadList != null) {
            InstrumentationParameter instrumentationParameter = new InstrumentationParameter();
            List<Instrumentation> instrumentationList = _loadList.doAction(instrumentationParameter);

            if(instrumentationList != null) {
                _table.setItems(FXCollections.observableList(instrumentationList));
                update();
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }
}
