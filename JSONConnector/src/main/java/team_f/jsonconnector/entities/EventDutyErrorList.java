package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * maps to a specific type (required for Jackson Json API to avoid class cast exception)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDutyErrorList extends ErrorList<EventDuty> {
}