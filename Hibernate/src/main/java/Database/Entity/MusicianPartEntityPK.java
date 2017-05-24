package Database.Entity;

import java.io.Serializable;

/**
 * @author Benjamin Grabherr
 */
class MusicianPartEntityPK implements Serializable {
    private PersonEntity musician;
    private PartEntity part;

    public PersonEntity getMusician() {
        return musician;
    }

    public void setMusician(PersonEntity musician) {
        this.musician = musician;
    }

    public PartEntity getPart() {
        return part;
    }

    public void setPart(PartEntity part) {
        this.part = part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MusicianPartEntityPK that = (MusicianPartEntityPK) o;

        if (musician != that.musician) return false;
        if (part != that.part) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = musician.getPersonId();
        result = 31 * result + part.getPartId();
        return result;
    }
}
