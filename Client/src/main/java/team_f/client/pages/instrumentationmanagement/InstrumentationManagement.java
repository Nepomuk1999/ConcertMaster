package team_f.client.pages.instrumentationmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import jfxtras.labs.scene.control.BigDecimalField;
import team_f.client.controls.numberfield.NumberField;
import team_f.client.converter.InstrumentationConverter;
import team_f.client.entities.KeyValuePair;
import team_f.client.exceptions.NumberRangeException;
import team_f.client.helper.ErrorMessageHelper;
import team_f.client.pages.BaseTablePage;
import team_f.client.pages.musicalwork.MusicalWorkHelper;
import team_f.client.pages.musicalwork.SpecialInstrumentationEntity;
import team_f.client.pages.musicianmanagement.MusicianTableHelper;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.errorlist.InstrumentationErrorList;
import team_f.jsonconnector.interfaces.JSONObjectEntity;


import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class InstrumentationManagement extends BaseTablePage<InstrumentationErrorList, Instrumentation, Instrumentation, InstrumentationParameter> {
    private TableView<Instrumentation> _instrumentationTable;
    private List<SpecialInstrumentationEntity> _specialInstrumentationEntityList;

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

    //SpecialInstrumentationriv
    private ScrollPane _specialInstrumentationPane;
    private GridPane _specialInstrumentationContent;
    private ComboBox<KeyValuePair> _specialInstrumentationComboBox;
    private TextField _specialInstrumentationTextField;
    private NumberField _specialInstrumentationNumberField;
    private Button _specialInstrumentationButton;


    private Button _addButton;
    private Button _updateButton;
    private Button _editButton;
    private Button _deleteButton;
    private Button _cancelButton;

    private GridPane _newDataPane;
    private List<BigDecimalField> _fields;

    public InstrumentationManagement() {

    }

    @Override
    public void initialize() {
        if (_initialize != null) {
            _initialize.doAction(null);
        }
        final URL Style = ClassLoader.getSystemResource("style/stylesheetInstrumentation.css");
        getStylesheets().add(Style.toString());
        _specialInstrumentationEntityList = new LinkedList();

        _fields = new ArrayList<>();
        try {
            _firstViolinField = new NumberField(0, 0, Integer.MAX_VALUE);
            _firstViolinField.setMaxWidth(60);
            _fields.add(_firstViolinField);
            _secondViolinField = new NumberField(0, 0, Integer.MAX_VALUE);
            _secondViolinField.setMaxWidth(60);
            _fields.add(_secondViolinField);
            _violaField = new NumberField(0, 0, Integer.MAX_VALUE);
            _violaField.setMaxWidth(60);
            _fields.add(_violaField);
            _violoncelloField = new NumberField(0, 0, Integer.MAX_VALUE);
            _violoncelloField.setMaxWidth(60);
            _fields.add(_violoncelloField);
            _doublebassField = new NumberField(0, 0, Integer.MAX_VALUE);
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

        _instrumentationTable = new TableView<>();
        _instrumentationTable.setEditable(false);
        _instrumentationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _instrumentationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _instrumentationTable.getColumns().addListener((ListChangeListener) change -> {
            change.next();
            if (change.wasReplaced()) {
                update();
            }
        });


        _instrumentationTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                _editButton.setDisable(false);
                _deleteButton.setDisable(false);
            } else {
                _editButton.setDisable(true);
                _deleteButton.setDisable(true);
            }
        });

        _newDataPane = getNewInstrumentationDataPane();
        HBox buttonsBox = new HBox(_editButton, _deleteButton);
        buttonsBox.setSpacing(10);


        VBox root = new VBox();
        root.getChildren().addAll(_newDataPane, _instrumentationTable, buttonsBox);
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
        _instrumentationTable.getColumns().clear();
        _instrumentationTable.getColumns().addAll(InstrumentationHelper.getIdColumn(), InstrumentationHelper.getInstrumentationColumn());
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

    public void addInstrumentation() {
        if (_create != null) {
            Instrumentation instrumentation = new Instrumentation();
            setInstrumentation(instrumentation);

            InstrumentationErrorList resultInstrumentationErrorList = _create.doAction(instrumentation);

            if (resultInstrumentationErrorList != null && resultInstrumentationErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = InstrumentationConverter.getAbstractList(resultInstrumentationErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultInstrumentationErrorList.getKeyValueList().size() == 1 && resultInstrumentationErrorList.getKeyValueList().get(0).getKey() != null && resultInstrumentationErrorList.getKeyValueList().get(0).getKey().getInstrumentationID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _instrumentationTable.getItems().add(resultInstrumentationErrorList.getKeyValueList().get(0).getKey());
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

    public void editInstrumentation() {
        if (_edit != null) {
            Instrumentation instrumentation = _instrumentationTable.getSelectionModel().getSelectedItem();
            setInstrumentation(instrumentation);

            InstrumentationErrorList resultInstrumentationErrorList = _edit.doAction(instrumentation);

            if (resultInstrumentationErrorList != null && resultInstrumentationErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = InstrumentationConverter.getAbstractList(resultInstrumentationErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultInstrumentationErrorList.getKeyValueList().size() == 1) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _instrumentationTable.getItems().remove(instrumentation);
                    _instrumentationTable.getItems().add(resultInstrumentationErrorList.getKeyValueList().get(0).getKey());
                    update();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    public void deleteInstrumentation() {
        if (_delete != null) {
            Instrumentation instrumentation = new Instrumentation();

            InstrumentationErrorList resultInstrumentationErrorList = _create.doAction(instrumentation);

            if (resultInstrumentationErrorList != null && resultInstrumentationErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = InstrumentationConverter.getAbstractList(resultInstrumentationErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if (tmpErrorText.isEmpty() && resultInstrumentationErrorList.getKeyValueList().size() == 1 && resultInstrumentationErrorList.getKeyValueList().get(0).getKey() != null && resultInstrumentationErrorList.getKeyValueList().get(0).getKey().getInstrumentationID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _instrumentationTable.getItems().remove(resultInstrumentationErrorList.getKeyValueList().get(0).getKey());
                    update();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    private GridPane getNewInstrumentationDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        pane.getColumnConstraints().addAll(new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90),
                new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(90), new ColumnConstraints(120));

        Label titleInstrumentation = new Label("Instrumentation");
        titleInstrumentation.setId("titleInstrumentation");
        titleInstrumentation.setMinWidth(200);

        pane.addRow(0, titleInstrumentation);

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

        List<TextField> textFields = new LinkedList();

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

        _addButton = new Button("Add");
        _addButton.setMinWidth(100);
        _addButton.setDisable(false);

        _addButton.setOnAction(e -> {
            if (_firstViolinField.getText().isEmpty() || _secondViolinField.getText().isEmpty() || _violaField.getText().isEmpty() || _violoncelloField.getText().isEmpty() ||
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

        _editButton = new Button("Edit Instrumentation");
        _editButton.setDisable(true);
        _editButton.setMinWidth(125);
        _editButton.setOnAction(e -> {
            _instrumentationTable.setDisable(true);
            _addButton.setDisable(true);
            _updateButton.setDisable(false);
            _editButton.setDisable(true);
            _deleteButton.setDisable(true);
            _cancelButton.setText("Cancel");
             fillFields(_instrumentationTable.getSelectionModel().getSelectedItem());
        });

        _deleteButton = new Button("Delete Instrumentation");
        _deleteButton.setDisable(true);
        _deleteButton.setOnAction(e -> deleteInstrumentation());

        _updateButton = new Button("Update");
        _updateButton.setMinWidth(100);
        _updateButton.setDisable(true);
        _updateButton.setOnAction(e -> {
            _instrumentationTable.setDisable(false);
            editInstrumentation();
            reset();
        });

        _cancelButton = new Button("Reset");
        _cancelButton.setMinWidth(100);
        _cancelButton.setOnAction(e -> {
            _instrumentationTable.setDisable(false);
            reset();
        });


        pane.add(new Label("String:"), 0, 3);
        pane.add(new Label("Wood:"), 2, 3);
        pane.add(new Label("Brass:"), 4, 3);
        pane.add(new Label("Percussion:"), 6, 3);
        pane.add(new Label("Special Instruments:"), 8, 2);
        pane.add(_addButton, 8, 9);
        pane.add(_updateButton, 9, 9);
        pane.add(_cancelButton, 0, 9);

        _specialInstrumentationContent = new GridPane();
        _specialInstrumentationComboBox = new ComboBox<>(MusicalWorkHelper.getSectionGroupTypeList());
        _specialInstrumentationComboBox.setMaxWidth(80);
        _specialInstrumentationComboBox.getSelectionModel().selectFirst();
        _specialInstrumentationContent.addColumn(0, _specialInstrumentationComboBox);
        _specialInstrumentationTextField = new TextField();
        _specialInstrumentationTextField.setMaxWidth(80);
        _specialInstrumentationContent.addColumn(1, _specialInstrumentationTextField);

        try {
            _specialInstrumentationNumberField = new NumberField(0, 0, Integer.MAX_VALUE);
        } catch (NumberRangeException e) {
        }

        _specialInstrumentationNumberField.setMaxWidth(60);
        _specialInstrumentationContent.addColumn(2, _specialInstrumentationNumberField);
        _specialInstrumentationButton = new Button("+");

        _specialInstrumentationButton.setOnAction(event -> {
            GridPane tmpPane = new GridPane();

            ComboBox<KeyValuePair> tmpComboBox = new ComboBox<>(_specialInstrumentationComboBox.getItems());
            tmpComboBox.getSelectionModel().select(_specialInstrumentationComboBox.getSelectionModel().getSelectedItem());
            tmpComboBox.setMaxWidth(80);
            tmpPane.addColumn(0, tmpComboBox);

            TextField tmpTextField = new TextField();
            tmpTextField.setMaxWidth(80);
            tmpTextField.setText(_specialInstrumentationTextField.getText());
            tmpPane.addColumn(1, tmpTextField);

            NumberField tmpNumberField = null;
            try {
                tmpNumberField = new NumberField(_specialInstrumentationNumberField.getNumber().intValue(), _specialInstrumentationNumberField.getMinValue().intValue(), _specialInstrumentationNumberField.getMaxValue().intValue());
                tmpPane.addColumn(2, tmpNumberField);
                tmpNumberField.setMaxWidth(60);
            } catch (NumberRangeException e) {
            }

            Button tmpButton = new Button("-");
            tmpPane.addColumn(3, tmpButton);

            _specialInstrumentationContent.addRow(_specialInstrumentationEntityList.size() + 1, tmpPane);
            _specialInstrumentationContent.setColumnSpan(tmpPane, 4);
            SpecialInstrumentationEntity specialInstrumentationEntity = new SpecialInstrumentationEntity(0, tmpComboBox, tmpTextField, tmpNumberField, tmpPane);

            tmpButton.setOnAction(e -> {
                _specialInstrumentationContent.getChildren().remove(tmpPane);
                _specialInstrumentationEntityList.remove(specialInstrumentationEntity);
            });

            _specialInstrumentationEntityList.add(specialInstrumentationEntity);
        });

        _specialInstrumentationContent.addColumn(3, _specialInstrumentationButton);
        _specialInstrumentationPane = new ScrollPane(_specialInstrumentationContent);
        _specialInstrumentationPane.setMaxHeight(250);
        _specialInstrumentationPane.setMinWidth(265);

        pane.add(_specialInstrumentationPane, 8, 3);
        pane.setRowSpan(_specialInstrumentationPane, 6);
        pane.setColumnSpan(_specialInstrumentationPane, 4);

        return pane;
    }

    private void loadList() {
        if (_loadList != null) {
            InstrumentationParameter instrumentationParameter = new InstrumentationParameter();
            List<Instrumentation> instrumentationList = _loadList.doAction(instrumentationParameter);

            if (instrumentationList != null) {
                _instrumentationTable.setItems(FXCollections.observableList(instrumentationList));
                update();
            } else {
                showTryAgainLaterErrorMessage();
            }
        }

    }

    private void reset() {
        _instrumentationTable.getSelectionModel().clearSelection();
        _instrumentationTable.setDisable(false);
        _instrumentationTable.getSelectionModel().clearSelection();
        _editButton.setDisable(true);
        _deleteButton.setDisable(true);
        _updateButton.setDisable(true);
        _addButton.setDisable(false);
        _cancelButton.setText("Reset");
        for (BigDecimalField field : _fields) {
            field.setNumber(new BigDecimal(0));
            field.setStyle("-fx-border-color: transparent");
        }
        for(SpecialInstrumentationEntity item : _specialInstrumentationEntityList) {
            removeSpecialInstrumentationItem(item);
        }
    }


    public void fillFields(Instrumentation instrumentation) {
        /*if (instrumentation.getName() != null) {
            _nameField.setText(instrumentation.getName());
        }*/
        if (instrumentation != null) {
            _firstViolinField.setNumber(new BigDecimal(instrumentation.getViolin1()));
            _secondViolinField.setNumber(new BigDecimal(instrumentation.getViolin2()));
            _violaField.setNumber(new BigDecimal(instrumentation.getViola()));
            _violoncelloField.setNumber(new BigDecimal(instrumentation.getViolincello()));
            _doublebassField.setNumber(new BigDecimal(instrumentation.getDoublebass()));

            _fluteField.setNumber(new BigDecimal(instrumentation.getFlute()));
            _oboeField.setNumber(new BigDecimal(instrumentation.getOboe()));
            _clarinetField.setNumber(new BigDecimal(instrumentation.getClarinet()));
            _bassoonField.setNumber(new BigDecimal(instrumentation.getBassoon()));

            _hornField.setNumber(new BigDecimal(instrumentation.getHorn()));
            _trumpetField.setNumber(new BigDecimal(instrumentation.getTrumpet()));
            _tromboneField.setNumber(new BigDecimal(instrumentation.getTrombone()));
            _tubeField.setNumber(new BigDecimal(instrumentation.getTube()));

            _kettledrumField.setNumber(new BigDecimal(instrumentation.getKettledrum()));
            _percussionField.setNumber(new BigDecimal(instrumentation.getPercussion()));
            _harpField.setNumber(new BigDecimal(instrumentation.getHarp()));

            for (SpecialInstrumentationEntity item : _specialInstrumentationEntityList) {
                removeSpecialInstrumentationItem(item);
            }

            if (instrumentation.getSpecialInstrumentation() != null) {
                List<KeyValuePair> sectionTypeList = MusicianTableHelper.getSectionTypeList();
                KeyValuePair keyValuePair;

                for (SpecialInstrumentation specialInstrumentation : instrumentation.getSpecialInstrumentation()) {
                    keyValuePair = null;

                    for (KeyValuePair pair : sectionTypeList) {
                        if (String.valueOf(pair.getValue()).equals(specialInstrumentation.getSectionType())) {
                            keyValuePair = pair;
                            break;
                        }
                    }

                    addSpecialInstrumentationItem(specialInstrumentation.getSpecialInstrumentationID(), keyValuePair,
                            specialInstrumentation.getSpecialInstrumentation(), specialInstrumentation.getSpecialInstrumentCount());
                }
            }
        }
    }
    private void addSpecialInstrumentationItem(int id, KeyValuePair sectionType, String specialInstrumentation, int specialInstrumentationCount) {
        GridPane tmpPane = new GridPane();

        ComboBox<KeyValuePair> tmpComboBox = new ComboBox<>(_specialInstrumentationComboBox.getItems());
        tmpComboBox.getSelectionModel().select(sectionType);
        tmpComboBox.setMaxWidth(80);
        tmpPane.addColumn(0, tmpComboBox);

        TextField tmpTextField = new TextField();
        tmpTextField.setMaxWidth(80);
        tmpTextField.setText(specialInstrumentation);
        tmpPane.addColumn(1, tmpTextField);

        NumberField tmpNumberField = null;
        try {
            tmpNumberField = new NumberField(specialInstrumentationCount, _specialInstrumentationNumberField.getMinValue().intValue(), _specialInstrumentationNumberField.getMaxValue().intValue());
            tmpPane.addColumn(2, tmpNumberField);
            tmpNumberField.setMaxWidth(60);
        } catch (NumberRangeException e) {
        }

        Button tmpButton = new Button("-");
        tmpPane.addColumn(3, tmpButton);

        _specialInstrumentationContent.addRow(_specialInstrumentationEntityList.size()+1, tmpPane);
        _specialInstrumentationContent.setColumnSpan(tmpPane, 4);
        SpecialInstrumentationEntity specialInstrumentationEntity = new SpecialInstrumentationEntity(id, tmpComboBox, tmpTextField, tmpNumberField, tmpPane);

        tmpButton.setOnAction(e -> removeSpecialInstrumentationItem(specialInstrumentationEntity));

        _specialInstrumentationEntityList.add(specialInstrumentationEntity);
    }

    private void removeSpecialInstrumentationItem(SpecialInstrumentationEntity specialInstrumentationEntity) {
        _specialInstrumentationContent.getChildren().remove(specialInstrumentationEntity.getPane());
        _specialInstrumentationEntityList.remove(specialInstrumentationEntity);
    }

    private void setInstrumentation(Instrumentation instrumentation) {
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

        List<SpecialInstrumentation> specialInstrumentationList = new LinkedList<>();
        SpecialInstrumentation specialInstrumentation;

        for(SpecialInstrumentationEntity item : _specialInstrumentationEntityList) {
            specialInstrumentation = new SpecialInstrumentation();
            specialInstrumentation.setSpecialInstrumentationID(item.getSpecialInstrumentationID());
            specialInstrumentation.setSectionType(String.valueOf(item.getSectionTypeComboBox().getSelectionModel().getSelectedItem()));
            specialInstrumentation.setSpecialInstrumentationCount(item.getSpecialInstrumentationNumberField().getNumber().intValue());
            specialInstrumentation.setSpecialInstrumentation(item.getSpecialInstrumentationTextField().getText());

            specialInstrumentationList.add(specialInstrumentation);
        }

        instrumentation.setSpecialInstrumentation(specialInstrumentationList);
    }
}
