package controller;

import com.vaadin.ui.VerticalLayout;
import controller.module.GalleryModul;
import controller.module.Modul;
import controller.module.NextLectureModul;
import model.User;
import view.MyUI;

import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class HomeController extends Modul {

    //TODO: Noch andere Module einfügen.

    private NextLectureModul nlModule;
    private GalleryModul allCourses;

    public HomeController(User user, MyUI ui) {
        super(user);

        nlModule = new NextLectureModul(user, ui);
        allCourses = new GalleryModul(user, ui);

        allCourses.setName("Kurse");
        allCourses.setData((ArrayList) user.getCourses());

        VerticalLayout contentLayout = new VerticalLayout();

        contentLayout.addComponent(nlModule.getContent());
        contentLayout.addComponent(allCourses.getContent());
        layout.addComponent(contentLayout);
    }
}
