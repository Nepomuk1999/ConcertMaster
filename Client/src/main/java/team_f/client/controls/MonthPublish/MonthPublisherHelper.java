package team_f.client.controls.MonthPublish;

/**
 * Created by w7pro on 30.04.2017.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.application.EventApplication;
import team_f.domain.entities.EventDuty;

import java.util.List;

public class MonthPublisherHelper
{

    public static ObservableList<Events> getEventsList(int month, int year)
    {
        ObservableList<Events> list = FXCollections.observableArrayList();
        EventApplication event=new EventApplication();
        List<EventDuty> eventlist=event.getEventsByMonth(month,year);
        for (EventDuty eventDuty:eventlist){
            list.add(new Events(eventDuty.getEventDutyId(),eventDuty.getName(),eventDuty.getStartDate("MM/DD/YYYY"),eventDuty.getEndDate("MM/DD/YYYY"),eventDuty.getConductor(),eventDuty.getLocation(),String.valueOf(eventDuty.getDefaultPoints()),eventDuty.getDescription(),eventDuty.getEventType().toString()));
        }


        return  list;
    }


    public static TableColumn<Events, Integer> getIdColumn()
    {
        TableColumn<Events, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<Events, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }


    public static TableColumn<Events, String> getNameColumn()
    {
        TableColumn<Events, String> firstNameCol = new TableColumn<>("Name");
        PropertyValueFactory<Events, String> firstNameCellValueFactory = new PropertyValueFactory<>("name");
        firstNameCol.setCellValueFactory(firstNameCellValueFactory);
        return firstNameCol;
    }


    public static TableColumn<Events, String> getStartdateColumn()
    {
        TableColumn<Events, String> lastNameCol = new TableColumn<>("Startdate");
        PropertyValueFactory<Events, String> lastNameCellValueFactory = new PropertyValueFactory<>("startdate");
        lastNameCol.setCellValueFactory(lastNameCellValueFactory);
        return lastNameCol;
    }


    public static TableColumn<Events, String> getEnddateColumn()
    {
        TableColumn<Events, String> streetCol = new TableColumn<>("Enddate");
        PropertyValueFactory<Events, String> streetCellValueFactory = new PropertyValueFactory<>("enddate");
        streetCol.setCellValueFactory(streetCellValueFactory);
        return streetCol;
    }


    public static TableColumn<Events, String> getConductorColumn()
    {
        TableColumn<Events, String> zipCodeCol = new TableColumn<>("Conductor");
        PropertyValueFactory<Events, String> zipCodeCellValueFactory = new PropertyValueFactory<>("conductor");
        zipCodeCol.setCellValueFactory(zipCodeCellValueFactory);
        return zipCodeCol;
    }


    public static TableColumn<Events, String> getLocationColumn()
    {
        TableColumn<Events, String> cityCol = new TableColumn<>("Location");
        PropertyValueFactory<Events, String> cityCellValueFactory = new PropertyValueFactory<>("location");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    public static TableColumn<Events, String> getPointsColumn()
    {
        TableColumn<Events, String> cityCol = new TableColumn<>("Points");
        PropertyValueFactory<Events, String> cityCellValueFactory = new PropertyValueFactory<>("points");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }


    public static TableColumn<Events, String> getDescriptionColumn()
    {
        TableColumn<Events, String> countryCol = new TableColumn<>("Description");
        PropertyValueFactory<Events, String> countryCellValueFactory = new PropertyValueFactory<>("description");
        countryCol.setCellValueFactory(countryCellValueFactory);
        return countryCol;
    }

    public static TableColumn<Events, String> getEventtypeColumn()
    {
        TableColumn<Events, String> countryCol = new TableColumn<>("Eventtype");
        PropertyValueFactory<Events, String> countryCellValueFactory = new PropertyValueFactory<>("eventtype");
        countryCol.setCellValueFactory(countryCellValueFactory);
        return countryCol;
    }





}
