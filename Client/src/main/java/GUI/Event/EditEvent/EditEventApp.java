package GUI.Event.EditEvent;

import Domain.Event.EventViewInterface;
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

/**
 * @author Julian
 */
public class EditEventApp extends Application {
    private double xOffset;
    private double yOffset;
    private Stage newStage;
    private EventViewInterface event;
    private EventController cont;

    public void setEvent(EventViewInterface e) {
        event = e;
    }

    public void setParentController(EventController c) {
        cont = c;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = null;
        EventType type = event.getEventType();
        if (type.equals(EventType.Opera)) {
            loader = new FXMLLoader(getClass().getResource("editEventOpera.fxml"));
        } else if (type.equals(EventType.Concert)) {
            loader = new FXMLLoader(getClass().getResource("editEventConcert.fxml"));
        } else if (type.equals(EventType.Tour)) {
            loader = new FXMLLoader(getClass().getResource("editEventTour.fxml"));
        } else if (type.equals(EventType.Hofkapelle)) {
            loader = new FXMLLoader(getClass().getResource("editEventHofkapelle.fxml"));
        } else if (type.equals(EventType.NonMusicalEvent)) {
            loader = new FXMLLoader(getClass().getResource("editEventNMEvent.fxml"));
        } else if (type.equals(EventType.Rehearsal)) {
            loader = new FXMLLoader(getClass().getResource("editEventRehearsal.fxml"));
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
            EditEventAppController controller = loader.getController();
            controller.setEditEventStage(newStage);
            controller.setParentController(cont);
            controller.setEditEventStage(newStage);
            controller.setEvent(event);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(stage);
            newStage.showAndWait();
        }

    }

}
