package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.enums.PublishType;

public class Publish extends JSONObjectEntity {
    private PublishType _publishType;
    private int _month;
    private int _year;

    @JsonGetter("type")
    public PublishType getPublishType() {
        return _publishType;
    }

    @JsonSetter("type")
    public void setPublishType(PublishType publishType) {
        _publishType = publishType;
    }

    @JsonGetter("month")
    public int getMonth() {
        return _month;
    }

    @JsonSetter("month")
    public void setMonth(int month) {
        _month = month;
    }

    @JsonGetter("year")
    public int getYear() {
        return _year;
    }

    @JsonSetter("year")
    public void setYear(int year) {
        _year = year;
    }
}
