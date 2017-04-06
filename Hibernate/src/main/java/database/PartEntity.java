package database;

import javax.persistence.*;

/**
 * Created by Home on 06.04.2017.
 */
@Entity
@Table(name = "Part", schema = "sem4_team2")
public class PartEntity {
    private int partId;
    private int partType;
    private Enum sectionType;

    @Id
    @Column(name = "partID", nullable = false)
    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    @Basic
    @Column(name = "partType", nullable = false)
    public int getPartType() {
        return partType;
    }

    public void setPartType(int partType) {
        this.partType = partType;
    }

    @Basic
    @Column(name = "sectionType", nullable = false, precision = 0)
    public Enum getSectionType() {
        return sectionType;
    }

    public void setSectionType(Enum sectionType) {
        this.sectionType = sectionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartEntity that = (PartEntity) o;

        if (partId != that.partId) return false;
        if (partType != that.partType) return false;
        if (sectionType != null ? !sectionType.equals(that.sectionType) : that.sectionType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = partId;
        result = 31 * result + partType;
        result = 31 * result + (sectionType != null ? sectionType.hashCode() : 0);
        return result;
    }
}
