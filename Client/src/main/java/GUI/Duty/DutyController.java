package GUI.Duty;

import Domain.Duty.DutyViewInterface;
import Domain.Event.EventViewInterface;
import Domain.Instrumentation.InstrumentationViewInterface;
import Domain.Person.PersonViewInterface;
import Enums.DutyDispositionStatus;
import Enums.EventType;
import Enums.SectionType;
import GUI.CosController;
import GUI.Duty.DutyDisposition.MusicianSelectionApp;
import UseCases.Duty.DutyUseCaseController;
import UseCases.Event.EventUseCaseController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Drazen
 */
public class DutyController implements Initializable {
    private Stage stage;
    private EventUseCaseController eventUseCaseController;
    private DutyUseCaseController dutyUseCaseController;
    private EventViewInterface eventView;
    private YearMonth selected;

    //top-left table with the events
    @FXML
    private ChoiceBox<YearMonth> monthChoiceBox;
    @FXML
    private TableView<EventViewInterface> eventTable;
    @FXML
    private TableColumn<EventViewInterface, String> dutyNameCol;
    @FXML
    private TableColumn<EventViewInterface, String> dutyStartCol;
    @FXML
    private TableColumn<EventViewInterface, String> dutyEndCol;
    @FXML
    private TableColumn<EventViewInterface, String> dutyTypeCol;

    //top-right detail view
    @FXML
    private Label eventLabel;
    @FXML
    private Label eventValue;
    @FXML
    private Label startLabel;
    @FXML
    private Label startValue;
    @FXML
    private Label endLabel;
    @FXML
    private Label endValue;

    //bottom Table with Duties
    @FXML
    private TableView<DutyTableObject> dutyTableMusicians;
    private String[] sectionNames;
    private String[] instrumentationLabels;
    private int[] instrumentationValues;
    private DutyTableObject selectedDuty;
    private String instrument;

    @FXML
    private Button selectMusician;
    @FXML
    private Button editMusicians;

