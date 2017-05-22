package team_f.client.pages.musicianManagementExplanation;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Sample data for a table view
 */
public class MMEPTableEntries {
    final static public ObservableList data = FXCollections.observableArrayList(
            new MMEPTableEntries("1", "ConcertMaster allows you to manage the musicians of your orchestra. To manage musicians, click the “Administration”\n button on the sidebar.The submenu that lists the available functions will appear. Click “Musician Management”."),
            new MMEPTableEntries("2", "You can now add musicians, external musicians, managers, musical librarians, facility managers and substitutes\nby entering their personal information and what their role in the orchestra is, what section they play in and what \ninstrument they play. You can also update these informations after selecting a musician from the table below.  You\ncan click “Clear” to clear all entries in the fields."),
            new MMEPTableEntries("3", "On the bottom of the page you see a table that lists every person entered into the musician management. To edit or\ndelete someone’s data click “Edit” “Delete” after selecting an entry from the table. You can search for a person by\ntheir first or last name.")
    );

    final private String fieldNumber;
    final private String str;


    public MMEPTableEntries(String fieldNumber, String str) {
        this.fieldNumber = fieldNumber;
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public String getFieldNumber(){return fieldNumber;}
}