package team_f.client.controls.MusicianManagement;

public class PersonTestData {
    // Properties of the person (name, address, job)
    private Integer id;
    private String firstName;
    private String lastName;
    private String street;
    private String email;
    private String phonenumber;
    private String role;
    private String instrument;
    private String section;

    public PersonTestData(Integer id, String firstName, String lastName, String street, String email, String phonenumber, String role, String section, String instrument) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.email = email;
        this.phonenumber = phonenumber;
        this.role = role;
        this.section = section;
        this.instrument = instrument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
