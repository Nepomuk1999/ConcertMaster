package team_f.jsonconnector.entities.special;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.list.ErrorList;

/**
 * Created by dominik on 11.05.17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentationErrorList extends ErrorList<Instrumentation> {
}
