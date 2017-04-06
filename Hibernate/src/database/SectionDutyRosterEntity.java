package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "SectionDutyRoster", schema = "sem4_team2", catalog = "")
public class SectionDutyRosterEntity {
    private int sectionDutyRosterId;

    @Id
    @Column(name = "sectionDutyRosterID", nullable = false)
    public int getSectionDutyRosterId() {
        return sectionDutyRosterId;
    }

    public void setSectionDutyRosterId(int sectionDutyRosterId) {
        this.sectionDutyRosterId = sectionDutyRosterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionDutyRosterEntity that = (SectionDutyRosterEntity) o;

        if (sectionDutyRosterId != that.sectionDutyRosterId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return sectionDutyRosterId;
    }
}
