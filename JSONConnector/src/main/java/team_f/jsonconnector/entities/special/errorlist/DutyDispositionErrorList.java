package team_f.jsonconnector.entities.special.errorlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.DutyDisposition;
import team_f.jsonconnector.entities.list.ErrorList;

/**
 * maps to a specific type (required for Jackson Json API to avoid class cast exception)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DutyDispositionErrorList extends ErrorList<DutyDisposition> {
}