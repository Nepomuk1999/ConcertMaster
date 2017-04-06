package database;

/**
 * Created by Home on 06.04.2017.
 */
public class SectionDutyRosterEntity {
    private int sectionDutyRosterId;
    private Enum dutyRosterStatus;
    private Enum sectionType;

    public int getSectionDutyRosterId() {
        return sectionDutyRosterId;
    }

    public void setSectionDutyRosterId(int sectionDutyRosterId) {
        this.sectionDutyRosterId = sectionDutyRosterId;
    }

    public Enum getDutyRosterStatus() {
        return dutyRosterStatus;
    }

    public void setDutyRosterStatus(Enum dutyRosterStatus) {
        this.dutyRosterStatus = dutyRosterStatus;
    }

    public Enum getSectionType() {
        return sectionType;
    }

    public void setSectionType(Enum sectionType) {
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
