package Domain.Instrumentation;

import java.util.List;

/**
 * @author Julian
 */
public interface InstrumentationViewInterface {
    int getId();

    StringInstrumentationViewInterface getStringInstrumentation();

    WoodInstrumentationViewInterface getWoodInstrumentation();

    BrassInstrumentationViewInterface getBrassInstrumentation();

    PercussionInstrumentationViewInterface getPercussionInstrumentation();

    List<SpecialInstrumentationViewInterface> getSpecialInstrumentation();
}
