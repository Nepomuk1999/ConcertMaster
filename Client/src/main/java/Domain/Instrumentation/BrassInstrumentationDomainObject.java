package Domain.Instrumentation;

/**
 * @author Julian
 */

public class BrassInstrumentationDomainObject implements BrassInstrumentationDomainInterface {
    private int id;
    private int horn;
    private int trumpet;
    private int trombone;
    private int tube;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getHorn() {
        return horn;
    }

    @Override
    public void setHorn(int horn) {
        this.horn = horn;
    }

    @Override
    public int getTrumpet() {
        return trumpet;
    }

    @Override
    public void setTrumpet(int trumpet) {
        this.trumpet = trumpet;
    }

    @Override
    public int getTrombone() {
        return trombone;
    }

    @Override
    public void setTrombone(int trombone) {
        this.trombone = trombone;
    }

    @Override
    public int getTube() {
        return tube;
    }

    @Override
    public void setTube(int tube) {
        this.tube = tube;
    }
}
