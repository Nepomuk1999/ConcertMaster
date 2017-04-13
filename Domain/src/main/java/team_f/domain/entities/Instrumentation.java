package team_f.domain.entities;

import team_f.domain.interfaces.DomainEntity;

/**
 * Created by dominik on 13.04.17.
 */
public class Instrumentation implements DomainEntity {
    private int instrumentationID;
    private int stringInstrumentation;
    private int woodInstrumentation;
    private int brassInstrumentation;
    private int percussionInstrumentation;


    public int getInstrumentationID() {
        return instrumentationID;
    }

    public void setInstrumentationID(int instrumentationID) {
        this.instrumentationID = instrumentationID;
    }

    public int getStringInstrumentation() {
        return stringInstrumentation;
    }

    public void setStringInstrumentation(int stringInstrumentation) {
        this.stringInstrumentation = stringInstrumentation;
    }

    public int getWoodInstrumentation() {
        return woodInstrumentation;
    }

    public void setWoodInstrumentation(int woodInstrumentation) {
        this.woodInstrumentation = woodInstrumentation;
    }

    public int getBrassInstrumentation() {
        return brassInstrumentation;
    }

    public void setBrassInstrumentation(int brassInstrumentation) {
        this.brassInstrumentation = brassInstrumentation;
    }

    public int getPercussionInstrumentation() {
        return percussionInstrumentation;
    }

    public void setPercussionInstrumentation(int percussionInstrumentation) {
        this.percussionInstrumentation = percussionInstrumentation;
    }
}
