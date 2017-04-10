package team_f.client.gui;

import javafx.scene.control.ToggleGroup;
import team_f.client.controls.sidebar.MenuSection;
import team_f.client.controls.sidebar.MenuSectionItem;
import team_f.client.controls.sidebar.Sidebar;

public class NavigationBar {
    public static Sidebar getNavigationBar() {
        Sidebar sidebar = new Sidebar();
        MenuSection menuSection;
        MenuSectionItem menuSectionItem;
        ToggleGroup toggleGroup = new ToggleGroup();
        sidebar.setStyle("-fx-background-color:   #e0e0d1");

        menuSection = new MenuSection("Home", "/homeM.png", null);
        menuSection.setAnimated(false);
        menuSection.setCollapsible(false);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Terminplan", "/calendarM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Pläne einsehen");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Terminplanverwaltung");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Dienstverwaltung");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Dienste", "/dutyM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Dienstpläne");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Dienstverwaltung");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Musiker", "/orchestraiconM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Musikerverwaltung");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musikerliste");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Werke", "/musicfolderM.png", toggleGroup);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Inventar", "/inventaryM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Inventar anzeigen");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Gegenstand hinzufügen");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Benutzer", "/userM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Stimmgruppenverwaltung");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musikerverwaltung");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Administration", "/settingsM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Stimmgruppenverwaltung");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musikerverwaltung");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        return sidebar;
    }
}
