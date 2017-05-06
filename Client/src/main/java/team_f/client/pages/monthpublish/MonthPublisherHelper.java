package team_f.client.pages.monthpublish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.client.helper.RequestResponseHelper;
import team_f.jsonconnector.entities.EventDuty;
import team_f.jsonconnector.entities.EventDutyList;
import team_f.jsonconnector.entities.Pair;
import team_f.jsonconnector.entities.Request;
import team_f.jsonconnector.enums.request.ActionType;
import team_f.jsonconnector.enums.request.EventDutyParameter;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class MonthPublisherHelper {
    public static ObservableList<Event> getEventsList(URL url, int month, int year) {
        ObservableList<Event> list = FXCollections.observableArrayList();

        Request request = new Request();
        request.setActionType(ActionType.GET_BY_PARAMETER);
        List<Pair<String, String>> keyValueList = new LinkedList<>();
        Pair<String, String> pair;

        pair = new Pair<>();
        pair.setKey(String.valueOf(EventDutyParameter.MONTH));
        pair.setValue("" + month);
        keyValueList.add(pair);

        pair = new Pair<>();
        pair.setKey(String.valueOf(EventDutyParameter.YEAR));
        pair.setValue("" + year);
        keyValueList.add(pair);

        request.setParameterKey(keyValueList);

        EventDutyList eventDutyList = (EventDutyList) RequestResponseHelper.writeAndReadJSONObject(url, request, EventDutyList.class);

        if(eventDutyList != null && eventDutyList.getEventDutyList() != null) {
            for(EventDuty eventDuty : eventDutyList.getEventDutyList()) {
                list.add(new Event(eventDuty.getEventDutyID(), eventDuty.getName(), eventDuty.getStartTime(), eventDuty.getEndTime(),
                                   eventDuty.getConductor(), eventDuty.getLocation(), String.valueOf(eventDuty.getDefaultPoints()),
                                   eventDuty.getDescription(), eventDuty.getEventType().toString(), eventDuty.getEventStatus().toString()));
            }
        }

        return list;
    }


    public static TableColumn<Event, Integer> getIdColumn() {
        TableColumn<Event, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<Event, Integer> idCellValueFactory = new PropertyValueFactory<>("id");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    public static TableColumn<Event, Integer> getEventstatusColumn() {
        TableColumn<Event, Integer> statusCol = new TableColumn<>("Eventstatus");
        PropertyValueFactory<Event, Integer> statusCellValueFactory = new PropertyValueFactory<>("eventstatus");
        statusCol.setCellValueFactory(statusCellValueFactory);
        return statusCol;
    }

    public static TableColumn<Event, String> getNameColumn() {
        TableColumn<Event, String> nameCol = new TableColumn<>("Name");
        PropertyValueFactory<Event, String> nameCellValueFactory = new PropertyValueFactory<>("name");
        nameCol.setCellValueFactory(nameCellValueFactory);
        return nameCol;
    }


    public static TableColumn<Event, String> getStartdateColumn() {
        TableColumn<Event, String> startdateCol = new TableColumn<>("Start Date");
        PropertyValueFactory<Event, String> startdateCellValueFactory = new PropertyValueFactory<>("startdate");
        startdateCol.setCellValueFactory(startdateCellValueFactory);
        return startdateCol;
    }


    public static TableColumn<Event, String> getEnddateColumn() {
        TableColumn<Event, String> enddateCol = new TableColumn<>("End Date");
        PropertyValueFactory<Event, String> enddateCellValueFactory = new PropertyValueFactory<>("enddate");
        enddateCol.setCellValueFactory(enddateCellValueFactory);
        return enddateCol;
    }


    public static TableColumn<Event, String> getConductorColumn() {
        TableColumn<Event, String> conductorCol = new TableColumn<>("Conductor");
        PropertyValueFactory<Event, String> conductorCellValueFactory = new PropertyValueFactory<>("conductor");
        conductorCol.setCellValueFactory(conductorCellValueFactory);
        return conductorCol;
    }


    public static TableColumn<Event, String> getLocationColumn() {
        TableColumn<Event, String> cityCol = new TableColumn<>("Location");
        PropertyValueFactory<Event, String> cityCellValueFactory = new PropertyValueFactory<>("location");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    public static TableColumn<Event, String> getPointsColumn() {
        TableColumn<Event, String> pointsCol = new TableColumn<>("Points");
        PropertyValueFactory<Event, String> pointsCellValueFactory = new PropertyValueFactory<>("points");
        pointsCol.setCellValueFactory(pointsCellValueFactory);
        return pointsCol;
    }


    public static TableColumn<Event, String> getDescriptionColumn() {
        TableColumn<Event, String> descriptionCol = new TableColumn<>("Description");
        PropertyValueFactory<Event, String> descriptionCellValueFactory = new PropertyValueFactory<>("description");
        descriptionCol.setCellValueFactory(descriptionCellValueFactory);
        return descriptionCol;
    }

    public static TableColumn<Event, String> getEventtypeColumn() {
        TableColumn<Event, String> eventtypeCol = new TableColumn<>("Eventtype");
        PropertyValueFactory<Event, String> eventtypeCellValueFactory = new PropertyValueFactory<>("eventtype");
        eventtypeCol.setCellValueFactory(eventtypeCellValueFactory);
        return eventtypeCol;
    }
}
