package GUI.Event;

import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * @author Julian
 */
public class UIEventHandler {

    public boolean displayAbortAlert(Stage stage) {
        ButtonType yes = new ButtonType("Abort");
        ButtonType no = new ButtonType("Do not Abort");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure that you want to abort? All information will be discarded.", yes, no);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Please confirm...");
        alert.showAndWait();
        if (alert.getResult().equals(yes)) {
            stage.close();
            return true;
        } else if (alert.getResult().equals(no)) {
            alert.close();
            return false;
        }
        return false;
    }

    public int displayPointErrorAlert() {
        ButtonType ok = new ButtonType("Back");
        Alert pointError = new Alert(Alert.AlertType.ERROR, "An error occurred reading data from the points field.", ok);
        pointError.initStyle(StageStyle.UNDECORATED);
        pointError.setTitle("Error Message");
        pointError.setHeaderText("An Error Occurred");
        pointError.showAndWait();
        return -1;
    }

    public void handleTabNav(KeyEvent event, Pane gridPane, TextArea area) {
        if (event.getCode().equals(KeyCode.TAB)) {
            int ind = gridPane.getChildren().indexOf(area);
            Node next = gridPane.getChildren().get(ind + 1);
            next.requestFocus();
            event.consume();
        }
    }

    public void handleErrors(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error validating the input.");
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleWarning(String message) {
        ButtonType okay = new ButtonType("Okay");
        Alert alert = new Alert(Alert.AlertType.WARNING, message, okay);
        alert.setHeaderText("Warning");
        alert.setTitle("Warning");
        alert.showAndWait();
    }

    public static void setHandlerToScene(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (scene.focusOwnerProperty().get() instanceof Button) {
                    Button button = (Button) scene.focusOwnerProperty().get();
                    Bounds bounds = button.getBoundsInLocal();
                    Bounds screenBounds = button.localToScreen(bounds);
                    Bounds sceneBounds = button.localToScene(bounds);
                    Event.fireEvent(button, new MouseEvent(MouseEvent.MOUSE_RELEASED,
                            sceneBounds.getMinX(), sceneBounds.getMinY(), screenBounds.getMinX(), screenBounds.getMinY(), MouseButton.PRIMARY, 1,
                            true, true, true, true, true, true, true, true, true, true, null));
                    event.consume();
                }
            }
        });
    }
}
