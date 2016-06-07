package controller;

import com.vaadin.ui.VerticalLayout;
import model.Course;
import model.User;
import view.MyUI;

import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class ProfileController extends Modul {

    private Profile profile;
    private GalleryModul coursesOverview;

    public ProfileController(User user, MyUI ui) {
        super(user);
        profile = new Profile(user);
        coursesOverview = new GalleryModul(user, ui);

        createLayout();
    }

    private void createLayout() {
        coursesOverview.setName("Kurse");
        coursesOverview.showAddBtn(user.isAdmin());
        coursesOverview.setData((ArrayList) user.getCourses());

        VerticalLayout contentLayout = new VerticalLayout();

        contentLayout.addComponent(profile.getContent());
        contentLayout.addComponent(coursesOverview.getContent());
        layout.addComponent(contentLayout);
    }

}
