package team_f.client.controls.sidebar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class MenuSectionItem extends ToggleButton {
    public MenuSectionItem(String title) {
        setText(title);

        // initialize with predefined settings
        setMaxWidth(Double.MAX_VALUE);
    }

    public MenuSectionItem(String title, EventHandler<ActionEvent> onclick) {
        this(title);

        setOnAction(onclick);


    }

    public void changeColor() {
        setStyle("-fx-background-color: aqua");
    }
}