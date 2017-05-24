package GUI.Event.CreateEvent;

import Enums.EventType;
import GUI.Event.EventController;
import GUI.Event.UIEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author Julian
 */
public class CreateEventApp extends Application {
    private EventController parentController;
    private final EventType eventType;
    private Stage newStage;
    private double xOffset;
    private double yOffset;

    public CreateEventApp(EventType type) {
        eventType = type;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = null;
        if (eventType.equals(EventType.Opera)) {
            loader = new FXMLLoader(getClass().getResource("createEventOpera.fxml"));
        } else if (eventType.equals(EventType.Concert)) {
            loader = new FXMLLoader(getClass().getResource("createEventConcert.fxml"));
        } else if (eventType.equals(EventType.Tour)) {
            loader = new FXMLLoader(getClass().getResource("createEventTour.fxml"));
        } else if (eventType.equals(EventType.Hofkapelle)) {
            loader = new FXMLLoader(getClass().getResource("createEventHofkapelle.fxml"));
        } else if (eventType.equals(EventType.NonMusicalEvent)) {
            loader = new FXMLLoader(getClass().getResource("createEventNMEvent.fxml"));
        }
        if (loader != null) {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            UIEventHandler.setHandlerToScene(scene);
            scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });
            scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
                newStage.setX(mouseEvent.getScreenX() - xOffset);
                newStage.setY(mouseEvent.getScreenY() - yOffset);
            });
            newStage = new Stage(StageStyle.UNDECORATED);
            newStage.setScene(scene);
            CreateEventAppController controller = loader.getController();
            controller.setCreateEventStage(newStage);
            controller.setParentController(parentController);
            controller.setEventType(eventType);
            controller.setParentStage(stage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(stage);
            newStage.showAndWait();
        }
    }

    public void setParentController(EventController eventController) {
        parentController = eventController;
    }

}
