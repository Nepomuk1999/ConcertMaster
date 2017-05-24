package Database.Entity;

import Enums.SectionType;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "SpecialInstrumentation", schema = "sem4_team2")
public class SpecialInstrumentationEntity {
    private int specialInstrumentationId;
    private SectionType sectionType;
    private InstrumentationEntity instrumentationId;
    private String specialInstrument;
    private int specialInstrumentationNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialInstrumentationID", nullable = false)
    public int getSpecialInstrumentationId() {
        return specialInstrumentationId;
    }

    public void setSpecialInstrumentationId(int specialInstrumentationId) {
        this.specialInstrumentationId = specialInstrumentationId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "sectionType", nullable = false, columnDefinition = "enum('Violin1','Violin2','Viola','Violincello','Doublebass','Woodwind','Brass','Percussion')")
    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    @ManyToOne
    @JoinColumn(name = "instrumentationID", nullable = false, referencedColumnName = "instrumentationID")
    public InstrumentationEntity getInstrumentationId() {
        return instrumentationId;
    }

    public void setInstrumentationId(InstrumentationEntity instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    @Basic
    @Column(name = "specialInstrument", nullable = false)
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
        if (sectionType != null ? !sectionType.equals(that.sectionType) : that.sectionType != null) return false;
        if (specialInstrument != null ? !specialInstrument.equals(that.specialInstrument) : that.specialInstrument != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = specialInstrumentationId;
        result = 31 * result + (sectionType != null ? sectionType.hashCode() : 0);
        result = 31 * result + instrumentationId.getInstrumentationId();
        result = 31 * result + (specialInstrument != null ? specialInstrument.hashCode() : 0);
        result = 31 * result + specialInstrumentationNumber;
        return result;
    }
}
