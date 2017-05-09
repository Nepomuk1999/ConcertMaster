package team_f.client.pages.musicianmanagement;

import team_f.jsonconnector.entities.Person;

public class PersonParameter {
    private Person _person;

    public Person getPerson() {
        return _person;
    }

    protected void setPerson(Person person) {
        _person = person;
    }
}
