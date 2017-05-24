package Domain.Person;

import Enums.SectionType;

/**
 * @author Drazen
 */
public class PartDomainObject {

    private int id;
    private PartTypeDomainObject partType;
    private SectionType sectionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PartTypeDomainObject getPartType() {
        return partType;
    }

    public void setPartType(PartTypeDomainObject partType) {
        this.partType = partType;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }
}
