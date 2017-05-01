package team_f.client.controls.sidebar;

import javafx.scene.control.Accordion;
import javafx.scene.layout.Pane;

import java.util.Collection;

public class Sidebar extends Pane {
    private Accordion _accordion;

    public Sidebar() {
        _accordion = new Accordion();
        _accordion.setStyle("-fx-background-color: #e0e0d1");
        getChildren().add(_accordion);
    }

    public boolean add(MenuSection menuSection) {
        boolean result = true;

        if (menuSection != null) {
            _accordion.getPanes().add(menuSection);
        } else {
            result = false;
        }

        return result;
    }

    public boolean addAll(Collection<MenuSection> menuSections) {
        boolean result = true;

        if (menuSections != null) {
            for (MenuSection item : menuSections) {
                if (!add(item)) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }

        return result;
    }
}
