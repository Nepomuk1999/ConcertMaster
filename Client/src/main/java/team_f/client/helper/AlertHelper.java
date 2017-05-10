package team_f.client.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AlertHelper {
    public static void showSuccessMessage(String headerText, String contentText, Pane pane) {
        showMessage("Success", headerText, contentText, Alert.AlertType.INFORMATION, new ImageView("check.png"), null, pane);
    }

    public static void showErrorMessage(String headerText, String contentText, Pane pane) {
        showMessage("Error", headerText, contentText, Alert.AlertType.ERROR, null, null, pane);
    }

    public static Boolean showWarningMessage(String headerText, String contentText, String okButtonLabel, Pane pane) {
        ButtonType okButton = new ButtonType(okButtonLabel);
        ButtonType cancelButton = new ButtonType("Cancel");
        List<ButtonType> buttonList = new LinkedList<>();
        buttonList.add(okButton);
        buttonList.add(cancelButton);

        Optional optional = showMessage("Error", headerText, contentText, Alert.AlertType.WARNING, null, buttonList, pane);

        if(optional != null) {
            if(optional.get().equals(okButton)) {
                return true;
            } else {
                return false;
            }
        }

        return null;
    }

    private static Optional showMessage(String title, String headerText, String contentText, Alert.AlertType type, ImageView icon, List<ButtonType> buttonTypeList, Pane pane) {
        pane.setDisable(true);

        Alert alert = new Alert(type);
        alert.setTitle(title);

        if(icon != null) {
            alert.setGraphic(icon);
        }

        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        if(buttonTypeList != null) {
            alert.getButtonTypes().setAll(buttonTypeList);
        }

        Optional optional = alert.showAndWait();
        alert.close();

        pane.setDisable(false);

        return optional;
    }
}
