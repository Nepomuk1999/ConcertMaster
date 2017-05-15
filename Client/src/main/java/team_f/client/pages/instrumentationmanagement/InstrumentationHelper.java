package team_f.client.pages.instrumentationmanagement;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.jsonconnector.entities.Instrumentation;

public class InstrumentationHelper {


    public static TableColumn<Instrumentation, Integer> getIdColumn() {
        TableColumn<Instrumentation, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<Instrumentation, Integer> idCellValueFactory = new PropertyValueFactory<>("instrumentationID");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    public static TableColumn<Instrumentation, String> getInstrumentationColumn() {
        TableColumn<Instrumentation, String> instrumentationCol = new TableColumn<>("Instrumentation");
        PropertyValueFactory<Instrumentation, String> instrumentationCellValueFactory = new PropertyValueFactory<>("instrumentation");
        instrumentationCol.setCellValueFactory(instrumentationCellValueFactory);
        return instrumentationCol;
    }
}
