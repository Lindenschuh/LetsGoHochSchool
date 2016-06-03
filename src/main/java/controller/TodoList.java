package controller;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import model.Course;
import model.User;

import java.util.ArrayList;

/**
 * Created by Lars Assmus on 03.06.2016.
 */
public class TodoList extends Modul {

    private ArrayList<String> todos;
    private User user;
    private Course course;

    public TodoList(User user, Course course) {
        super(user);

        this.user = user;
        this.course = course;
        todos = user.getTodos(course.getName());

        createComponent();

    }

    private void createComponent() {


        VerticalLayout vert = new VerticalLayout();
        vert.addComponent(new Label("<h2>Todos<h2>", ContentMode.HTML));
        for(int i = 0;i<todos.size();i++)
        {
            final int aktIndex = i;
            HorizontalLayout hori = new HorizontalLayout();
            Button butt = new Button(FontAwesome.TRASH);
            butt.addStyleName(ValoTheme.BUTTON_BORDERLESS);
            butt.addClickListener(e ->{deletToDo(aktIndex);});
            butt.setSizeFull();


            hori.addComponent(new Label("-\t" + todos.get(i)));

            hori.addComponent(butt);

            vert.addComponent(hori);
        }
        HorizontalLayout hori = new HorizontalLayout();

        TextField newTodo = new TextField();
        newTodo.setValue("new Todo");

        Button submit = new Button("create");

        submit.addClickListener(e -> {addTodo(newTodo);});

        hori.addComponent(newTodo);
        hori.addComponent(submit);
        vert.addComponent(hori);


        layout.addComponent(vert);

    }

    private void addTodo(TextField tf) {
        todos.add(tf.getValue());
        update();
    }

    private void deletToDo(int index)
    {
        todos.remove(index);
        update();

    }
    private void update()
    {
        user.setTodos(todos,course.getName());
        layout.removeAllComponents();
        createComponent();
    }


}
