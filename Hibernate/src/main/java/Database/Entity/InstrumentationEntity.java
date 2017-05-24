package Database.Entity;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "Instrumentation", schema = "sem4_team2")
public class InstrumentationEntity {
    private int instrumentationId;
    private StringInstrumentationEntity stringInstrumentation;
    private WoodInstrumentationEntity woodInstrumentation;
    private BrassInstrumentationEntity brassInstrumentation;
    private PercussionInstrumentationEntity percussionInstrumentation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrumentationID", nullable = false)
    public int getInstrumentationId() {
        return instrumentationId;
    }

    public void setInstrumentationId(int instrumentationId) {
        this.instrumentationId = instrumentationId;
    }

    @ManyToOne
    @JoinColumn(name = "stringInstrumentation", nullable = false, referencedColumnName = "stringInstrumentationID")
    public StringInstrumentationEntity getStringInstrumentation() {
        return stringInstrumentation;
    }

    public void setStringInstrumentation(StringInstrumentationEntity stringInstrumentation) {
        this.stringInstrumentation = stringInstrumentation;
    }

    @ManyToOne
    @JoinColumn(name = "woodInstrumentation", nullable = false, referencedColumnName = "woodInstrumentationID")
    public WoodInstrumentationEntity getWoodInstrumentation() {
        return woodInstrumentation;
    }

    public void setWoodInstrumentation(WoodInstrumentationEntity woodInstrumentation) {
        this.woodInstrumentation = woodInstrumentation;
    }

    @ManyToOne
    @JoinColumn(name = "brassInstrumentation", nullable = false, referencedColumnName = "brassInstrumentationID")
    public BrassInstrumentationEntity getBrassInstrumentation() {
        return brassInstrumentation;
    }

    public void setBrassInstrumentation(BrassInstrumentationEntity brassInstrumentation) {
        this.brassInstrumentation = brassInstrumentation;
    }

    @ManyToOne
    @JoinColumn(name = "percussionInstrumentation", nullable = false, referencedColumnName = "percussionInstrumentationID")
    public PercussionInstrumentationEntity getPercussionInstrumentation() {
        return percussionInstrumentation;
    }

    public void setPercussionInstrumentation(PercussionInstrumentationEntity percussionInstrumentation) {
        this.percussionInstrumentation = percussionInstrumentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstrumentationEntity that = (InstrumentationEntity) o;

        if (instrumentationId != that.instrumentationId) return false;
        if (stringInstrumentation != that.stringInstrumentation) return false;
        if (woodInstrumentation != that.woodInstrumentation) return false;
        if (brassInstrumentation != that.brassInstrumentation) return false;
        if (percussionInstrumentation != that.percussionInstrumentation) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = instrumentationId;
        result = 31 * result + stringInstrumentation.getStringInstrumentationId();
        result = 31 * result + woodInstrumentation.getWoodInstrumentationId();
        result = 31 * result + brassInstrumentation.getBrassInstrumentationId();
        result = 31 * result + percussionInstrumentation.getPercussionInstrumentationId();
        return result;
    }
}
