package team_f.domain.logic;

import javafx.util.Pair;
import team_f.domain.entities.Person;
import team_f.domain.enums.PersonProperty;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static team_f.domain.enums.PersonProperty.*;

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
                        resultList.add(new Pair<>(String.valueOf(FIRSTNAME), "is empty"));
                    }

                    break;

                case LASTNAME:
                    if(person.getLastname() == null){
                        resultList.add(new Pair<>(String.valueOf(LASTNAME), "is empty"));
                    }

                    break;

                case PHONE_NUMBER:
                    if(person.getPhoneNumber() == null){
                        resultList.add(new Pair<>(String.valueOf(PHONE_NUMBER), "is empty"));
                    }

                    break;

                case ADDRESS:
                    if(person.getAddress() == null){
                        resultList.add(new Pair<>(String.valueOf(ADDRESS), "is empty"));
                    }

                    break;

                case EMAIL:
                    if(person.getEmail() == null){
                        resultList.add(new Pair<>(String.valueOf(EMAIL), "is empty"));
                    } else {
                        Pattern ptr = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                        Matcher matcher = ptr.matcher(person.getEmail());

                        if(!matcher.matches()){
                            resultList.add(new Pair<>(String.valueOf(EMAIL), "is not valid"));
                        }
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
