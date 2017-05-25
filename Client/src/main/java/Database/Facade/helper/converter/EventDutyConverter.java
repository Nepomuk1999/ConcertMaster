package Database.Facade.helper.converter;

import Domain.Event.*;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Enums.EventStatus;
import Enums.EventType;
import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.MusicalWork;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EventDutyConverter {
    public static EventDomainInterface convert(EventDuty eventDuty) {
        EventDomainInterface eventDomain = null;

        switch (eventDuty.getEventType()) {
            case Tour:
                eventDomain = new TourEventDomain();
                break;
            case Opera:
                eventDomain = new OperaEventDomain();
                break;
            case Concert:
                eventDomain = new ConcertEventDomain();
                break;
            case Rehearsal:
                eventDomain = new RehearsalEventDomain();
                break;
            case Hofkapelle:
                eventDomain = new HofkapelleEventDomain();
                break;
            case NonMusicalEvent:
                eventDomain = new NonMusicalEventDomain();
                break;
        }

        ((EventDomain) eventDomain).setId(eventDuty.getEventDutyID());
        eventDomain.setName(eventDuty.getName());
        eventDomain.setDefaultPoints((int) eventDuty.getDefaultPoints());
        eventDomain.setStartDate(eventDuty.getStartTime().toLocalDate());
        eventDomain.setStartTime(eventDuty.getStartTime().toLocalTime());
        eventDomain.setEndDate(eventDuty.getEndTime().toLocalDate());
        eventDomain.setEndTime(eventDuty.getEndTime().toLocalTime());
        eventDomain.setEventStatus(EventStatus.valueOf(String.valueOf(eventDuty.getEventStatus())));
        eventDomain.setDescription(eventDuty.getDescription());
        eventDomain.setLocation(eventDuty.getLocation());

        if(String.valueOf(EventType.Tour).equals(String.valueOf(eventDuty.getEventType())) || String.valueOf(EventType.Concert).equals(String.valueOf(eventDuty.getEventType())) || String.valueOf(EventType.Hofkapelle).equals(String.valueOf(eventDuty.getEventType()))) {
            eventDomain.setConductor(eventDuty.getConductor());
        }

        //eventDomain.setDuties();

        if(eventDuty.getInstrumentation() != null) {
            eventDomain.setGeneralInstrumentation(InstrumentationConverter.convert(eventDuty.getInstrumentation()));
        }

        if (String.valueOf(EventType.Tour).equals(String.valueOf(eventDuty.getEventType())) || String.valueOf(EventType.Opera).equals(String.valueOf(eventDuty.getEventType()))
                || String.valueOf(EventType.Concert).equals(String.valueOf(eventDuty.getEventType())) || String.valueOf(EventType.Hofkapelle).equals(String.valueOf(eventDuty.getEventType()))) {
            List<MusicalWorkDomainInterface> musicalWorkList = new LinkedList<>();

            if(eventDuty.getMusicalWorkList() != null) {
                musicalWorkList = new ArrayList<>(eventDuty.getMusicalWorkList().size());

                if(eventDuty.getMusicalWorkList() != null) {
                    for(MusicalWork musicalWork : eventDuty.getMusicalWorkList()) {
                        musicalWorkList.add(MusicalWorkConverter.convert(musicalWork));
                    }
                }
            }

            eventDomain.setMusicalWorks(musicalWorkList);
        }

        if(eventDuty.getRehearsalFor() != null && String.valueOf(EventType.Rehearsal).equals(String.valueOf(eventDuty.getEventType()))) {
            eventDomain.setRehearsalFor(EventDutyConverter.convert(eventDuty.getRehearsalFor()));
        }

        return eventDomain;
    }
}
