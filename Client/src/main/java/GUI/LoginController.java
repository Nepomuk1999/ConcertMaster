package GUI;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Julian
 */
public class LoginController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void performLogin() {
        COS cos = new COS();
        try {
            cos.start(new Stage(StageStyle.DECORATED));
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
