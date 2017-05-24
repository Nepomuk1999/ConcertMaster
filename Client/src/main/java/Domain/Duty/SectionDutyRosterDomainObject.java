package Domain.Duty;

import Domain.Event.EventDomainInterface;
import Enums.DutyRosterStatus;
import Enums.SectionType;

/**
 * @author Julian
 */
public class SectionDutyRosterDomainObject {
    private EventDomainInterface event;
    private DutyRosterStatus status;
    private SectionType sectionType;

    public EventDomainInterface getEvent() {
        return event;
    }

    public void setEvent(EventDomainInterface event) {
        this.event = event;
    }

    public DutyRosterStatus getStatus() {
        return status;
    }

    public void setStatus(DutyRosterStatus status) {
        this.status = status;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }
}
