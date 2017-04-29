package team_f.client.controls.MusicianManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class MusicianTableHelper
{
	// Returns an observable list of persons
	public static ObservableList<PersonTestData> getPersonList()
	{
		PersonTestData p1 = new PersonTestData(1,"Hans","Müller","Street 2","example@gmail.com","06651111111","Musician","1. Section", "Violine");
		PersonTestData p2 = new PersonTestData(2,"Karl","Mayer","Street 3","example@gmail.com","06651111111","Substitut","3. Section","Violine ");
		PersonTestData p3 = new PersonTestData(3,"David","Grabherr","Street 5","example@gmail.com","06651111111","External Musician","6. Section","Violine");
		PersonTestData p4 = new PersonTestData(4,"Lisa","Miller","Street 86","example@gmail.com","06651111111","Orchestra Facility Manager","2. Section","Violine");
		PersonTestData p5 = new PersonTestData(5,"Martha","Feuerstein","Street 375","example@gmail.com","06651111111","Music Librarian","8. Section","Violine");
		PersonTestData p6 = new PersonTestData(6,"Stefan","Würth","Street 3","example@gmail.com","06651111111","Manager","7. Section","Violine");

		return FXCollections.<PersonTestData>observableArrayList(p1, p2, p3, p4, p5, p6);
	}

	// Returns PersonTestData Id TableColumn
	public static TableColumn<PersonTestData, Integer> getIdColumn()
	{
		TableColumn<PersonTestData, Integer> idCol = new TableColumn<>("Id");
		PropertyValueFactory<PersonTestData, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
		idCol.setCellValueFactory(idCellValueFactory);
		return idCol;
	}

	// Returns First Name TableColumn
	public static TableColumn<PersonTestData, String> getFirstNameColumn()
	{
		TableColumn<PersonTestData, String> firstNameCol = new TableColumn<>("First Name");
		PropertyValueFactory<PersonTestData, String> firstNameCellValueFactory = new PropertyValueFactory<>("firstName");
		firstNameCol.setCellValueFactory(firstNameCellValueFactory);
		return firstNameCol;
	}

	// Returns Last Name TableColumn
	public static TableColumn<PersonTestData, String> getLastNameColumn()
	{
		TableColumn<PersonTestData, String> lastNameCol = new TableColumn<>("Last Name");
		PropertyValueFactory<PersonTestData, String> lastNameCellValueFactory = new PropertyValueFactory<>("lastName");
		lastNameCol.setCellValueFactory(lastNameCellValueFactory);
		return lastNameCol;
	}

	// Returns Street TableColumn
	public static TableColumn<PersonTestData, String> getStreetColumn()
	{
		TableColumn<PersonTestData, String> streetCol = new TableColumn<>("Street");
		PropertyValueFactory<PersonTestData, String> streetCellValueFactory = new PropertyValueFactory<>("street");
		streetCol.setCellValueFactory(streetCellValueFactory);
		return streetCol;
	}

	// Returns ZipCode TableColumn
	public static TableColumn<PersonTestData, String> getZipCodeColumn()
	{
		TableColumn<PersonTestData, String> zipCodeCol = new TableColumn<>("Email");
		PropertyValueFactory<PersonTestData, String> zipCodeCellValueFactory = new PropertyValueFactory<>("email");
		zipCodeCol.setCellValueFactory(zipCodeCellValueFactory);
		return zipCodeCol;
	}

	// Returns City TableColumn
	public static TableColumn<PersonTestData, String> getPhonenumberColumn()
	{
		TableColumn<PersonTestData, String> cityCol = new TableColumn<>("Phonenumber");
		PropertyValueFactory<PersonTestData, String> cityCellValueFactory = new PropertyValueFactory<>("phonenumber");
		cityCol.setCellValueFactory(cityCellValueFactory);
		return cityCol;
	}
	// Returns Phone TableColumn
	public static TableColumn<PersonTestData, String> getRoleColumn()
	{
		TableColumn<PersonTestData, String> cityCol = new TableColumn<>("Role");
		PropertyValueFactory<PersonTestData, String> cityCellValueFactory = new PropertyValueFactory<>("role");
		cityCol.setCellValueFactory(cityCellValueFactory);
		return cityCol;
	}

	// Returns Section TableColumn
	public static TableColumn<PersonTestData, String> getSectionColumn()
	{
		TableColumn<PersonTestData, String> countryCol = new TableColumn<>("Section");
		PropertyValueFactory<PersonTestData, String> countryCellValueFactory = new PropertyValueFactory<>("section");
		countryCol.setCellValueFactory(countryCellValueFactory);
		return countryCol;
	}

	public static TableColumn<PersonTestData, String> getInstrumentColumn()
	{
		TableColumn<PersonTestData, String> countryCol = new TableColumn<>("Instruments");
		PropertyValueFactory<PersonTestData, String> countryCellValueFactory = new PropertyValueFactory<>("instrument");
		countryCol.setCellValueFactory(countryCellValueFactory);
		return countryCol;
	}

}
