package controller;

import com.vaadin.ui.VerticalLayout;
import controller.module.*;
import model.Course;
import model.User;
import view.MyUI;

import java.util.ArrayList;

/**
 * The start page. Currently two modules.
 * The next lecture module, which displays the next lecture of the current user
 * and a gallery with all courses of the user.
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class HomeController extends Modul {

    public HomeController(User user, MyUI ui) {
        super(user);

        //Create the modules.
        NextLectureModul nextLecture = new NextLectureModul(user);
        GalleryModul courseGallery = new GalleryModul(user, ui);

        //Setup the gallery module.
        courseGallery.setName("Kurse");
        courseGallery.setEmptyMsg("Keine Kurse vorhanden.");
        courseGallery.setMaxWidth(true);
        courseGallery.addItemClickedListener(data -> ui.setContentPage(new CourseController(user, (Course) data)));
        courseGallery.setData((ArrayList) user.getCourses());
        if (user.isAdmin()) {
            courseGallery.addButtonClickedListener(() -> ui.setContentPage(new NewLecture(user, ui)));
        }
        if (!user.isAdmin()) {
            courseGallery.addButtonClickedListener(() -> ui.setContentPage(new SubscribeModul(user, ui)));
        }

        VerticalLayout contentLayout = new VerticalLayout();

        contentLayout.setStyleName("page");
        contentLayout.addComponent(nextLecture.getContent());
        contentLayout.addComponent(courseGallery.getContent());
        contentLayout.setSpacing(true);
        layout.setWidth("100%");
        layout.addComponent(contentLayout);
    }



}
