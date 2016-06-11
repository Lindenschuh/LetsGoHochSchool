package controller.module;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import model.Achievement;
import model.Course;
import model.User;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class SearchResultModul extends Modul {

    private final static int IMAGE_SIZE = 75;

    private Object data;

    private Image img;
    private Image toDoImg;
    private Label nameLabel;
    private Label descriptionLabel;

    private VerticalLayout moduleLayout;
    private HorizontalLayout contentLayout;
    private VerticalLayout descriptionLayout;



    public SearchResultModul(User user, Object o) {
        super(user);
        data = o;
        createLayout();
    }

    private void createLayout() {

        createToDoImg();

        nameLabel = new Label("");
        descriptionLabel = new Label("");
        moduleLayout = new VerticalLayout();
        contentLayout = new HorizontalLayout();
        descriptionLayout = new VerticalLayout();

        nameLabel.setStyleName("h3");
        moduleLayout.setStyleName("module");
        contentLayout.setStyleName("moduleContent");

        contentLayout.setSpacing(true);

        update();


        descriptionLayout.addComponent(nameLabel);
        descriptionLayout.addComponent(descriptionLabel);

        contentLayout.addComponent(img);
        contentLayout.addComponent(descriptionLayout);

        moduleLayout.addComponent(contentLayout);
        layout.addComponent(moduleLayout);
    }

    private void update() {
        if (data instanceof String) {
            createToDo();
        } else if (data instanceof User) {
            createUser();
        } else if (data instanceof Course) {
            createCourse();
        } else if (data instanceof Achievement) {
            createAchievement();
        } else {
            img = new Image(null, null);
        }
        img.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        img.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
    }

    private void createToDo(){
        img = toDoImg;
        nameLabel.setValue("ToDo");
        descriptionLabel.setValue((String) data);
    }

    private void createUser() {
        User dataUser = (User) data;

        img = new Image(null, dataUser.getImage().getSource());
        nameLabel.setValue(dataUser.getName());
        if(dataUser.isAdmin()) {
            //descriptionLabel.setValue(dataUser.getTimes());
            descriptionLabel.setValue("Montag, 11:30 Uhr");
        } else {
            descriptionLabel.setValue(dataUser.getEmail());
        }
    }

    private void createCourse() {
        Course course = (Course) data;

        img = new Image(null, course.getImage().getSource());
        nameLabel.setValue(course.getName());

        descriptionLabel.setValue(course.getDescription());
        //descriptionLabel.setValue(course.getAdmin().getName());
    }

    private void createAchievement() {
        Achievement achievement = (Achievement) data;

        img = new Image(null, achievement.getImage().getSource());
        nameLabel.setValue(achievement.getName());
    }

    private void createToDoImg() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/search_todo.png");
        if (f.exists()) {
            FileResource r = new FileResource(f);
            toDoImg = new Image(null, r);
            toDoImg.setDescription("ToDo");
        }
    }
}
