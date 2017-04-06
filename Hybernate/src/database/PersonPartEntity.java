package database;

/**
 * Created by Home on 06.04.2017.
 */
public class PersonPartEntity {
    private int musician;
    private int part;

    public int getMusician() {
        return musician;
    }

    public void setMusician(int musician) {
        this.musician = musician;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonPartEntity that = (PersonPartEntity) o;

        if (musician != that.musician) return false;
        if (part != that.part) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = musician;
        result = 31 * result + part;
        return result;
    }
}
