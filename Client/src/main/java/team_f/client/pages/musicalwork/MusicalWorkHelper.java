package team_f.client.pages.musicalwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by dominik on 04.05.17.
 */
public class MusicalWorkHelper {
    // Returns an observable list of musical works
    public static ObservableList<MusicalWorkTestData> getMusicalWorkList() {
        MusicalWorkTestData mw1 = new MusicalWorkTestData(1,"Don Giovanni", "Wolfgang Amadeus Mozart", "");
        MusicalWorkTestData mw2 = new MusicalWorkTestData(2, "Klavierquartett", "Johannes Brahms","");
        MusicalWorkTestData mw3 = new MusicalWorkTestData(3, "Aufforderung zum Tanze op. 65", "Carl Maria von Weber","");
        MusicalWorkTestData mw4 = new MusicalWorkTestData(4, "Concertino des Enfants", "Paul Kont", "");

        return FXCollections.<MusicalWorkTestData>observableArrayList(mw1, mw2, mw3, mw4);
    }

    // Returns PersonTestData Id TableColumn
    public static TableColumn<MusicalWorkTestData, Integer> getIdColumn() {
        TableColumn<MusicalWorkTestData, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<MusicalWorkTestData, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    public static TableColumn<MusicalWorkTestData, String> getMusicalWorkNameColumn() {
        TableColumn<MusicalWorkTestData, String> musicalWorkNameCol = new TableColumn<>("MusicalWork Name");
        PropertyValueFactory<MusicalWorkTestData, String> musicalWorkNameCellValueFactory = new PropertyValueFactory<>("name");
        musicalWorkNameCol.setCellValueFactory(musicalWorkNameCellValueFactory);
        return musicalWorkNameCol;
    }

    public static TableColumn<MusicalWorkTestData, String> getComposerColumn() {
        TableColumn<MusicalWorkTestData, String> composerCol = new TableColumn<>("Composer name");
        PropertyValueFactory<MusicalWorkTestData, String> composerCellValueFactory = new PropertyValueFactory<>("composer");
        composerCol.setCellValueFactory(composerCellValueFactory);
        return composerCol;
    }

    public static TableColumn<MusicalWorkTestData, String> getInstrumentationColumn() {
        TableColumn<MusicalWorkTestData, String> instrumentationCol = new TableColumn<>("Instrumentation");
        PropertyValueFactory<MusicalWorkTestData, String> composerCellValueFactory = new PropertyValueFactory<>("instrumentation");
        instrumentationCol.setCellValueFactory(composerCellValueFactory);
        return instrumentationCol;
    }
}

