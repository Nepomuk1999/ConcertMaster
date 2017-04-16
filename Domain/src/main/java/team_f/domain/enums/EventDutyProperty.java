package team_f.domain.enums;

import team_f.domain.interfaces.DomainEntityProperty;

public enum EventDutyProperty implements DomainEntityProperty {
    ID,
    NAME,
    DESCRIPTION,
    START_DATE,
    END_DATE,
    EVENT_TYPE,
    EVENT_STATUS,
    CONDUCTOR,
    LOCATION,
    REHEARSAL_FOR,
    DEFAULT_POINTS,
    INSTRUMENTATION,
    DISPOSITION_LIST,
    MUSICAL_WORK_LIST,
    SECTION_DUTY_ROSTER_LIST,
    REQUEST_LIST,

    // only used to parse objects from the view
    START_TIME,
    END_TIME,
    ALTERNATIVE_INSTRUMENTATION_LIST
}
