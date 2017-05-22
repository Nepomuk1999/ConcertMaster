package team_f.client.pages.musicianManagementExplanation;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import team_f.client.pages.BasePage;
import javax.lang.model.type.NullType;
import java.net.URL;


public class MMEPTable extends BasePage<Void, NullType, NullType, NullType> {
    private TableView<MMEPTableEntries> table;


    public MMEPTable() {
    }

    @Override
    public void initialize() {
        table = new TableView(MMEPTableEntries.data);
        table.getColumns().addAll(makeStringColumn("Field Number", "fieldNumber", 80),
                makeStringColumn("Explanation", "str", 990));
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(100);
        table.setEditable(false);

        Label label = new Label("ConcertMaster Musician Management Explanation");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Musician Management Explanation");
        alert.setHeaderText("                                 ");

        Image image1 = new Image("MusicianManagementExplanationPic.png");
        ImageView imageView = new ImageView(image1);

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(400);

        alert.setGraphic(imageView);

        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(table, Priority.ALWAYS);
        GridPane.setHgrow(table, Priority.ALWAYS);
        GridPane content = new GridPane();
        content.setMinWidth(500);
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

    private TableColumn<MMEPTableEntries, String> makeStringColumn(String columnName, String propertyName, int prefWidth) {
        TableColumn<MMEPTableEntries, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<MMEPTableEntries, String>(propertyName));
        //column.setWrappingWidth(column.getWidth());
        column.setCellFactory(new Callback<TableColumn<MMEPTableEntries, String>, TableCell<MMEPTableEntries, String>>() {
            @Override
            public TableCell<MMEPTableEntries, String> call(TableColumn<MMEPTableEntries, String> soCalledFriendStringTableColumn) {
                return new TableCell<MMEPTableEntries, String>() {
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