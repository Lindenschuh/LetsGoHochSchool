package controller;

import com.vaadin.ui.VerticalLayout;
import controller.module.GalleryModul;
import controller.module.Modul;
import controller.module.NextLectureModul;
import controller.module.SubscribeModul;
import model.Course;
import model.User;
import view.MyUI;

import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class HomeController extends Modul {

    //TODO: Noch andere Module einfügen.

    private NextLectureModul nextLecture;
    private GalleryModul courseGallery;

    public HomeController(User user, MyUI ui) {
        super(user);

        //Create the modules.
        nextLecture = new NextLectureModul(user, ui);
        courseGallery = new GalleryModul(user, ui);

        //Setup the gallery module.
        courseGallery.setName("Kurse");
        courseGallery.setEmptyMsg("Keine Kurse vorhanden.");
        courseGallery.addItemClickedListener(data -> ui.setContentPage(new CourseController(user, (Course) data)));
        courseGallery.addButtonClickedListener(() -> ui.setContentPage(new SubscribeModul(user, ui)));
        courseGallery.setData((ArrayList) user.getCourses());

        VerticalLayout contentLayout = new VerticalLayout();

        contentLayout.setStyleName("page");
        contentLayout.addComponent(nextLecture.getContent());
        contentLayout.addComponent(courseGallery.getContent());
        contentLayout.setSpacing(true);
        layout.setWidth("100%");
        layout.addComponent(contentLayout);
    }



}
