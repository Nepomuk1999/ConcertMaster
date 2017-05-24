package GUI.Duty;

import Domain.Person.PersonViewInterface;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
class DutyTableObject {
    private String section;
    private String instrumentLabel;
    private int instrumentAmount;
    private List<PersonViewInterface> musicians;
    private PersonViewInterface spareMusician;

    String getSection() {
        return section;
    }

    void setSection(String section) {
        this.section = section;
    }

    String getInstrumentLabel() {
        return instrumentLabel;
    }

    void setInstrumentLabel(String instrumentLabel) {
        this.instrumentLabel = instrumentLabel;
    }

    int getInstrumentAmount() {
        return instrumentAmount;
    }

    void setInstrumentAmount(int instrumentAmount) {
        this.instrumentAmount = instrumentAmount;
    }

    List<PersonViewInterface> getMusicians() {
        return musicians;
    }

    void addMusician(PersonViewInterface person) {
        if(musicians == null){
            musicians = new LinkedList<>();
        }
        musicians.add(person);
    }

    void setMusicians(ObservableList<PersonViewInterface> musicians) {
        this.musicians = musicians;
    }

    void changeSpare(PersonViewInterface musician) {
        spareMusician = musician;
    }

    PersonViewInterface getSpareMusician(){
        return spareMusician;
    }
}
