package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import controller.ProfileController;
import model.Course;
import model.DataObject;
import model.User;
import util.Master;
import view.MyUI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Lee on 26/06/2016.
 */
public class SubscribeModul extends Modul {

    GalleryModul subscribtion;
    VerticalLayout lay;
    Button cancel;
    ArrayList<Course> courseList = new ArrayList<>();

    public SubscribeModul(User user, MyUI ui) {
        super(user);


        lay = new VerticalLayout();

        cancel = new Button("Cancel");

        cancel.addClickListener(clickEvent -> {
            ui.setContentPage(new ProfileController(user, ui));
        });

        subscribtion = new GalleryModul(user, ui);

        subscribtion.setName("Einschreiben");
        subscribtion.addItemClickedListener(data -> {
            user.addCourse((Course) data);

            Notification notify = new Notification("Erfolgreich eingeschrieben", Notification.Type.ASSISTIVE_NOTIFICATION);
            notify.setDelayMsec(1000);
            notify.setPosition(Position.TOP_RIGHT);
            notify.show(Page.getCurrent());

            ui.setContentPage(new ProfileController(user, ui));

        });

        Master.allCourse.forEach(course -> {
            if(!user.getCourses().contains(course))
            {
                courseList.add(course);
            }
        });

        subscribtion.setData((ArrayList) courseList);

        lay.addComponent(subscribtion.getContent());
        lay.addComponent(cancel);

        layout.addComponent(lay);

    }

}
