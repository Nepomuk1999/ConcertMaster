package GUI.Event.PublishEvent;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julian
 */
public class PublishEventWindow extends Application {
    private Stage newStage;
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("publishEventWindow.fxml"));
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
            PublishEventWindowController controller = loader.getController();
            controller.setStage(newStage);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(primaryStage);
            newStage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}
