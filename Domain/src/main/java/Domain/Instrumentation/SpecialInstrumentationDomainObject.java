package Domain.Instrumentation;

import Enums.SectionType;

/**
 * @author Julian
 */
public class SpecialInstrumentationDomainObject implements SpecialInstrumentationDomainInterface {
    private int id;
    private SectionType sectionType;
    private InstrumentationDomainInterface instrumentation;
    private String specialInstrument;
    private int specialInstrumentationNumber;


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public SectionType getSectionType() {
        return sectionType;
    }

    @Override
    public void setSectionType(SectionType type) {
        this.sectionType = type;
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
    public String getSpecialInstrument() {
        return specialInstrument;
    }

    @Override
    public void setSpecialInstrument(String s) {
        this.specialInstrument = s;
    }

    @Override
    public int getSpecialInstrumentationNumber() {
        return specialInstrumentationNumber;
    }

    @Override
    public void setSpecialInstrumentationNumber(int number) {
        this.specialInstrumentationNumber = number;
    }
}
