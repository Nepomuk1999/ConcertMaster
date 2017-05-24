package Database.Entity;

import Enums.RequestType;

import javax.persistence.*;

/**
 * @author Benjamin Grabherr
 */
@Entity
@Table(name = "Request", schema = "sem4_team2")
@IdClass(RequestEntityPK.class)
public class RequestEntity {
    private EventDutyEntity eventDuty;
    private PersonEntity musician;
    private RequestType requestType;
    private String description;

    @Id
    @ManyToOne
    @JoinColumn(name = "eventDuty", nullable = false, referencedColumnName = "eventDutyID")
    public EventDutyEntity getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(EventDutyEntity eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "musician", nullable = false, referencedColumnName = "personId")
    public PersonEntity getMusician() {
        return musician;
    }

    public void setMusician(PersonEntity musician) {
        this.musician = musician;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "requestType", nullable = false, columnDefinition = "enum('Leave_of_absence','Playrequest')")
    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestEntity that = (RequestEntity) o;

        if (eventDuty != that.eventDuty) return false;
        if (musician != that.musician) return false;
        if (requestType != null ? !requestType.equals(that.requestType) : that.requestType != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventDuty.getEventDutyId();
        result = 31 * result + musician.getPersonId();
        result = 31 * result + (requestType != null ? requestType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
