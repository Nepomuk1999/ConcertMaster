package team_f.client.controls.Legende;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.util.Callback;


// demonstrates highlighting rows in a tableview based upon the data values in the rows.
public class LegendTable extends BorderPane {
    private TableView<LegendEntries> table ;

    public LegendTable() {
        table = new TableView(LegendEntries.data);
        TableView<LegendEntries> table = new TableView(LegendEntries.data);
        table.getColumns().addAll(makeStringColumn("Eventtype", "eventtype", 200), makeStringColumn("Code", "code", 50), makeStringColumn("Color", "color", 250));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(100);

        setCenter(table);
        setVisible(true);
    }



    private TableColumn<LegendEntries, String> makeStringColumn(String columnName, String propertyName, int prefWidth) {
        TableColumn<LegendEntries, String> column = new TableColumn<>(columnName);
        column.setCellValueFactory(new PropertyValueFactory<LegendEntries, String>(propertyName));
        column.setCellFactory(new Callback<TableColumn<LegendEntries, String>, TableCell<LegendEntries, String>>() {
            @Override
            public TableCell<LegendEntries, String> call(TableColumn<LegendEntries, String> soCalledFriendStringTableColumn) {
                return new TableCell<LegendEntries, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);
                            setFont(new Font("Arial", 20));
                            setAlignment(Pos.CENTER);
                            if (item.equals("DODGERBLUE")) {
                                setStyle("-fx-background-color: DODGERBLUE");
                            }
                            if (item.equals("DARKMAGENTA")) {
                                setStyle("-fx-background-color: DARKMAGENTA");
                            }
                            if (item.equals("DARKSLATEBLUE")) {
                                setStyle("-fx-background-color: DARKSLATEBLUE");
                            }
                            if (item.equals("SADDLEBROWN")) {
                                setStyle("-fx-background-color: SADDLEBROWN");
                            }
                            if (item.equals("DARKSALMON")) {
                                setStyle("-fx-background-color: DARKSALMON");
                            }
                            if (item.equals("DARKCYAN")) {
                                setStyle("-fx-background-color: DARKCYAN");
                            }

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