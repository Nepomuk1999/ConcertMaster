package team_f.client.pages.musicianmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.client.entities.KeyValuePair;
import team_f.jsonconnector.entities.Person;
import team_f.jsonconnector.enums.AccountRole;
import team_f.jsonconnector.enums.Gender;
import team_f.jsonconnector.enums.PersonRole;

public class MusicianTableHelper {
    // Returns an observable list of persons
    public static ObservableList<Person> getPersonList() {
        // @todo: load the data dynamically

        return FXCollections.observableArrayList();
    }

    // Returns PersonTestData Id TableColumn
    public static TableColumn<Person, Integer> getIdColumn() {
        TableColumn<Person, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<Person, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    // Returns First Name TableColumn
    public static TableColumn<Person, String> getFirstNameColumn() {
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        PropertyValueFactory<Person, String> firstNameCellValueFactory = new PropertyValueFactory<>("firstName");
        firstNameCol.setCellValueFactory(firstNameCellValueFactory);
        return firstNameCol;
    }

    // Returns Last Name TableColumn
    public static TableColumn<Person, String> getLastNameColumn() {
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        PropertyValueFactory<Person, String> lastNameCellValueFactory = new PropertyValueFactory<>("lastName");
        lastNameCol.setCellValueFactory(lastNameCellValueFactory);
        return lastNameCol;
    }

    // Returns Street TableColumn
    public static TableColumn<Person, String> getStreetColumn() {
        TableColumn<Person, String> streetCol = new TableColumn<>("Street");
        PropertyValueFactory<Person, String> streetCellValueFactory = new PropertyValueFactory<>("street");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    }

    // Returns ZipCode TableColumn
    public static TableColumn<Person, String> getZipCodeColumn() {
        TableColumn<Person, String> zipCodeCol = new TableColumn<>("Email");
        PropertyValueFactory<Person, String> zipCodeCellValueFactory = new PropertyValueFactory<>("email");
        zipCodeCol.setCellValueFactory(zipCodeCellValueFactory);
        return zipCodeCol;
    }

    // Returns City TableColumn
    public static TableColumn<Person, String> getPhonenumberColumn() {
        TableColumn<Person, String> cityCol = new TableColumn<>("Phonenumber");
        PropertyValueFactory<Person, String> cityCellValueFactory = new PropertyValueFactory<>("phonenumber");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    // Returns Phone TableColumn
    public static TableColumn<Person, String> getRoleColumn() {
        TableColumn<Person, String> cityCol = new TableColumn<>("Role");
        PropertyValueFactory<Person, String> cityCellValueFactory = new PropertyValueFactory<>("role");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    // Returns Section TableColumn
    public static TableColumn<Person, String> getSectionColumn() {
        TableColumn<Person, String> countryCol = new TableColumn<>("Section");
        PropertyValueFactory<Person, String> countryCellValueFactory = new PropertyValueFactory<>("section");
        countryCol.setCellValueFactory(countryCellValueFactory);
        return countryCol;
    }

    public static TableColumn<Person, String> getInstrumentColumn() {
        TableColumn<Person, String> countryCol = new TableColumn<>("Instruments");
        PropertyValueFactory<Person, String> countryCellValueFactory = new PropertyValueFactory<>("instrument");
        countryCol.setCellValueFactory(countryCellValueFactory);
        return countryCol;
    }

    public static ObservableList<KeyValuePair> getPersonRoleList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("Musician", PersonRole.Musician),
                new KeyValuePair("External Musician", PersonRole.External_musician),
                new KeyValuePair("Manager", PersonRole.Manager),
                new KeyValuePair("Musician Librarian", PersonRole.Music_librarian),
                new KeyValuePair("Orchestral Facility Manager", PersonRole.Orchestral_facility_manager),
                new KeyValuePair("Substitute", PersonRole.Substitute));

        return list;
    }

    public static ObservableList<KeyValuePair> getAccountRoleList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("Musician", AccountRole.Musician),
                new KeyValuePair("Administrator", AccountRole.Administrator),
                new KeyValuePair("Manager", AccountRole.Manager),
                new KeyValuePair("Section Representative", AccountRole.Section_representative),
                new KeyValuePair("Substitute", AccountRole.Substitute));

        return list;
    }

    public static ObservableList<KeyValuePair> getGenderList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("Female", Gender.FEMALE),
                new KeyValuePair("Male", Gender.MALE));

        return list;
    }
}
