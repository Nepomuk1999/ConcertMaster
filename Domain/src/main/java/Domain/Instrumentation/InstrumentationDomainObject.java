package Domain.Instrumentation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Julian
 */

public class InstrumentationDomainObject implements InstrumentationDomainInterface {
    private int id;
    private BrassInstrumentationDomainInterface brassInstrumentation;
    private PercussionInstrumentationDomainInterface percussionInstrumentation;
    private StringInstrumentationDomainInterface stringInstrumentation;
    private WoodInstrumentationDomainInterface woodInstrumentation;
    private List<SpecialInstrumentationDomainInterface> specialInstrumentation;

    public InstrumentationDomainObject() {
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public StringInstrumentationDomainInterface getStringInstrumentation() {
        return stringInstrumentation;
    }

    @Override
    public void setStringInstrumentation(StringInstrumentationDomainInterface stringInstrumentation) {
        this.stringInstrumentation = stringInstrumentation;
    }

    @Override
    public WoodInstrumentationDomainInterface getWoodInstrumentation() {
        return woodInstrumentation;
    }

    @Override
    public void setWoodInstrumentation(WoodInstrumentationDomainInterface woodInstrumentation) {
        this.woodInstrumentation = woodInstrumentation;
    }

    @Override
    public BrassInstrumentationDomainInterface getBrassInstrumentation() {
        return brassInstrumentation;
    }

    @Override
    public void setBrassInstrumentation(BrassInstrumentationDomainInterface brassInstrumentation) {
        this.brassInstrumentation = brassInstrumentation;
    }

    @Override
    public PercussionInstrumentationDomainInterface getPercussionInstrumentation() {
        return percussionInstrumentation;
    }

    @Override
    public void setPercussionInstrumentation(PercussionInstrumentationDomainInterface percussionInstrumentation) {
        this.percussionInstrumentation = percussionInstrumentation;
    }

    public List<SpecialInstrumentationViewInterface> getSpecialInstrumentation() {
        List<SpecialInstrumentationViewInterface> specialInstrumentationView = new LinkedList<>();
        specialInstrumentationView.addAll(specialInstrumentation);
        return specialInstrumentationView;
    }

    public void setSpecialInstrumentation(List<SpecialInstrumentationDomainInterface> specialInstrumentation) {
        this.specialInstrumentation = specialInstrumentation;
    }
}
