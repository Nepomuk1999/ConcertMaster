package team_f.jsonconnector.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import team_f.jsonconnector.enums.Gender;
import team_f.jsonconnector.enums.InstrumentType;
import team_f.jsonconnector.enums.PersonRole;
import team_f.jsonconnector.enums.SectionType;
import team_f.jsonconnector.interfaces.JSONObjectEntity;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person implements JSONObjectEntity {
    private int _personID;
    private String _initials;
    private String _firstname;
    private String _lastname;
    private String _email;
    private Gender _gender;
    private String _address;
    private String _phoneNumber;
    private PersonRole _personRole;
    private Account _account;
    private InstrumentType _instrumentType;

    @JsonGetter("id")
    public int getPersonID() {
        return _personID;
    }

    @JsonGetter("initials")
    public String getInitials() {
        return _initials;
    }

    @JsonGetter("firstname")
    public String getFirstname() {
        return _firstname;
    }

    @JsonGetter("lastname")
    public String getLastname() {
        return _lastname;
    }

    @JsonGetter("email")
    public String getEmail() {
        return _email;
    }

    @JsonGetter("gender")
    public Gender getGender() {
        return _gender;
    }

    @JsonGetter("address")
    public String getAddress() {
        return _address;
    }

    @JsonGetter("phone_number")
    public String getPhoneNumber() {
        return _phoneNumber;
    }

    @JsonGetter("role")
    public PersonRole getPersonRole() {
        return _personRole;
    }

    @JsonGetter("account")
    public Account getAccount() {
        return _account;
    }

    @JsonGetter("instrument_type")
    public InstrumentType getInstrumentType() {
        return _instrumentType;
    }

    @JsonSetter("id")
    public void setPersonID(int personID) {
        _personID = personID;
    }

    @JsonSetter("initials")
    public void setInitials(String initials) {
        _initials = initials;
    }

    @JsonSetter("firstname")
    public void setFirstname(String firstname) {
        _firstname = firstname;
    }

    @JsonSetter("lastname")
    public void setLastname(String lastname) {
        _lastname = lastname;
    }

    @JsonSetter("email")
    public void setEmail(String email) {
        _email = email;
    }

    @JsonSetter("gender")
    public void setGender(Gender gender) {
        _gender = gender;
    }

    @JsonSetter("address")
    public void setAddress(String address) {
        _address = address;
    }

    @JsonSetter("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        _phoneNumber = phoneNumber;
    }

    @JsonSetter("role")
    public void setPersonRole(PersonRole personRole) {
        _personRole = personRole;
    }

    @JsonSetter("account")
    public void setAccount(Account account) {
        _account = account;
    }

    @JsonSetter("instrument_type")
    public void setInstrumentType(InstrumentType instrumentType) {
        instrumentType = _instrumentType;
    }

    @Override
    public String getEntityName() {
        return "Person";
    }

    @Override
    public String getDisplayName() {
        return getFirstname() + " " + getLastname();
    }
}
