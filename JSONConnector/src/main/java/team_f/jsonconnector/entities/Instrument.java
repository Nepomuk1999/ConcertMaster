package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.enums.InstrumentType;
import team_f.jsonconnector.interfaces.JSONObjectEntity;

public class Instrument implements JSONObjectEntity {
    private int _instrumentID;
    private InstrumentType _instrumentType;
    private String _brand;
    private String _model;

    @JsonGetter("id")
    public int getInstrumentID() {
        return _instrumentID;
    }

    @JsonGetter("type")
    public InstrumentType getInstrumentType() {
        return _instrumentType;
    }

    @JsonGetter("brand")
    public String getBrand() {
        return _brand;
    }

    @JsonGetter("model")
    public String getModel() {
        return _model;
    }

    @JsonSetter("type")
    public void setInstrumentType(InstrumentType instrumentType) {
        _instrumentType = instrumentType;
    }

    @JsonSetter("brand")
    public void setBrand(String brand) {
        _brand = brand;
    }

    @JsonSetter("model")
    public void setModel(String model) {
        _model = model;
    }

    @JsonSetter("id")
    public void setInstrumentID(int instrumentID) {
        _instrumentID = instrumentID;
    }
}
