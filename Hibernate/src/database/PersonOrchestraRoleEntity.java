package database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "Person_OrchestraRole", schema = "sem4_team2", catalog = "")
public class PersonOrchestraRoleEntity {
    private int person;

    @Basic
    @Column(name = "person", nullable = false)
    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonOrchestraRoleEntity that = (PersonOrchestraRoleEntity) o;

        if (person != that.person) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return person;
    }
}
