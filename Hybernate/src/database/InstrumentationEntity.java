package database;

/**
 * Created by Home on 06.04.2017.
 */
public class InstrumentationEntity {
    private int instrumentationId;
    private int stringInstrumentation;
    private int woodInstrumentation;
    private int brassInstrumentation;
    private int percussionInstrumentation;

    public int getInstrumentationId() {
        return instrumentationId;
    }

    public void setInstrumentationId(int instrumentationId) {
        this.instrumentationId = instrumentationId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentationEntity that = (InstrumentationEntity) o;

        if (instrumentationId != that.instrumentationId) return false;
        if (stringInstrumentation != that.stringInstrumentation) return false;
        if (woodInstrumentation != that.woodInstrumentation) return false;
        if (brassInstrumentation != that.brassInstrumentation) return false;
        if (percussionInstrumentation != that.percussionInstrumentation) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = instrumentationId;
        result = 31 * result + stringInstrumentation;
        result = 31 * result + woodInstrumentation;
        result = 31 * result + brassInstrumentation;
        result = 31 * result + percussionInstrumentation;
        return result;
    }
}
