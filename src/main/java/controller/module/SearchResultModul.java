package controller.module;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import model.Achievement;
import model.Course;
import model.User;
import view.MyUI;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class SearchResultModul extends Modul {

    private final static int IMAGE_SIZE = 75;

    private MyUI ui;
    private Object data;

    private Image img;
    private Image toDoImg;
    private Label nameLabel;
    private Label descriptionLabel;

    private VerticalLayout moduleLayout;
    private HorizontalLayout contentLayout;
    private VerticalLayout descriptionLayout;
    private int moduleWidth;


    public SearchResultModul(User user, MyUI ui, Object o) {
        super(user);
        data = o;
        this.ui = ui;
        createLayout();
    }

    private void calcLayout() {

        int naviWidth = 180;
        int pageSpace = 40;
        int browserWidth = ui.getPage().getBrowserWindowWidth();

        moduleWidth = browserWidth - naviWidth - 2 * pageSpace ;
    }


    private void createLayout() {

        loadToDoImg();

        nameLabel = new Label("");
        descriptionLabel = new Label("");
        moduleLayout = new VerticalLayout();
        contentLayout = new HorizontalLayout();
        descriptionLayout = new VerticalLayout();

        nameLabel.setStyleName("searchResultName");
        descriptionLayout.setStyleName("searchResultDescriptionLayout");
        contentLayout.setStyleName("moduleContent");
        moduleLayout.setStyleName("module");

        update();

        descriptionLayout.addComponent(nameLabel);
        descriptionLayout.addComponent(descriptionLabel);

        contentLayout.addComponent(img);
        contentLayout.addComponent(descriptionLayout);

        moduleLayout.addComponent(contentLayout);
        layout.addComponent(moduleLayout);

        ui.getPage().addBrowserWindowResizeListener(browserWindowResizeEvent -> update() );
    }


    private void update() {
        calcLayout();
        moduleLayout.setWidth(moduleWidth, Sizeable.Unit.PIXELS);

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
        descriptionLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
    }

    private void loadToDoImg() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/search_todo.png");
        if (f.exists()) {
            FileResource r = new FileResource(f);
            toDoImg = new Image(null, r);
            toDoImg.setDescription("ToDo");
        }
    }
}
