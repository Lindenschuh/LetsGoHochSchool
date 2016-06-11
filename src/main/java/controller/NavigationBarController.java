package controller;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
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
    private Modul page;

    public NavigationBarController(User user, MyUI ui) {
        super(user);
        this.ui = ui;

        createLayout();
    }

    private void createLayout() {

        VerticalLayout naviBar = new VerticalLayout();
        naviBar.setStyleName("menu");

        Button search = new Button("Search");
        search.addClickListener(e -> ui.getSearch().show(!ui.getSearch().isVisible()));
        search.setIcon(FontAwesome.SEARCH);
        naviBar.addComponent(search);


        Button home = new Button("Home");
        home.addClickListener(e -> {
            if(!(page instanceof HomeController)) {
                ui.setPage(new HomeController(user, ui));
            }
        });
        home.setIcon(FontAwesome.HOME);
        naviBar.addComponent(home);


        Button profile = new Button("Profile");
        profile.addClickListener(e -> {
            if(!(page instanceof ProfileController)) {
                ui.setPage(new ProfileController(user, ui));
            }
        });
        profile.setIcon(FontAwesome.USER);
        naviBar.addComponent(profile);


        Button course = new Button("Course");
        course.addClickListener(e -> {
            if (!(page instanceof CourseController)) {
                ui.setPage(new CourseController(user));
            }
        });
        course.setIcon(FontAwesome.BOOK);
        naviBar.addComponent(course);


        Button schedule = new Button("Schedule");
        schedule.addClickListener(e -> {
            if (!(page instanceof CalenderController)) {
                ui.setPage(new CalenderController(user));
            }
        });
        schedule.setIcon(FontAwesome.CALENDAR);
        naviBar.addComponent(schedule);


        Button achievements = new Button("Achievements");
        achievements.addClickListener(e -> {
            if (!(page instanceof AchievementsController)) {
                ui.setPage(new AchievementsController(user, ui));
            }
        });
        achievements.setIcon(FontAwesome.TROPHY);
        naviBar.addComponent(achievements);

        layout.addComponent(naviBar);
    }

    public void setPage(Modul page) {
        this.page = page;
    }
}
