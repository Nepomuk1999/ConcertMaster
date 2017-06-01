package Domain.Event;

import Domain.Duty.DutyViewInterface;
import Domain.Instrumentation.InstrumentationViewInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;
import Enums.EventStatus;
import Enums.EventType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Julian
 */
public interface EventViewInterface {

    int getId();

    String getName();

    String getDescription();

    LocalDate getStartDate();

    LocalDate getEndDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    String getConductor();

    String getLocation();

    int getDefaultPoints();

    EventType getEventType();

    EventStatus getEventStatus();

    List<MusicalWorkViewInterface> getMusicalWorks();

    InstrumentationViewInterface getGeneralInstrumentation();

    EventViewInterface getRehearsalFor();

    List<DutyViewInterface> getDuties();

}
