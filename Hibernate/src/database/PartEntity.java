package database;

import javax.persistence.*;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "Part", schema = "sem4_team2", catalog = "")
public class PartEntity {
    private int partId;
    private int partType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartEntity that = (PartEntity) o;

        if (partId != that.partId) return false;
        if (partType != that.partType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = partId;
        result = 31 * result + partType;
        return result;
    }
}
