package Database.Entity;

import Enums.DutyRosterStatus;
import Enums.SectionType;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "SectionDutyRoster", schema = "sem4_team2")
public class SectionDutyRosterEntity {
    private int sectionDutyRosterId;
    private DutyRosterStatus dutyRosterStatus;
    private SectionType sectionType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionDutyRosterID", nullable = false)
    public int getSectionDutyRosterId() {
        return sectionDutyRosterId;
    }

    public void setSectionDutyRosterId(int sectionDutyRosterId) {
        this.sectionDutyRosterId = sectionDutyRosterId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "dutyRosterStatus", nullable = false, columnDefinition = "enum('Unpublished','Published')")
    public DutyRosterStatus getDutyRosterStatus() {
        return dutyRosterStatus;
    }

    public void setDutyRosterStatus(DutyRosterStatus dutyRosterStatus) {
        this.dutyRosterStatus = dutyRosterStatus;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "sectionType", nullable = false, columnDefinition = "enum('Violin1','Violin2','Viola','Violincello','Doublebass','Woodwind','Brass','Percussion'")
    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionDutyRosterEntity that = (SectionDutyRosterEntity) o;

        if (sectionDutyRosterId != that.sectionDutyRosterId) return false;
        if (dutyRosterStatus != null ? !dutyRosterStatus.equals(that.dutyRosterStatus) : that.dutyRosterStatus != null)
            return false;
        if (sectionType != null ? !sectionType.equals(that.sectionType) : that.sectionType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sectionDutyRosterId;
        result = 31 * result + (dutyRosterStatus != null ? dutyRosterStatus.hashCode() : 0);
        result = 31 * result + (sectionType != null ? sectionType.hashCode() : 0);
        return result;
    }
}
