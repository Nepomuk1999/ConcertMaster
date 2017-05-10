package team_f.jsonconnector.entities.special;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import team_f.jsonconnector.entities.list.ErrorList;
import team_f.jsonconnector.entities.EventDuty;

/**
 * maps to a specific type (required for Jackson Json API to avoid class cast exception)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDutyErrorList extends ErrorList<EventDuty> {
}