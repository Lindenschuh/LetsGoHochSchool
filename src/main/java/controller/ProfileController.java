package controller;

import com.vaadin.ui.VerticalLayout;
import controller.module.*;
import model.Achievement;
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
        VerticalLayout contentLayout = new VerticalLayout();
        GalleryModul gallery = new GalleryModul(user, ui);
        gallery.setMaxWidth(true);


        contentLayout.setSpacing(true);
        contentLayout.setStyleName("page");
        contentLayout.addComponents(profile.getContent(), gallery.getContent());

        layout.setWidth("100%");
        layout.addComponent(contentLayout);

        //Check, if the user views his or another users profile.
        if (user == ui.getUser()) {

            //Setup the gallery module.
            gallery.setName("Kurse");
            gallery.setEmptyMsg("Keine Kurse vorhanden.");
            gallery.addItemClickedListener(data -> ui.setContentPage(new CourseController(user, (Course) data)));
            if (user.isAdmin()) {
                gallery.addButtonClickedListener(() -> ui.setContentPage(new NewLecture(user, ui)));
            }
            if (!user.isAdmin()) {
                gallery.addButtonClickedListener(() -> ui.setContentPage(new SubscribeModul(user, ui)));
            }
            gallery.setData((ArrayList) user.getCourses());

        } else if (user.isAdmin()) {
            gallery.setName("Vorlesungen");
            gallery.setEmptyMsg("Keine Vorlesungen vorhanden.");
            gallery.setData((ArrayList) user.getCourses());
            gallery.addItemClickedListener(dataObject -> {
                if (ui.getUser().getCourses().contains(dataObject))
                    ui.setContentPage(new CourseController(ui.getUser(), (Course) dataObject));
            });

        } else {
            gallery.setName("Erfolge");
            gallery.setEmptyMsg("Keine Erfolge vorhanden.");
            gallery.setData((ArrayList) user.getFinishedAchievment());
            gallery.addItemClickedListener(dataObject -> {
                Achievement achievement = (Achievement) dataObject;
                if (!ui.getUser().isAdmin()) {
                    if (ui.getUser().getCourses().contains(dataObject))
                        ui.setContentPage(new AchievementDetailController(
                                ui.getUser(), ui, achievement));
                } else {
                    if (ui.getUser().getCourses().contains(achievement.getCourse())) {
                        ui.setContentPage(new AchievementDetailController(
                                ui.getUser(), ui, achievement));
                    }
                }
            });
        }
    }

    public User getUser() {
        return user;
    }
}
