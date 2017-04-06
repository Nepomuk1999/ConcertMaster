package database;

/**
 * Created by Home on 06.04.2017.
 */
public class InstrumentTypeEntity {
    private int instrumentTypeId;
    private String instrumentType;

    public int getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(int instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentTypeEntity that = (InstrumentTypeEntity) o;

        if (instrumentTypeId != that.instrumentTypeId) return false;
        if (instrumentType != null ? !instrumentType.equals(that.instrumentType) : that.instrumentType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = instrumentTypeId;
        result = 31 * result + (instrumentType != null ? instrumentType.hashCode() : 0);
        return result;
    }
}
