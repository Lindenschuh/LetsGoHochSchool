package controller;

import model.Course;
import model.User;
import view.MyUI;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class ProfileController extends Modul {

    private Profile profile;
    private GalleryModul<Course> coursesOverview;

    public ProfileController(User user, MyUI ui) {
        super(user);
        profile = new Profile(user);
        coursesOverview = new GalleryModul<>(user, ui);

        createLayout();
    }

    private void createLayout() {
        coursesOverview.setName("Kurse");
        coursesOverview.showAddBtn(user.isAdmin());
        coursesOverview.setData(user.getCourses());

        layout.addComponent(profile.getContent());
        layout.addComponent(coursesOverview.getContent());
    }

}
