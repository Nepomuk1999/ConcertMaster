package team_f.client.pages.musicalWorkManagementExplanation;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.util.Callback;
import team_f.client.pages.BasePage;
import javax.lang.model.type.NullType;


public class MWMETable extends BasePage<Void, NullType, NullType, NullType> {
    private TableView<MWMETableEntries> table;


    public MWMETable() {
    }

    @Override
    public void initialize() {
        table = new TableView(MWMETableEntries.data);
        table.getColumns().addAll(makeStringColumn("Field Number", "fieldNumber", 80),
                makeStringColumn("Explanation", "str", 860));

        table.setPrefHeight(100);
        table.setEditable(false);

        Label label = new Label("ConcertMaster Musical Work Management Explanation");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Musical Work Management Explanation");
        alert.setHeaderText(" ");

        Image image1 = new Image("musicalWorkManagementExplanation.png");
        ImageView imageView = new ImageView(image1);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(400);

        alert.setGraphic(imageView);

        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(table, Priority.ALWAYS);
        GridPane.setHgrow(table, Priority.ALWAYS);
        GridPane content = new GridPane();
        content.setMinWidth(480);
        content.setMinHeight(230);
        content.add(table, 0, 1);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();

    }

    @Override
    public void load() {
    }

    @Override
    public void update() {
    }

    @Override
    public void exit() {
    }

    @Override
    public void dispose() {
    }

    private TableColumn<MWMETableEntries, String> makeStringColumn(String columnName, String propertyName, int prefWidth) {
        TableColumn<MWMETableEntries, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<MWMETableEntries, String>(propertyName));
        //column.setWrappingWidth(column.getWidth());
        column.setCellFactory(new Callback<TableColumn<MWMETableEntries, String>, TableCell<MWMETableEntries, String>>() {
            @Override
            public TableCell<MWMETableEntries, String> call(TableColumn<MWMETableEntries, String> soCalledFriendStringTableColumn) {
                return new TableCell<MWMETableEntries, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setFont(new Font("Arial", 15));
                            setAlignment(Pos.TOP_LEFT);
                        }
                    }
                };

            }
        });

        column.setPrefWidth(prefWidth);
        column.setSortable(false);

        return column;
    }
}