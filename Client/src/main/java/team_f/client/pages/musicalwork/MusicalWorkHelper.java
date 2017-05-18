package team_f.client.pages.musicalwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import team_f.client.entities.KeyValuePair;
import team_f.jsonconnector.entities.MusicalWork;
import team_f.jsonconnector.enums.InstrumentType;
import team_f.jsonconnector.enums.SectionGroupType;
import team_f.jsonconnector.enums.SectionType;

public class MusicalWorkHelper {

    public static TableColumn<MusicalWork, Integer> getIdColumn() {
        TableColumn<MusicalWork, Integer> idCol = new TableColumn<>("Id");
        PropertyValueFactory<MusicalWork, Integer> idCellValueFactory = new PropertyValueFactory<>("musicalWorkID");
        idCol.setCellValueFactory(idCellValueFactory);
        return idCol;
    }

    public static TableColumn<MusicalWork, String> getMusicalWorkNameColumn() {
        TableColumn<MusicalWork, String> musicalWorkNameCol = new TableColumn<>("MusicalWork Name");
        PropertyValueFactory<MusicalWork, String> musicalWorkNameCellValueFactory = new PropertyValueFactory<>("name");
        musicalWorkNameCol.setCellValueFactory(musicalWorkNameCellValueFactory);
        return musicalWorkNameCol;
    }

    public static TableColumn<MusicalWork, String> getComposerColumn() {
        TableColumn<MusicalWork, String> composerCol = new TableColumn<>("Composer name");
        PropertyValueFactory<MusicalWork, String> composerCellValueFactory = new PropertyValueFactory<>("composer");
        composerCol.setCellValueFactory(composerCellValueFactory);
        return composerCol;
    }

    public static TableColumn<MusicalWork, String> getInstrumentationColumn() {
        TableColumn<MusicalWork, String> instrumentationCol = new TableColumn<>("Instrumentation");
        PropertyValueFactory<MusicalWork, String> instrumentationCellValueFactory = new PropertyValueFactory<>("instrumentation");
        instrumentationCol.setCellValueFactory(instrumentationCellValueFactory);
        return instrumentationCol;
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

