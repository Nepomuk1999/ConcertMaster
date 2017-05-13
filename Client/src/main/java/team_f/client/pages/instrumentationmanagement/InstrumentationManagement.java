package team_f.client.pages.instrumentationmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import jfxtras.labs.scene.control.BigDecimalField;
import team_f.client.controls.numberfield.NumberField;
import team_f.client.converter.InstrumentationConverter;
import team_f.client.exceptions.NumberRangeException;
import team_f.client.helper.ErrorMessageHelper;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.Instrumentation;

import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.special.errorlist.InstrumentationErrorList;
import team_f.jsonconnector.interfaces.JSONObjectEntity;


import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    private List<BigDecimalField> _fields;
    private HBox _textfields;

    public InstrumentationManagement(){

    }

    @Override
    public void initialize() {
        if(_initialize != null) {
            _initialize.doAction(null);
        }
        final URL Style = ClassLoader.getSystemResource("style/stylesheetInstrumentation.css");
        getStylesheets().add(Style.toString());
        _nameField = new TextField();
        _nameField.setMinWidth(200);
        _textfields=new HBox(_nameField);
        _fields=new ArrayList<>();
        try {
            _firstViolinField = new NumberField(0 , 0, Integer.MAX_VALUE);
            _firstViolinField.setMaxWidth(60);
            _fields.add(_firstViolinField);
            _secondViolinField = new NumberField(0, 0, Integer.MAX_VALUE);
            _secondViolinField.setMaxWidth(60);
            _fields.add(_secondViolinField);
            _violaField = new NumberField( 0, 0, Integer.MAX_VALUE);
            _violaField.setMaxWidth(60);
            _fields.add(_violaField);
            _violoncelloField = new NumberField(0, 0, Integer.MAX_VALUE);
            _violoncelloField.setMaxWidth(60);
            _fields.add(_violoncelloField);
            _doublebassField = new NumberField( 0, 0, Integer.MAX_VALUE);
            _doublebassField.setMaxWidth(60);
            _fields.add(_doublebassField);

            _fluteField = new NumberField(0, 0, Integer.MAX_VALUE);
            _fluteField.setMaxWidth(60);
            _fields.add(_fluteField);
            _oboeField = new NumberField(0, 0, Integer.MAX_VALUE);
            _oboeField.setMaxWidth(60);
            _fields.add(_oboeField);
            _clarinetField = new NumberField(0, 0, Integer.MAX_VALUE);
            _clarinetField.setMaxWidth(60);
            _fields.add(_clarinetField);
            _bassoonField = new NumberField(0, 0, Integer.MAX_VALUE);
            _bassoonField.setMaxWidth(60);
            _fields.add(_bassoonField);

            _hornField = new NumberField(0, 0, Integer.MAX_VALUE);
            _hornField.setMaxWidth(60);
            _fields.add(_hornField);
            _trumpetField = new NumberField(0, 0, Integer.MAX_VALUE);
            _trumpetField.setMaxWidth(60);
            _fields.add(_trumpetField);
            _tromboneField = new NumberField(0, 0, Integer.MAX_VALUE);
            _tromboneField.setMaxWidth(60);
            _fields.add(_tromboneField);
            _tubeField = new NumberField(0, 0, Integer.MAX_VALUE);
            _tubeField.setMaxWidth(60);
            _fields.add(_tubeField);

            _kettledrumField = new NumberField(0, 0, Integer.MAX_VALUE);
            _kettledrumField.setMaxWidth(60);
            _fields.add(_kettledrumField);
            _percussionField = new NumberField(0, 0, Integer.MAX_VALUE);
            _percussionField.setMaxWidth(60);
            _fields.add(_percussionField);
            _harpField = new NumberField(0, 0, Integer.MAX_VALUE);
            _harpField.setMaxWidth(60);
            _fields.add(_harpField);
        } catch (NumberRangeException e) {
        }

        _specialInstrumentation = new TextField();

        _table = new TableView<>();
        _table.setEditable(false);

        TableView.TableViewSelectionModel<Instrumentation> tsm = _table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.SINGLE);

        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _table.getColumns().addListener((ListChangeListener) change -> {
            change.next();
            if (change.wasReplaced()) {
                update();
            }
        });

        GridPane newDataPane = getNewInstrumentationDataPane();

        // Create the Delete Button and add Event-Handler
        Button deleteButton = new Button("Delete Selected Rows");
        deleteButton.setOnAction(e -> deleteInstrumentation());

        VBox root = new VBox();
        root.getChildren().addAll(newDataPane, _table, deleteButton);
        root.setSpacing(5);
        BorderPane borderPane=new BorderPane();
        borderPane.setId("borderPane");

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
        if(_create != null) {
            Instrumentation instrumentation = new Instrumentation();

            instrumentation.setViolin1(Integer.parseInt(_firstViolinField.getText()));
            instrumentation.setViolin2(Integer.parseInt(_secondViolinField.getText()));
            instrumentation.setViola(Integer.parseInt(_violaField.getText()));
            instrumentation.setViolincello(Integer.parseInt(_violoncelloField.getText()));
            instrumentation.setDoublebass(Integer.parseInt(_doublebassField.getText()));

            instrumentation.setFlute(Integer.parseInt(_fluteField.getText()));
            instrumentation.setOboe(Integer.parseInt(_oboeField.getText()));
            instrumentation.setClarinet(Integer.parseInt(_clarinetField.getText()));
            instrumentation.setBassoon(Integer.parseInt(_bassoonField.getText()));

            instrumentation.setHorn(Integer.parseInt(_hornField.getText()));
            instrumentation.setTrumpet(Integer.parseInt(_trumpetField.getText()));
            instrumentation.setTrombone(Integer.parseInt(_tromboneField.getText()));
            instrumentation.setTube(Integer.parseInt(_tubeField.getText()));

            instrumentation.setKettledrum(Integer.parseInt(_kettledrumField.getText()));
            instrumentation.setPercussion(Integer.parseInt(_percussionField.getText()));
            instrumentation.setHarp(Integer.parseInt(_harpField.getText()));

            //instrumentation.setSpecialInstrumentation();

            InstrumentationErrorList resultInstrumentationErrorList = _create.doAction(instrumentation);

            if (resultInstrumentationErrorList != null && resultInstrumentationErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = InstrumentationConverter.getAbstractList(resultInstrumentationErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if(tmpErrorText.isEmpty() && resultInstrumentationErrorList.getKeyValueList().size() == 1 && resultInstrumentationErrorList.getKeyValueList().get(0).getKey() != null && resultInstrumentationErrorList.getKeyValueList().get(0).getKey().getInstrumentationID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _table.getItems().add(resultInstrumentationErrorList.getKeyValueList().get(0).getKey());
                    update();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
        reset();
    }

    public void deleteInstrumentation(){
        if(_delete != null) {
            Instrumentation instrumentation = new Instrumentation();

            InstrumentationErrorList resultInstrumentationErrorList = _create.doAction(instrumentation);

            if (resultInstrumentationErrorList != null && resultInstrumentationErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = InstrumentationConverter.getAbstractList(resultInstrumentationErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if(tmpErrorText.isEmpty() && resultInstrumentationErrorList.getKeyValueList().size() == 1 && resultInstrumentationErrorList.getKeyValueList().get(0).getKey() != null && resultInstrumentationErrorList.getKeyValueList().get(0).getKey().getInstrumentationID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _table.getItems().remove(resultInstrumentationErrorList.getKeyValueList().get(0).getKey());
                    update();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    private GridPane getNewInstrumentationDataPane(){
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        pane.getColumnConstraints().addAll(new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90),
                new ColumnConstraints(90),new ColumnConstraints(90),new ColumnConstraints(90),new ColumnConstraints(90),new ColumnConstraints(90),new ColumnConstraints(120));

        Label titleInstrumentation = new Label("Instrumentation");
        titleInstrumentation.setId("titleInstrumentation");
        titleInstrumentation.setMinWidth(200);

        _textfields =new HBox();
        _textfields.setSpacing(10);
        Label nameLabel=new Label("Name:");
        nameLabel.setMinWidth(60);
        _textfields.getChildren().addAll(nameLabel,_nameField);

        pane.addRow(0,titleInstrumentation);
        pane.addRow(1,_textfields);

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
        addButton.setMinWidth(160);

        addButton.setOnAction(e -> {
            if (_nameField.getText().isEmpty() ||
                    _firstViolinField.getText().isEmpty() || _secondViolinField.getText().isEmpty() || _violaField.getText().isEmpty() || _violoncelloField.getText().isEmpty() ||
                    _doublebassField.getText().isEmpty() || _fluteField.getText().isEmpty() || _oboeField.getText().isEmpty() || _clarinetField.getText().isEmpty() ||
                    _bassoonField.getText().isEmpty() || _hornField.getText().isEmpty() || _trumpetField.getText().isEmpty() || _tromboneField.getText().isEmpty() || _tubeField.getText().isEmpty()
                    || _kettledrumField.getText().isEmpty() || _percussionField.getText().isEmpty() || _harpField.getText().isEmpty()) {
                validate(textFields);
                validate(decimalFields);
                showValuesMissingMessage();
            } else {
                addInstrumentation();
            }
        });

        pane.add(new Label("Instrumentation:"), 0,2);
        pane.add(new Label("String:"), 0,3);
        pane.add(new Label("Wood:"),2,3);
        pane.add(new Label("Brass:"),4, 3);
        pane.add(new Label("Percussion:"),6, 3);
        pane.add(new Label("Special Instruments:"),8,3);
        pane.add(addButton, 8, 8);
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

    private void reset() {
        _nameField.clear();
        _nameField.setStyle("-fx-border-color: transparent");
        _table.getSelectionModel().clearSelection();
     for(BigDecimalField field:_fields){
        field.setNumber(new BigDecimal(0));
        field.setStyle("-fx-border-color: transparent");
    }
    }
}