    private String section;
    private List<PersonViewInterface> allMusicians;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage = CosController.getPrimaryStage();
        eventUseCaseController = new EventUseCaseController();
        dutyUseCaseController = new DutyUseCaseController();
        setUpDutyCellValuesFactories();
        setUpListener();
        Alert alert = new Alert(Alert.AlertType.NONE);
        ButtonType wood = new ButtonType("Wood");
        ButtonType string = new ButtonType("String");
        ButtonType brass = new ButtonType("Brass");
        ButtonType percussion = new ButtonType("Percussion");
        alert.getButtonTypes().addAll(wood, string, brass, percussion);
        alert.setContentText("Which section?");
        alert.showAndWait();
        section = alert.getResult().getText();
        setUpChoiceBox();
        allMusicians = dutyUseCaseController.getAllMusicians();
    }

    private void setUpDutyCellValuesFactories() {
        dutyNameCol.setCellValueFactory((e) -> {
            String string = e.getValue().getName();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });

        dutyStartCol.setCellValueFactory((e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM yy HH:mm");
            String string = LocalDateTime.of(e.getValue().getStartDate(), e.getValue().getStartTime()).format(formatter);
            return new SimpleStringProperty(string.length() > 0 ? string : "");
        });
        dutyEndCol.setCellValueFactory((e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM yy HH:mm");
            String string = LocalDateTime.of(e.getValue().getEndDate(), e.getValue().getEndTime()).format(formatter);
            return new SimpleStringProperty(string.length() > 0 ? string : "");
        });
        dutyTypeCol.setCellValueFactory((e) -> {
            EventType type = e.getValue().getEventType();
            String string;
            if (type != null) {
                string = type.name();
            } else {
                string = null;
            }
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
    }

    private void setUpListener() {

        eventTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            eventView = newValue;
            if (eventView != null) {
                createDutyScheduleDetailView();
                initGenericTableForMusicians();
            } else {
                eventLabel.setVisible(false);
                startLabel.setVisible(false);
                endLabel.setVisible(false);
            }
        });

        dutyTableMusicians.getSelectionModel().getSelectedCells().addListener((ListChangeListener<TablePosition>) c -> {
            selectedDuty = dutyTableMusicians.getSelectionModel().getSelectedItem();
            if (dutyTableMusicians.getSelectionModel().getSelectedCells().size() > 0) {
                int column = dutyTableMusicians.getColumns().indexOf(dutyTableMusicians.getSelectionModel().getSelectedCells().get(0).getTableColumn());
                if (selectedDuty != null){
                    if((column > 1) && ((column <= (selectedDuty.getInstrumentAmount() + 1)) || (section.equals("String") && column == (dutyTableMusicians.getColumns().size() - 1)))) {
                        if ((selectedDuty.getMusicians() == null) || ((column - 2) >= selectedDuty.getMusicians().size())) {
                            selectMusician.setDisable(false);
                            instrument = selectedDuty.getInstrumentLabel();
                            if((section.equals("String") && column == (dutyTableMusicians.getColumns().size() - 1))){
                                instrument = "Spare Musician";
                            }
                            editMusicians.setDisable(true);
                            return;
                        } else {
                            selectMusician.setDisable(true);
                            instrument = selectedDuty.getInstrumentLabel();
                            if((section.equals("String") && column == (dutyTableMusicians.getColumns().size() - 1))){
                                instrument = "Spare Musician";
                            }
                            editMusicians.setDisable(false);
                            return;
                        }
                    }
                }
            }
            selectMusician.setDisable(true);
            editMusicians.setDisable(true);
        });
        monthChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selected = newValue;
            dutyTableMusicians.getItems().removeAll(dutyTableMusicians.getItems());
            eventTable.getItems().removeAll(eventTable.getItems());
            eventTable.refresh();
            eventTable.getItems().addAll(eventUseCaseController.getUnpublishedEventsForMonth(selected));
        });
    }

    private void setUpChoiceBox() {
        YearMonth yearMonth = YearMonth.now().plusMonths(1);
        ObservableList<YearMonth> list = FXCollections.observableArrayList();
        for (int count = 1; count < 36; count++) {
            list.add(yearMonth);
            yearMonth = yearMonth.plusMonths(1);
        }
        ObservableList<YearMonth> months = FXCollections.observableArrayList();
        List<YearMonth> unpublishedMonths = new LinkedList<>();
        switch (section) {
            case "String":
                unpublishedMonths.addAll(dutyUseCaseController.getUnpublishedDutyMonths(SectionType.Doublebass, SectionType.Viola, SectionType.Violin1, SectionType.Violin2, SectionType.Violincello));
                break;
            case "Brass":
                unpublishedMonths.addAll(dutyUseCaseController.getUnpublishedDutyMonths(SectionType.Brass));
                break;
            case "Wood":
                unpublishedMonths.addAll(dutyUseCaseController.getUnpublishedDutyMonths(SectionType.Woodwind));
                break;
            case "Percussion":
                unpublishedMonths.addAll(dutyUseCaseController.getUnpublishedDutyMonths(SectionType.Percussion));
                break;
        }
        months.addAll(unpublishedMonths);
        monthChoiceBox.setItems(months);
    }

    private void initGenericTableForMusicians() {
        dutyTableMusicians.getColumns().removeAll(dutyTableMusicians.getColumns());
        dutyTableMusicians.getItems().removeAll(dutyTableMusicians.getItems());
        InstrumentationViewInterface instrumentation = eventView.getGeneralInstrumentation();
        if (instrumentation == null) {
            return;
        }
        List<DutyViewInterface> dutyDispositions = eventView.getDuties();
        int maxCols = 0;
        switch (section) {
            case "Brass":
                maxCols = generateBrassRows(instrumentation);
                break;
            case "Wood":
                maxCols = generateWoodRows(instrumentation);
                break;
            case "String":
                maxCols = generateStringRows(instrumentation);
                break;
            case "Percussion":
                maxCols = generatePercussionInstrumentation(instrumentation);
                break;
        }
        dutyTableMusicians.setEditable(false);
        dutyTableMusicians.getSelectionModel().setCellSelectionEnabled(true);
        TableColumn<DutyTableObject, String> sectionColumn = new TableColumn<>("Section");
        sectionColumn.setCellValueFactory(e -> {
            String string = e.getValue().getSection();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        sectionColumn.setSortable(false);
        sectionColumn.setStyle("-fx-selection-bar: none");
        TableColumn<DutyTableObject, String> instrumentationColumn = new TableColumn<>("Instrument");
        instrumentationColumn.setCellValueFactory(e -> {
            String string = e.getValue().getInstrumentLabel();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        instrumentationColumn.setSortable(false);
        instrumentationColumn.setStyle("-fx-selection-bar: none");
        ObservableList<TableColumn<DutyTableObject, String>> musicianColumns = FXCollections.observableArrayList();
        Callback<TableColumn<DutyTableObject, String>, TableCell<DutyTableObject, String>> factory = new Callback<TableColumn<DutyTableObject, String>, TableCell<DutyTableObject, String>>() {

            @Override
            public TableCell<DutyTableObject, String> call(TableColumn<DutyTableObject, String> param) {
                return new TableCell<DutyTableObject, String>() {

                    private final int columnIndex = param.getTableView().getColumns().indexOf(param);

                    @Override
                    public void updateIndex(int i) {
                        super.updateIndex(i);
                        // select color based on index of row/column
                        if ((i >= 0) && (i < this.getTableView().getItems().size())) {
                            DutyTableObject item = ((DutyTableObject) this.getTableRow().getItem());
                            if (item != null) {
                                int instrumentAmount = item.getInstrumentAmount();
                                if (((instrumentAmount + 1) < columnIndex) && !((columnIndex == (dutyTableMusicians.getColumns().size() - 1)) && (section.equals("String")))) {
                                    this.setStyle("-fx-background-color: #949494; -fx-selection-bar: none; -fx-border-color: #949494");
                                }
                            }
                        }
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        // assign item's toString value as text
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }

                };
            }
        };
        //add columns for musicians (as much as needed).
        for (int i = 0; i < maxCols; i++) {
            TableColumn<DutyTableObject, String> musician = new TableColumn<>("Musician " + (i + 1));
            musician.setCellValueFactory(e -> {
                String string = null;
                int index = (dutyTableMusicians.getColumns().indexOf(e.getTableColumn()) - 2);
                if ((e.getValue() != null) && (e.getValue().getMusicians() != null) && (e.getValue().getMusicians().size() > index)) {
                    string = e.getValue().getMusicians().get(index).getFirstName() + " " +
                            e.getValue().getMusicians().get(index).getLastName();
                }
                return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
            });
            musician.setSortable(false);
            musician.setCellFactory(factory);
            musicianColumns.add(musician);
        }
        dutyTableMusicians.getColumns().add(sectionColumn);
        dutyTableMusicians.getColumns().add(instrumentationColumn);
        dutyTableMusicians.getColumns().addAll(musicianColumns);
        if (section.equals("String")) {
            TableColumn<DutyTableObject, String> spareMusician = new TableColumn<>("Spare Musician");
            spareMusician.setCellValueFactory(e -> {
                String string = null;
                if (e.getValue().getSpareMusician() != null) {
                    string = e.getValue().getSpareMusician().getFirstName() + " " +
                            e.getValue().getSpareMusician().getLastName();
                }
                return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
            });
            spareMusician.setSortable(false);
            spareMusician.setCellFactory(factory);
            dutyTableMusicians.getColumns().add(spareMusician);
        }
        ObservableList<DutyTableObject> rows = FXCollections.observableArrayList();
        boolean endReached = false;
        for (int counter = 0; counter < instrumentationValues.length && !endReached; counter++) {
            DutyTableObject current = new DutyTableObject();
            current.setInstrumentAmount(instrumentationValues[counter]);
            current.setInstrumentLabel(instrumentationLabels[counter]);
            current.setSection(sectionNames[counter]);
            if ((dutyDispositions != null) && (dutyDispositions.size() > 0)) {
                ObservableList<PersonViewInterface> musicians = FXCollections.observableArrayList();
                for (DutyViewInterface duty : dutyDispositions) {
                    if (duty.getStatus().equals(DutyDispositionStatus.Normal) && (duty.getMusician().getParts().get(0).getPartType().getDescription().equals(current.getInstrumentLabel()))) {
                        musicians.add(duty.getMusician());
                    /*}else if (current.duty.getStatus().equals(DutyDispositionStatus.Spare) && (!section.equals("String"))) {
                        SectionType type = duty.getMusician().getParts().get(0).getSectionType();
                        if((type.equals(SectionType.Woodwind) && section.equals("Wood")) || (type.equals(SectionType.Brass) && section.equals("Brass")) || (type.equals(SectionType.Percussion) && section.equals("Percussion"))){
                            musicians.add(duty.getMusician());
                        }*/
                    }
                }
                for(PersonViewInterface person : musicians) {
                    current.addMusician(person);
                }
            }
            if ((counter + 1 < instrumentationValues.length) && (instrumentationValues[counter + 1] == 0)) {
                endReached = true;
            }
            rows.add(current);
        }
        if(!section.equals("String")) {
            DutyTableObject current = new DutyTableObject();
            current.setInstrumentAmount(1);
            current.setSection("");
            current.setInstrumentLabel("Spare Musician");
            if ((dutyDispositions != null) && (dutyDispositions.size() > 0)) {
                ObservableList<PersonViewInterface> spareMusicians = FXCollections.observableArrayList();
                for (DutyViewInterface duty : dutyDispositions) {
                    String tempSection = null;
                    switch (duty.getMusician().getParts().get(0).getSectionType()) {
                        case Brass:
                            tempSection = "Brass";
                            break;
                        case Woodwind:
                            tempSection = "Wood";
                            break;
                        case Percussion:
                            tempSection = "Percussion";
                            break;
                    }
                    if (duty.getStatus().equals(DutyDispositionStatus.Spare) && ((tempSection != null) && (tempSection.equals(section)))) {
                        spareMusicians.add(duty.getMusician());
                    }
                }
                for(PersonViewInterface person : spareMusicians) {
                    current.addMusician(person);
                }
            }
            rows.add(current);
        }
        dutyTableMusicians.getItems().addAll(rows);
    }

    private int generateStringRows(InstrumentationViewInterface instrumentation) {
        instrumentationValues = new int[5];
        instrumentationLabels = new String[5];
        sectionNames = new String[5];
        int count = 0;
        int currentInstrumentation = instrumentation.getStringInstrumentation().getDoublebass();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Double Bass";
            sectionNames[count] = "String";
            count++;
        }
        currentInstrumentation = instrumentation.getStringInstrumentation().getViola();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Viola";
            if (!Arrays.asList(sectionNames).contains("String")) {
                sectionNames[count] = "String";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getStringInstrumentation().getViolin1();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "First Violin";
            if (!Arrays.asList(sectionNames).contains("String")) {
                sectionNames[count] = "String";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getStringInstrumentation().getViolin2();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Second Violin";
            if (!Arrays.asList(sectionNames).contains("String")) {
                sectionNames[count] = "String";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getStringInstrumentation().getViolincello();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Violoncello";
            if (!Arrays.asList(sectionNames).contains("String")) {
                sectionNames[count] = "String";
            }
        }
        OptionalInt max = Arrays.stream(instrumentationValues).max();
        if (max.isPresent()) {
            return max.getAsInt();
        }
        return 0;
    }

    private int generateBrassRows(InstrumentationViewInterface instrumentation) {
        instrumentationValues = new int[4];
        instrumentationLabels = new String[4];
        sectionNames = new String[4];
        int count = 0;
        int currentInstrumentation = instrumentation.getBrassInstrumentation().getHorn();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Horn";
            sectionNames[count] = "Brass";
            count++;
        }
        currentInstrumentation = instrumentation.getBrassInstrumentation().getTrombone();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Trombone";
            if (count == 0) {
                sectionNames[count] = "Brass";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getBrassInstrumentation().getTrumpet();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Trumpet";
            if (count == 0) {
                sectionNames[count] = "Brass";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getBrassInstrumentation().getTube();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Tuba";
            if (count == 0) {
                sectionNames[count] = "Brass";
            }
        }
        OptionalInt max = Arrays.stream(instrumentationValues).max();
        if (max.isPresent()) {
            return max.getAsInt();
        }
        return 0;
    }

    private int generateWoodRows(InstrumentationViewInterface instrumentation) {
        instrumentationValues = new int[4];
        instrumentationLabels = new String[4];
        sectionNames = new String[4];
        int count = 0;
        int currentInstrumentation = instrumentation.getWoodInstrumentation().getBassoon();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Bassoon";
            sectionNames[count] = "Wood";
            count++;
        }
        currentInstrumentation = instrumentation.getWoodInstrumentation().getClarinet();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Clarinet";
            if (!Arrays.asList(sectionNames).contains("Wood")) {
                sectionNames[count] = "Wood";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getWoodInstrumentation().getFlute();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Flute";
            if (!Arrays.asList(sectionNames).contains("Wood")) {
                sectionNames[count] = "Wood";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getWoodInstrumentation().getOboe();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Oboe";
            if (!Arrays.asList(sectionNames).contains("Wood")) {
                sectionNames[count] = "Wood";
            }
        }
        OptionalInt max = Arrays.stream(instrumentationValues).max();
        if (max.isPresent()) {
            return max.getAsInt();
        }
        return 0;
    }

    private int generatePercussionInstrumentation(InstrumentationViewInterface instrumentation) {
        instrumentationValues = new int[4];
        instrumentationLabels = new String[4];
        sectionNames = new String[4];
        int count = 0;
        int currentInstrumentation = instrumentation.getPercussionInstrumentation().getHarp();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Harp";
            sectionNames[count] = "Percussion";
            count++;
        }
        currentInstrumentation = instrumentation.getPercussionInstrumentation().getKettledrum();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Kettledrum";
            if (!Arrays.asList(sectionNames).contains("Percussion")) {
                sectionNames[count] = "Percussion";
            }
            count++;
        }
        currentInstrumentation = instrumentation.getPercussionInstrumentation().getPercussion();
        if (currentInstrumentation != 0) {
            instrumentationValues[count] = currentInstrumentation;
            instrumentationLabels[count] = "Percussion";
            if (!Arrays.asList(sectionNames).contains("Percussion")) {
                sectionNames[count] = "Percussion";
            }
        }
        OptionalInt max = Arrays.stream(instrumentationValues).max();
        if (max.isPresent()) {
            return max.getAsInt();
        }
        return 0;
    }

    private void createDutyScheduleDetailView() {
        eventLabel.setVisible(true);
        startLabel.setVisible(true);
        endLabel.setVisible(true);
        eventValue.setText(eventView.getName());
        startValue.setText(LocalDateTime.of(eventView.getStartDate(), eventView.getStartTime()).format(DateTimeFormatter.ofPattern("dd. MMM yy hh:mm")));
        endValue.setText(LocalDateTime.of(eventView.getEndDate(), eventView.getEndTime()).format(DateTimeFormatter.ofPattern("dd. MMM yy hh:mm")));
    }

    @FXML
    public void selectMusician() {
        MusicianSelectionApp m = new MusicianSelectionApp();
        m.setMusicianList(allMusicians);
        m.setEvent(eventView);
        m.setSection(section);
        m.setType(instrument);
        m.setParentController(this);
        int required = selectedDuty.getInstrumentAmount();
        if (selectedDuty.getMusicians() != null) {
            required -= selectedDuty.getMusicians().size();
        }
        m.setMaxMusicians(required);
        if(section.equals("String") && (instrument.equals("Spare Musician"))){
            m.setMaxMusicians(1);
            m.setSpareInstrument(selectedDuty.getInstrumentLabel());
        }
        try {
            m.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void editMusicians() {
        MusicianSelectionApp m = new MusicianSelectionApp();
        m.setMusicianList(allMusicians);
        m.setEvent(eventView);
        m.setSection(section);
        m.setType(instrument);
        m.setMusicians(selectedDuty.getMusicians());
        m.setParentController(this);
        int required = selectedDuty.getInstrumentAmount();
        m.setMaxMusicians(required);
        if(section.equals("String") && (instrument.equals("Spare Musician"))){
            m.setSpareInstrument(selectedDuty.getInstrumentLabel());
            m.setMaxMusicians(1);
        }
        try {
            m.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMusicians(ObservableList<PersonViewInterface> person) {
        for(PersonViewInterface musician : person) {
            dutyTableMusicians.getSelectionModel().getSelectedItem().addMusician(musician);
        }
        dutyTableMusicians.refresh();
        selectMusician.setDisable(true);
        editMusicians.setDisable(false);
    }

    public void editMusicians(ObservableList<PersonViewInterface> person) {
        dutyTableMusicians.getSelectionModel().getSelectedItem().setMusicians(person);
        dutyTableMusicians.refresh();
        selectMusician.setDisable(true);
        editMusicians.setDisable(false);
    }

    public void addSpareMusicians(PersonViewInterface musician) {
        if(section.equals("String")) {
            dutyTableMusicians.getSelectionModel().getSelectedItem().changeSpare(musician);
        }else{
            dutyTableMusicians.getSelectionModel().getSelectedItem().addMusician(musician);
        }
        dutyTableMusicians.refresh();
        selectMusician.setDisable(true);
        editMusicians.setDisable(false);
    }

    public void editSpareMusicians(PersonViewInterface musician) {
        if(section.equals("String")) {
            dutyTableMusicians.getSelectionModel().getSelectedItem().changeSpare(musician);
        }else{
            dutyTableMusicians.getSelectionModel().getSelectedItem().addMusician(musician);
        }
        dutyTableMusicians.refresh();
        selectMusician.setDisable(true);
        editMusicians.setDisable(false);
    }
}
