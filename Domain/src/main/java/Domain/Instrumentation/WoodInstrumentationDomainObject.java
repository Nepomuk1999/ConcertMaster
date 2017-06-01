package Domain.Instrumentation;

/**
 * @author Julian
 */
public class WoodInstrumentationDomainObject implements WoodInstrumentationDomainInterface {
    private int id;
    private int flute;
    private int oboe;
    private int clarinet;
    private int bassoon;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getFlute() {
        return flute;
    }

    @Override
    public void setFlute(int flute) {
        this.flute = flute;
    }

    @Override
    public int getOboe() {
        return oboe;
    }

    @Override
    public void setOboe(int oboe) {
        this.oboe = oboe;
    }

    @Override
    public int getClarinet() {
        return clarinet;
    }

    @Override
    public void setClarinet(int clarinet) {
        this.clarinet = clarinet;
    }

    @Override
    public int getBassoon() {
        return bassoon;
    }

    @Override
    public void setBassoon(int bassoon) {
        this.bassoon = bassoon;
    }
}
