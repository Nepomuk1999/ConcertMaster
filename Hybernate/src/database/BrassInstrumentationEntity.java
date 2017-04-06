package database;

/**
 * Created by Home on 06.04.2017.
 */
public class BrassInstrumentationEntity {
    private int brassInstrumentationId;
    private int horn;
    private int trumpet;
    private int trombone;
    private int tube;

    public int getBrassInstrumentationId() {
        return brassInstrumentationId;
    }

    public void setBrassInstrumentationId(int brassInstrumentationId) {
        this.brassInstrumentationId = brassInstrumentationId;
    }

    public int getHorn() {
        return horn;
    }

    public void setHorn(int horn) {
        this.horn = horn;
    }

    public int getTrumpet() {
        return trumpet;
    }

    public void setTrumpet(int trumpet) {
        this.trumpet = trumpet;
    }

    public int getTrombone() {
        return trombone;
    }

    public void setTrombone(int trombone) {
        this.trombone = trombone;
    }

    public int getTube() {
        return tube;
    }

    public void setTube(int tube) {
        this.tube = tube;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrassInstrumentationEntity that = (BrassInstrumentationEntity) o;

        if (brassInstrumentationId != that.brassInstrumentationId) return false;
        if (horn != that.horn) return false;
        if (trumpet != that.trumpet) return false;
        if (trombone != that.trombone) return false;
        if (tube != that.tube) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = brassInstrumentationId;
        result = 31 * result + horn;
        result = 31 * result + trumpet;
        result = 31 * result + trombone;
        result = 31 * result + tube;
        return result;
    }
}
