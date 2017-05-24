package Domain.Event;

import Domain.Duty.DutyDispositionDomainObject;
import Domain.Duty.DutyViewInterface;
import Domain.Instrumentation.InstrumentationDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Enums.EventStatus;
import Exceptions.DateException;
import Exceptions.RequiredFieldMissingException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Julian
 */
public interface EventDomainInterface extends EventViewInterface {

    void setName(String name);

    void setDescription(String description);

    void setStartDate(LocalDate startDate);

    void setEndDate(LocalDate endDate);

    void setStartTime(LocalTime startTime);

    void setEndTime(LocalTime endTime);

    void setConductor(String conductor);

    void setLocation(String location);

    void setDefaultPoints(int points);

    void setEventStatus(EventStatus status);

    void setMusicalWorks(List<MusicalWorkDomainInterface> event);

    void setGeneralInstrumentation(InstrumentationDomainInterface instrumentation);

    void setRehearsalFor(EventDomainInterface event);

    void setDuties(List<DutyViewInterface> duties);

    void validate() throws DateException, RequiredFieldMissingException;

    void addDuty(DutyViewInterface duty);

    void removeDuty(DutyViewInterface duty);
}