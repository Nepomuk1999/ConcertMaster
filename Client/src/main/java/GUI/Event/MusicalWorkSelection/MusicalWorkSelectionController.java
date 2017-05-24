package GUI.Event.MusicalWorkSelection;

import Domain.Instrumentation.*;
import Domain.MusicalWork.MusicalWorkViewInterface;
import GUI.Event.CreateEvent.CreateEventAppController;
import GUI.Event.EditEvent.EditEventAppController;
import GUI.Event.UIEventHandler;
import UseCases.MusicalWork.MusicalWorkController;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Julian
 */
public class MusicalWorkSelectionController implements Initializable {
    private CreateEventAppController createEventAppController;
    private EditEventAppController editEventAppController;
    private UIEventHandler eventHandler;
    private Stage addMusicalWorkSelectionStage;
    @FXML
    private TableView<MusicalWorkViewInterface> musicalWorkTable;
    @FXML
    private TableColumn<MusicalWorkViewInterface, String> nameCol;
    @FXML
    private TableColumn<MusicalWorkViewInterface, String> composerCol;
    @FXML
    private TableColumn<MusicalWorkViewInterface, String> stringCol;
    @FXML
    private TableColumn<MusicalWorkViewInterface, String> brassCol;
    @FXML
    private TableColumn<MusicalWorkViewInterface, String> woodCol;
    @FXML
    private TableColumn<MusicalWorkViewInterface, String> percussionCol;
    @FXML
    private TableColumn<MusicalWorkViewInterface, String> otherCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventHandler = new UIEventHandler();
        setUpCellValueFactories();
        setUpChangeListeners();
        MusicalWorkController controller = new MusicalWorkController();
        List<MusicalWorkViewInterface> musicalWorksList = controller.getAllMusicalWorksAsView();
        musicalWorkTable.getItems().addAll(musicalWorksList);
    }

    private void setUpCellValueFactories() {
        nameCol.setCellValueFactory((e) -> {
            String string = e.getValue().getName();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        composerCol.setCellValueFactory((e) -> {
            String string = e.getValue().getComposer();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        stringCol.setCellValueFactory((e) -> {
            StringInstrumentationViewInterface stringInstrumentation = e.getValue().getInstrumentation().getStringInstrumentation();
            String string;
            if (stringInstrumentation != null) {
                string = stringInstrumentation.getViolin1() + "/" + stringInstrumentation.getViolin2() + "/" +
                        stringInstrumentation.getViola() + "/" + stringInstrumentation.getViolincello() + "/" + stringInstrumentation.getDoublebass();
            } else {
                string = null;
            }
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        brassCol.setCellValueFactory((e) -> {
            BrassInstrumentationViewInterface brassInstrumentation = e.getValue().getInstrumentation().getBrassInstrumentation();
            String string;
            if (brassInstrumentation != null) {
                string = brassInstrumentation.getHorn() + "/" + brassInstrumentation.getTrumpet() + "/" +
                        brassInstrumentation.getTrombone() + "/" + brassInstrumentation.getTube();
            } else {
                string = null;
            }
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        woodCol.setCellValueFactory((e) -> {
            WoodInstrumentationViewInterface woodInstrumentation = e.getValue().getInstrumentation().getWoodInstrumentation();
            String string;
            if (woodInstrumentation != null) {
                string = woodInstrumentation.getFlute() + "/" + woodInstrumentation.getOboe() + "/" +
                        woodInstrumentation.getClarinet() + "/" + woodInstrumentation.getBassoon();
            } else {
                string = null;
            }
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        percussionCol.setCellValueFactory((e) -> {
            PercussionInstrumentationViewInterface percussionInstrumentation = e.getValue().getInstrumentation().getPercussionInstrumentation();
            String string;
            if (percussionInstrumentation != null) {
                string = percussionInstrumentation.getKettledrum() + "/" + percussionInstrumentation.getPercussion() + "/" +
                        percussionInstrumentation.getHarp();
            } else {
                string = null;
            }
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        otherCol.setCellValueFactory((e) -> {
            List<SpecialInstrumentationViewInterface> specialInstrumentation = e.getValue().getInstrumentation().getSpecialInstrumentation();
            StringBuilder stringBuilder = new StringBuilder();
            String string = null;
            for (SpecialInstrumentationViewInterface i : specialInstrumentation) {
                stringBuilder.append(i.getSpecialInstrumentationNumber()).append(" ").append(i.getSpecialInstrument()).append("/");
            }
            if (stringBuilder.length() > 0) {
                string = stringBuilder.substring(0, stringBuilder.length() - 2);
            }
            return new SimpleStringProperty(string != null && stringBuilder.length() > 0 ? stringBuilder.toString() : "");
        });
    }

    private void setUpChangeListeners() {
        musicalWorkTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        musicalWorkTable.setRowFactory(tableView2 -> {
            final TableRow<MusicalWorkViewInterface> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                final int index = row.getIndex();
                if (index >= 0 && index < musicalWorkTable.getItems().size() && musicalWorkTable.getSelectionModel().isSelected(index)) {
                    musicalWorkTable.getSelectionModel().clearSelection(index);
                    event.consume();
                } else if (index >= 0 && index < musicalWorkTable.getItems().size() && !musicalWorkTable.getSelectionModel().isSelected(index)) {
                    musicalWorkTable.getSelectionModel().select(index);
                    event.consume();
                }
            });
            return row;
        });
    }

    void setMusicalWorkList(List<MusicalWorkViewInterface> musicalWorks) {
        for (MusicalWorkViewInterface musicalWork : musicalWorks) {
            for (MusicalWorkViewInterface mWork : musicalWorkTable.getItems()) {
                if (mWork.getId() == musicalWork.getId()) {
                    musicalWorkTable.getSelectionModel().select(mWork);
                }
            }
        }

    }

    void setCreateEventAppController(CreateEventAppController controller) {
        createEventAppController = controller;
    }

    void setEditEventAppController(EditEventAppController controller) {
        editEventAppController = controller;
    }

    public void setStage(Stage s) {
        addMusicalWorkSelectionStage = s;
    }

    @FXML
    private void abortAction() {
        eventHandler.displayAbortAlert(addMusicalWorkSelectionStage);
    }

    @FXML
    public void addWorks() {
        if (createEventAppController != null) {
            createEventAppController.setMusicalWorksList(musicalWorkTable.getSelectionModel().getSelectedItems());
        } else if (editEventAppController != null) {
            editEventAppController.setMusicalWorksList(musicalWorkTable.getSelectionModel().getSelectedItems());
        }
        addMusicalWorkSelectionStage.close();
    }
}
