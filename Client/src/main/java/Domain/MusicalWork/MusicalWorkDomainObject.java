package Domain.MusicalWork;

import Domain.Instrumentation.InstrumentationDomainInterface;

/**
 * @author Julian
 */
public class MusicalWorkDomainObject implements MusicalWorkDomainInterface {
    private int id;
    private InstrumentationDomainInterface instrumentation;
    private String name;
    private String composer;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public InstrumentationDomainInterface getInstrumentation() {
        return instrumentation;
    }

    @Override
    public void setInstrumentation(InstrumentationDomainInterface instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getComposer() {
        return composer;
    }

    @Override
    public void setComposer(String composer) {
        this.composer = composer;
    }
}
