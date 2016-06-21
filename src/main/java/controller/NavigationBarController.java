package controller;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import controller.module.Modul;
import model.User;
import view.MyUI;

/**
 * Created by AnAnd on 10.06.2016.
 *
 */
public class NavigationBarController extends Modul {

    private MyUI ui;
    private Modul currentPage;
    private SearchController search;

    public NavigationBarController(User user, MyUI ui) {
        super(user);
        this.ui = ui;
        search = ui.getSearch();

        createLayout();
    }

    private void createLayout() {

        VerticalLayout naviBar = new VerticalLayout();
        naviBar.setStyleName("pageNavi");

        //Create and add search button.
        Button searchBtn = new NativeButton("Search");
        searchBtn.addClickListener(e ->
                //Toggle the search.
                search.show(!search.isVisible()));
        searchBtn.setIcon(FontAwesome.SEARCH);
        naviBar.addComponent(searchBtn);

        //Create and add home button.
        Button homeBtn = new NativeButton("Home");
        homeBtn.addClickListener(e -> {
            //Hide the search and check, if a page change is needed.
            search.show(false);
            if(!(currentPage instanceof HomeController)) {
                ui.setContentPage(new HomeController(user, ui));
            }
        });
        homeBtn.setIcon(FontAwesome.HOME);
        naviBar.addComponent(homeBtn);

        //Create and add profile button.
        Button profileBtn = new NativeButton("Profile");
        profileBtn.addClickListener(e -> {
            //Hide the search and check, if a page change is needed.
            search.show(false);
            if(!(currentPage instanceof ProfileController)) {
                ui.setContentPage(new ProfileController(user, ui));
            }
        });
        profileBtn.setIcon(FontAwesome.USER);
        naviBar.addComponent(profileBtn);

        //Create and add Course button.
        Button courseBtn = new Button("Course");
        courseBtn.addClickListener(e -> {
            //Hide the search and check, if a page change is needed.
            search.show(false);
            if (!(currentPage instanceof CourseController)) {
                ui.setContentPage(new CourseController(user));
            }
        });
        courseBtn.setIcon(FontAwesome.BOOK);
        naviBar.addComponent(courseBtn);

        //Create and add calender button.
        Button scheduleBtn = new Button("Schedule");
        scheduleBtn.addClickListener(e -> {
            //Hide the search and check, if a page change is needed.
            search.show(false);
            if (!(currentPage instanceof CalenderController)) {
                ui.setContentPage(new CalenderController(user));
            }
        });
        scheduleBtn.setIcon(FontAwesome.CALENDAR);
        naviBar.addComponent(scheduleBtn);

        //Create and add achievements button.
        Button achievementsBtn = new Button("Achievements");
        achievementsBtn.addClickListener(e -> {
            //Hide the search and check, if a page change is needed.
            search.show(false);
            if (!(currentPage instanceof AchievementsController)) {
                ui.setContentPage(new AchievementsController(user, ui));
            }
        });
        achievementsBtn.setIcon(FontAwesome.TROPHY);
        naviBar.addComponent(achievementsBtn);

        layout.addComponent(naviBar);

        //Add a page changed listener, that the navigation bar always knows what the current page is.
        ui.getContentLayout().addComponentAttachListener(listener -> {
            if (ui.getContentPage() != null) {
                currentPage = ui.getContentPage();
            }
        });
    }

}
