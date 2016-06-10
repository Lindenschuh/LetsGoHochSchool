package controller;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import controller.module.Modul;
import model.User;
import view.MyUI;

/**
 * Created by AnAnd on 10.06.2016.
 */
public class NavigationBarController extends Modul {

    private MyUI ui;

    //TODO: Muss noch optimiert werden, aber erfüllt erstmal seinen Zweck.
    private boolean searchSelected;
    private boolean homeSelected;
    private boolean profileSelected;
    private boolean courseSelected;
    private boolean scheduleSelected;
    private boolean achievementSelected;

    public NavigationBarController(User user, MyUI ui) {
        super(user);
        this.ui = ui;

        searchSelected = false;
        homeSelected = true;
        profileSelected = false;
        courseSelected = false;
        scheduleSelected = false;
        achievementSelected = false;

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
            if (!homeSelected) {
                searchSelected = false;
                homeSelected = true;
                profileSelected = false;
                courseSelected = false;
                scheduleSelected = false;
                achievementSelected = false;
                ui.setPage(new HomeController(user, ui).getContent());
            }
        });
        home.setSizeFull();
        home.setIcon(FontAwesome.HOME);
        layout.addComponent(home);

        Button profile = new Button("Profile");
        profile.addClickListener(e -> {
            if (!profileSelected) {
                searchSelected = false;
                homeSelected = false;
                profileSelected = true;
                courseSelected = false;
                scheduleSelected = false;
                achievementSelected = false;
                ui.setPage(new ProfileController(user, ui).getContent());
            }
        });
        profile.setSizeFull();
        profile.setIcon(FontAwesome.USER);
        layout.addComponent(profile);

        Button course = new Button("Course");
        course.addClickListener(e -> {
            if (!courseSelected) {
                searchSelected = false;
                homeSelected = false;
                profileSelected = false;
                courseSelected = true;
                scheduleSelected = false;
                achievementSelected = false;
                ui.setPage(new CourseController(user).getContent());
            }
        });
        course.setSizeFull();
        course.setIcon(FontAwesome.BOOK);
        layout.addComponent(course);


        Button schedule = new Button("Schedule");
        schedule.addClickListener(e -> {
            if (!scheduleSelected) {
                searchSelected = false;
                homeSelected = false;
                profileSelected = false;
                courseSelected = false;
                scheduleSelected = true;
                achievementSelected = false;
                ui.setPage(new CalenderController(user).getContent());
            }
        });
        schedule.setSizeFull();
        schedule.setIcon(FontAwesome.CALENDAR);
        layout.addComponent(schedule);


        Button achievements = new Button("Achievements");
        achievements.addClickListener(e -> {
            if (!achievementSelected) {
                searchSelected = false;
                homeSelected = false;
                profileSelected = false;
                courseSelected = false;
                scheduleSelected = false;
                achievementSelected = true;
                ui.setPage(new AchievementsController(user, ui).getContent());
            }
        });
        achievements.setSizeFull();
        achievements.setIcon(FontAwesome.TROPHY);
        layout.addComponent(achievements);
    }

}
