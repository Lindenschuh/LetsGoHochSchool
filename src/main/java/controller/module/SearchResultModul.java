package controller.module;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import controller.AchievementDetailController;
import controller.CourseController;
import controller.ProfileController;
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

    /**
     * Width and height of the images in pixels.
     */
    private final static int IMAGE_SIZE = 75;

    /**
     * Period of time, that the error message gets shown in seconds.
     */
    private final static int MESSAGETIME = 45;

    /**
     * Reference of the {@link MyUI}.
     */
    private MyUI ui;

    /**
     * Data object of the search result.
     */
    private Object data;

    /**
     * Image for the search result.
     */
    private Image img;

    /**
     * Image for to do search results.
     */
    private Image toDoImg;

    /**
     * Label with the name of the search result.
     */
    private Label nameLabel;

    /**
     * Label with the description of the search result.
     */
    private Label descriptionLabel;

    private HorizontalLayout contentLayout;
    private VerticalLayout descriptionLayout;

    /**
     * Width of a the search result module.
     */
    private int moduleWidth;


    /**
     * Create a new search result.
     * @param user The user using the search.
     * @param ui The my ui.
     * @param o The search result.
     */
    public SearchResultModul(User user, MyUI ui, Object o) {
        super(user);
        data = o;
        this.ui = ui;
        createLayout();
    }

    /**
     * Calculate the search result module size.
     */
    private void calcLayout() {

        int naviWidth = 180;
        int pageSpace = 40;
        int browserWidth = ui.getPage().getBrowserWindowWidth();

        moduleWidth = browserWidth - naviWidth - 2 * pageSpace ;
    }

    /**
     * Create the search result layout.
     */
    private void createLayout() {

        loadToDoImg();

        nameLabel = new Label("");
        descriptionLabel = new Label("");
        contentLayout = new HorizontalLayout();
        descriptionLayout = new VerticalLayout();

        nameLabel.setStyleName("searchResultName");
        descriptionLabel.setStyleName("searchResultDetail");
        descriptionLayout.setStyleName("searchResultDescriptionLayout");
        contentLayout.setStyleName("moduleSimple");

        update();

        descriptionLayout.addComponent(nameLabel);
        descriptionLayout.addComponent(descriptionLabel);

        contentLayout.addComponent(img);
        contentLayout.addComponent(descriptionLayout);

        contentLayout.setExpandRatio(descriptionLayout, 1);

        layout.addComponent(contentLayout);

        ui.getPage().addBrowserWindowResizeListener(browserWindowResizeEvent -> update() );
    }


    /**
     * Update the search result.
     */
    private void update() {
        calcLayout();
        contentLayout.setWidth(moduleWidth, Sizeable.Unit.PIXELS);

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

    /**
     * Create a to do search result.
     */
    private void createToDo(){
        img = toDoImg;
        nameLabel.setValue("ToDo");
        descriptionLabel.setValue((String) data);
    }

    /**
     * Create a user search result.
     */
    private void createUser() {
        User dataUser = (User) data;
        img = new Image(null, dataUser.getImage().getSource());
        nameLabel.setValue(dataUser.getName());
        descriptionLabel.setValue(dataUser.getEmail());

        contentLayout.addLayoutClickListener(event -> ui.setContentPage(new ProfileController(dataUser, ui)));
    }

    /**
     * Create a course search result.
     */
    private void createCourse() {
        Course course = (Course) data;

        img = new Image(null, course.getImage().getSource());
        nameLabel.setValue(course.getName());
        descriptionLabel.setValue(course.getAdmin().getName());

        contentLayout.addLayoutClickListener(event -> {
            if (user.getCourses().contains(course)) {
                ui.setContentPage(new CourseController(user, course));
            } else {
                Notification notify = new Notification("Sie sind kein Mitglied dieses Kurse und besitzen daher nicht die notwendigen Rechte", Notification.Type.ERROR_MESSAGE);
                notify.setDelayMsec(MESSAGETIME);
                notify.setPosition(Position.TOP_RIGHT);
                notify.show(ui.getPage());
            }
        });
    }

    /**
     * Create a achievement search result.
     */
    private void createAchievement() {
        Achievement achievement = (Achievement) data;

        img = new Image(null, achievement.getImage().getSource());
        nameLabel.setValue(achievement.getName());

        descriptionLabel.setValue(achievement.getDescription());

        contentLayout.addLayoutClickListener(event -> {
            if (user.getFinishedAchievment().contains(achievement) | user.getWorkingOnAchievment().contains(achievement)) {
                ui.setContentPage(new AchievementDetailController(user, ui, achievement));
            } else {
                Notification notify = new Notification("Sie sind leider nicht berechtigt diesen Erfolg anzusehen.", Notification.Type.ERROR_MESSAGE);
                notify.setDelayMsec(MESSAGETIME);
                notify.setPosition(Position.TOP_RIGHT);
                notify.show(ui.getPage());
            }
        });
    }

    /**
     * Load the to do image.
     */
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
