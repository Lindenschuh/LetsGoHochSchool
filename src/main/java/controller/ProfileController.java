package controller;

import com.vaadin.ui.VerticalLayout;
import controller.module.GalleryModul;
import controller.module.Modul;
import controller.module.Profile;
import model.Course;
import model.User;
import view.MyUI;

import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class ProfileController extends Modul {


    public ProfileController(User user, MyUI ui) {
        super(user);

        //Create the modules.
        Profile profile = new Profile(user);
        GalleryModul courseGallery = new GalleryModul(user, ui);

        //Setup the gallery module.
        courseGallery.setName("Kurse");
        courseGallery.addItemClickedListener(data -> ui.setContentPage(new CourseController(user, (Course) data)));
        courseGallery.addButtonClickedListener(() -> System.out.println("Gallery add button clicked."));
        courseGallery.setData((ArrayList) user.getCourses());

        VerticalLayout contentLayout = new VerticalLayout();

        contentLayout.addComponent(profile.getContent());
        contentLayout.addComponent(courseGallery.getContent());
        contentLayout.setStyleName("page");
        contentLayout.setSpacing(true);

        layout.addComponent(contentLayout);
    }
}
