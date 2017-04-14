package team_f.application;

import team_f.database_wrapper.database.EventDutyEntity;
import team_f.database_wrapper.entities.EventStatus;
import team_f.database_wrapper.entities.EventType;
import team_f.database_wrapper.facade.Facade;
import team_f.domain.entities.EventDuty;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public Application (){}

    Facade facade = new Facade();

    public Integer addEvent(EventDuty eventDuty) {

        EventDutyEntity event = new EventDutyEntity();

        event.setName(eventDuty.getName());
        event.setDescription(eventDuty.getDescription());
        event.setStarttime(eventDuty.getStarttime());
        event.setEndtime(eventDuty.getEndtime());

        try {
            event.setEventType(EventType.valueOf(eventDuty.getEventType()));
            event.setEventStatus(EventStatus.valueOf(eventDuty.getEventStatus()));
        } catch(IllegalArgumentException e) {
            return 0;
        }

        event.setConductor(eventDuty.getConductor());
        event.setLocation(eventDuty.getLocation());
        event.setDefaultPoints(eventDuty.getDefaultPoints());
        event.setInstrumentation(eventDuty.getInstrumentation());

        return facade.addEvent(event);
    }

    public List<EventDuty> getEvents(int month, int year) {
        List<EventDutyEntity> entities = facade.getEvents(month, year);
        List<EventDuty> eventDuties = new ArrayList<>(entities.size());
        EventDuty event;

        for(EventDutyEntity eventDuty : entities) {
            event = new EventDuty();
            event.setConductor(eventDuty.getConductor());
            event.setDefaultPoints(eventDuty.getDefaultPoints());
            event.setDescription(eventDuty.getDescription());
            event.setEndtime(eventDuty.getEndtime());
            event.setStarttime(eventDuty.getStarttime());
            event.setEventDutyId(eventDuty.getEventDutyId());
            event.setRehearsalFor(eventDuty.getRehearsalFor());
            event.setLocation(eventDuty.getLocation());
            event.setInstrumentation(eventDuty.getInstrumentation());
            event.setName(eventDuty.getName());

            event.setEventType(String.valueOf(eventDuty.getEventType()));
            event.setEventStatus(String.valueOf(eventDuty.getEventStatus()));
            event.setEventStatus(String.valueOf(eventDuty.getEventStatus()));
        }

        return eventDuties;
    }
}
