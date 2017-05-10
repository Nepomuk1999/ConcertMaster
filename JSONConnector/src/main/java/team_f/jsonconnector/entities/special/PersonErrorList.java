package team_f.jsonconnector.entities.special;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.ErrorList;
import team_f.jsonconnector.entities.Person;

/**
 * maps to a specific type (required for Jackson Json API to avoid class cast exception)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonErrorList extends ErrorList<Person> {
}