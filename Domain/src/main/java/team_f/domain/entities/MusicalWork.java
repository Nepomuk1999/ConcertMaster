package team_f.domain.entities;

import team_f.domain.interfaces.DomainEntity;

public class MusicalWork implements DomainEntity {
    private int musicalWorkID;
    private int instrumentationID;
    private String name;
    private String composer;
    private int alternativeInstrumentationId;

    public int getMusicalWorkID() {
        return musicalWorkID;
    }

    public void setMusicalWorkID(int musicalWorkID) {
        this.musicalWorkID = musicalWorkID;
    }

    public int getInstrumentationID() {
        return instrumentationID;
    }

    public void setInstrumentationID(int instrumentationID) {
        this.instrumentationID = instrumentationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public void setAlternativeInstrumentationId(int alternativeInstrumentationId) {
        this.alternativeInstrumentationId = alternativeInstrumentationId;
    }

    public int getAlternativeInstrumentationId() {
        return this.alternativeInstrumentationId;
    }
}
