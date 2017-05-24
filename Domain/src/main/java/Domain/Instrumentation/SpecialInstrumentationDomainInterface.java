package Domain.Instrumentation;

import Enums.SectionType;

/**
 * @author Julian
 */
public interface SpecialInstrumentationDomainInterface extends SpecialInstrumentationViewInterface {
    void setSectionType(SectionType type);

    @Override
    InstrumentationDomainInterface getInstrumentation();

    void setInstrumentation(InstrumentationDomainInterface instrumentation);

    void setSpecialInstrument(String s);

    void setSpecialInstrumentationNumber(int number);

}
