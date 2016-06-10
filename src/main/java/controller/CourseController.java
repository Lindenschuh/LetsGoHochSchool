package controller;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import controller.module.*;
import model.Course;
import model.User;

import java.util.ArrayList;

/**
 * Created by Lars Assmus on 04.06.2016.
 */
public class CourseController extends Modul {
    ArrayList<Course> courses;
    User user;
    ComboBox comb;
    Course currentCourse;


    public CourseController(User user) {
        super(user);

        this.courses = user.getCourses();
        this.user = user;
        comb = new ComboBox();
        comb.setWidth(500f, Sizeable.Unit.PIXELS);
        comb.setNullSelectionAllowed(false);
        comb.addValueChangeListener(e ->loadNewLayout());
        addToComboBox();

    }
    public CourseController(User user,Course course) {
        super(user);

        this.courses = user.getCourses();
        this.user = user;
        this.currentCourse = course;
        comb = new ComboBox();
        comb.setWidth(500f, Sizeable.Unit.PIXELS);
        comb.setNullSelectionAllowed(false);
        comb.addValueChangeListener(e ->loadNewLayout());
        addToComboBox();

    }

    private void loadNewLayout() {
        createView(getCourseOutOfComboBox());
    }

    public void createView(Course course) {
        layout.removeAllComponents();
        HorizontalLayout hori = new HorizontalLayout();
        VerticalLayout ver = new VerticalLayout();
        hori.addComponent(ver);
        ver.addComponent(comb);
        ver.addComponent(new TodoList(user,course).getContent());
        ver.addComponent(new CourseInfo(user,course).getContent());
        hori.addComponent(new CodeGenModul(user,course).getContent());
        hori.addComponent(new FileModul(user,course).getContent());
        layout.addComponent(hori);

    }

    private void addToComboBox() {
        for(int i=0;i<courses.size();i++)
        {
            comb.addItem(courses.get(i).getName());

        }
        if(currentCourse == null)
            comb.setValue(courses.get(0).getName());
        else
            comb.setValue(currentCourse.getName());

    }
    private Course getCourseOutOfComboBox()
    {
        String CorseName =(String) comb.getValue();
        Course searchedCourse = null;

        for(int i=0;i<courses.size();i++)
        {
            if(courses.get(i).getName() == CorseName)
                searchedCourse = courses.get(i);
        }


        return searchedCourse;
    }


}
