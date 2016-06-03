package controller;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import model.Course;
import model.User;

import java.util.ArrayList;

/**
 * Created by Lars Assmus on 03.06.2016.
 */
public class TodoList extends Modul {

    private ArrayList<String> todos;

    public TodoList(User user, Course course) {
        super(user);

        todos = user.getTodos(course.getName());

        createComponent();

    }

    private void createComponent() {

        VerticalLayout vert = new VerticalLayout();

        for(int i = 0;i<todos.size();i++)
        {
            HorizontalLayout hori = new HorizontalLayout();
            hori.addComponent(new Label("-\t" + todos.get(i)));
            vert.addComponent(hori);
        }

        layout.addComponent(vert);

    }


}
