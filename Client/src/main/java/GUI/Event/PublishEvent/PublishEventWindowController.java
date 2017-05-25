package GUI.Event.PublishEvent;

import Domain.Event.EventViewInterface;
import GUI.Event.UIEventHandler;
import UseCases.Event.EventUseCaseController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Julian
 */
public class PublishEventWindowController implements Initializable {
    private Stage stage;
    private EventUseCaseController eventUseCaseController;
    private YearMonth selected;
    private String path;
    private UIEventHandler errorHandler;
    @FXML
    private ChoiceBox<YearMonth> monthChoiceBox;
    @FXML
    private TableView<EventViewInterface> eventTable;
    @FXML
    private TableColumn<EventViewInterface, String> nameCol;
    @FXML
    private TableColumn<EventViewInterface, String> descriptionCol;
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
    private TextField dirTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpCVF();
        setUpChoiceBox();
        setUpListener();
        errorHandler = new UIEventHandler();
        dirTextField.setText("No directory selected.");
        eventUseCaseController = new EventUseCaseController();
    }

    private void setUpCVF() {
        nameCol.setCellValueFactory((e) -> {
            String string = e.getValue().getName();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        descriptionCol.setCellValueFactory((e) -> {
            String string = e.getValue().getDescription();
            return new SimpleStringProperty(string != null && string.length() > 0 ? string : "");
        });
        startCol.setCellValueFactory((e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM HH:mm");
            String string = LocalDateTime.of(e.getValue().getStartDate(), e.getValue().getStartTime()).format(formatter);
            return new SimpleStringProperty(string.length() > 0 ? string : "");
        });
        endCol.setCellValueFactory((e) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM HH:mm");
            String string = LocalDateTime.of(e.getValue().getEndDate(), e.getValue().getEndTime()).format(formatter);
            return new SimpleStringProperty(string.length() > 0 ? string : "");
        });
        typeCol.setCellValueFactory((e) -> {
            String string = e.getValue().getEventType().name();
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

    private void setUpChoiceBox() {
        YearMonth yearMonth = YearMonth.now().plusMonths(1);
        ObservableList<YearMonth> list = FXCollections.observableArrayList();
        for (int count = 1; count < 36; count++) {
            list.add(yearMonth);
            yearMonth = yearMonth.plusMonths(1);
        }
        monthChoiceBox.setItems(list);
    }

    private void setUpListener() {
        monthChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selected = newValue;
            eventTable.getItems().removeAll(eventTable.getItems());
            eventTable.refresh();
            eventTable.getItems().addAll(eventUseCaseController.getUnpublishedEventsForMonth(selected));
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void abort() {
        if (errorHandler.displayAbortAlert(stage)) {
            stage.close();
        }
    }

    @FXML
    private void selectDirectory() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File filePath = dirChooser.showDialog(stage);
        if (filePath == null) {
            dirTextField.setText("No directory selected.");
        } else {
            path = filePath.getPath();
            dirTextField.setText(path);
        }
    }

    @FXML
    private void publishSchedule() {
        if ((selected != null) && (path != null)) {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(path + "/schedule_" + selected.getMonth().name().toLowerCase() + ".pdf"));
                document.open();
                /*Image logo = Image.getInstance("src/GUI/Icons/COS_Logo.png");
                logo.scalePercent(10);
                logo.setAlignment(Element.ALIGN_RIGHT);
                document.add(logo);*/
                document.add(createEventSchedule(eventTable.getItems()));
                document.close();
                //eventUseCaseController.publishSchedule(selected); //TODO SET Active
                stage.close();
            } catch (DocumentException | IOException e) {
                errorHandler.handleErrors("An error occurred. Please check your input.");
                e.printStackTrace();
            }
        } else {
            errorHandler.handleErrors("You have to choose a path and a month.");
        }
    }

    private PdfPTable createEventSchedule(List<EventViewInterface> eventsToPublish) {
        PdfPTable table = new PdfPTable(4);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
        PdfPCell cell = new PdfPCell(new Phrase("Name", headerFont));
        cell.setFixedHeight(35);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Date", headerFont));
        cell.setFixedHeight(35);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Type", headerFont));
        cell.setFixedHeight(35);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Location", headerFont));
        cell.setFixedHeight(35);
        table.addCell(cell);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM HH:mm");
        for (EventViewInterface event : eventsToPublish) {
            cell = new PdfPCell(new Phrase(event.getName()));
            table.addCell(cell);
            String dateString = LocalDateTime.of(event.getStartDate(), event.getStartTime()).format(formatter);
            dateString += " - ";
            dateString += LocalDateTime.of(event.getEndDate(), event.getEndTime()).format(formatter);
            cell = new PdfPCell(new Phrase(dateString));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(event.getEventType().name()));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(event.getLocation()));
            table.addCell(cell);
        }
        return table;
    }
}
