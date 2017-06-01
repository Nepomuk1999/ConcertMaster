package UseCases.Event;

import Domain.Event.*;
import Domain.Instrumentation.InstrumentationDomainInterface;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */
class EventValueTranslator {

    public static EventValueContainer translateToValueContainer(EventViewInterface viewInterface) {
        EventValueContainer values = new EventValueContainer(viewInterface.getId(), viewInterface.getEventType());
        values.setName(viewInterface.getName());
        values.setDescription(viewInterface.getDescription());
        values.setStartDate(viewInterface.getStartDate());
        values.setEndDate(viewInterface.getEndDate());
        values.setStartTime(viewInterface.getStartTime());
        values.setEndTime(viewInterface.getEndTime());
        values.setConductor(viewInterface.getConductor());
        values.setLocation(viewInterface.getLocation());
        values.setEventStatus(viewInterface.getEventStatus());
        values.setDefaultPoints(viewInterface.getDefaultPoints());
        values.setMusicalWorks(viewInterface.getMusicalWorks());
        values.setGeneralInstrumentation(viewInterface.getGeneralInstrumentation());
        values.setRehearsalFor(viewInterface.getRehearsalFor());
        return values;
    }

    public static EventDomainInterface translateToDomainInterface(EventValueContainer values) {
        EventDomainInterface eventInterface = null;
        switch (values.getEventType()) {
            case Tour:
                eventInterface = new TourEventDomain();
                ((TourEventDomain) eventInterface).setId(values.getId());
                eventInterface.setDescription(values.getDescription());
                eventInterface.setConductor(values.getConductor());
                eventInterface.setGeneralInstrumentation((InstrumentationDomainInterface) values.getGeneralInstrumentation());
                eventInterface.setMusicalWorks(getMusicalWorkDomainList(values.getMusicalWorks()));
                break;
            case Opera:
                eventInterface = new OperaEventDomain();
                ((OperaEventDomain) eventInterface).setId(values.getId());
                eventInterface.setDescription(values.getDescription());
                eventInterface.setConductor(values.getConductor());
                eventInterface.setGeneralInstrumentation((InstrumentationDomainInterface) values.getGeneralInstrumentation());
                eventInterface.setMusicalWorks(getMusicalWorkDomainList(values.getMusicalWorks()));
                break;
            case Concert:
                eventInterface = new ConcertEventDomain();
                ((ConcertEventDomain) eventInterface).setId(values.getId());
                eventInterface.setDescription(values.getDescription());
                eventInterface.setConductor(values.getConductor());
                eventInterface.setGeneralInstrumentation((InstrumentationDomainInterface) values.getGeneralInstrumentation());
                eventInterface.setMusicalWorks(getMusicalWorkDomainList(values.getMusicalWorks()));
                break;
            case Hofkapelle:
                eventInterface = new HofkapelleEventDomain();
                ((HofkapelleEventDomain) eventInterface).setId(values.getId());
                eventInterface.setDescription(values.getDescription());
                eventInterface.setConductor(values.getConductor());
                eventInterface.setGeneralInstrumentation((InstrumentationDomainInterface) values.getGeneralInstrumentation());
                eventInterface.setMusicalWorks(getMusicalWorkDomainList(values.getMusicalWorks()));
                break;
            case NonMusicalEvent:
                eventInterface = new NonMusicalEventDomain();
                ((NonMusicalEventDomain) eventInterface).setId(values.getId());
                eventInterface.setDescription(values.getDescription());
                break;
            case Rehearsal:
                eventInterface = new RehearsalEventDomain();
                ((RehearsalEventDomain) eventInterface).setId(values.getId());
                eventInterface.setGeneralInstrumentation((InstrumentationDomainInterface) values.getGeneralInstrumentation());
                eventInterface.setRehearsalFor((EventDomainInterface) values.getRehearsalFor());
                break;
        }
        eventInterface.setName(values.getName());
        eventInterface.setStartDate(values.getStartDate());
        eventInterface.setEndDate(values.getEndDate());
        eventInterface.setStartTime(values.getStartTime());
        eventInterface.setEndTime(values.getEndTime());
        eventInterface.setLocation(values.getLocation());
        eventInterface.setEventStatus(values.getEventStatus());
        eventInterface.setDefaultPoints(values.getDefaultPoints());
        return eventInterface;
    }

    public static EventViewInterface translateToViewInterface(EventValueContainer values) {
        return translateToDomainInterface(values);
    }

    private static List<MusicalWorkDomainInterface> getMusicalWorkDomainList(List<MusicalWorkViewInterface> musicalWorks) {
        List<MusicalWorkDomainInterface> musicalWorkDomainList = null;
        if ((musicalWorks != null) && (musicalWorks.size() > 0)) {
            musicalWorkDomainList = new LinkedList<>();
            for (MusicalWorkViewInterface mWork : musicalWorks) {
                musicalWorkDomainList.add((MusicalWorkDomainInterface) mWork);
            }
        }
        return musicalWorkDomainList;
    }
}
