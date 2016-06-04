package controller;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import model.Course;
import model.User;

import java.util.ArrayList;

/**
 * Created by Lars Assmus on 04.06.2016.
 */
public class CourseController extends Modul{
    ArrayList<Course> courses;
    User user;
    ComboBox comb;



    public CourseController(User user) {
        super(user);

        this.courses = user.getCourses();
        this.user = user;
        comb = new ComboBox();
        comb.setWidth(500f, Sizeable.Unit.PIXELS);
        comb.setNullSelectionAllowed(false);
        comb.addValueChangeListener(e ->{loadNewLayout();});
        addToComboBox();

    }

    private void loadNewLayout() {
        layout.removeAllComponents();
        creatView(getCourseOutofComboBox());
    }

    private void creatView(Course course) {
        HorizontalLayout hori = new HorizontalLayout();
        VerticalLayout ver = new VerticalLayout();
        hori.addComponent(ver);
        ver.addComponent(comb);
        ver.addComponent(new TodoList(user,course).getContend());
        hori.addComponent(new FileModul(user,course).getContend());
        layout.addComponent(hori);

    }

    private void addToComboBox() {
        for(int i=0;i<courses.size();i++)
        {
            comb.addItem(courses.get(i).getName());
            if(i == 0)
               comb.setValue(courses.get(i).getName());
        }

    }
    private Course getCourseOutofComboBox()
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
