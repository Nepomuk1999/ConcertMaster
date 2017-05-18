package team_f.client.helper.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import team_f.client.controls.numberfield.NumberField;
import team_f.client.entities.KeyValuePair;
import team_f.client.exceptions.NumberRangeException;
import team_f.jsonconnector.entities.Instrumentation;
import team_f.jsonconnector.entities.SpecialInstrumentation;
import team_f.jsonconnector.enums.InstrumentType;
import team_f.jsonconnector.enums.SectionGroupType;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class InstrumentationHelper {
    public static List<SpecialInstrumentation> getSpecialInstrumentation(List<SpecialInstrumentationEntity> _specialInstrumentationEntityList) {
        List<SpecialInstrumentation> specialInstrumentationList = new LinkedList<>();
        SpecialInstrumentation specialInstrumentation;

        for(SpecialInstrumentationEntity item : _specialInstrumentationEntityList) {
            if (item.getSpecialInstrumentationNumberField().getNumber().intValue() > 0) {
                specialInstrumentation = new SpecialInstrumentation();
                specialInstrumentation.setSpecialInstrumentationID(item.getSpecialInstrumentationID());
                specialInstrumentation.setSectionType(String.valueOf(item.getSectionTypeComboBox().getSelectionModel().getSelectedItem()));
                specialInstrumentation.setSpecialInstrumentationCount(item.getSpecialInstrumentationNumberField().getNumber().intValue());
                specialInstrumentation.setSpecialInstrumentation(String.valueOf(item.getSpecialInstrumentationComboBox().getSelectionModel().getSelectedItem().getValue()));
                specialInstrumentationList.add(specialInstrumentation);
            }
        }

        return specialInstrumentationList;
    }

    public static void addListeners(List<SpecialInstrumentationEntity> specialInstrumentationEntityList, GridPane specialInstrumentationContent,
                                    ComboBox<KeyValuePair> specialInstrumentationSectionGroupComboBox, ComboBox<KeyValuePair> specialInstrumentationInstrumentTypeComboBox,
                                    NumberField specialInstrumentationNumberField, Button specialInstrumentationButton) {
        specialInstrumentationSectionGroupComboBox.setOnAction(event -> {
            specialInstrumentationInstrumentTypeComboBox.setItems(team_f.client.helper.gui.InstrumentationHelper.getInstrumentTypes((SectionGroupType) specialInstrumentationSectionGroupComboBox.getSelectionModel().getSelectedItem().getValue()));
            specialInstrumentationInstrumentTypeComboBox.getSelectionModel().selectFirst();
        } );

        specialInstrumentationButton.setOnAction(event -> {
            team_f.client.helper.gui.InstrumentationHelper.addSpecialInstrumentationItem(0, specialInstrumentationSectionGroupComboBox.getSelectionModel().getSelectedItem(), specialInstrumentationInstrumentTypeComboBox.getSelectionModel().getSelectedItem(), specialInstrumentationNumberField.getNumber().intValue(),
                    specialInstrumentationEntityList, specialInstrumentationContent, specialInstrumentationSectionGroupComboBox, specialInstrumentationNumberField);
        });
    }

    public static void resetSpecialInstrumentation(List<SpecialInstrumentationEntity> _specialInstrumentationEntityList, Pane contentPane) {
        for(SpecialInstrumentationEntity item : _specialInstrumentationEntityList) {
            item.getSpecialInstrumentationNumberField().setNumber(BigDecimal.ZERO);
            item.getSectionTypeComboBox().getSelectionModel().selectFirst();
            item.getSpecialInstrumentationComboBox().setItems(getInstrumentTypes((SectionGroupType) item.getSectionTypeComboBox().getSelectionModel().getSelectedItem().getValue()));
            item.getSpecialInstrumentationComboBox().getSelectionModel().selectFirst();
            contentPane.getChildren().remove(item.getPane());
        }

        _specialInstrumentationEntityList.clear();
    }

    public static void fillSpecialInstrumentationEntity(List<SpecialInstrumentationEntity> specialInstrumentationEntityList, Instrumentation instrumentation,
                                                        GridPane specialInstrumentationContent, ComboBox<KeyValuePair> specialInstrumentationSectionGroupComboBox, NumberField specialInstrumentationNumberField) {
        for (SpecialInstrumentationEntity item : specialInstrumentationEntityList) {
            removeSpecialInstrumentationItem(item, specialInstrumentationContent, specialInstrumentationEntityList);
        }

        if (instrumentation.getSpecialInstrumentation() != null) {
            List<KeyValuePair> sectionTypeList = team_f.client.helper.gui.InstrumentationHelper.getSectionGroupTypeList();
            KeyValuePair sectionTypeKeyValuePair;
            List<KeyValuePair> specialInstrumentationList;
            KeyValuePair specialInstrumentationKeyValuePair = null;

            for(SpecialInstrumentation specialInstrumentation : instrumentation.getSpecialInstrumentation()) {
                sectionTypeKeyValuePair = null;

                for(KeyValuePair pair : sectionTypeList) {
                    if(String.valueOf(pair.getValue()).equals(specialInstrumentation.getSectionType())) {
                        sectionTypeKeyValuePair = pair;
                        specialInstrumentationList = team_f.client.helper.gui.InstrumentationHelper.getInstrumentTypes((SectionGroupType) sectionTypeKeyValuePair.getValue());

                        for(KeyValuePair item : specialInstrumentationList) {
                            if((String.valueOf(item.getValue())).equals(specialInstrumentation)) {
                                specialInstrumentationKeyValuePair = item;
                                break;
                            }
                        }

                        break;
                    }
                }

                addSpecialInstrumentationItem(specialInstrumentation.getSpecialInstrumentationID(), sectionTypeKeyValuePair,
                        specialInstrumentationKeyValuePair, specialInstrumentation.getSpecialInstrumentCount(),
                        specialInstrumentationEntityList, specialInstrumentationContent, specialInstrumentationSectionGroupComboBox,
                        specialInstrumentationNumberField);
            }
        }
    }

    public static void addSpecialInstrumentationItem(int id, KeyValuePair sectionType, KeyValuePair specialInstrumentation, int specialInstrumentationCount,
                                                     List<SpecialInstrumentationEntity> specialInstrumentationEntityList, GridPane specialInstrumentationContent,
                                                     ComboBox<KeyValuePair> specialInstrumentationSectionGroupComboBox, NumberField specialInstrumentationNumberField) {
        GridPane tmpPane = new GridPane();

        ComboBox<KeyValuePair> sectionTypeComboBox = new ComboBox<>(specialInstrumentationSectionGroupComboBox.getItems());
        sectionTypeComboBox.getSelectionModel().select(sectionType);
        sectionTypeComboBox.setMaxWidth(80);
        tmpPane.addColumn(0, sectionTypeComboBox);

        ComboBox<KeyValuePair> specialInstrumentationComboBox = new ComboBox<>(team_f.client.helper.gui.InstrumentationHelper.getInstrumentTypes((SectionGroupType) sectionType.getValue()));
        specialInstrumentationComboBox.getSelectionModel().select(specialInstrumentation);
        specialInstrumentationComboBox.setMaxWidth(80);
        tmpPane.addColumn(1, specialInstrumentationComboBox);

        NumberField tmpNumberField = null;
        try {
            tmpNumberField = new NumberField(specialInstrumentationCount, specialInstrumentationNumberField.getMinValue().intValue(), specialInstrumentationNumberField.getMaxValue().intValue());
            tmpPane.addColumn(2, tmpNumberField);
            tmpNumberField.setMaxWidth(60);
            tmpNumberField.setStyle("-fx-opacity: 1");
        } catch (NumberRangeException e) {
        }

        Button tmpButton = new Button("-");
        tmpPane.addColumn(3, tmpButton);

        specialInstrumentationContent.addRow(specialInstrumentationEntityList.size()+1, tmpPane);
        specialInstrumentationContent.setColumnSpan(tmpPane, 4);
        SpecialInstrumentationEntity specialInstrumentationEntity = new SpecialInstrumentationEntity(id, sectionTypeComboBox, specialInstrumentationComboBox, tmpNumberField, tmpPane);

        tmpButton.setOnAction(e -> removeSpecialInstrumentationItem(specialInstrumentationEntity, specialInstrumentationContent, specialInstrumentationEntityList));

        specialInstrumentationEntityList.add(specialInstrumentationEntity);
    }

    protected static void removeSpecialInstrumentationItem(SpecialInstrumentationEntity specialInstrumentationEntity, Pane specialInstrumentationContent, List<SpecialInstrumentationEntity> specialInstrumentationEntityList) {
        specialInstrumentationContent.getChildren().remove(specialInstrumentationEntity.getPane());
        specialInstrumentationEntityList.remove(specialInstrumentationEntity);
    }

    public static ObservableList<KeyValuePair> getSectionGroupTypeList() {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList(
                new KeyValuePair("String", SectionGroupType.STRING),
                new KeyValuePair("Wood", SectionGroupType.WOOD),
                new KeyValuePair("Brass", SectionGroupType.BRASS),
                new KeyValuePair("Percussion", SectionGroupType.PERCUSSION));

        return list;
    }

    public static ObservableList<KeyValuePair> getInstrumentTypes(SectionGroupType sectionGroupType) {
        ObservableList<KeyValuePair> list = FXCollections.observableArrayList();

        switch (sectionGroupType) {
            case STRING:
                list.addAll(
                        new KeyValuePair("1.Violin", InstrumentType.FIRSTVIOLIN),
                        new KeyValuePair("2.Violin", InstrumentType.SECONDVIOLIN),
                        new KeyValuePair("Viola", InstrumentType.VIOLA),
                        new KeyValuePair("Violoncello", InstrumentType.VIOLONCELLO),
                        new KeyValuePair("Doublebass", InstrumentType.DOUBLEBASS)
                );

                break;
            case BRASS:
                list.addAll(
                        new KeyValuePair("French Horn", InstrumentType.FRENCHHORN),
                        new KeyValuePair("Trumpet", InstrumentType.TRUMPET),
                        new KeyValuePair("Trombone", InstrumentType.TROMBONE),
                        new KeyValuePair("Bass Trombone", InstrumentType.BASSTROMBONE),
                        new KeyValuePair("Contrabass Trombone", InstrumentType.CONTRABASSTROMBONE),
                        new KeyValuePair("Tube", InstrumentType.TUBE),
                        new KeyValuePair("Horn", InstrumentType.HORN),
                        new KeyValuePair("Euphonium", InstrumentType.EUPHONIUM),
                        new KeyValuePair("Wagner Tuba", InstrumentType.WAGNERTUBA),
                        new KeyValuePair("Cimbasso", InstrumentType.CIMBASSO)
                );

                break;
            case WOOD:
                list.addAll(
                        new KeyValuePair("English Horn", InstrumentType.ENGLISHHORN),
                        new KeyValuePair("Basset Horn", InstrumentType.BASSETHORN),
                        new KeyValuePair("Flute", InstrumentType.FLUTE),
                        new KeyValuePair("Piccolo", InstrumentType.PICCOLO),
                        new KeyValuePair("Oboe", InstrumentType.OBOE),
                        new KeyValuePair("Clarinet", InstrumentType.CLARINET),
                        new KeyValuePair("Bass Clarinet", InstrumentType.BASSCLARINET),
                        new KeyValuePair("Bassoon", InstrumentType.BASSOON),
                        new KeyValuePair("Heckelphone", InstrumentType.HECKELPHONE),
                        new KeyValuePair("Contrabassoon", InstrumentType.CONTRABASSOON),
                        new KeyValuePair("Saxophone", InstrumentType.SAXOPHONE)
                );

                break;
            case PERCUSSION:
                list.addAll(
                        new KeyValuePair("Kettledrum", InstrumentType.KETTLEDRUM),
                        new KeyValuePair("Percussion", InstrumentType.PERCUSSION),
                        new KeyValuePair("Harp", InstrumentType.HARP),
                        new KeyValuePair("Piano", InstrumentType.PIANO),
                        new KeyValuePair("Celesta", InstrumentType.CELESTA),
                        new KeyValuePair("Organ", InstrumentType.ORGAN),
                        new KeyValuePair("Cembalo", InstrumentType.CEMBALO),
                        new KeyValuePair("Keyboard", InstrumentType.KEYBOARD),
                        new KeyValuePair("Accordeon", InstrumentType.ACCORDEON),
                        new KeyValuePair("Bandoneon", InstrumentType.BANDONEON),
                        new KeyValuePair("Guitar", InstrumentType.GUITAR),
                        new KeyValuePair("Mandolin", InstrumentType.MANDOLIN)
                );

                break;
        }

        return list;
    }
}
