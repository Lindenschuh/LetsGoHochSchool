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
import view.MyUI;

import java.util.ArrayList;

/**
 * Created by Lars Assmus on 04.06.2016.
 */
public class CourseController extends Modul {
    ArrayList<Course> courses;
    User user;
    ComboBox comb;
    Course currentCourse;
    MyUI ui;


    public CourseController(User user, MyUI ui) {
        super(user);

        this.ui = ui;
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

        GridLayout grid = new GridLayout(5, 5);
        grid.setWidth("100%");
        grid.setSpacing(true);


        grid.addComponent(combLayout);
        grid.addComponent(codeGenLayout, 1, 0);
        grid.addComponent(new TodoList(user, course).getContent(), 0, 1, 0, 3);
        grid.addComponent(new CourseInfo(user, course, ui).getContent(),0, 4, 0, 4);

        grid.addComponent(new FileModul(user, course).getContent(), 1, 1, 1, 4);

        grid.setColumnExpandRatio(2, 1);

        layout.addComponent(grid);

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
