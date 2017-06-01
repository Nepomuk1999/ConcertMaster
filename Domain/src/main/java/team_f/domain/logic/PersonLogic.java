package team_f.domain.logic;

import javafx.util.Pair;
import org.apache.commons.validator.routines.EmailValidator;
import team_f.domain.entities.Person;
import team_f.domain.enums.PersonRole;
import team_f.domain.enums.properties.PersonProperty;
import team_f.domain.helper.IntegerHelper;
import team_f.domain.helper.StringHelper;
import team_f.domain.interfaces.EntityLogic;
import java.util.LinkedList;
import java.util.List;

import static team_f.domain.enums.properties.EventDutyProperty.ID;
import static team_f.domain.enums.properties.PersonProperty.*;

public class PersonLogic implements EntityLogic<Person, PersonProperty> {
    protected PersonLogic() {
    }

    @Override
    public List<Pair<String, String>> validate(Person person, PersonProperty... properties) {
        List<Pair<String, String>> resultList = new LinkedList<>();

        LOOP:
        for (PersonProperty property : properties) {
            switch (property) {
                case ID:
                    if (!IntegerHelper.isValidId(person.getID())) {
                        resultList.add(new Pair<>(String.valueOf(ID), "is not in the correct range"));
                    }

                    break;

                case FIRSTNAME:
                    if (person.getFirstname() != null && !StringHelper.isNotEmpty(person.getFirstname())) {
                        resultList.add(new Pair<>(String.valueOf(FIRSTNAME), "is empty"));
                    }

                    break;

                case LASTNAME:
                    if (person.getLastname() != null && !StringHelper.isNotEmpty(person.getLastname())) {
                        resultList.add(new Pair<>(String.valueOf(LASTNAME), "is empty"));
                    }

                    break;

                case PHONE_NUMBER:
                    if (person.getPhoneNumber() != null && !StringHelper.isNotEmpty(person.getPhoneNumber())) {
                        resultList.add(new Pair<>(String.valueOf(PHONE_NUMBER), "is empty"));
                    }

                    break;

                case ADDRESS:
                    if (person.getAddress() != null && !StringHelper.isNotEmpty(person.getAddress())) {
                        resultList.add(new Pair<>(String.valueOf(ADDRESS), "is empty"));
                    }

                    break;

                case EMAIL:
                    if (person.getEmail() != null && !StringHelper.isNotEmpty(person.getEmail())) {
                        resultList.add(new Pair<>(String.valueOf(EMAIL), "is empty"));
                    } else {
                        if(!EmailValidator.getInstance().isValid(person.getEmail())) {
                            resultList.add(new Pair<>(String.valueOf(EMAIL), "is not valid"));
                        }
                    }

                    break;

                case GENDER:
                    if (person.getGender() != null && !StringHelper.isNotEmpty(person.getGender())) {
                        resultList.add(new Pair<>(String.valueOf(GENDER), "is empty"));
                    }

                    break;

                case INITIALS:
                    if (person.getInitials() != null && !StringHelper.isNotEmpty(person.getInitials())) {
                        resultList.add(new Pair<>(String.valueOf(INITIALS), "is empty"));
                    }

                    break;

                case PERSON_ROLE:
                    if (person.getPersonRole() == null) {
                        resultList.add(new Pair<>(String.valueOf(PERSON_ROLE), "is empty"));
                    } else {
                        boolean isValid = false;
                        for (PersonRole personRole : PersonRole.values()) {
                            if (String.valueOf(personRole).equals(String.valueOf(person.getPersonRole()))) {
                                isValid = true;
                            }
                        }

                        if (!isValid) {
                            resultList.add(new Pair<>(String.valueOf(PERSON_ROLE), "is not valid"));
                        }
                    }

                    break;
            }
        }

        return resultList;
    }

    @Override
    public List<Pair<String, String>> validate(Person person) {
        return validate(person, PersonProperty.values());
    }
}
