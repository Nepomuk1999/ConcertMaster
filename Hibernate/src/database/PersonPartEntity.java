package database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "Person_Part", schema = "sem4_team2", catalog = "")
public class PersonPartEntity {
    private int musician;
    private int part;

    @Basic
    @Column(name = "musician", nullable = false)
    public int getMusician() {
        return musician;
    }

    public void setMusician(int musician) {
        this.musician = musician;
    }

    @Basic
    @Column(name = "part", nullable = false)
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
