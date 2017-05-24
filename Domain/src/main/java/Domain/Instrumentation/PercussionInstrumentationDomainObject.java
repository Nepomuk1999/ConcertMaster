package Domain.Instrumentation;

/**
 * @author Julian
 */

public class PercussionInstrumentationDomainObject implements PercussionInstrumentationDomainInterface {
    private int id;
    private int kettledrum;
    private int percussion;
    private int harp;
    private String percussionDescription;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getKettledrum() {
        return kettledrum;
    }

    @Override
    public void setKettledrum(int kettledrum) {
        this.kettledrum = kettledrum;
    }

    @Override
    public int getPercussion() {
        return percussion;
    }

    @Override
    public void setPercussion(int percussion) {
        this.percussion = percussion;
    }

    @Override
    public int getHarp() {
        return harp;
    }

    @Override
    public void setHarp(int harp) {
        this.harp = harp;
    }

    @Override
    public String getPercussionDescription() {
        return percussionDescription;
    }

    @Override
    public void setPercussionDescription(String description) {
        this.percussionDescription = description;
    }
}
