package Domain.Instrumentation;

/**
 * @author Julian
 */

public class StringInstrumentationDomainObject implements StringInstrumentationDomainInterface {
    private int id;
    private int violin1;
    private int violin2;
    private int viola;
    private int violincello;
    private int doublebass;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getViolin1() {
        return violin1;
    }

    @Override
    public void setViolin1(int violin1) {
        this.violin1 = violin1;
    }

    @Override
    public int getViolin2() {
        return violin2;
    }

    @Override
    public void setViolin2(int violin2) {
        this.violin2 = violin2;
    }

    @Override
    public int getViola() {
        return viola;
    }

    @Override
    public void setViola(int viola) {
        this.viola = viola;
    }

    @Override
    public int getViolincello() {
        return violincello;
    }

    @Override
    public void setViolincello(int violincello) {
        this.violincello = violincello;
    }

    @Override
    public int getDoublebass() {
        return doublebass;
    }

    @Override
    public void setDoublebass(int doublebass) {
        this.doublebass = doublebass;
    }
}
