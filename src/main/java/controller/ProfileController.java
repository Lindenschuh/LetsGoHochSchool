package controller;

import controller.course.OverviewCourses;
import model.User;
import view.MyUI;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class ProfileController extends Modul {

    private MyUI ui;

    private Profile profile;
    private OverviewCourses ovCourses;

    public ProfileController(User user, MyUI ui) {
        super(user);
        this.ui = ui;

        profile = new Profile(user);
        ovCourses = new OverviewCourses(user, ui);
        layout.addComponent(profile.getContent());
        layout.addComponent(ovCourses.getContent());
    }
}
