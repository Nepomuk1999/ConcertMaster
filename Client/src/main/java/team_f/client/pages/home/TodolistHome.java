package team_f.client.pages.home;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TodolistHome extends BorderPane {
    public TodolistHome() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("My TodoList");
        alert.setHeaderText("Enter new Todo's or delete solved Todo's");


        final Label title = new Label();
        title.setText("TODO-List");

        final ListView<String> listView = new ListView<>();
        initListView(listView);


        final Button removeButton = new Button("Remove Selected");
        removeButton.setDisable(true);
        removeButton.setOnAction(event -> {
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
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> removeButton.setDisable(false));


        TextField inputField = new TextField();
        inputField.setPromptText("Add a new Todo here");
        final Button addButton = new Button("Add Todo");
        addButton.setOnAction(event -> {
            listView.getItems().add(inputField.getText());
            inputField.setText("");
            inputField.requestFocus();


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

        Label label = new Label("");
        layout.setPrefSize(400, 400);
        listView.setMaxWidth(Double.MAX_VALUE);
        listView.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(listView, Priority.ALWAYS);
        GridPane.setHgrow(listView, Priority.ALWAYS);
        GridPane content2 = new GridPane();
        content2.setMinWidth(400);
        content2.setMinHeight(215);
        content2.getChildren().addAll(layout);
        alert.getDialogPane().setContent(content2);
        alert.showAndWait();
    }

    private void initListView(ListView<String> listView) {
        listView.getItems().setAll("Termin anlegen für 10.12", "Punkte eintragen", "Terminplan Veröffentlichen");
    }
}
