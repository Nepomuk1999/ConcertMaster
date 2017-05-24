package GUI.Event.EditEvent;

import Domain.Event.EventViewInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julian
 */
public class EditEventAppController implements Initializable {
    private Stage editEventStage;
    private EventViewInterface eventView;
    private EventValueContainer event;
    private EventUseCaseController eventUseCaseController;
    private UIEventHandler eventHandler;
    private EventController controller;
    private List<MusicalWorkViewInterface> musicalWorksList;
    private boolean musicalWorksListChanged;
    private String nameValue;
    private String descriptionValue;
    private LocalDate startDateValue;
    private LocalDate endDateValue;
    private LocalTime startTimeValue;
    private LocalTime endTimeValue;
    private String conductorValue;
    private String locationValue;
    private int pointValue;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventHandler = new UIEventHandler();
        eventUseCaseController = new EventUseCaseController();
        musicalWorksListChanged = false;
    }

    void setEditEventStage(Stage s) {
        editEventStage = s;
    }

    void setParentController(EventController c) {
        controller = c;
    }

    private void addListeners() {
        if (name != null) {
            name.textProperty().addListener((observable, oldValue, newValue) -> {
                event.setName(newValue);
                checkInserted();
            });
        }
        if (description != null) {
            description.textProperty().addListener((observable, oldValue, newValue) -> {
                event.setDescription(newValue);
                checkInserted();
            });
        }
        if (conductor != null) {
            conductor.textProperty().addListener((observable, oldValue, newValue) -> {
                event.setConductor(newValue);
                checkInserted();
            });
        }
        if (location != null) {
            location.textProperty().addListener((observable, oldValue, newValue) -> {
                event.setLocation(newValue);
                checkInserted();
            });
        }
        if (points != null) {
            points.valueProperty().addListener((observable, oldValue, newValue) -> {
                event.setDefaultPoints(newValue);
                checkInserted();
            });
        }
        if (startTime != null) {
            startTime.valueProperty().addListener((observable, oldValue, newValue) -> {
                event.setStartTime(newValue);
                checkInserted();
            });
        }
        if (endTime != null) {
            endTime.valueProperty().addListener((observable, oldValue, newValue) -> {
                event.setEndTime(newValue);
                checkInserted();
            });
        }
        if (startDate != null) {
            startDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                event.setStartDate(newValue);
                if (!event.getEventType().equals(EventType.Tour)) {
                    event.setEndDate(newValue);
                }
                checkInserted();
            });
        }
        if (endDate != null) {
            endDate.valueProperty().addListener((observable, oldValue, newValue) -> {
                event.setEndDate(newValue);
                checkInserted();
            });
        }
    }

    public void setEvent(EventViewInterface e) {
        eventView = e;
        event = eventUseCaseController.getEventInstance(eventView);
        setUpOldValues();
        name.setText(eventView.getName());
        startDate.setValue(eventView.getStartDate());
        startTime.setValue(eventView.getStartTime());
        endTime.setValue(eventView.getEndTime());
        points.getEditor().setText(Integer.toString(Math.round(eventView.getDefaultPoints())));
        location.setText(eventView.getLocation());
        switch (event.getEventType()) {
            case Tour:
                initializeTour();
                break;
            case NonMusicalEvent:
                initializeNMEvent();
                break;
            case Rehearsal:
                descriptionFor.setText("Rehearsal for event: " + event.getName() +
                        " (" + event.getEventType().name() + ")\nDate and Time: " +
                        LocalDateTime.of(event.getStartDate(), event.getStartTime()).format(DateTimeFormatter.ofPattern("dd. MMM yy HH:mm"))
                        + " - " + LocalDateTime.of(event.getEndDate(), event.getEndTime()).format(DateTimeFormatter.ofPattern("dd. MMM yy HH:mm")));
                break;
            case Opera:
                initializeMusicalEvent();
                break;
            case Concert:
                initializeMusicalEvent();
                break;
            case Hofkapelle:
                initializeMusicalEvent();
                break;
        }
        addListeners();
    }

    private void setUpOldValues() {
        nameValue = event.getName();
        descriptionValue = event.getDescription();
        startDateValue = event.getStartDate();
        endDateValue = event.getEndDate();
        startTimeValue = event.getStartTime();
        endTimeValue = event.getEndTime();
        locationValue = event.getLocation();
        conductorValue = event.getConductor();
        pointValue = event.getDefaultPoints();
    }

    private void initializeMusicalEvent() {
        description.setText(event.getDescription());
        musicalWorksList = event.getMusicalWorks();
        StringBuilder s = new StringBuilder();
        if (musicalWorksList != null) {
            for (MusicalWorkViewInterface m : musicalWorksList) {
                s.append(m.getName()).append("\n");
            }
            musicalWorks.setText(s.toString());
        }
        conductor.setText(event.getConductor());
    }

    private void initializeNMEvent() {
        description.setText(event.getDescription());
    }

    private void initializeTour() {
        endDate.setValue(event.getEndDate());
        StringBuilder s = new StringBuilder();
        if (event.getMusicalWorks() != null) {
            for (MusicalWorkViewInterface m : event.getMusicalWorks()) {
                s.append(m.getName()).append("\n");
            }
            musicalWorks.setText(s.toString());
        }
        description.setText(event.getDescription());
        conductor.setText(event.getConductor());
    }

    @FXML
    private void enableTabNav(KeyEvent event) {
        eventHandler.handleTabNav(event, gridPane, description);
    }

    @FXML
    private void abortAction() {
        if (!dataInserted) {
            editEventStage.close();
        } else if (eventHandler.displayAbortAlert(editEventStage)) {
            event.setName(nameValue);
            if (descriptionValue != null) {
                event.setDescription(descriptionValue);
            }
            event.setStartDate(startDateValue);
            event.setEndDate(endDateValue);
            event.setStartTime(startTimeValue);
            event.setEndTime(endTimeValue);
            if (locationValue != null) {
                event.setLocation(locationValue);
            }
            if (conductorValue != null) {
                event.setConductor(conductorValue);
            }
            event.setDefaultPoints(pointValue);
        }
    }

    @FXML
    private void saveEdit() {
        try {
            int pointsValue;
            if ((pointsValue = checkPoints()) != -1) {
                event.setDefaultPoints(pointsValue);
                event.setMusicalWorks(musicalWorksList);
                if (!eventUseCaseController.checkMusicians(event)) {
                    ButtonType save = new ButtonType("Save anyway");
                    ButtonType abort = new ButtonType("Abort");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "At the specified time there aren't enough musicians available. " +
                            "Do you want to save anyway?", save, abort);
                    alert.setHeaderText("Resource Warning");
                    alert.setTitle("Warning");
                    alert.showAndWait();

                    if (alert.getResult().equals(abort)) {
                        alert.close();
                        return;
                    }
                    alert.close();
                }
                if (musicalWorksListChanged) {
                    event.setMusicalWorks(musicalWorksList);
                    eventView = eventUseCaseController.handleEditEvent(event);
                    if ((musicalWorksList != null) && (musicalWorksList.size() != 0)) {
                        eventUseCaseController.saveMusicalWorksForEvent(eventView);
                    }
                } else {
                    eventView = eventUseCaseController.handleEditEvent(event);
                }
                editEventStage.close();
                controller.updateEvent(eventView);
            }
        } catch (RequiredFieldMissingException | DateException exception) {
            eventHandler.handleErrors(exception.getMessage());
        }
    }

    private int checkPoints() {
        Integer value;
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
    public void addWorks() {
        MusicalWorksSelectionApp selection = new MusicalWorksSelectionApp();
        selection.setEditEventAppController(this);
        selection.setMusicalWorksList(musicalWorksList);
        try {
            selection.start(editEventStage);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void setMusicalWorksList(List<MusicalWorkViewInterface> list) {
        musicalWorksList = list;
        List<MusicalWorkViewInterface> tempWorks = eventView.getMusicalWorks();
        musicalWorksListChanged = false;
        if (tempWorks.size() == musicalWorksList.size()) {
            boolean contains = true;
            for (int count = 0; ((count < musicalWorksList.size()) && (contains)); count++) {
                contains = false;
                MusicalWorkViewInterface mWork = musicalWorksList.get(count);
                for (MusicalWorkViewInterface muWo : tempWorks) {
                    if (muWo.getId() == mWork.getId()) {
                        contains = true;
                    }
                }
            }
            musicalWorksListChanged = !contains;
        } else {
            musicalWorksListChanged = true;
        }
        StringBuilder s = new StringBuilder();
        for (MusicalWorkViewInterface m : musicalWorksList) {
            s.append(m.getName()).append("\n");
        }
        musicalWorks.setText(s.toString());
        checkInserted();
    }

    private void checkInserted() {
        if (checkRequiredFields()) {
            if (valuesChanged()) {
                saveButton.setDisable(false);
                dataInserted = true;
            } else {
                saveButton.setDisable(true);
                dataInserted = false;
            }
        } else {
            saveButton.setDisable(true);
            dataInserted = false;
        }
    }

    private boolean valuesChanged() {
        if (!name.getText().equals(eventView.getName())) {
            return true;
        }
        if (!(description == null) && (!description.getText().equals((eventView.getDescription())))) {
            return true;
        }
        if (!startDate.getValue().equals(eventView.getStartDate())) {
            return true;
        }
        if (!(endDate == null) && (!endDate.getValue().equals(eventView.getEndDate()))) {
            return true;
        }
        if (!startTime.getValue().equals(eventView.getStartTime())) {
            return true;
        }
        if (!endTime.getValue().equals(eventView.getEndTime())) {
            return true;
        }
        if (!conductor.getText().equals(eventView.getConductor())) {
            return true;
        }
        if (!location.getText().equals(eventView.getLocation())) {
            return true;
        }
        if (musicalWorksListChanged) {
            return true;
        }
        int currentPointValue;
        try {
            currentPointValue = Integer.valueOf(points.getEditor().getText());
        } catch (NumberFormatException e) {
            currentPointValue = 0;
        }
        return currentPointValue != eventView.getDefaultPoints();
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
