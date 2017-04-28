package team_f.domain.entities;

/**
 * Created by Christoph on 28.04.2017.
 */
public class SpecialInstrumentation {

    private String sectionType;
    private String specialInstrument;
    private int specialInstrumentCount;

    public SpecialInstrumentation (String specialInstrument, int specialInstrumentCount, String sectionType) {
        this.sectionType = sectionType;
        this.specialInstrument = specialInstrument;
        this.specialInstrumentCount = specialInstrumentCount;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        sectionType = sectionType;
    }

    public String getSpecialInstrument() {
        return specialInstrument;
    }

    public void setSpecialInstrument(String specialInstrument) {
        specialInstrument = specialInstrument;
    }

    public int getSpecialInstrumentCount() {
        return specialInstrumentCount;
    }

    public void setSpecialInstrumentCount(int specialInstrumentCount) {
        this.specialInstrumentCount = specialInstrumentCount;
    }
}
