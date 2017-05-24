package Database.Entity;

import Enums.OrchestraRole;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "Person_OrchestraRole", schema = "sem4_team2")
@IdClass(PersonOrchestraRoleEntityPK.class)
public class PersonOrchestraRoleEntity {
    private PersonEntity person;
    private OrchestraRole orchestraRole;

    @Id
    @ManyToOne
    @JoinColumn(name = "person", nullable = false, referencedColumnName = "personId")
    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Enumerated(EnumType.STRING)
    @Column(name = "orchestraRole", nullable = false, columnDefinition = "enum('Concertmaster','Section_leader','Tuttiplayer','Soloist')")
    public OrchestraRole getOrchestraRole() {
        return orchestraRole;
    }

    public void setOrchestraRole(OrchestraRole orchestraRole) {
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
        int result = person.getPersonId();
        result = 31 * result + (orchestraRole != null ? orchestraRole.hashCode() : 0);
        return result;
    }
}
