package GUI.Event.CreateEvent;

import Domain.Event.EventViewInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;
import Enums.EventStatus;
import Enums.EventType;
import Exceptions.DateException;
import Exceptions.RequiredFieldMissingException;
import GUI.Event.EventController;
import GUI.Event.MusicalWorkSelection.MusicalWorksSelectionApp;
import GUI.Event.UIEventHandler;
import UseCases.Event.EventUseCaseController;
import UseCases.Event.EventValueContainer;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julian
 */
public class CreateEventAppController implements Initializable {
    private Stage createEventStage;
    private Stage parentStage;
    private UIEventHandler eventHandler;
    private EventController parentController;
    private EventUseCaseController eventUseCaseController;
    private EventViewInterface rehearsalFor;
    private List<MusicalWorkViewInterface> musicalWorksList;
    private EventType eventType;
    private boolean dataInserted;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label descriptionFor;
    @FXML
    private TextField name;
    @FXML
    private TextArea description;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXTimePicker startTime;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private JFXTimePicker endTime;
    @FXML
    private TextArea musicalWorks;
    @FXML
    private TextField conductor;
    @FXML
    private TextField location;
    @FXML
    private Spinner<Integer> points;
    @FXML
    private Button saveButton;
    @FXML
    private MenuButton saveMenuButton;
    @FXML
    private MenuItem saveWithoutRehearsalButton;
    @FXML
    private MenuItem saveWithRehearsalButton;
    @FXML
    private Button saveAndAddButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventHandler = new UIEventHandler();
        eventUseCaseController = new EventUseCaseController();
        setUpChangeListeners();
        if (saveMenuButton != null) {
            saveMenuButton.getItems().addAll(saveWithoutRehearsalButton, saveWithRehearsalButton);
        }
    }

    void setCreateEventStage(Stage s) {
        createEventStage = s;
    }

    void setParentStage(Stage s) {
        parentStage = s;
    }

    void setParentController(EventController con) {
        parentController = con;
    }

    void setEventType(EventType type) {
        eventType = type;
        if (type.equals(EventType.Hofkapelle)) {
            startDate.setValue(LocalDate.now().plusMonths(1).with(TemporalAdjusters.next(DayOfWeek.SUNDAY)));
            startTime.setValue(LocalTime.of(6, 0));
            endTime.setValue(LocalTime.of(9, 0));
        } else if (type.equals(EventType.Rehearsal)) {
            descriptionFor.setText("Rehearsal for Event: " + rehearsalFor.getName() +
                    " (" + rehearsalFor.getEventType().name() + ")\nDate and Time: " +
                    LocalDateTime.of(rehearsalFor.getStartDate(), rehearsalFor.getStartTime()).format(DateTimeFormatter.ofPattern("dd. MMM yy HH:mm"))
                    + " - " + LocalDateTime.of(rehearsalFor.getEndDate(), rehearsalFor.getEndTime()).format(DateTimeFormatter.ofPattern("dd. MMM yy HH:mm")));
            descriptionFor.setWrapText(true);
        }
    }

    private void setUpChangeListeners() {
        if (name != null) {
            name.textProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (description != null) {
            description.textProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (conductor != null) {
            conductor.textProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (location != null) {
            location.textProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (points != null) {
            points.valueProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (startTime != null) {
            startTime.valueProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (endTime != null) {
            endTime.valueProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (startDate != null) {
            startDate.valueProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
        if (endDate != null) {
            endDate.valueProperty().addListener((observable, oldValue, newValue) -> checkInserted());
        }
    }

    @FXML
    private void enableTabNav(KeyEvent event) {
        eventHandler.handleTabNav(event, gridPane, description);
    }

    @FXML
    private void abortAction() {
        if (dataInserted) {
            eventHandler.displayAbortAlert(createEventStage);
        } else {
            createEventStage.close();
        }
    }

    @FXML
    private EventValueContainer createMusicalEvent() {
        int pointsValue;
        if ((pointsValue = checkPoints()) != -1) {
            EventValueContainer e = eventUseCaseController.getEventInstance(eventType);
            e.setName(name.getText());
            e.setDescription(description.getText());
            e.setStartTime(startTime.getValue());
            e.setEndTime(endTime.getValue());
            e.setStartDate(startDate.getValue());
            e.setEndDate(startDate.getValue());
            e.setConductor(conductor.getText());
            e.setLocation(location.getText());
            e.setMusicalWorks(musicalWorksList);
            e.setDefaultPoints(pointsValue);
            return e;
        }
        return null;
    }

    @FXML
    public void createNMEvent() {
        int pointsValue;
        if ((pointsValue = checkPoints()) != -1) {
            EventValueContainer e = eventUseCaseController.getEventInstance(eventType);
            e.setName(name.getText());
            e.setDescription(description.getText());
            e.setStartTime(startTime.getValue());
            e.setEndTime(endTime.getValue());
            e.setStartDate(startDate.getValue());
            e.setEndDate(startDate.getValue());
            e.setLocation(location.getText());
            e.setDefaultPoints(pointsValue);
            handleCreateEvent(e);
        }
    }

    @FXML
    public EventValueContainer createTour() {
        EventValueContainer valueContainer = createMusicalEvent();
        if (valueContainer != null) {
            valueContainer.setEndDate(endDate.getValue());
        }
        return valueContainer;
    }

    @FXML
    private void createRehearsal() {
        int pointsValue;
        if ((pointsValue = checkPoints()) != -1) {
            EventValueContainer e = eventUseCaseController.getEventInstance(EventType.Rehearsal);
            e.setName(name.getText());
            e.setStartTime(startTime.getValue());
            e.setEndTime(endTime.getValue());
            e.setStartDate(startDate.getValue());
            e.setEndDate(startDate.getValue());
            e.setLocation(location.getText());
            e.setRehearsalFor(rehearsalFor);
            e.setDefaultPoints(pointsValue);
            handleCreateEvent(e);
        }
    }

    @FXML
    private void addAnotherRehearsal() {
        addRehearsal(rehearsalFor);
        createRehearsal();
    }

    @FXML
    public void addRehearsal(EventViewInterface event) {
        CreateRehearsalApp createRehearsalApp = new CreateRehearsalApp();
        createRehearsalApp.setEvent(rehearsalFor);
        createRehearsalApp.setParentController(parentController);
        try {
            createRehearsalApp.start(parentStage);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    private void handleErrors(String error) {
        eventHandler.handleErrors(error);
    }

    private void handleCreateEvent(EventValueContainer event) {
        try {
            event.setEventStatus(EventStatus.Unpublished);
            if (eventType.equals(EventType.Rehearsal)) {
                event.setMusicalWorks(rehearsalFor.getMusicalWorks());
                if (!eventUseCaseController.checkMusicians(event)) {
                    ButtonType save = new ButtonType("Save anyway");
                    ButtonType abort = new ButtonType("Abort");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "At the specified time there aren't enough musicians available. " +
                            "Do you want to save anyway?", save, abort);
                    alert.setHeaderText("An error occurred:");
                    alert.setTitle("Error");
                    alert.showAndWait();

                    if (alert.getResult().equals(abort)) {
                        alert.close();
                        return;
                    }
                    alert.close();
                }
            }
            EventViewInterface viewInterface = eventUseCaseController.handleCreateEvent(event);
            parentController.addEvent(viewInterface);
            createEventStage.close();
        } catch (RequiredFieldMissingException | DateException exception) {
            handleErrors(exception.getMessage());
        }
    }

    private EventViewInterface handleCreateMusicalEvent(EventValueContainer event) {
        try {
            event.setEventStatus(EventStatus.Unpublished);
            event.setMusicalWorks(musicalWorksList);
            if (!eventUseCaseController.checkMusicians(event)) {
                ButtonType save = new ButtonType("Save anyway");
                ButtonType abort = new ButtonType("Abort");
                Alert alert = new Alert(Alert.AlertType.WARNING, "At the specified time there aren't enough musicians available. " +
                        "Do you want to save anyway?", save, abort);
                alert.setTitle("Warning");
                alert.setHeaderText("Resource Warning");
                alert.showAndWait();

                if (alert.getResult().equals(abort)) {
                    alert.close();
                    return null;
                }
                alert.close();
            }
            EventViewInterface eventViewInterface;
            if ((musicalWorksList != null) && (musicalWorksList.size() != 0)) {
                event.setMusicalWorks(musicalWorksList);
                eventViewInterface = eventUseCaseController.handleCreateEvent(event);
                eventUseCaseController.saveMusicalWorksForEvent(eventViewInterface);
            } else {
                eventViewInterface = eventUseCaseController.handleCreateEvent(event);
            }
            createEventStage.close();
            parentController.addEvent(eventViewInterface);
            return eventViewInterface;
        } catch (RequiredFieldMissingException | DateException exception) {
            handleErrors(exception.getMessage());
            return null;
        }

    }

    @FXML
    public void saveWithRehearsal() {
        EventValueContainer event;
        if (eventType.equals(EventType.Tour)) {
            event = createTour();
        } else {
            event = createMusicalEvent();
        }
        EventViewInterface eventView = handleCreateMusicalEvent(event);
        if (eventView != null) {
            addRehearsal(eventView);
        }
    }

    @FXML
    public void saveWithoutRehearsal() {
        EventValueContainer event;
        switch (eventType) {
            case Tour:
                event = createTour();
                break;
            default:
                event = createMusicalEvent();
        }
        handleCreateMusicalEvent(event);
    }

    @FXML
    public void addWorks() {
        MusicalWorksSelectionApp selection = new MusicalWorksSelectionApp();
        selection.setCreateEventAppController(this);
        selection.setMusicalWorksList(musicalWorksList);
        try {
            selection.start(createEventStage);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void setMusicalWorksList(List<MusicalWorkViewInterface> list) {
        musicalWorksList = list;
        StringBuilder s = new StringBuilder();
        for (MusicalWorkViewInterface m : musicalWorksList) {
            s.append(m.getName()).append("\n");
        }
        musicalWorks.setText(s.toString());
    }

    private int checkPoints() {
        Integer value;
        if (points.getEditor().getText().equals("")) {
            points.getEditor().setText("0");
        }
        try {
            value = Integer.valueOf(points.getEditor().getText());
        } catch (NumberFormatException e) {
            return eventHandler.displayPointErrorAlert();
        }
        if ((value != null) && (points.getValue() != null)) {
            if (!points.getValue().equals(value)) {
                return value;
            } else {
                return points.getValue();
            }
        }
        return eventHandler.displayPointErrorAlert();
    }

    @FXML
    private void abortPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            abortAction();
        }
    }

    void setRehearsalFor(EventViewInterface e) {
        rehearsalFor = e;
    }

    private void checkInserted() {
        if (checkRequiredFields()) {
            if (saveButton != null) {
                saveButton.setDisable(false);
            } else if (saveMenuButton != null) {
                saveMenuButton.setDisable(false);
            }
            dataInserted = true;
        } else {
            if (saveButton != null) {
                saveButton.setDisable(true);
            } else if (saveMenuButton != null) {
                saveMenuButton.setDisable(true);
            }
            dataInserted = false;
        }
        if (eventType.equals(EventType.Rehearsal)) {
            if (saveButton != null) {
                saveAndAddButton.setDisable(saveButton.isDisable());
            }
        }
    }

    private boolean checkRequiredFields() {
        if (name.getText().equals("")) {
            return false;
        }
        if (startDate.getValue() == null) {
            return false;
        }
        if ((endDate != null) && (endDate.getValue() == null)) {
            return false;
        }
        if (startTime.getValue() == null) {
            return false;
        }
        if (endTime.getValue() == null) {
            return false;
        }
        return true;
    }
}