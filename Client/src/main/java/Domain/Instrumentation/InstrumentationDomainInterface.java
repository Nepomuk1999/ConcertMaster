package Domain.Instrumentation;

import java.util.List;

/**
 * @author Julian
 */
public interface InstrumentationDomainInterface extends InstrumentationViewInterface {
    @Override
    StringInstrumentationDomainInterface getStringInstrumentation();

    void setStringInstrumentation(StringInstrumentationDomainInterface stringInstrumentation);

    @Override
    WoodInstrumentationDomainInterface getWoodInstrumentation();

    void setWoodInstrumentation(WoodInstrumentationDomainInterface woodInstrumentation);

    @Override
    BrassInstrumentationDomainInterface getBrassInstrumentation();

    void setBrassInstrumentation(BrassInstrumentationDomainInterface brassInstrumentation);

    @Override
    PercussionInstrumentationDomainInterface getPercussionInstrumentation();

    void setPercussionInstrumentation(PercussionInstrumentationDomainInterface percussionInstrumentation);

    void setSpecialInstrumentation(List<SpecialInstrumentationDomainInterface> specialInstrumentation);
}
