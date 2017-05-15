package team_f.client.pages.musicalwork;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import team_f.client.controls.numberfield.NumberField;
import team_f.client.entities.KeyValuePair;

public class SpecialInstrumentationEntity {
    private int _specialInstrumentationID = 0;
    private ComboBox<KeyValuePair> _sectionTypeComboBox;
    private TextField _specialInstrumentationTextField;
    private NumberField _specialInstrumentationNumberField;
    private Pane _pane;

    public SpecialInstrumentationEntity(int specialInstrumentationID, ComboBox<KeyValuePair> sectionTypeComboBox, TextField specialInstrumentTextField, NumberField specialInstrumentNumberField, Pane pane) {
        _sectionTypeComboBox = sectionTypeComboBox;
        _specialInstrumentationTextField = specialInstrumentTextField;
        _specialInstrumentationNumberField = specialInstrumentNumberField;
        _specialInstrumentationID = specialInstrumentationID;
        _pane = pane;
    }

    public int getSpecialInstrumentationID() {
        return _specialInstrumentationID;
    }

    public ComboBox<KeyValuePair> getSectionTypeComboBox() {
        return _sectionTypeComboBox;
    }

    public TextField getSpecialInstrumentationTextField() {
        return _specialInstrumentationTextField;
    }

    public NumberField getSpecialInstrumentationNumberField() {
        return _specialInstrumentationNumberField;
    }

    public Pane getPane() {
        return _pane;
    }
}
