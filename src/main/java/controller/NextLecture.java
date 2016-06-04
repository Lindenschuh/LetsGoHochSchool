package controller;

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

    public NextLecture(User user) {
        super(user);
        nextLecture = null;
        setupLayout(user);
    }

    private void setupLayout(User user)
    {
        user.getCourses().forEach(course -> {

            if(course.getDates().get(0).isBefore(nextLecture) || nextLecture == null )
            {
                nextLecture = course.getDates().get(0);
                c = course;
            }

        });

        Label next;

        if(nextLecture == null)
        {
            next = new Label("<h1>Benutzer hat keine Vorlesungen<h1>");
        }
        else
        {
            next = new Label("<h1>Nächste Vorlesung: <h1>" + c.getName() + " am " + c.getDates().get(0).toString() + "im Raum " + c.getRoom());
        }

        HorizontalLayout horilayout = new HorizontalLayout();
        horilayout.addComponent(next);
        layout.addComponent(horilayout);
    }
}
