package database;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dominik on 06.04.17.
 */
@Entity
@Table(name = "Request", schema = "sem4_team2", catalog = "")
public class RequestEntity {
    private int eventDuty;
    private int musician;
    private String description;

    @Basic
    @Column(name = "eventDuty", nullable = false)
    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Basic
    @Column(name = "musician", nullable = false)
    public int getMusician() {
        return musician;
    }

    public void setMusician(int musician) {
        this.musician = musician;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
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
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventDuty;
        result = 31 * result + musician;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
