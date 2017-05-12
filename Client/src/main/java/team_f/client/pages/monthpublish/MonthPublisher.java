package team_f.client.pages.monthpublish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import team_f.client.converter.EventDutyConverter;
import team_f.client.helper.ErrorMessageHelper;
import team_f.client.pages.BaseTablePage;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.Error;
import team_f.jsonconnector.entities.special.errorlist.EventDutyErrorList;
import team_f.jsonconnector.enums.PublishType;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

public class MonthPublisher extends BaseTablePage<EventDutyErrorList, Publish, EventDuty, MonthPublishParameter> {
    private ObservableList<Month> _data;
    private ObservableList<Integer> _year;
    private int _selectedYear;
    private Month _selectedMonth;
    private VBox _root;
    private TableView<EventDuty> _table;
    private TextField _selectedPath;

    public MonthPublisher() {
    }

    @Override
    public void initialize() {
        if(_initialize != null) {
            _initialize.doAction(null);
        }
        final URL Style = ClassLoader.getSystemResource("style/stylesheetMonthPublisher.css");
        getStylesheets().add(Style.toString());
        _data = FXCollections.observableArrayList(
                new Month("January", 1),
                new Month("February", 2),
                new Month("March", 3),
                new Month("April", 4),
                new Month("May", 5),
                new Month("June", 6),
                new Month("July", 7),
                new Month("August", 8),
                new Month("September", 9),
                new Month("October", 10),
                new Month("November", 11),
                new Month("December", 12));

        _selectedPath = new TextField();
        _selectedPath.setMinWidth(250);
        _selectedPath.setEditable(false);

        _year = FXCollections.observableArrayList();
        LocalDateTime current = LocalDateTime.now();
        for (int i = -1; i < 6; i++) {
            _year.add(current.getYear() + i);
        }

        //Comboboxes
        ComboBox<Integer> comboBoxYear = new ComboBox<>(_year);
        comboBoxYear.setMinWidth(125);
        Label labelYear = new Label("Year:");
        labelYear.setMinWidth(125);
        comboBoxYear.getSelectionModel().select(1);
        _selectedYear = comboBoxYear.getSelectionModel().getSelectedItem().intValue();

        ComboBox<Month> comboBoxMonth = new ComboBox<>(_data);
        comboBoxMonth.setMinWidth(125);
        Label labelMonth = new Label("Month:");
        labelMonth.setMinWidth(125);
        comboBoxMonth.getSelectionModel().selectFirst();
        _selectedMonth = _data.get(LocalDateTime.now().getMonth().getValue() -1);
        comboBoxMonth.getSelectionModel().select(_selectedMonth);

        comboBoxYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxYear.hide();
                comboBoxYear.getSelectionModel().select(newValue);
                _selectedYear = newValue.intValue();

                loadList();
            }
        });

        comboBoxMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxMonth.hide();
                comboBoxMonth.getSelectionModel().select(newValue);
                _selectedMonth.setValue(newValue.getValue());

                loadList();
            }
        });

        _table = new TableView<>();
        _table.setEditable(false);
        _table.getColumns().addAll(MonthPublisherHelper.getIdColumn(), MonthPublisherHelper.getEventtypeColumn(), MonthPublisherHelper.getNameColumn(),
                MonthPublisherHelper.getStartdateColumn(), MonthPublisherHelper.getEnddateColumn(),
                MonthPublisherHelper.getConductorColumn(), MonthPublisherHelper.getLocationColumn(), MonthPublisherHelper.getDescriptionColumn(), MonthPublisherHelper.getPointsColumn(), MonthPublisherHelper.getEventstatusColumn());
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //Button
        Button publishButton = new Button("Publish Month");
        publishButton.setMinWidth(125);
        publishButton.setOnAction(event -> {
            publish();
        });

        Button unpublishButton = new Button("Unpublish Month");
        unpublishButton.setMinWidth(125);
        unpublishButton.setOnAction(event -> {
            unpublish();
        });

        Label directoryLabel = new Label("Selected Directory:");
        directoryLabel.setMinWidth(250);

        _selectedPath.setDisable(true);
        _selectedPath.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null&&newValue.matches(".*\\w.*")&&!newValue.isEmpty()){
                _selectedPath.setDisable(false);
            }else{
                _selectedPath.setDisable(true);
            }
        });

        Button pdfGeneratorButton = new Button("Convert Schedule to PDF");
        pdfGeneratorButton.setMinWidth(180);
        pdfGeneratorButton.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if(selectedFile != null) {
                _selectedPath.setText(selectedFile.getAbsolutePath());
                List<EventDuty> items = _table.getItems();
                String selectedValues = _selectedMonth + "/" + _selectedYear;
                try {
                    PublisherPDFGenerator main = new PublisherPDFGenerator(items, selectedValues, _selectedPath.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //Title Label
        Label titlePublisher=new Label("Publisher");
        titlePublisher.setId("titlePublisher");


        Label titlePdfConverter=new Label("PDF Converter");
        titlePdfConverter.setId("titlePdfConverter");

        //Event Pane
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.add(titlePublisher, 12, 1);
        pane.add(labelYear, 12, 2);
        pane.add(comboBoxYear, 12, 3);
        pane.add(labelMonth, 13, 2);
        pane.add(comboBoxMonth, 13, 3);
        pane.add(titlePdfConverter, 36, 1);
        pane.add(directoryLabel, 36, 2);
        pane.add(_selectedPath, 36, 3);
        pane.add(pdfGeneratorButton, 37, 3);

        HBox buttonsBox=new HBox(publishButton, unpublishButton);
        buttonsBox.setSpacing(10);

        _root = new VBox(25);
        _root.getChildren().addAll(pane,_table,buttonsBox);

        BorderPane borderPane=new BorderPane();
        borderPane.setId("borderPane");

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
            Scale scale = new Scale(newValue.doubleValue()+0.018, newValue.doubleValue()+0.018);
            scale.setPivotX(0);
            scale.setPivotY(0);
            borderPane.getTransforms().setAll(scale);

        });

        VBox zoomTool = new VBox();
        zoomTool.setId("zoomTool");
        zoomTool.getChildren().addAll(new Label("Zoom"),mySlider);
        setTop(zoomTool);
        borderPane.setCenter(_root);
        setCenter(borderPane);
    }

    @Override
    public void load() {
        if(_load != null) {
            //List<EventDuty> eventDuty = _load.doAction(null);
        }

        loadList();
    }

    @Override
    public void update() {
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

    private void publish() {
        Boolean warningResult = showWarningMessage("Are you sure you want to Publish the following month?\nYou are no longer able to edit the Events assigned to this month", _selectedMonth.getMonth() + " " + _selectedYear, "Publish");

        ProgressIndicator pi = new ProgressIndicator();
        pi.setMinSize(100,100);

        VBox box = new VBox(pi);
        box.setAlignment(Pos.TOP_CENTER);
        _root.getChildren().add(box);
        // @TODO: use a other method and a thread + runnable

        if (warningResult == null || warningResult == false) {
            _root.getChildren().remove(box);
            return;
        } else {
            if(_update != null) {
                Publish publish = new Publish();
                publish.setPublishType(PublishType.PUBLISH);
                publish.setMonth(_selectedMonth.getValue());
                publish.setYear(_selectedYear);

                EventDutyErrorList eventDutyErrorList = _update.doAction(publish);

                if (eventDutyErrorList != null && eventDutyErrorList.getKeyValueList() != null) {
                    List<Pair<JSONObjectEntity, List<Error>>> errorList = EventDutyConverter.getAbstractList(eventDutyErrorList.getKeyValueList());
                    String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                    if(tmpErrorText.isEmpty()) {
                        showSuccessMessage("Successfully published selected Month", _selectedMonth.getMonth() + " " + _selectedYear + "\n\n" + tmpErrorText);

                        // reload the table content
                        loadList();
                    } else {
                        showErrorMessage("Error during publishing", _selectedMonth.getMonth() + " " + _selectedYear + "\n\n" + tmpErrorText);
                    }
                } else {
                    showTryAgainLaterErrorMessage();
                }
            }
        }

        _root.getChildren().remove(box);
    }

    private void unpublish() {
        Boolean warningResult = showWarningMessage("Are you sure you want to Unpublish the following month?", _selectedMonth.getMonth() + " " + _selectedYear, "Unpublish");

        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox(pi);
        box.setAlignment(Pos.CENTER);
        _root.getChildren().add(box);
        // @TODO: use a other method and a thread + runnable

        if (warningResult == null || warningResult == false) {
            _root.getChildren().remove(box);
            return;
        } else {
            if(_update != null) {
                Publish publish = new Publish();
                publish.setPublishType(PublishType.UNPUBLISH);
                publish.setMonth(_selectedMonth.getValue());
                publish.setYear(_selectedYear);

                EventDutyErrorList eventDutyErrorList = _update.doAction(publish);

                if (eventDutyErrorList != null && eventDutyErrorList.getKeyValueList() != null) {
                    List<Pair<JSONObjectEntity, List<Error>>> errorList = EventDutyConverter.getAbstractList(eventDutyErrorList.getKeyValueList());
                    String tmpErrorText = ErrorMessageHelper.getErrorMessage(errorList);

                    if(tmpErrorText.isEmpty()) {
                        showSuccessMessage("Successfully unpublished selected Month", _selectedMonth.getMonth() + " " + _selectedYear + "\n\n" + tmpErrorText);

                        // reload the table content
                        loadList();
                    } else {
                        showErrorMessage("Error during unpublishing", _selectedMonth.getMonth() + " " + _selectedYear + "\n\n" + tmpErrorText);
                    }
                } else {
                    showTryAgainLaterErrorMessage();
                }
            }
        }

        _root.getChildren().remove(box);
    }

    private void loadList() {
        if(_loadList != null) {
            MonthPublishParameter eventDutySearchItem = new MonthPublishParameter();
            eventDutySearchItem.setMonth(_selectedMonth.getValue());
            eventDutySearchItem.setYear(_selectedYear);
            List<EventDuty> eventDuties = _loadList.doAction(eventDutySearchItem);

            if(eventDuties != null) {
                _table.setItems(FXCollections.observableList(eventDuties));
            } else {
                showTryAgainLaterErrorMessage();
            }
        }
    }
}