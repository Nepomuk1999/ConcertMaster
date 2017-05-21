package team_f.client.pages.musicianmanagement;

import team_f.jsonconnector.enums.InstrumentType;

import java.util.List;

public class PersonReturnValue {
    List<InstrumentType> _instrumentTypeList;

    public List<InstrumentType> getInstrumentTypeList() {
        return _instrumentTypeList;
    }

    public void setInstrumentTypeList(List<InstrumentType> instrumentTypeList) {
        _instrumentTypeList = instrumentTypeList;
    }
}
