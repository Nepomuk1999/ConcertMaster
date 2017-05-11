package team_f.client.pages.musicalwork;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import team_f.client.converter.MusicalWorkConverter;
import team_f.client.helper.ErrorMessageHelper;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.MusicalWorkErrorList;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

import java.util.ArrayList;
import java.util.List;

public class MusicalWorkManagement extends BaseTablePage<MusicalWorkErrorList, MusicalWork, MusicalWork, MusicalWorkParameter> {
    private TextField _nameField;
    private TextField _composerField;
    private TableView<MusicalWork> _table;

    //String
    private TextField _firstViolinField;
    private TextField _secondViolinField;
    private TextField _violaField;
    private TextField _violoncelloField;
    private TextField _doublebassField;

    //Wood
    private TextField _fluteField;
    private TextField _oboeField;
    private TextField _clarinetField;
    private TextField _bassoonField;

    //Brass
    private TextField _hornField;
    private TextField _trumpetField;
    private TextField _tromboneField;
    private TextField _tubeField;

    //Percussion
    private TextField _kettledrumField;
    private TextField _percussionField;
    private TextField _harpField;

    //SpecialInstrumentation
    private TextField _specialInstrumentation;

    public MusicalWorkManagement(){
    }

    @Override
    public void initialize() {
        if(_initialize != null) {
            _initialize.doAction(null);
        }

        _nameField = new TextField();
        _composerField = new TextField();

        _firstViolinField = new TextField();
        _firstViolinField.setMaxWidth(40.0);
        _secondViolinField = new TextField();
        _secondViolinField.setMaxWidth(40.0);
        _violaField = new TextField();
        _violaField.setMaxWidth(40.0);
        _violoncelloField = new TextField();
        _violoncelloField.setMaxWidth(40.0);
        _doublebassField = new TextField();
        _doublebassField.setMaxWidth(40.0);


        _fluteField = new TextField();
        _fluteField.setMaxWidth(40.0);
        _oboeField = new TextField();
        _oboeField.setMaxWidth(40.0);
        _clarinetField = new TextField();
        _clarinetField.setMaxWidth(40.0);
        _bassoonField = new TextField();
        _bassoonField.setMaxWidth(40.0);

        _hornField = new TextField();
        _hornField.setMaxWidth(40.0);
        _trumpetField = new TextField();
        _trumpetField.setMaxWidth(40.0);
        _tromboneField = new TextField();
        _tromboneField.setMaxWidth(40.0);
        _tubeField = new TextField();
        _tubeField.setMaxWidth(40.0);

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
        BorderPane borderPane=new BorderPane();
        borderPane.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

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

        Scale scaleDefault = new Scale(0.82,1);
        scaleDefault.setPivotX(0);
        scaleDefault.setPivotY(0);
        borderPane.getTransforms().setAll(scaleDefault);

        mySlider.valueProperty().addListener((arg0, arg1, arg2) -> {
            Scale scale = new Scale(arg2.doubleValue()-0.25, arg2.doubleValue()-0.075);
            scale.setPivotX(0);
            scale.setPivotY(0);
            borderPane.getTransforms().setAll(scale);
        });

        VBox zoomTool = new VBox();
        zoomTool.setStyle("-fx-padding: 10;");
        zoomTool.getChildren().addAll(new Label("Zoom"),mySlider);
        setTop(zoomTool);
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
        _table.getColumns().addAll(MusicalWorkHelper.getIdColumn(), MusicalWorkHelper.getMusicalWorkNameColumn(),
                MusicalWorkHelper.getComposerColumn(), MusicalWorkHelper.getInstrumentationColumn());
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

    public void addMusicalWork() {
        if(_create != null) {
            MusicalWork musicalWork = new MusicalWork();
            musicalWork.setName(_nameField.getText());
            musicalWork.setComposer(_composerField.getText());
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

            musicalWork.setInstrumentation(instrumentation);

            MusicalWorkErrorList resultMusicalWorkErrorList = _create.doAction(musicalWork);

            if (resultMusicalWorkErrorList != null && resultMusicalWorkErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = MusicalWorkConverter.getAbstractList(resultMusicalWorkErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if(tmpErrorText.isEmpty() && resultMusicalWorkErrorList.getKeyValueList().size() == 1 && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey() != null && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey().getMusicalWorkID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _table.getItems().add(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
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

    public void deleteMusicalWork() {
        if(_delete != null) {
            MusicalWork musicalWork = new MusicalWork();

            MusicalWorkErrorList resultMusicalWorkErrorList = _create.doAction(musicalWork);

            if (resultMusicalWorkErrorList != null && resultMusicalWorkErrorList.getKeyValueList() != null) {
                List<Pair<JSONObjectEntity, List<Error>>> errorList = MusicalWorkConverter.getAbstractList(resultMusicalWorkErrorList.getKeyValueList());
                String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                if(tmpErrorText.isEmpty() && resultMusicalWorkErrorList.getKeyValueList().size() == 1 && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey() != null && resultMusicalWorkErrorList.getKeyValueList().get(0).getKey().getMusicalWorkID() > 0) {
                    showSuccessMessage("Successful", tmpErrorText);

                    _table.getItems().remove(resultMusicalWorkErrorList.getKeyValueList().get(0).getKey());
                    update();
                } else {
                    showErrorMessage("Error", tmpErrorText);
                }
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }

    private GridPane getNewMusicalWorkDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);

        pane.getColumnConstraints().addAll(new ColumnConstraints(130), new ColumnConstraints(130), new ColumnConstraints(130),
                new ColumnConstraints(130),new ColumnConstraints(130),new ColumnConstraints(130),new ColumnConstraints(130),new ColumnConstraints(130),new ColumnConstraints(130));

        Label titleMusicalWork = new Label("MusicalWork");
        titleMusicalWork.setStyle(" -fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        pane.addRow(0,titleMusicalWork);
        pane.addRow(1, new Label("Name"), _nameField);
        pane.addRow(1, new Label("Composer:"), _composerField);


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


        ArrayList<TextField> fields = new ArrayList<>();
        fields.add(_nameField);
        fields.add(_composerField);

        fields.add(_firstViolinField);
        fields.add(_secondViolinField);
        fields.add(_violaField);
        fields.add(_violoncelloField);
        fields.add(_doublebassField);

        fields.add(_fluteField);
        fields.add(_oboeField);
        fields.add(_clarinetField);
        fields.add(_bassoonField);

        fields.add(_hornField);
        fields.add(_trumpetField);
        fields.add(_tromboneField);
        fields.add(_tubeField);

        fields.add(_kettledrumField);
        fields.add(_percussionField);
        fields.add(_harpField);


        Button addButton = new Button("Add MusicalWork");
        addButton.setMinWidth(130);

        addButton.setOnAction(e -> {
            if (_nameField.getText().isEmpty() || _composerField.getText().isEmpty() ||
                    _firstViolinField.getText().isEmpty() || _secondViolinField.getText().isEmpty() || _violaField.getText().isEmpty() || _violoncelloField.getText().isEmpty() ||
                    _doublebassField.getText().isEmpty() || _fluteField.getText().isEmpty() || _oboeField.getText().isEmpty() || _clarinetField.getText().isEmpty() ||
                    _bassoonField.getText().isEmpty() || _hornField.getText().isEmpty() || _trumpetField.getText().isEmpty() || _tromboneField.getText().isEmpty() || _tubeField.getText().isEmpty()
                    || _kettledrumField.getText().isEmpty() || _percussionField.getText().isEmpty() || _harpField.getText().isEmpty()) {
                validate(fields);

                showValuesMissingMessage();
            } else {
                addMusicalWork();
            }
        });


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
            MusicalWorkParameter musicalWorkParameter = new MusicalWorkParameter();
            List<MusicalWork> musicalWorkList = _loadList.doAction(musicalWorkParameter);

            if(musicalWorkList != null) {
                _table.setItems(FXCollections.observableList(musicalWorkList));
                update();
            }
        }
    }

    private void reset() {
        _nameField.setText(null);
        _composerField.setText(null);
    }
}
