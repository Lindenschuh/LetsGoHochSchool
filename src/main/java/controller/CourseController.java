package controller;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import controller.module.TodoList;
import controller.module.CodeGenModul;
import controller.module.CourseInfo;
import controller.module.FileModul;
import controller.module.Modul;
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
        layout.setStyleName("page");

        layout.setWidth("100%");

        CssLayout combLayout = new CssLayout();
        combLayout.setStyleName("moduleSimple");
        combLayout.addComponent(comb);

        CssLayout codeGenLayout = new CodeGenModul(user,course).getContent();
        codeGenLayout.setStyleName("moduleSimple");

        HorizontalLayout hori = new HorizontalLayout();
        VerticalLayout rowOne = new VerticalLayout();
        VerticalLayout rowTwo = new VerticalLayout();

        hori.setSpacing(true);
        hori.setWidth("100%");
        hori.addComponent(rowOne);
        hori.addComponent(rowTwo);
        hori.setExpandRatio(rowOne, 1.0f);
        hori.setComponentAlignment(rowTwo, Alignment.TOP_RIGHT);

        rowOne.setSpacing(true);
        rowOne.setWidthUndefined();
        rowOne.addComponent(combLayout);
        rowOne.addComponent(new TodoList(user,course).getContent());
        rowOne.addComponent(new CourseInfo(user,course).getContent());

        rowTwo.setSpacing(true);
        rowTwo.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
        rowTwo.setWidthUndefined();
        rowTwo.addComponent(codeGenLayout);
        rowTwo.addComponent(new FileModul(user,course).getContent());

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
