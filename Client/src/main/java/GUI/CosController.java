package GUI;

import javafx.stage.Stage;

/**
 * @author Julian
 */
public class CosController {
    private static Stage stage;

    static public Stage getPrimaryStage() {
        return stage;
    }


    void setStage(Stage s) {
        stage = s;
    }
}

