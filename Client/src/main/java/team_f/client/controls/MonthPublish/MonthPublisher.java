package team_f.client.controls.MonthPublish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;
import java.util.Optional;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import team_f.application.EventApplication;


public class MonthPublisher extends BorderPane {
    private final ObservableList<Month> _data;
    private ObservableList<Integer> _year;
    private int _selectedYear;
    private Month _selectedMonth;
    VBox _root;


    public MonthPublisher() {
        _data = FXCollections.observableArrayList(
                new Month("January", 1),
                new Month("February", 2),
                new Month("March", 3),
                new Month("April", 4),
                new Month("May", 5),
                new Month("June", 6),
                new Month("July", 7),
                new Month("August", 8),
                new Month("September", 9),
                new Month("October", 10),
                new Month("November", 11),
                new Month("December", 12));

        _year = FXCollections.observableArrayList();
        LocalDateTime current = LocalDateTime.now();
        for (int i = -1; i < 6; i++) {
            _year.add(current.getYear() + i);
        }

        ComboBox<Integer> comboBoxYear = new ComboBox<>(_year);
        comboBoxYear.setStyle("-fx-font: 14 arial;");
        Label labelYear = new Label("Select a Year!");
        labelYear.setStyle("-fx-font: 14 arial;");
        comboBoxYear.getSelectionModel().select(1);
        _selectedYear = comboBoxYear.getSelectionModel().getSelectedItem().intValue();

        ComboBox<Month> comboBoxMonth = new ComboBox<>(_data);
        comboBoxMonth.setStyle("-fx-font: 14 arial;");
        Label labelMonth = new Label("Select a Month to publish or unpublish!");
        labelMonth.setStyle("-fx-font: 14 arial;");
        comboBoxMonth.getSelectionModel().selectFirst();
        _selectedMonth = new Month(comboBoxMonth.getSelectionModel().getSelectedItem().getMonth(), comboBoxMonth.getSelectionModel().getSelectedItem().getValue());

        comboBoxYear.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                _selectedYear = arg2.intValue();
            }
        });

        comboBoxMonth.getSelectionModel().selectedItemProperty().addListener((arg0, arg1, arg2) -> {
            if (arg2 != null) {
                _selectedMonth.setMonth(arg2.getMonth());
                _selectedMonth.setValue(arg2.getValue());
            }
        });


        Button publishButton = new Button("Publish Month");
        publishButton.setStyle("-fx-font: 14 arial;");
        Label labelPublishButton=new Label("Click here to publish Month!");
        labelPublishButton.setStyle("-fx-font: 14 arial;");
        publishButton.setOnAction(event -> {
            publish();
        });

        Button unpublishButton = new Button("Unpublish Month");
        unpublishButton.setStyle("-fx-font: 14 arial;");
        Label labelUnpblishButton=new Label("Click here to unpublish Month!");
        labelUnpblishButton.setStyle("-fx-font: 14 arial;");
        unpublishButton.setOnAction(event -> {
            unpublish();
        });

        GridPane pane=new GridPane();

        pane.setHgap(10);
        pane.setVgap(5);

        pane.add(labelYear, 30,16);
        pane.add(comboBoxYear, 31,16);
        pane.add(labelMonth, 30,18);
        pane.add(comboBoxMonth, 31,18);
        pane.add(labelPublishButton, 30,20);
        pane.add(publishButton, 31,20);
        pane.add(labelUnpblishButton, 30,22);
        pane.add(unpublishButton, 31,22);


         _root=new VBox();
         _root.getChildren().addAll(pane);


        StackPane header = new StackPane();
        StackPane bottom = new StackPane();
        Label headerTitle = new Label("Publish/Unpublish Shedule");
        headerTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 36));
        header.getChildren().add(headerTitle);
        setStyle("-fx-border-color: cornflowerblue; -fx-border-radius: 10; -fx-border-insets: 80;");
        header.setStyle("-fx-background-color: derive(cornflowerblue, 70%); -fx-background-radius: 10 10 0 0; ");
        bottom.setStyle("-fx-background-color: derive(cornflowerblue, 70%); -fx-background-radius: 0 0 10 10; ");
        setTop(header);
        Label bottomTitle = new Label("");
        bottomTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 36));
        bottom.getChildren().add(bottomTitle);
        setBottom(bottom);

        setCenter(_root);
    }

    private void publish() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Publish  Month");
        alert.setHeaderText("Are you sure you want to Publish the following month? You are no longer able to edit the Events assigned to this month  ");
        alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);

        ButtonType buttonTypeOne = new ButtonType("Publish");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        ProgressIndicator pi = new ProgressIndicator();

        VBox box = new VBox(pi);
        box.setAlignment(Pos.CENTER);
        _root.setDisable(true);
        _root.getChildren().add(box);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeCancel) {
            alert.close();
            _root.setDisable(false);
            _root.getChildren().remove(box);
            return;
        } else {

            EventApplication event=new EventApplication();
            int success=event.publishEventsByMonth(_selectedMonth.getValue(),_selectedYear);
            //Todo: send value to Eventapplication to publish events, is this correct? GUI-->Application
            if(success==1){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("Succesfully published selected Month");
                alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);
                _root.setDisable(false);
                _root.getChildren().remove(box);

            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText("An Error occured while publishing selected Month. Please try it again later or contact your System-Administrator!");
                alert.setContentText("ERROR during publishing: "+_selectedMonth.getMonth() + " " + _selectedYear);
                _root.setDisable(false);
                _root.getChildren().remove(box);
        }
        alert.showAndWait();
        }}

    private void unpublish() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Unpublish  Month");
        alert.setHeaderText("Are you sure you want to Unpublish the following month?");
        alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);

        ButtonType buttonTypeOne = new ButtonType("Unpublish");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        ProgressIndicator pi = new ProgressIndicator();
        VBox box = new VBox(pi);
        box.setAlignment(Pos.CENTER);
        _root.setDisable(true);
        _root.getChildren().add(box);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeCancel) {
            alert.close();
            _root.setDisable(false);
            _root.getChildren().remove(box);
            return;
        } else {
            EventApplication event=new EventApplication();
            int success=event.unpublishEventsByMonth(_selectedMonth.getValue(),_selectedYear);
            //Todo: send value to Eventapplication to unpublish events, is this correct? GUI-->Application
            if(success==1){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("Succesfully unpublished selected Month");
                alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);
                _root.setDisable(false);
                _root.getChildren().remove(box);

            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText("An Error occured while unpublishing selected Month.  Please try it again later or contact your System-Administrator!");
                alert.setContentText("ERROR during unpublishing: "+": "+_selectedMonth.getMonth() + " " + _selectedYear);
                _root.setDisable(false);
                _root.getChildren().remove(box);
            }
        }
        alert.showAndWait();
            }




    public static class Month {
        private String month;
        private int value;


        public Month(String month, int value) {
            this.month = month;
            this.value = value;
        }


        @Override
        public String toString()
        {
            return month.toString();
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}








