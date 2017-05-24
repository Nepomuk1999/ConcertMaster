package Database.Entity;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "Instrument", schema = "sem4_team2")
public class InstrumentEntity {
    private int instrumentId;
    private InstrumentTypeEntity instrumentType;
    private String brand;
    private String model;
    private String description;
    private PersonEntity musician;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentID", nullable = false)
    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    @ManyToOne
    @JoinColumn(name = "instrumentType", nullable = false, referencedColumnName = "instrumentTypeID")
    public InstrumentTypeEntity getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentTypeEntity instrumentType) {
        this.instrumentType = instrumentType;
    }

    @Basic
    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "musician", referencedColumnName = "personId")
    public PersonEntity getMusician() {
        return musician;
    }

    public void setMusician(PersonEntity musician) {
        this.musician = musician;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentEntity that = (InstrumentEntity) o;

        if (instrumentId != that.instrumentId) return false;
        if (instrumentType != that.instrumentType) return false;
        if (brand != null ? !brand.equals(that.brand) : that.brand != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (musician != null ? !musician.equals(that.musician) : that.musician != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = instrumentId;
        result = 31 * result + instrumentType.getInstrumentTypeId();
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (musician != null ? musician.hashCode() : 0);
        return result;
    }
}
