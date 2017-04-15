package team_f.client.controls.Home;

/**
 * Created by w7pro on 14.04.2017.
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//TODO: optional: integrate to HomeScreen(Home/HomeScreen.class) and connect with Database to save Todos etc.....
public class TodolistHome extends Application {
    @Override public void start(final Stage stage) {

        final Label title = new Label();
        title.setText("TODO-List");

        final ListView<String> listView = new ListView<>();
        initListView(listView);


        final Button removeButton = new Button("Remove Selected");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
                if (selectedIdx != -1) {
                    String itemToRemove = listView.getSelectionModel().getSelectedItem();

                    final int newSelectedIdx =
                            (selectedIdx == listView.getItems().size() - 1)
                                    ? selectedIdx - 1
                                    : selectedIdx;

                    listView.getItems().remove(selectedIdx);
                    listView.getSelectionModel().select(newSelectedIdx);
                }
            }
        });

        TextField inputField = new TextField();
        inputField.setPromptText("Add a new Todo here");
        final Button addButton = new Button("Add Todo");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                listView.getItems().add(inputField.getText());
                inputField.setText("");
                inputField.requestFocus();


            }
        });
        addButton.disableProperty()
                .bind(Bindings.isEmpty(inputField.textProperty()));



        final HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(removeButton, inputField, addButton);

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 10; -fx-background-color: snow;");
        layout.getChildren().setAll(
                title,
                listView,
                controls

        );

        layout.setPrefSize(400,400);

        stage.setScene(new Scene(layout));
        stage.show();
    }

    private void initListView(ListView<String> listView) {
        listView.getItems().setAll("test1", "test2", "test3", "test4");
    }

    public static void main(String[] args) { launch(args); }
}