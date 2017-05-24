package Database.Entity;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "Musician_Part", schema = "sem4_team2")
@IdClass(MusicianPartEntityPK.class)
public class MusicianPartEntity {
    private PersonEntity musician;
    private PartEntity part;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musician", nullable = false, referencedColumnName = "personId")
    public PersonEntity getMusician() {
        return musician;
    }

    public void setMusician(PersonEntity musician) {
        this.musician = musician;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "part", nullable = false, referencedColumnName = "partID")
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

        MusicianPartEntity that = (MusicianPartEntity) o;

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
