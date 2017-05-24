package GUI.Event.CreateEvent;

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
public class CreateRehearsalApp extends Application {
    private EventViewInterface event;
    private EventController parentController;
    private double xOffset;
    private double yOffset;
    private Stage newStage;

    @Override
    public void start(Stage parentStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createEventRehearsal.fxml"));
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
        controller.setParentStage(parentStage);
        controller.setParentController(parentController);
        controller.setRehearsalFor(event);
        controller.setEventType(EventType.Rehearsal);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initOwner(parentStage);
        newStage.showAndWait();
    }

    public void setParentController(EventController controller) {
        parentController = controller;
    }

    public void setEvent(EventViewInterface event) {
        this.event = event;
    }
}
