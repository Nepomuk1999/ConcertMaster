package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonList implements JSONObjectEntity {
    private List<Person> _personList;

    @JsonGetter("persons")
    public List<Person> getPersonList() {
        return _personList;
    }

    @JsonSetter("persons")
    public void setPersonList(List<Person> personList) {
        _personList = personList;
    }
}
