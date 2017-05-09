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
    public static ObservableList<EventDuty> getEventsList(URL url, int month, int year) {
        ObservableList<EventDuty> list = FXCollections.observableArrayList();

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

        request.setParameterKeyList(keyValueList);

        EventDutyList eventDutyList = (EventDutyList) RequestResponseHelper.writeAndReadJSONObject(url, request, EventDutyList.class);

        if(eventDutyList != null && eventDutyList.getEventDutyList() != null) {
            list.addAll(eventDutyList.getEventDutyList());
        }

        return list;
    }


    public static TableColumn<EventDuty, Integer> getIdColumn() {
        TableColumn<EventDuty, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<EventDuty, Integer> idCellValueFactory = new PropertyValueFactory<>("eventDutyID");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    public static TableColumn<EventDuty, Integer> getEventstatusColumn() {
        TableColumn<EventDuty, Integer> statusCol = new TableColumn<>("Eventstatus");
        PropertyValueFactory<EventDuty, Integer> statusCellValueFactory = new PropertyValueFactory<>("eventStatus");
        statusCol.setCellValueFactory(statusCellValueFactory);
        return statusCol;
    }

    public static TableColumn<EventDuty, String> getNameColumn() {
        TableColumn<EventDuty, String> nameCol = new TableColumn<>("Name");
        PropertyValueFactory<EventDuty, String> nameCellValueFactory = new PropertyValueFactory<>("name");
        nameCol.setCellValueFactory(nameCellValueFactory);
        return nameCol;
    }


    public static TableColumn<EventDuty, String> getStartdateColumn() {
        TableColumn<EventDuty, String> startdateCol = new TableColumn<>("Start Date");
        PropertyValueFactory<EventDuty, String> startdateCellValueFactory = new PropertyValueFactory<>("startTime");
        startdateCol.setCellValueFactory(startdateCellValueFactory);
        return startdateCol;
    }


    public static TableColumn<EventDuty, String> getEnddateColumn() {
        TableColumn<EventDuty, String> enddateCol = new TableColumn<>("End Date");
        PropertyValueFactory<EventDuty, String> enddateCellValueFactory = new PropertyValueFactory<>("endTime");
        enddateCol.setCellValueFactory(enddateCellValueFactory);
        return enddateCol;
    }


    public static TableColumn<EventDuty, String> getConductorColumn() {
        TableColumn<EventDuty, String> conductorCol = new TableColumn<>("Conductor");
        PropertyValueFactory<EventDuty, String> conductorCellValueFactory = new PropertyValueFactory<>("conductor");
        conductorCol.setCellValueFactory(conductorCellValueFactory);
        return conductorCol;
    }


    public static TableColumn<EventDuty, String> getLocationColumn() {
        TableColumn<EventDuty, String> cityCol = new TableColumn<>("Location");
        PropertyValueFactory<EventDuty, String> cityCellValueFactory = new PropertyValueFactory<>("location");
        cityCol.setCellValueFactory(cityCellValueFactory);
        return cityCol;
    }

    public static TableColumn<EventDuty, String> getPointsColumn() {
        TableColumn<EventDuty, String> pointsCol = new TableColumn<>("Points");
        PropertyValueFactory<EventDuty, String> pointsCellValueFactory = new PropertyValueFactory<>("defaultPoints");
        pointsCol.setCellValueFactory(pointsCellValueFactory);
        return pointsCol;
    }


    public static TableColumn<EventDuty, String> getDescriptionColumn() {
        TableColumn<EventDuty, String> descriptionCol = new TableColumn<>("Description");
        PropertyValueFactory<EventDuty, String> descriptionCellValueFactory = new PropertyValueFactory<>("description");
        descriptionCol.setCellValueFactory(descriptionCellValueFactory);
        return descriptionCol;
    }

    public static TableColumn<EventDuty, String> getEventtypeColumn() {
        TableColumn<EventDuty, String> eventtypeCol = new TableColumn<>("Eventtype");
        PropertyValueFactory<EventDuty, String> eventtypeCellValueFactory = new PropertyValueFactory<>("eventType");
        eventtypeCol.setCellValueFactory(eventtypeCellValueFactory);
        return eventtypeCol;
    }
}
