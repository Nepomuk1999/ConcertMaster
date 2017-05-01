package team_f.client.controls.MonthPublish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.application.EventApplication;
import team_f.domain.entities.EventDuty;

import java.util.List;

public class MonthPublisherHelper {
    private static EventApplication event = new EventApplication();

    public static ObservableList<Events> getEventsList(int month, int year) {
        ObservableList<Events> list = FXCollections.observableArrayList();
        List<EventDuty> eventlist = event.getEventsByMonth(month, year);
        for (EventDuty eventDuty : eventlist) {
            list.add(new Events(eventDuty.getEventDutyId(), eventDuty.getName(), eventDuty.getStartDate("dd/MM/yyyy"), eventDuty.getEndDate("dd/MM/yyyy"), eventDuty.getConductor(), eventDuty.getLocation(), String.valueOf(eventDuty.getDefaultPoints()),
                    eventDuty.getDescription(), eventDuty.getEventType().toString(), eventDuty.getEventStatus().toString()));
        }


        return list;
    }


    public static TableColumn<Events, Integer> getIdColumn() {
        TableColumn<Events, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<Events, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    public static TableColumn<Events, Integer> getEventstatusColumn() {
        TableColumn<Events, Integer> statusCol = new TableColumn<>("Eventstatus");
        PropertyValueFactory<Events, Integer> statusCellValueFactory = new PropertyValueFactory<>("eventstatus");
        statusCol.setCellValueFactory(statusCellValueFactory);
        return statusCol;
    }

    public static TableColumn<Events, String> getNameColumn() {
        TableColumn<Events, String> nameCol = new TableColumn<>("Name");
        PropertyValueFactory<Events, String> nameCellValueFactory = new PropertyValueFactory<>("name");
        nameCol.setCellValueFactory(nameCellValueFactory);
        return nameCol;
    }


    public static TableColumn<Events, String> getStartdateColumn() {
        TableColumn<Events, String> startdateCol = new TableColumn<>("Startdate");
        PropertyValueFactory<Events, String> startdateCellValueFactory = new PropertyValueFactory<>("startdate");
        startdateCol.setCellValueFactory(startdateCellValueFactory);
        return startdateCol;
    }


    public static TableColumn<Events, String> getEnddateColumn() {
        TableColumn<Events, String> enddateCol = new TableColumn<>("Enddate");
        PropertyValueFactory<Events, String> enddateCellValueFactory = new PropertyValueFactory<>("enddate");
        enddateCol.setCellValueFactory(enddateCellValueFactory);
        return enddateCol;
    }


    public static TableColumn<Events, String> getConductorColumn() {
        TableColumn<Events, String> conductorCol = new TableColumn<>("Conductor");
        PropertyValueFactory<Events, String> conductorCellValueFactory = new PropertyValueFactory<>("conductor");
        conductorCol.setCellValueFactory(conductorCellValueFactory);
        return conductorCol;
    }


    public static TableColumn<Events, String> getLocationColumn() {
        TableColumn<Events, String> cityCol = new TableColumn<>("Location");
        PropertyValueFactory<Events, String> cityCellValueFactory = new PropertyValueFactory<>("location");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    public static TableColumn<Events, String> getPointsColumn() {
        TableColumn<Events, String> pointsCol = new TableColumn<>("Points");
        PropertyValueFactory<Events, String> pointsCellValueFactory = new PropertyValueFactory<>("points");
        pointsCol.setCellValueFactory(pointsCellValueFactory);
        return pointsCol;
    }


    public static TableColumn<Events, String> getDescriptionColumn() {
        TableColumn<Events, String> descriptionCol = new TableColumn<>("Description");
        PropertyValueFactory<Events, String> descriptionCellValueFactory = new PropertyValueFactory<>("description");
        descriptionCol.setCellValueFactory(descriptionCellValueFactory);
        return descriptionCol;
    }

    public static TableColumn<Events, String> getEventtypeColumn() {
        TableColumn<Events, String> eventtypeCol = new TableColumn<>("Eventtype");
        PropertyValueFactory<Events, String> eventtypeCellValueFactory = new PropertyValueFactory<>("eventtype");
        eventtypeCol.setCellValueFactory(eventtypeCellValueFactory);
        return eventtypeCol;
    }


}
