package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Pair {
    private String _key;
    private String _value;

    @JsonGetter("key")
    public String getKey() {
        return _key;
    }

    @JsonGetter("value")
    public String getValue() {
        return _value;
    }

    @JsonSetter("key")
    public void setKey(String key) {
        _key = key;
    }

    @JsonSetter("value")
    public void setValue(String value) {
        _value = value;
    }
}
