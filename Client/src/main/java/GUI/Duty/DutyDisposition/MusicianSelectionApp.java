package GUI.Duty.DutyDisposition;

import Domain.Event.EventViewInterface;
import Domain.Person.PersonViewInterface;
import GUI.Duty.DutyController;
import GUI.Event.UIEventHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

/**
 * @author Julian
 */
public class MusicianSelectionApp extends Application {
    private Stage newStage;
    private double xOffset;
    private double yOffset;
    private int maxMusicians;
    private EventViewInterface event;
    private String instrument;
    private String section;
    private String spareInstrument;
    private DutyController parentController;
    private List<PersonViewInterface> musicians;
    private List<PersonViewInterface> musicianList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectMusician.fxml"));
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
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initOwner(primaryStage);
        newStage.setScene(scene);
        MusicianSelectionController controller = loader.getController();
        controller.setMusicianListAndController(musicianList);
        controller.setMusicianSelectionStage(newStage);
        controller.setEvent(event);
        controller.setSection(section);
        if(spareInstrument != null) {
            controller.setSpareInstrument(spareInstrument);
        }
        controller.setInstrument(instrument);
        controller.setParentController(parentController);
        controller.setMaxMusicians(maxMusicians);
        controller.initializeTable();
        if (musicians != null) {
            controller.setMusicians(musicians);
        }
        newStage.showAndWait();
    }

    public void setEvent(EventViewInterface event) {
        this.event = event;
    }

    public void setType(String instrument) {
        this.instrument = instrument;
    }

    public void setParentController(DutyController parentController) {
        this.parentController = parentController;
    }

    public void setMaxMusicians(int maxMusicians) {
        this.maxMusicians = maxMusicians;
    }

    public void setMusicians(List<PersonViewInterface> musicians) {
        this.musicians = musicians;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setSpareInstrument(String spareInstrument) {
        this.spareInstrument = spareInstrument;
    }

    public void setMusicianList(List<PersonViewInterface> musicianList){
        this.musicianList = musicianList;
    }
}
