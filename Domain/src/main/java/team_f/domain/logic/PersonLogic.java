package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Person;
import team_f.domain.enums.PersonProperty;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

public class PersonLogic implements EntityLogic<Person, PersonProperty> {
    protected PersonLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(Person person, PersonProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (PersonProperty property : properties) {

            switch (property) {

                case FIRSTNAME:
                    if (person.getFirstname() == null) {
                        resultList.add(new Pair<>(String.valueOf(property.FIRSTNAME), "is empty"));
                    }

                    break;

                case LASTNAME:
                    if(person.getLastname() == null){
                        resultList.add(new Pair<>(String.valueOf(property.LASTNAME), "is empty"));
                    }

                    break;

                case PHONE_NUMBER:
                    if(person.getPhoneNumber() == null){
                        new Pair<>(String.valueOf(property.PHONE_NUMBER), "is empty");
                    }

                    break;

                case ADDRESS:
                    if(person.getAddress() == null){
                        new Pair<>(String.valueOf(property.ADDRESS), "is empty");
                    }

                    break;

                case EMAIL:
                    if(person.getEmail() == null){
                        new Pair<>(String.valueOf(property.EMAIL), "is empty");
                    }

                    break;

                // @TODO: validate the cases
                // use AccountLogic for the account specific logic
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Person person) {
        return validate(person, PersonProperty.values());
    }
}
