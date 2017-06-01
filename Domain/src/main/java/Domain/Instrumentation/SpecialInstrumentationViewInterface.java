package Domain.Instrumentation;

import Enums.SectionType;

/**
 * @author Julian
 */
public interface SpecialInstrumentationViewInterface {
    int getId();

    SectionType getSectionType();

    InstrumentationViewInterface getInstrumentation();

    String getSpecialInstrument();

    int getSpecialInstrumentationNumber();
}
