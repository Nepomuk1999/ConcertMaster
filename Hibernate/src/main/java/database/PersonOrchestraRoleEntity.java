package database;

import javax.persistence.*;

/**
 * Created by Home on 06.04.2017.
 */

/**
 * , catalog = ""aus @Table am Schluss entfernt
 */
@Entity
@Table(name = "Person_OrchestraRole", schema = "sem4_team2")
@IdClass(PersonOrchestraRoleEntityPK.class)
public class PersonOrchestraRoleEntity {
    private int person;
    private Enum orchestraRole;

    @Id
    @Column(name = "person", nullable = false)
    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    @Id
    @Column(name = "orchestraRole", nullable = false)
    public Enum getOrchestraRole() {
        return orchestraRole;
    }

    public void setOrchestraRole(Enum orchestraRole) {
        this.orchestraRole = orchestraRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonOrchestraRoleEntity that = (PersonOrchestraRoleEntity) o;

        if (person != that.person) return false;
        if (orchestraRole != null ? !orchestraRole.equals(that.orchestraRole) : that.orchestraRole != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = person;
        result = 31 * result + (orchestraRole != null ? orchestraRole.hashCode() : 0);
        return result;
    }
}
