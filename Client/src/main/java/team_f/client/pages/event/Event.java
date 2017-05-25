package team_f.client.pages.event;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import team_f.client.pages.BasePage;
import javax.lang.model.type.NullType;
import java.io.IOException;

public class Event extends BasePage<Void, NullType, NullType, NullType> {
    public Event(){
    }

    @Override
    public void initialize() {
        if(_initialize != null) {
            _initialize.doAction(null);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Event/Event.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            setCenter(scene.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        if(_load != null) {
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void exit() {
        if(_exit != null) {
            _exit.doAction(null);
        }
    }

    @Override
    public void dispose() {
    }
}
