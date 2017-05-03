package team_f.client.pages.home;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class HomeScreen extends BorderPane {
    public HomeScreen() {

        Image image1 = new Image("Logo Neu.jpg");
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

        this.setBackground(new Background(new BackgroundImage(image1,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }
}
