package team_f.client.gui;

import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import team_f.client.configuration.Configuration;
import team_f.client.controls.sidebar.MenuSection;
import team_f.client.controls.sidebar.MenuSectionItem;
import team_f.client.controls.sidebar.Sidebar;
import team_f.client.singletons.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NavigationBar {
    public static Sidebar getNavigationBar(BorderPane pane, Configuration configuration) {
        Sidebar sidebar = new Sidebar();
        //MenuSection menuSection;
        ArrayList<MenuSection> menuSectionArrayList=new ArrayList<>();
        MenuSectionItem menuSectionItem;
        ToggleGroup toggleGroup = new ToggleGroup();
        sidebar.setStyle("-fx-background-color:   #e0e0d1");

        MenuSection menuSectionHome = new MenuSection("Home", "/homeM.png", null);
        menuSectionHome.setAnimated(false);
        menuSectionHome.setCollapsible(false);
        sidebar.add(menuSectionHome);
        menuSectionArrayList.add(menuSectionHome);

        MenuSection menuSectionServiceSchedule = new MenuSection("Service Schedule", "/calendarM.png", toggleGroup);
        //menuSectionServiceSchedule.setStyle("-fx-color: rgb(0,100,157);"+"-fx-text-fill: white");

        menuSectionItem = new MenuSectionItem("Show Schedules");
        menuSectionItem.setOnAction(event -> {
            try {
                pane.setCenter(BrowserSingleton.getInstance(new URL("http://localhost:8080/Calendar")));
            } catch (MalformedURLException e) {
            }
        });

        menuSectionServiceSchedule.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Publish/Unpublish Schedule");
        //  menuSectionItem.setOnMouseClicked(event -> new LegendTable());
        menuSectionItem.setOnMouseClicked(event -> {
            try {
                pane.setCenter(MonthPublisherSingleton.getInstance(new URL(configuration.getStartURI())));
            } catch (MalformedURLException e) {
            }
        });
        menuSectionServiceSchedule.add(menuSectionItem);
        sidebar.add(menuSectionServiceSchedule);
        menuSectionArrayList.add(menuSectionServiceSchedule);

        MenuSection menuSectionServices = new MenuSection("Services", "/dutyM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Service Schedules");
        menuSectionServices.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Service Management");
        menuSectionServices.add(menuSectionItem);
        sidebar.add(menuSectionServices);
        menuSectionArrayList.add(menuSectionServices);

        MenuSection menuSectionMusician = new MenuSection("Musician", "/orchestraiconM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSectionItem.setOnMouseClicked(event -> {
            try {
                pane.setCenter(MusiciansTableSingleton.getInstance(new URL(configuration.getStartURI())));
            } catch (MalformedURLException e) {
            }
        });
        menuSectionMusician.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician List");
        menuSectionMusician.add(menuSectionItem);
        sidebar.add(menuSectionMusician);
        menuSectionArrayList.add(menuSectionMusician);

        MenuSection menuSectionCompositions = new MenuSection("Compositions", "/musicfolderM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("MusicalWork Management");
        menuSectionItem.setOnMouseClicked(event -> pane.setCenter(MusicalWorkSingleton.getInstance()));
        menuSectionCompositions.add(menuSectionItem);
        sidebar.add(menuSectionCompositions);
        menuSectionArrayList.add(menuSectionCompositions);

        MenuSection menuSectionInventory = new MenuSection("Inventory", "/inventaryM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Show Inventory");
        menuSectionInventory.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Add Item");
        menuSectionInventory.add(menuSectionItem);
        sidebar.add(menuSectionInventory);
        menuSectionArrayList.add(menuSectionInventory);

        MenuSection menuSectionUserScreen = new MenuSection("User Screen", "/userM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Section Management");
        menuSectionUserScreen.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSectionUserScreen.add(menuSectionItem);
        sidebar.add(menuSectionUserScreen);
        menuSectionArrayList.add(menuSectionUserScreen);

        MenuSection menuSectionAdministration = new MenuSection("Administration", "/settingsM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Section Management");
        menuSectionAdministration.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSectionAdministration.add(menuSectionItem);
        sidebar.add(menuSectionAdministration);
        menuSectionArrayList.add(menuSectionAdministration);

        menuSectionHome.setOnMouseClicked(event -> {
            for (MenuSection menuSection : menuSectionArrayList) {
                menuSection.setExpanded(false);
            }
            pane.setCenter(HomeScreenSingleton.getInstance());
        });


        return sidebar;
    }
}
