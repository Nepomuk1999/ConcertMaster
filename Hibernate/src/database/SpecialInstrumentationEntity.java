package database;

import javax.persistence.*;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "SpecialInstrumentation", schema = "sem4_team2", catalog = "")
public class SpecialInstrumentationEntity {
    private int specialInstrumentationId;
    private int instrumentationId;
    private String specialInstrument;
    private int specialInstrumentationNumber;

    @Id
    @Column(name = "specialInstrumentationID", nullable = false)
    public int getSpecialInstrumentationId() {
        return specialInstrumentationId;
    }

    public void setSpecialInstrumentationId(int specialInstrumentationId) {
        this.specialInstrumentationId = specialInstrumentationId;
    }

    @Basic
    @Column(name = "instrumentationID", nullable = false)
    public int getInstrumentationId() {
        return instrumentationId;
    }

    public void setInstrumentationId(int instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    @Basic
    @Column(name = "specialInstrument", nullable = false, length = 255)
    public String getSpecialInstrument() {
        return specialInstrument;
    }

    public void setSpecialInstrument(String specialInstrument) {
        this.specialInstrument = specialInstrument;
    }

    @Basic
    @Column(name = "specialInstrumentationNumber", nullable = false)
    public int getSpecialInstrumentationNumber() {
        return specialInstrumentationNumber;
    }

    public void setSpecialInstrumentationNumber(int specialInstrumentationNumber) {
        this.specialInstrumentationNumber = specialInstrumentationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpecialInstrumentationEntity that = (SpecialInstrumentationEntity) o;

        if (specialInstrumentationId != that.specialInstrumentationId) return false;
        if (instrumentationId != that.instrumentationId) return false;
        if (specialInstrumentationNumber != that.specialInstrumentationNumber) return false;
        if (specialInstrument != null ? !specialInstrument.equals(that.specialInstrument) : that.specialInstrument != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = specialInstrumentationId;
        result = 31 * result + instrumentationId;
        result = 31 * result + (specialInstrument != null ? specialInstrument.hashCode() : 0);
        result = 31 * result + specialInstrumentationNumber;
        return result;
    }
}
