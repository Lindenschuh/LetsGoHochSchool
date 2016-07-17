package controller.module;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import model.Course;
import model.User;
import util.Master;

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

        Label nameLbl = new Label("Todos");
        VerticalLayout module = new VerticalLayout();
        VerticalLayout vert = new VerticalLayout();
        vert.setWidth("530px");
        for(int i = 0;i<todos.size();i++)
        {
            final int aktIndex = i;
            HorizontalLayout hori = new HorizontalLayout();
            Button butt = new NativeButton();
            butt.setIcon(FontAwesome.TRASH);
            butt.addStyleName("borderlessButton");
            butt.addClickListener(e -> deletToDo(aktIndex));
            butt.setSizeFull();


            hori.addComponent(new Label("-\t" + todos.get(i)));

            hori.addComponent(butt);

            vert.addComponent(hori);
        }
        HorizontalLayout hori = new HorizontalLayout();
        hori.setSpacing(true);

        TextField newTodo = new TextField();
        newTodo.setWidth("410px");
        newTodo.setInputPrompt("New ToDo");
        Button submit = new Button("create");

        submit.addClickListener(e -> addTodo(newTodo));


        module.setStyleName("module");
        nameLbl.setStyleName("moduleHead");
        vert.setStyleName("moduleContent");
        hori.setStyleName("moduleFoot");

        hori.addComponent(newTodo);
        hori.addComponent(submit);

        module.addComponent(nameLbl);
        module.addComponent(vert);
        module.addComponent(hori);

        layout.addComponent(module);

    }

    private void addTodo(TextField tf) {
        if(!tf.isEmpty())
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
