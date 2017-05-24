package Database.Entity;

import Enums.OrchestraRole;

import java.io.Serializable;

/**
 * @author Benjamin Grabherr
 */
class PersonOrchestraRoleEntityPK implements Serializable {
    private PersonEntity person;
    private OrchestraRole orchestraRole;

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

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

        PersonOrchestraRoleEntityPK that = (PersonOrchestraRoleEntityPK) o;

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
