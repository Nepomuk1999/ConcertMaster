package team_f.client.pages.musicalwork;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import team_f.client.controls.numberfield.NumberField;
import team_f.client.entities.KeyValuePair;
import team_f.jsonconnector.enums.SectionType;

public class SpecialInstrumentationEntity {
    private int _specialInstrumentationID = 0;
    private ComboBox<KeyValuePair> _sectionTypeComboBox;
    private TextField _specialInstrumentTextField;
    private NumberField _specialInstrumentNumberField;

    public SpecialInstrumentationEntity(int specialInstrumentationID, ComboBox<KeyValuePair> sectionTypeComboBox, TextField specialInstrumentTextField, NumberField specialInstrumentNumberField) {
        _sectionTypeComboBox = sectionTypeComboBox;
        _specialInstrumentTextField = specialInstrumentTextField;
        _specialInstrumentNumberField = specialInstrumentNumberField;
        _specialInstrumentationID = specialInstrumentationID;
    }

    public int getSpecialInstrumentationID() {
        return _specialInstrumentationID;
    }

    public ComboBox<KeyValuePair> getSectionTypeComboBox() {
        return _sectionTypeComboBox;
    }

    public TextField getSpecialInstrumentTextField() {
        return _specialInstrumentTextField;
    }

    public NumberField getSpecialInstrumentNumberField() {
        return _specialInstrumentNumberField;
    }
}
