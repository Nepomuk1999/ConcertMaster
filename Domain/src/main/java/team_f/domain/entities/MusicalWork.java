package team_f.domain.entities;

import team_f.domain.interfaces.DomainEntity;

/**
 * Created by dominik on 13.04.17.
 */
public class MusicalWork implements DomainEntity {
    private int musicalWorkID;
    private int instrumentationID;
    private String text;
    private String composer;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }
}
