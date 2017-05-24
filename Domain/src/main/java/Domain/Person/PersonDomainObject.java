package Domain.Person;

import Domain.Account.AccountDomainObject;
import Domain.Duty.DutyViewInterface;
import Domain.Event.EventViewInterface;
import Enums.PersonRole;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Drazen
 */
public class PersonDomainObject implements PersonViewInterface {

    private int id;
    private String initials;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String address;
    private String phoneNumber;
    private PersonRole personRole;
    private AccountDomainObject account;
    private List<PartDomainObject> parts;
    private List<DutyViewInterface> duties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String f) {
        this.firstName = f;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String l) {
        this.lastName = l;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PersonRole getPersonRole() {
        return personRole;
    }

    public void setPersonRole(PersonRole personRole) {
        this.personRole = personRole;
    }

    public AccountDomainObject getAccount() {
        return account;
    }

    public void setAccount(AccountDomainObject account) {
        this.account = account;
    }

    public List<PartDomainObject> getParts() {
        return parts;
    }

    public void setParts(List<PartDomainObject> parts) {
        this.parts = parts;
    }

    public List<DutyViewInterface> getDuties() {
        return duties;
    }

    public void setDuties(List<DutyViewInterface> duties) {
        this.duties = duties;
    }

    public void addDuty(DutyViewInterface duty){
        if(duties == null){
            duties = new LinkedList<>();
        }
        duties.add(duty);
    }

    public void removeDuty(DutyViewInterface duty) {
        Iterator<DutyViewInterface> iterator = duties.iterator();
        boolean found = false;
        while(!found && iterator.hasNext()){
            DutyViewInterface d = iterator.next();
            if(duty.getEvent().equals(d.getEvent())){
                found = true;
            }
            iterator.remove();
        }
    }

    @Override
    public boolean playsInstrument(String instrument){
        if(parts.size() >= 1) {
            return parts.get(0).getPartType().getDescription().equals(instrument);
        }
        return false;
    }

    @Override
    public boolean isAvailable(EventViewInterface event) {
        LocalDateTime startDate = LocalDateTime.of(event.getStartDate(), event.getStartTime());
        LocalDateTime endDate = LocalDateTime.of(event.getEndDate(), event.getEndTime());
        for (DutyViewInterface duty : duties) {
            LocalDateTime tempStartDate = LocalDateTime.of(duty.getEvent().getStartDate(), duty.getEvent().getStartTime()).minusHours(3);
            LocalDateTime tempEndDate = LocalDateTime.of(duty.getEvent().getEndDate(), duty.getEvent().getEndTime()).plusHours(3);
            if ((tempStartDate.isBefore(startDate) || tempStartDate.isEqual(startDate)) && ((tempEndDate.isAfter(startDate)) || (tempEndDate.isEqual(startDate))) && ((tempEndDate.isBefore(endDate) || (tempEndDate.isEqual(endDate))))) {
                return false;
            } else if ((tempStartDate.isBefore(endDate) || tempStartDate.isEqual(endDate)) && (tempEndDate.isEqual(endDate) || tempEndDate.isAfter(endDate))) {
                return false;
            } else if ((tempStartDate.isBefore(startDate) || tempStartDate.isEqual(startDate)) && (tempEndDate.isAfter(endDate) || tempEndDate.isEqual(endDate))) {
                return false;
            } else if ((tempStartDate.isAfter(startDate) || tempStartDate.isEqual(startDate)) && (tempEndDate.isBefore(endDate) || tempEndDate.isEqual(endDate))) {
                return false;
            }
        }
        return true;
    }
}
