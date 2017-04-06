package database;

/**
 * Created by Home on 06.04.2017.
 */
public class PercussionInstrumentationEntity {
    private int percussionInstrumentationId;
    private int kettledrum;
    private int percussion;
    private int harp;
    private String percussionDescription;

    public int getPercussionInstrumentationId() {
        return percussionInstrumentationId;
    }

    public void setPercussionInstrumentationId(int percussionInstrumentationId) {
        this.percussionInstrumentationId = percussionInstrumentationId;
    }

    public int getKettledrum() {
        return kettledrum;
    }

    public void setKettledrum(int kettledrum) {
        this.kettledrum = kettledrum;
    }

    public int getPercussion() {
        return percussion;
    }

    public void setPercussion(int percussion) {
        this.percussion = percussion;
    }

    public int getHarp() {
        return harp;
    }

    public void setHarp(int harp) {
        this.harp = harp;
    }

    public String getPercussionDescription() {
        return percussionDescription;
    }

    public void setPercussionDescription(String percussionDescription) {
        this.percussionDescription = percussionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PercussionInstrumentationEntity that = (PercussionInstrumentationEntity) o;

        if (percussionInstrumentationId != that.percussionInstrumentationId) return false;
        if (kettledrum != that.kettledrum) return false;
        if (percussion != that.percussion) return false;
        if (harp != that.harp) return false;
        if (percussionDescription != null ? !percussionDescription.equals(that.percussionDescription) : that.percussionDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = percussionInstrumentationId;
        result = 31 * result + kettledrum;
        result = 31 * result + percussion;
        result = 31 * result + harp;
        result = 31 * result + (percussionDescription != null ? percussionDescription.hashCode() : 0);
        return result;
    }
}
