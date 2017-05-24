package GUI.Event.MusicalWorkSelection;

import Domain.MusicalWork.MusicalWorkViewInterface;
import GUI.Event.CreateEvent.CreateEventAppController;
import GUI.Event.EditEvent.EditEventAppController;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Julian
 */
public class MusicalWorksSelectionApp extends Application {
    private EditEventAppController editEventAppController;
    private CreateEventAppController createEventAppController;
    private List<MusicalWorkViewInterface> musicalWorksList;
    private Stage newStage;
    private double xOffset;
    private double yOffset;

    public void setEditEventAppController(EditEventAppController c) {
        editEventAppController = c;
    }

    public void setCreateEventAppController(CreateEventAppController c) {
        createEventAppController = c;
    }

    public void setMusicalWorksList(List<MusicalWorkViewInterface> musicalWorksList) {
        this.musicalWorksList = musicalWorksList;
    }


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("musicalWorkSelection.fxml"));
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
            MusicalWorkSelectionController controller = loader.getController();
            controller.setEditEventAppController(editEventAppController);
            controller.setCreateEventAppController(createEventAppController);
            controller.setStage(newStage);
            if (musicalWorksList != null) {
                controller.setMusicalWorkList(musicalWorksList);
            }
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initOwner(stage);
            newStage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

}
