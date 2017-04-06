package database;

/**
 * Created by Home on 06.04.2017.
 */
public class PartEntity {
    private int partId;
    private int partType;

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

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
