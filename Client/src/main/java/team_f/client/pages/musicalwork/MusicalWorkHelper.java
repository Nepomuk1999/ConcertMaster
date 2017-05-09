package team_f.client.pages.musicalwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.MusicalWork;

public class MusicalWorkHelper {
    // Returns an observable list of musical works
    public static ObservableList<MusicalWork> getMusicalWorkList() {
        MusicalWork mw1 = new MusicalWork();
        mw1.setMusicalWorkID(1);
        mw1.setName("Don Giovanni");
        mw1.setComposer("Wolfgang Amadeus Mozart");
        mw1.setInstrumentation(new Instrumentation());

        MusicalWork mw2 = new MusicalWork();
        mw2.setMusicalWorkID(2);
        mw2.setName("Klavierquartett");
        mw2.setComposer("Johannes Brahms");
        mw2.setInstrumentation(new Instrumentation());

        MusicalWork mw3 = new MusicalWork();
        mw3.setMusicalWorkID(3);
        mw3.setName("Aufforderung zum Tanze op. 65");
        mw3.setComposer("Carl Maria von Weber");
        mw3.setInstrumentation(new Instrumentation());

        return FXCollections.observableArrayList(mw1, mw2, mw3);
    }

    // Returns PersonTestData Id TableColumn
    public static TableColumn<MusicalWork, Integer> getIdColumn() {
        TableColumn<MusicalWork, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<MusicalWork, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    public static TableColumn<MusicalWork, String> getMusicalWorkNameColumn() {
        TableColumn<MusicalWork, String> musicalWorkNameCol = new TableColumn<>("MusicalWork Name");
        PropertyValueFactory<MusicalWork, String> musicalWorkNameCellValueFactory = new PropertyValueFactory<>("_musicalWorkName");
        musicalWorkNameCol.setCellValueFactory(musicalWorkNameCellValueFactory);
        return musicalWorkNameCol;
    }

    public static TableColumn<MusicalWork, String> getComposerColumn() {
        TableColumn<MusicalWork, String> composerCol = new TableColumn<>("Composer name");
        PropertyValueFactory<MusicalWork, String> composerCellValueFactory = new PropertyValueFactory<>("_composer");
        composerCol.setCellValueFactory(composerCellValueFactory);
        return composerCol;
    }

    public static TableColumn<MusicalWork, String> getInstrumentationColumn() {
        TableColumn<MusicalWork, String> instrumentationCol = new TableColumn<>("Instrumentation");
        PropertyValueFactory<MusicalWork, String> composerCellValueFactory = new PropertyValueFactory<>("_instrumentation");
        instrumentationCol.setCellValueFactory(composerCellValueFactory);
        return instrumentationCol;
    }
}

