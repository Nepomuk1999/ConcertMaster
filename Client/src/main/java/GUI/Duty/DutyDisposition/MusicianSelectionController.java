package GUI.Duty.DutyDisposition;

import Domain.Event.EventViewInterface;
import Domain.Person.PersonViewInterface;
import GUI.Duty.DutyController;
import GUI.Event.UIEventHandler;
import UseCases.Duty.DutyUseCaseController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Julian
 */
public class MusicianSelectionController implements Initializable {
    private Stage musicianSelectionStage;
    private int maxMusicians;
    private boolean isEdit;
    private DutyController parentController;
    private EventViewInterface event;
    private List<PersonViewInterface> musicianList;
    private List<PersonViewInterface> oldMusicians;
    private String section;
    private String instrument;
    private String spareInstrument;
    private boolean isSpare;
    private DutyUseCaseController useCaseController;
    private UIEventHandler uiEventHandler;
    @FXML
    private TableView<PersonViewInterface> musicianTable;
    @FXML
    private TableColumn<PersonViewInterface, String> fNameColumn;
    @FXML
    private TableColumn<PersonViewInterface, String> lNameColumn;
    @FXML
    private TableColumn<PersonViewInterface, String> pointsColumn;
    @FXML
    private Spinner<Integer> points;
    @FXML
    private TextArea description;
    @FXML
    private Label musiciansHint;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        uiEventHandler = new UIEventHandler();
        setUpCellValueFactories();
        setUpChangeListener();
    }

    private void setUpCellValueFactories() {
        fNameColumn.setCellValueFactory((e) -> {
            String string = e.getValue().getFirstName();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        lNameColumn.setCellValueFactory((e) -> {
            String string = e.getValue().getLastName();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        pointsColumn.setCellValueFactory((e) -> {
            Double points = useCaseController.getPointsForMusicianAndDate(e.getValue(), event);
            String string = Double.toString(points);
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "0");
        });
    }

    private void setUpChangeListener() {
        musicianTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        musicianTable.setRowFactory(tableView2 -> {
            final TableRow<PersonViewInterface> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && index < musicianTable.getItems().size() && musicianTable.getSelectionModel().isSelected(index)) {
                    musicianTable.getSelectionModel().clearSelection(index);
                    event.consume();
                } else if (index >= 0 && index < musicianTable.getItems().size() && !musicianTable.getSelectionModel().isSelected(index)) {
                    if ((musicianTable.getSelectionModel().getSelectedItems().size() + 1) > maxMusicians) {
                        uiEventHandler.handleWarning("You already have selected the required number of musicians. To select another musician please deselect a musician first.");
                        event.consume();
                    } else {
                        musicianTable.getSelectionModel().select(index);
                        event.consume();
                    }
                }
            });
            return row;
        });
    }

    void setMusicianListAndController(List<PersonViewInterface> musicianList) {
        this.musicianList = musicianList;
        useCaseController = new DutyUseCaseController(musicianList);
    }

    void setMaxMusicians(int maxMusicians) {
        this.maxMusicians = maxMusicians;
        musiciansHint.setText("You can select up to " + maxMusicians + " musicians.");
    }

    void setMusicianSelectionStage(Stage musicianSelectionStage) {
        this.musicianSelectionStage = musicianSelectionStage;
    }

    public void setEvent(EventViewInterface event) {
        this.event = event;
        points.getEditor().setText(Integer.toString(Math.round(event.getDefaultPoints())));
    }

    void setSpareInstrument(String spareInstrument) {
        this.spareInstrument = spareInstrument;
    }

    void setSection(String section) {
        this.section = section;
    }

    void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    void setParentController(DutyController parentController) {
        this.parentController = parentController;
    }

    void initializeTable() {
        if(instrument.equals("Spare Musician")){
            isSpare = true;
            musicianTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            ObservableList<PersonViewInterface> musicians = FXCollections.observableArrayList();
            switch(section){
                case "String":
                    musicians.addAll(useCaseController.getAvailableMusicians(event, spareInstrument));
                    break;
                case "Wood":
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Bassoon"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Clarinet"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Flute"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Oboe"));
                    break;
                case "Brass":
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Horn"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Trombone"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Trumpet"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Tube"));
                    break;
                case "Percussion":
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Harp"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Kettledrum"));
                    musicians.addAll(useCaseController.getAvailableMusicians(event, "Percussion"));
                    break;
            }
            musicianTable.getItems().addAll(musicians);
        }else {
            isSpare = false;
            musicianTable.getItems().addAll(useCaseController.getAvailableMusicians(event, instrument));
        }
        musicianTable.getSortOrder().add(pointsColumn);
    }

    void setMusicians(List<PersonViewInterface> musicians) {
        isEdit = true;
        oldMusicians = musicians;
        for (PersonViewInterface musician : musicians) {
            musicianTable.getItems().add(musician);
            musicianTable.getSelectionModel().select(musician);
        }
    }

    public void saveSelection() {
        int pointValue;
        if ((pointValue = checkPoints()) != -1) {
            String desc = description.getText();
            if (desc.equals("")) {
                desc = null;
            }
            ObservableList<PersonViewInterface> musicians = musicianTable.getSelectionModel().getSelectedItems();
            if(isEdit){
                if(isSpare){
                    useCaseController.editSpareDuties(event, oldMusicians, musicians, desc, pointValue);
                    parentController.editSpareMusicians(musicians.get(0));
                }else{
                    useCaseController.editDuties(event, oldMusicians, musicians, desc, pointValue);
                    parentController.editMusicians(musicians);
                }
            }else{
                if(isSpare){
                    useCaseController.createSpareDuty(event, musicians, desc, pointValue);
                    parentController.addSpareMusicians(musicians.get(0));
                }else{
                    useCaseController.createDuty(event, musicians, desc, pointValue);
                    parentController.addMusicians(musicians);
                }
            }
            musicianSelectionStage.close();
        }
    }

    private int checkPoints() {
        Integer value;
        try {
            value = Integer.valueOf(points.getEditor().getText());
        } catch (NumberFormatException e) {
            return uiEventHandler.displayPointErrorAlert();
        }
        if ((value != null) && (points.getValue() != null)) {
            if (!points.getValue().equals(value)) {
                return value;
            } else {
                return points.getValue();
            }
        }
        return uiEventHandler.displayPointErrorAlert();
    }

    public void cancelSelection() {
        if (musicianTable.getSelectionModel().getSelectedItems().size() == 0) {
            musicianSelectionStage.close();
        } else if (uiEventHandler.displayAbortAlert(musicianSelectionStage)) {
            musicianSelectionStage.close();
        }
    }
}
