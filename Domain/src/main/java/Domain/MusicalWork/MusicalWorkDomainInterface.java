package Domain.MusicalWork;

import Domain.Instrumentation.InstrumentationDomainInterface;

/**
 * @author Julian
 */
public interface MusicalWorkDomainInterface extends MusicalWorkViewInterface {
    void setInstrumentation(InstrumentationDomainInterface instrumentation);

    void setName(String name);

    void setComposer(String composer);

    @Override
    InstrumentationDomainInterface getInstrumentation();
}
