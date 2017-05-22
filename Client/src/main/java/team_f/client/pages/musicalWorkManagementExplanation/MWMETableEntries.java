package team_f.client.pages.musicalWorkManagementExplanation;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MWMETableEntries {
    final static public ObservableList data = FXCollections.observableArrayList(
            new MWMETableEntries("1", "ConcertMaster allows you to manage musical works. Click the “Compositions” button on the sidebar. The submenu \nthat lists the available functions will appear. Click “Musical Work Management”."),
            new MWMETableEntries("2", "You can now add or update musical works by entering their name, composer and instrumentation. You can click \n“Clear” to clear all entries in the fields."),
            new MWMETableEntries("3", "On the bottom of the page you see a table listing all musical works entered into the musical work management.\nTo edit or delete a musical work click “Delete” or “Edit” after selecting an entry from the table. You can also search\nfor musical works by name of the composer.")
    );

    final private String fieldNumber;
    final private String str;


    public MWMETableEntries(String fieldNumber, String str) {
        this.fieldNumber = fieldNumber;
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public String getFieldNumber(){return fieldNumber;}
}