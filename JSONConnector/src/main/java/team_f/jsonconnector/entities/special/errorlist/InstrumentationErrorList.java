package team_f.jsonconnector.entities.special.errorlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.list.ErrorList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentationErrorList extends ErrorList<Instrumentation> {
}
