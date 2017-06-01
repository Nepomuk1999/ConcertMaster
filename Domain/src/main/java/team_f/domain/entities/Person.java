package team_f.domain.entities;

import team_f.domain.enums.EntityType;
import team_f.domain.enums.PersonRole;
import team_f.domain.enums.AllInstrumentTypes;
import team_f.domain.enums.properties.PersonProperty;
import java.util.LinkedList;
import java.util.List;

public class Person extends BaseDomainEntity<PersonProperty> {
    private int _personID;
    private String _initials;
    private String _firstname;
    private String _lastname;
    private String _email;
    private String _gender;
    private String _address;
    private String _phoneNumber;
    private PersonRole _personRole;
    private Account _account;
    private List<Instrument> _instruments = new LinkedList<>();
    private List<AllInstrumentTypes> _playedInstruments = new LinkedList<>();
    private List<DutyDisposition> _dutyDispositions = new LinkedList<>();

    public Person() {
        super(EntityType.PERSON);
    }

    public int getPersonID() {
        return _personID;
    }

    public String getInitials() {
        return _initials;
    }

    public String getFirstname() {
        return _firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public String getEmail() {
        return _email;
    }

    public String getGender() {
        return _gender;
    }

    public String getAddress() {
        return _address;
    }

    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public PersonRole getPersonRole() {
        return _personRole;
    }

    public List<Instrument> getInstruments() {
        return _instruments;
    }

    public List<AllInstrumentTypes> getPlayedInstruments() {
        return _playedInstruments;
    }

    public Account getAccount() {
        return _account;
    }

    public List<DutyDisposition> getDutyDispositionList() {
        return _dutyDispositions;
    }

    public void setPersonID(int personID) {
        _personID = personID;
    }

    public void setInitials(String initials) {
        _initials = initials;
    }

    public void setFirstname(String firstname) {
        _firstname = firstname;
    }

    public void setLastname(String lastname) {
        _lastname = lastname;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public void setGender(String gender) {
        _gender = gender;
    }

    public void setAddress(String address) {
        _address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        _phoneNumber = phoneNumber;
    }

    public void setPersonRole(PersonRole personRole) {
        _personRole = personRole;
    }

    public void setInstruments(List<Instrument> instruments) {
        _instruments = instruments;
    }

    public void addInstrument(Instrument instrument) {
        if(_instruments == null) {
            _instruments = new LinkedList<>();
        }

        _instruments.add(instrument);
    }

    public void setPlayedInstruments(List<AllInstrumentTypes> instrumentTypes) {
        this._playedInstruments = instrumentTypes;
    }

    public void addPlayedInstrument (AllInstrumentTypes instrumentType) {
        _playedInstruments.add(instrumentType);
    }

    public void setAccount(Account account) {
        _account = account;
    }

    public void setDutyDispositions(List<DutyDisposition> dutyDispositions) {
        _dutyDispositions = dutyDispositions;
    }

    @Override
    public int getID() {
        return getPersonID();
    }
}
