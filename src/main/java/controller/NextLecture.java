package controller;


import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import model.Course;
import model.User;


import java.time.LocalDateTime;

/**
 * Created by Kevin Lee on 04/06/2016.
 */

public class NextLecture extends Modul {

    private LocalDateTime nextLecture;
    private Course c;
    private Label next;

    public NextLecture(User user) {
        super(user);

        if(!user.getCourses().isEmpty()) {
            nextLecture = user.getCourses().get(0).getDates().get(0);
            setupLayout(user);
        }
        else {
            next = new Label("Benutzer hat keine Vorlesungen", ContentMode.HTML);
        }
    }

    private void setupLayout(User user)
    {
        user.getCourses().forEach(course -> {

            if(course.getDates().get(0).isBefore(nextLecture))
            {
                nextLecture = course.getDates().get(0);
                c = course;
            }
        });
        next = new Label("<u>Nächste Vorlesung</u>" +
                            "<br>" + c.getName() +
                            "<br>Zeit: " + c.getDates().get(0).toString() +
                             "<br>Raum: " + c.getRoom(), ContentMode.HTML);


        HorizontalLayout horilayout = new HorizontalLayout();
        horilayout.addComponent(next);
        layout.addComponent(horilayout);
    }
}
