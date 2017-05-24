package Domain.MusicalWork;

import Domain.Instrumentation.InstrumentationViewInterface;

/**
 * @author Julian
 **/
public interface MusicalWorkViewInterface {
    int getId();

    InstrumentationViewInterface getInstrumentation();

    String getName();

    String getComposer();
}
