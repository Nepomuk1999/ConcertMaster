package team_f.client.gui;

import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import team_f.client.configuration.Configuration;
import team_f.client.controls.sidebar.MenuSection;
import team_f.client.controls.sidebar.MenuSectionItem;
import team_f.client.controls.sidebar.Sidebar;
import team_f.client.pages.BasePage;
import team_f.client.pages.home.HomeScreen;
import team_f.client.singletons.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class NavigationBar {
    private BorderPane _pane;
    private Configuration _configuration;
    private BasePage _currentPage;
    private List<BasePage> _initializedPageList = new LinkedList<>();

    public NavigationBar(BorderPane pane, Configuration configuration) {
        _pane = pane;
        _configuration = configuration;
    }

    public Sidebar getNavigationBar() {
        Sidebar sidebar = new Sidebar();
        MenuSection menuSection;
        MenuSectionItem menuSectionItem;
        ToggleGroup toggleGroup = new ToggleGroup();
        sidebar.setStyle("-fx-background-color:   #e0e0d1");

        menuSection = new MenuSection("Home", "/homeM.png", null);
        menuSection.setAnimated(false);
        menuSection.setCollapsible(false);
        menuSection.setOnMouseClicked(event -> {
            loadPage(HomeScreenSingleton.getInstance());
        });
        sidebar.add(menuSection);

        menuSection = new MenuSection("Service Schedule", "/calendarM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Show Schedules");
        menuSectionItem.setOnAction(event -> {
            try {
                exitPage(getCurrentPage());
                _pane.setCenter(BrowserSingleton.getInstance(new URL("http://localhost:8080/Calendar")));
            } catch (MalformedURLException e) {
            }
        });
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Publish/Unpublish Schedule");
        //  menuSectionItem.setOnMouseClicked(event -> new LegendTable());
        menuSectionItem.setOnMouseClicked(event -> {
            loadPage(MonthPublisherSingleton.getInstance(_configuration));
        });
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Services", "/dutyM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Service Schedules");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Service Management");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Musician", "/orchestraiconM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSectionItem.setOnMouseClicked(event -> {
            loadPage(MusiciansTableSingleton.getInstance(_configuration));
        });
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician List");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Compositions", "/musicfolderM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("MusicalWork Management");
        menuSectionItem.setOnMouseClicked(event -> loadPage(MusicalWorkSingleton.getInstance(_configuration)));
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Inventory", "/inventaryM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Show Inventory");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Add Item");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("User Screen", "/userM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Section Management");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Administration", "/settingsM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Section Management");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        // load the default site
        loadPage(HomeScreenSingleton.getInstance());

        return sidebar;
    }

    private BasePage getCurrentPage() {
        return _currentPage;
    }

    private void setCurrentPage(BasePage page) {
        _currentPage = page;
    }

    private List<BasePage> getInitializedPageList() {
        return _initializedPageList;
    }

    private void addInitializedPageListItem(BasePage page) {
        if(!_initializedPageList.contains(page)) {
            page.initialize();
            _initializedPageList.add(page);
        }
    }

    private void setPane(BasePage page) {
        _pane.setCenter(page);
    }

    private boolean loadPage(BasePage page) {
        if(page != null) {
            if(exitPage(page)) {
                addInitializedPageListItem(page);
                page.load();
                setCurrentPage(page);
                setPane(getCurrentPage());
                return true;
            }

            return false;
        }

        return false;
    }

    private boolean exitPage(BasePage page) {
        if(page != null) {
            page.exit();
            setCurrentPage(null);
            return true;
        }

        return false;
    }
}
