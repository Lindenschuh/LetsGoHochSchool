package controller;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
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

        layout.setStyleName("menu");
        layout.setSizeFull();


        Button search = new Button("Search");
        search.addClickListener(e -> ui.getSearch().show(!ui.getSearch().isVisible()));
        search.setSizeFull();
        search.setIcon(FontAwesome.SEARCH);
        layout.addComponent(search);


        Button home = new Button("Home");
        home.addClickListener(e -> {
            if(!(page instanceof HomeController)) {
                ui.setPage(new HomeController(user, ui));
            }
        });
        home.setSizeFull();
        home.setIcon(FontAwesome.HOME);
        layout.addComponent(home);


        Button profile = new Button("Profile");
        profile.addClickListener(e -> {
            if(!(page instanceof ProfileController)) {
                ui.setPage(new ProfileController(user, ui));
            }
        });
        profile.setSizeFull();
        profile.setIcon(FontAwesome.USER);
        layout.addComponent(profile);


        Button course = new Button("Course");
        course.addClickListener(e -> {
            if (!(page instanceof CourseController)) {
                ui.setPage(new CourseController(user));
            }
        });
        course.setSizeFull();
        course.setIcon(FontAwesome.BOOK);
        layout.addComponent(course);


        Button schedule = new Button("Schedule");
        schedule.addClickListener(e -> {
            if (!(page instanceof CalenderController)) {
                ui.setPage(new CalenderController(user));
            }
        });
        schedule.setSizeFull();
        schedule.setIcon(FontAwesome.CALENDAR);
        layout.addComponent(schedule);


        Button achievements = new Button("Achievements");
        achievements.addClickListener(e -> {
            if (!(page instanceof AchievementsController)) {
                ui.setPage(new AchievementsController(user, ui));
            }
        });
        achievements.setSizeFull();
        achievements.setIcon(FontAwesome.TROPHY);
        layout.addComponent(achievements);
    }

    public void setPage(Modul page) {
        this.page = page;
    }
}
