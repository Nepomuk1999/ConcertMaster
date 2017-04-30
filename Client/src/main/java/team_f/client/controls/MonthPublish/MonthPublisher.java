package team_f.client.controls.MonthPublish;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;
import java.util.Optional;
import team_f.application.EventApplication;


public class MonthPublisher extends BorderPane {
    private final ObservableList<Month> _data;
    private ObservableList<Integer> _year;
    private int _selectedYear;
    private Month _selectedMonth;


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
        Label labelYear = new Label("Select a Year!");
        labelYear.setStyle("-fx-font: 14 arial;");
        comboBoxYear.getSelectionModel().select(1);
        _selectedYear = comboBoxYear.getSelectionModel().getSelectedItem().intValue();

        ComboBox<Month> comboBoxMonth = new ComboBox<>(_data);
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
        Label labelPublishButton=new Label("Click here to publish Month!");
        labelPublishButton.setStyle("-fx-font: 14 arial;");
        publishButton.setOnAction(event -> {
            publish();
        });

        Button unpublishButton = new Button("Unpublish Month");
        Label labelUnpblishButton=new Label("Click here to unpublish Month!");
        labelUnpblishButton.setStyle("-fx-font: 14 arial;");
        unpublishButton.setOnAction(event -> {
            unpublish();
        });


        GridPane pane=new GridPane();
        ImageView imageView = new ImageView(
                new Image("Logo2.jpg")
        );
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


        VBox root = new VBox();
        root.getChildren().addAll(pane);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 80;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");


        setTop(imageView);
        setAlignment(imageView, Pos.CENTER);

        setCenter(root);
    }

    private void publish() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Publish  Month");
        alert.setHeaderText("Are you sure you want to Publish the following month? You are no longer able to edit the Events assigned to this month  ");
        alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);

        ButtonType buttonTypeOne = new ButtonType("Publish");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeCancel) {
            alert.close();
            return;
        } else {
            EventApplication event=new EventApplication();
            //Todo: send value to Eventapplication to publish events, is this correct? GUI-->Application
            if(event.publishEventsByMonth(_selectedMonth.getValue(),_selectedYear)==1){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("Succesfully published selected Month");
                alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);

            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText("An Error occured while publishing selected Month. Please try it againg later!");
                alert.setContentText("ERROR"+": "+_selectedMonth.getMonth() + " " + _selectedYear);}
        }
        alert.showAndWait();
        }

    private void unpublish() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Unpublish  Month");
        alert.setHeaderText("Are you sure you want to Unpublish the following month?");
        alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);

        ButtonType buttonTypeOne = new ButtonType("Unpublish");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeCancel) {
            alert.close();
            return;
        } else {
            EventApplication event=new EventApplication();
            //Todo: send value to Eventapplication to unpublish events, is this correct? GUI-->Application
            if(event.unpublishEventsByMonth(_selectedMonth.getValue(),_selectedYear)==1){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setHeaderText("Succesfully unpublished selected Month");
                alert.setContentText(_selectedMonth.getMonth() + " " + _selectedYear);

            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText("An Error occured while unpublishing selected Month. Please try it againg later!");
                alert.setContentText("ERROR"+": "+_selectedMonth.getMonth() + " " + _selectedYear);}

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








