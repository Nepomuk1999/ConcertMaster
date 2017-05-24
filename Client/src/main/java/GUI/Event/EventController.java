package GUI.Event;

import Domain.Event.EventViewInterface;
import Enums.EventStatus;
import Enums.EventType;
import Exceptions.DateException;
import Exceptions.RequiredFieldMissingException;
import GUI.CosController;
import GUI.Event.CreateEvent.CreateEventApp;
import GUI.Event.CreateEvent.CreateRehearsalApp;
import GUI.Event.EditEvent.EditEventApp;
import GUI.Event.PublishEvent.PublishEventWindow;
import UseCases.Event.EventUseCaseController;
import UseCases.Event.EventValueContainer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julian
 */
public class EventController implements Initializable, Callback<Agenda.Appointment, Void> {
    private static Stage stage;
    private UIEventHandler eventHandler;
    private EventUseCaseController eventUseCaseController;
    private EventViewInterface selectedEvent;
    @FXML
    private Tab listTab;
    @FXML
    private Tab calendarTab;
    @FXML
    private Button editButton;
    @FXML
    private Button addRehearsalButton;
    @FXML
    private Agenda calAgenda;
    @FXML
    private TableView<EventViewInterface> eventTable;
    @FXML
    private TableColumn<EventViewInterface, String> nameCol;
    @FXML
    private TableColumn<EventViewInterface, String> descCol;
    @FXML
    private TableColumn<EventViewInterface, String> startCol;
    @FXML
    private TableColumn<EventViewInterface, String> endCol;
    @FXML
    private TableColumn<EventViewInterface, String> typeCol;
    @FXML
    private TableColumn<EventViewInterface, String> conductorCol;
    @FXML
    private TableColumn<EventViewInterface, String> locationCol;
    @FXML
    private Button btnFwd;
    @FXML
    private Button btnBack;
    @FXML
    private MenuButton eventButton;
    @FXML
    private MenuItem operaButton;
    @FXML
    private MenuItem concertButton;
    @FXML
    private MenuItem tourButton;
    @FXML
    private MenuItem nmeButton;
    @FXML
    private MenuItem hofkapelleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnBack.setText("\u25C0");
        btnFwd.setText("\u25B6");
        initializeGroups();
        setUpCellValueFactories();
        setUpChangeListeners();
        eventUseCaseController = new EventUseCaseController();
        eventHandler = new UIEventHandler();
        List<EventViewInterface> events = eventUseCaseController.getAllEvents();
        addAllToCalendar(events);
        eventButton.getItems().addAll(operaButton, concertButton, tourButton, hofkapelleButton, nmeButton);
        stage = CosController.getPrimaryStage();
    }

    private void initializeGroups() {
        calAgenda.appointmentGroups().add(new Agenda.AppointmentGroupImpl()
                .withDescription("Opera")
                .withStyleClass("opera")
        );
        calAgenda.appointmentGroups().add(new Agenda.AppointmentGroupImpl()
                .withDescription("Concert")
                .withStyleClass("concert")
        );
        calAgenda.appointmentGroups().add(new Agenda.AppointmentGroupImpl()
                .withDescription("Non-Musical Event")
                .withStyleClass("nonMusicalEvent")
        );
        calAgenda.appointmentGroups().add(new Agenda.AppointmentGroupImpl()
                .withDescription("Tour")
                .withStyleClass("tour")
        );
        calAgenda.appointmentGroups().add(new Agenda.AppointmentGroupImpl()
                .withDescription("Hofkapelle")
                .withStyleClass("hofkapelle")
        );
        calAgenda.appointmentGroups().add(new Agenda.AppointmentGroupImpl()
                .withDescription("Rehearsal")
                .withStyleClass("rehearsal")
        );
    }

    private void setUpCellValueFactories() {
        nameCol.setCellValueFactory((e) -> {
            String string = e.getValue().getName();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        descCol.setCellValueFactory((e) -> {
            String string = e.getValue().getDescription();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        startCol.setCellValueFactory((e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM yy HH:mm");
            String string = LocalDateTime.of(e.getValue().getStartDate(), e.getValue().getStartTime()).format(formatter);
            return new SimpleStringProperty(string.length() > 0 ? string : "");
        });
        endCol.setCellValueFactory((e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM yy HH:mm");
            String string = LocalDateTime.of(e.getValue().getEndDate(), e.getValue().getEndTime()).format(formatter);
            return new SimpleStringProperty(string.length() > 0 ? string : "");
        });
        typeCol.setCellValueFactory((e) -> {
            EventType type = e.getValue().getEventType();
            String string;
            if (type != null) {
                string = type.name();
            } else {
                string = null;
            }
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        conductorCol.setCellValueFactory((e) -> {
            String string = e.getValue().getConductor();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        locationCol.setCellValueFactory((e) -> {
            String string = e.getValue().getLocation();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
    }


    private void setUpChangeListeners() {
        eventTable.getFocusModel().focus(0);
        eventTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ((newValue != null) && (newValue.getStartDate().isAfter(LocalDate.now())) && newValue.getEventStatus().equals(EventStatus.Unpublished)) {
                selectedEvent = newValue;
                String eventTypeString = selectedEvent.getEventType().name().substring(0, 1).toUpperCase() + selectedEvent.getEventType().name().substring(1).toLowerCase();
                if (selectedEvent.getEventType().equals(EventType.NonMusicalEvent)) {
                    eventTypeString = "Non-Musical Event";
                }
                editButton.setText("Edit " + eventTypeString);
                editButton.setDisable(false);
                if (newValue.getEventType().equals(EventType.Concert) || newValue.getEventType().equals(EventType.Tour)
                        || newValue.getEventType().equals(EventType.Hofkapelle) || newValue.getEventType().equals(EventType.Opera)) {
                    addRehearsalButton.setDisable(false);
                } else {
                    addRehearsalButton.setDisable(true);
                }
            } else {
                editButton.setDisable(true);
                editButton.setText("Edit Event");
                addRehearsalButton.setDisable(true);
            }
        });
        eventTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                selectedEvent = eventTable.getSelectionModel().getSelectedItem();
                editEvent();
            }
        });
        calAgenda.setEditAppointmentCallback(this);
        calAgenda.selectedAppointments().addListener((ListChangeListener<Agenda.Appointment>) c -> {
            c.next();
            if (c.getAddedSubList().size() > 0) {
                Agenda.Appointment a = c.getAddedSubList().get(c.getAddedSubList().size() - 1);
                queryAppointment(a);
                editButton.setText("Edit " + selectedEvent.getEventType().name().toLowerCase());
                editButton.setDisable(false);
                if (selectedEvent.getEventType().equals(EventType.Concert) || selectedEvent.getEventType().equals(EventType.Tour)
                        || selectedEvent.getEventType().equals(EventType.Hofkapelle) || selectedEvent.getEventType().equals(EventType.Opera)) {
                    addRehearsalButton.setDisable(false);
                } else {
                    addRehearsalButton.setDisable(true);
                }
            } else {
                editButton.setDisable(true);
                addRehearsalButton.setDisable(true);
            }
        });
        calAgenda.setAppointmentChangedCallback(this);
    }

    private void addAllToCalendar(List<EventViewInterface> events) {
        for (EventViewInterface event : events) {
            addEvent(event);
        }
    }

    private void createEvent(EventType eventType) {
        try {
            CreateEventApp app = new CreateEventApp(eventType);
            app.setParentController(this);
            app.start(stage);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    @FXML
    private void createOpera() {
        createEvent(EventType.Opera);
    }

    @FXML
    private void createConcert() {
        createEvent(EventType.Concert);
    }

    @FXML
    private void createTour() {
        createEvent(EventType.Tour);
    }

    @FXML
    private void createNME() {
        createEvent(EventType.NonMusicalEvent);
    }

    @FXML
    private void createHofkapelle() {
        createEvent(EventType.Hofkapelle);
    }

    @FXML
    private void editEvent() {
        try {
            EditEventApp editEventApp = new EditEventApp();
            editEventApp.setParentController(this);
            editEventApp.setEvent(selectedEvent);
            editEventApp.start(stage);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    @FXML
    private void calendarSelectionChanged() {
        calAgenda.setDisplayedLocalDateTime(LocalDateTime.now());
    }

    public void addEvent(EventViewInterface event) {
        Agenda.AppointmentGroup group = null;
        for (Agenda.AppointmentGroup g : calAgenda.appointmentGroups()) {
            if (event.getEventType().name().equals(g.getDescription())) {
                group = g;
            }
        }
        calAgenda.appointments().add(new Agenda.AppointmentImplLocal()
                .withStartLocalDateTime(LocalDateTime.of(event.getStartDate(), event.getStartTime()))
                .withEndLocalDateTime(LocalDateTime.of(event.getEndDate(), event.getEndTime()))
                .withSummary(event.getName())
                .withLocation(event.getLocation())
                .withDescription(Integer.toString(event.getId()))
                .withAppointmentGroup(group));
        eventTable.getItems().add(event);
    }

    @FXML
    public void calForward() {
        if (calAgenda.getSkin() instanceof AgendaDaySkin) {
            LocalDateTime nextDay = calAgenda.getDisplayedLocalDateTime().plusDays(1);
            calAgenda.setDisplayedLocalDateTime(nextDay);
        } else if (calAgenda.getSkin() instanceof MyMonthSkin) {
            LocalDateTime nextMonth = calAgenda.getDisplayedLocalDateTime().plusMonths(1);
            calAgenda.setDisplayedLocalDateTime(nextMonth);
        } else {
            LocalDateTime nextWeek = calAgenda.getDisplayedLocalDateTime().plusDays(7);
            calAgenda.setDisplayedLocalDateTime(nextWeek);
        }
    }

    @FXML
    public void calBackwards() {
        if (calAgenda.getSkin() instanceof AgendaDaySkin) {
            LocalDateTime lastDay = calAgenda.getDisplayedLocalDateTime().minusDays(1);
            calAgenda.setDisplayedLocalDateTime(lastDay);
        } else if (calAgenda.getSkin() instanceof MyMonthSkin) {
            LocalDateTime lastMonth = calAgenda.getDisplayedLocalDateTime().minusMonths(1);
            calAgenda.setDisplayedLocalDateTime(lastMonth);
        } else {
            LocalDateTime lastWeek = calAgenda.getDisplayedLocalDateTime().minusDays(7);
            calAgenda.setDisplayedLocalDateTime(lastWeek);
        }
    }

    @FXML
    public void addRehearsal() {
        CreateRehearsalApp rehearsalApp = new CreateRehearsalApp();
        rehearsalApp.setParentController(this);
        rehearsalApp.setEvent(selectedEvent);
        try {
            rehearsalApp.start(stage);
        } catch (Exception e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    @FXML
    public void enableDayView() {
        calAgenda.setSkin(new AgendaDaySkin(calAgenda));
    }

    @FXML
    public void enableWeekView() {
        calAgenda.setSkin(new AgendaWeekSkin(calAgenda));
    }

    @FXML
    public void enableMonthView() {
        calAgenda.setSkin(new MyMonthSkin(calAgenda));
    }

    public void updateEvent(EventViewInterface e) {
        List<EventViewInterface> eventViews = eventTable.getItems();
        ListIterator<EventViewInterface> eventIterator = eventViews.listIterator();
        boolean found = false;
        while (eventIterator.hasNext() && !found) {
            EventViewInterface event = eventIterator.next();
            if (event.getId() == e.getId()) {
                found = true;
                eventIterator.set(e);
            }
        }

        List<Agenda.Appointment> appointments = calAgenda.appointments();
        Iterator<Agenda.Appointment> appointmentIterator = appointments.iterator();
        found = false;
        while ((appointmentIterator.hasNext()) && !found) {
            Agenda.Appointment appointment = appointmentIterator.next();
            if (Integer.parseInt(appointment.getDescription()) == e.getId()) {
                found = true;
                appointmentIterator.remove();
            }
        }
        Agenda.AppointmentGroup group = null;
        for (Agenda.AppointmentGroup g : calAgenda.appointmentGroups()) {
            if (e.getEventType().name().equals(g.getDescription())) {
                group = g;
            }
        }
        calAgenda.appointments().add(new Agenda.AppointmentImplLocal()
                .withStartLocalDateTime(LocalDateTime.of(e.getStartDate(), e.getStartTime()))
                .withEndLocalDateTime(LocalDateTime.of(e.getEndDate(), e.getEndTime()))
                .withSummary(e.getName())
                .withLocation(e.getLocation())
                .withDescription(Integer.toString(e.getId()))
                .withAppointmentGroup(group));
        calAgenda.refresh();
    }

    @Override
    public Void call(Agenda.Appointment appointment) {
        queryAppointment(appointment);
        boolean wasChanged = false;
        EventValueContainer values = eventUseCaseController.getEventInstance(selectedEvent);
        LocalDate startDate = selectedEvent.getStartDate();
        LocalDate endDate = selectedEvent.getEndDate();
        LocalTime startTime = selectedEvent.getStartTime();
        LocalTime endTime = selectedEvent.getEndTime();
        if (!appointment.getStartLocalDateTime().toLocalDate().equals(selectedEvent.getStartDate())) {
            values.setStartDate(appointment.getStartLocalDateTime().toLocalDate());
            wasChanged = true;
        }
        if (!appointment.getStartLocalDateTime().toLocalTime().equals(selectedEvent.getStartTime())) {
            values.setStartTime(appointment.getStartLocalDateTime().toLocalTime());
            wasChanged = true;
        }
        if (!appointment.getEndLocalDateTime().toLocalDate().equals(selectedEvent.getEndDate())) {
            values.setEndDate(appointment.getEndLocalDateTime().toLocalDate());
            wasChanged = true;
        }
        if (!appointment.getEndLocalDateTime().toLocalTime().equals(selectedEvent.getEndTime())) {
            values.setEndTime(appointment.getEndLocalDateTime().toLocalTime());
            wasChanged = true;
        }
        if (!wasChanged) {
            editEvent();
        } else {
            try {
                eventUseCaseController.handleEditEvent(values);
                List<EventViewInterface> events = eventTable.getItems();
                Iterator<EventViewInterface> eventIterator = events.iterator();
                boolean found = false;
                while ((eventIterator.hasNext()) && !found) {
                    EventViewInterface event = eventIterator.next();
                    if (selectedEvent.getId() == event.getId()) {
                        found = true;
                        eventIterator.remove();
                    }
                }
                eventTable.getItems().add(selectedEvent);
                eventTable.refresh();
            } catch (DateException | RequiredFieldMissingException exception) {
                List<Agenda.Appointment> appointments = calAgenda.appointments();
                Iterator<Agenda.Appointment> appointmentIterator = appointments.iterator();
                boolean found = false;
                while ((appointmentIterator.hasNext()) && !found) {
                    Agenda.Appointment a = appointmentIterator.next();
                    if (Integer.parseInt(a.getDescription()) == selectedEvent.getId()) {
                        found = true;
                        a.setStartLocalDateTime(LocalDateTime.of(startDate, startTime));
                        a.setEndLocalDateTime(LocalDateTime.of(endDate, endTime));
                    }
                }
                eventHandler.handleErrors(exception.getMessage());
            }
        }
        return null;
    }

    private void queryAppointment(Agenda.Appointment appointment) {
        selectedEvent = eventUseCaseController.getEventForId(Integer.parseInt(appointment.getDescription()));
    }

    @FXML
    private void publishSchedule() {
        PublishEventWindow publishEventWindow = new PublishEventWindow();
        try {
            publishEventWindow.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
