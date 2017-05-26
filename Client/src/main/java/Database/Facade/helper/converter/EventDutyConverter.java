package Database.Facade.helper.converter;

import Domain.Duty.DutyViewInterface;
import Domain.Event.*;
import Domain.Instrumentation.InstrumentationDomainObject;
import Domain.MusicalWork.MusicalWorkDomainInterface;
import Domain.MusicalWork.MusicalWorkViewInterface;
import Enums.EventStatus;
import Enums.EventType;
import team_f.client.configuration.Configuration;
import team_f.client.helper.RequestResponseHelper;
import team_f.jsonconnector.common.URIList;
import team_f.jsonconnector.entities.*;
import team_f.jsonconnector.entities.list.DutyDispositionList;
import team_f.jsonconnector.enums.request.ActionType;
import team_f.jsonconnector.enums.request.DutyDispositionParameter;
import team_f.jsonconnector.enums.request.EventDutyParameter;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EventDutyConverter {
    public static EventDomainInterface convert(EventDuty eventDuty, Configuration configuration, int level) {
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
            // no null values are allowed in the view
            if(eventDomain.getConductor() != null) {
                eventDomain.setConductor(eventDuty.getConductor());
            } else {
                eventDomain.setConductor("");
            }
        }

        List<DutyDisposition> dutyDispositions = getDutyDispositions(eventDomain.getId(), configuration);

        if (dutyDispositions != null) {
            List<DutyViewInterface> dutyDispositionsList = new LinkedList<>();

            for (DutyDisposition dutyDisposition : dutyDispositions) {
                dutyDispositionsList.add(DutyDispositionConverter.convert(dutyDisposition, configuration, level -1));
            }

            eventDomain.setDuties(dutyDispositionsList);
        }

        if(!String.valueOf(EventType.NonMusicalEvent).equals(String.valueOf(eventDuty.getEventType()))) {
            if(eventDuty.getMusicalWorkList() != null) {
                int flute = 0;
                int oboe = 0;
                int clarinet = 0;
                int bassoon = 0;

                //StringInstrumentation
                int violin1 = 0;
                int violin2 = 0;
                int viola = 0;
                int violincello = 0;
                int doublebass = 0;

                //BrassInstrumentation
                int horn = 0;
                int trumpet = 0;
                int trombone = 0;
                int tube = 0;

                //PercussionInstrumentation
                int kettledrum = 0;
                int percussion = 0;
                int harp = 0;

                List<SpecialInstrumentation> specialInstrumentations = new LinkedList<>();

                Instrumentation currentInstrumenation;

                for(MusicalWork musicalWork : eventDuty.getMusicalWorkList()) {
                    currentInstrumenation = null;

                    if(musicalWork.getAlternativeInstrumentationId() != null) {
                        currentInstrumenation = musicalWork.getAlternativeInstrumentationId();
                    } else if(musicalWork.getInstrumentation() != null) {
                        currentInstrumenation = musicalWork.getInstrumentation();
                    }

                    if(currentInstrumenation != null) {
                        flute += currentInstrumenation.getFlute();
                        oboe += currentInstrumenation.getOboe();
                        clarinet += currentInstrumenation.getClarinet();
                        bassoon += currentInstrumenation.getBassoon();

                        //StringInstrumentation
                        violin1 += currentInstrumenation.getViolin1();
                        violin2 += currentInstrumenation.getViolin2();
                        viola += currentInstrumenation.getViola();
                        violincello += currentInstrumenation.getViolincello();
                        doublebass += currentInstrumenation.getDoublebass();

                        //BrassInstrumentation
                        horn += currentInstrumenation.getHorn();
                        trumpet += currentInstrumenation.getTrumpet();
                        trombone += currentInstrumenation.getTrombone();
                        tube += currentInstrumenation.getTube();

                        //PercussionInstrumentation
                        kettledrum += currentInstrumenation.getKettledrum();
                        percussion += currentInstrumenation.getPercussion();
                        harp += currentInstrumenation.getHarp();

                        if(currentInstrumenation.getSpecialInstrumentation() != null) {
                            specialInstrumentations.addAll(currentInstrumenation.getSpecialInstrumentation());
                        }
                    }
                }

                Instrumentation maxInstrumentation = new Instrumentation();
                maxInstrumentation.setFlute(flute);
                maxInstrumentation.setOboe(oboe);
                maxInstrumentation.setClarinet(clarinet);
                maxInstrumentation.setBassoon(bassoon);
                maxInstrumentation.setViolin1(violin1);
                maxInstrumentation.setViolin2(violin2);
                maxInstrumentation.setViola(viola);
                maxInstrumentation.setViolincello(violincello);
                maxInstrumentation.setDoublebass(doublebass);
                maxInstrumentation.setHorn(horn);
                maxInstrumentation.setTrumpet(trumpet);
                maxInstrumentation.setTrombone(trombone);
                maxInstrumentation.setTube(tube);
                maxInstrumentation.setKettledrum(kettledrum);
                maxInstrumentation.setPercussion(percussion);
                maxInstrumentation.setHarp(harp);
                maxInstrumentation.setSpecialInstrumentation(specialInstrumentations);

                eventDomain.setGeneralInstrumentation(InstrumentationConverter.convert(maxInstrumentation));
            } else {
                // no null values are allowed in the view
                eventDomain.setGeneralInstrumentation(InstrumentationConverter.convert(new Instrumentation()));
            }
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
            eventDomain.setRehearsalFor(EventDutyConverter.convert(eventDuty.getRehearsalFor(), configuration, level -1));
        }

        return eventDomain;
    }

    public static EventDuty convert(EventViewInterface eventDuty) {
        EventDuty result = new EventDuty();
        result.setEventDutyID(eventDuty.getId());
        result.setEventType(team_f.jsonconnector.enums.EventType.valueOf(String.valueOf(eventDuty.getEventType())));
        result.setName(eventDuty.getName());
        result.setDefaultPoints(eventDuty.getDefaultPoints());
        result.setStartTime(LocalDateTime.of(eventDuty.getStartDate(), eventDuty.getStartTime()));
        result.setEndTime(LocalDateTime.of(eventDuty.getEndDate(), eventDuty.getEndTime()));
        result.setEventStatus(team_f.jsonconnector.enums.EventStatus.valueOf(String.valueOf(eventDuty.getEventStatus())));
        result.setDescription(eventDuty.getDescription());
        result.setLocation(eventDuty.getLocation());

        // no null values are allowed in the view
        if(eventDuty.getConductor() != null) {
            result.setConductor(eventDuty.getConductor());
        } else {
            result.setConductor("");
        }

        // we do not have to use this method
        //result.setMaxInstrumentation();
        //eventDuty.getGeneralInstrumentation();

        List<MusicalWorkViewInterface> musicalWorkList = null;

        // the view throws an exception where there isn't a musicalwork available
        try {
            musicalWorkList = eventDuty.getMusicalWorks();
        } catch (Exception e) {

        }

        if(musicalWorkList != null) {
            List<MusicalWork> musicalWorks = new ArrayList<>(musicalWorkList.size());

            for(MusicalWorkViewInterface item : musicalWorkList) {
                musicalWorks.add(MusicalWorkConverter.convert(item));
            }

            result.setMusicalWorkList(musicalWorks);
        } else {
            // no null values are allowed in the view
            result.setMusicalWorkList(new LinkedList<>());
        }

        if(eventDuty.getRehearsalFor() != null) {
            result.setRehearsalFor(EventDutyConverter.convert(eventDuty.getRehearsalFor()));
        }

        return result;
    }

    private static List<DutyDisposition> getDutyDispositions(int eventID, Configuration configuration) {
        Request request = new Request();
        request.setActionType(ActionType.GET_BY_PARAMETER);

        List<Pair<String, String>> keyValueList = new LinkedList<>();
        Pair<String, String> pair;

        pair = new Pair<>();
        pair.setKey(String.valueOf(DutyDispositionParameter.EVENT_ID));
        pair.setValue("" + eventID);
        keyValueList.add(pair);

        request.setParameterKeyList(keyValueList);

        DutyDispositionList dutyDispositionList = (DutyDispositionList) RequestResponseHelper.writeAndReadJSONObject(getDutyDispositionURL(configuration), request, DutyDispositionList.class);

        if(dutyDispositionList != null) {
            List<DutyDisposition> tmpDutyDispositions = dutyDispositionList.getDutyDispositionList();

            if(tmpDutyDispositions != null) {
                List<DutyDisposition> resultList = new ArrayList<>(tmpDutyDispositions.size());

                for(DutyDisposition dutyDisposition : tmpDutyDispositions) {
                    resultList.add(dutyDisposition);
                }

                return resultList;
            }
        } else {
            System.out.println("socket error");
        }

        return new LinkedList<>();
    }

    private static URL getDutyDispositionURL(Configuration configuration) {
        try {
            return new URL(new URL(configuration.getStartURI()), URIList.dutyDisposition);
        } catch (MalformedURLException e) {
        }

        return null;
    }
}
