package team_f.client.controls.Home;

/**
 * Created by w7pro on 14.04.2017.
 */

import javafx.scene.image.Image;
import javafx.scene.layout.*;


//TODO: Integrate to Client as Homescreen
public class HomeScreen {

    BorderPane _borderPane;
  public HomeScreen(){
        BorderPane _borderPane = new BorderPane();
        Image image1 = new Image("Logo Neu.jpg");


        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

        _borderPane.setBackground(new Background(new BackgroundImage(image1,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));



    }
    public BorderPane getHomescreen(){
      return _borderPane;
    }


}
