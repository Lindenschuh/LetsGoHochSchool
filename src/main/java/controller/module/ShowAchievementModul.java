package controller.module;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import model.Achievement;
import model.Course;
import model.User;
import view.MyUI;

/**
 * Created by Aljos on 25.06.2016.
 */
public class ShowAchievementModul extends Modul {

    private static final String MODUL_NAME = "Achievement:";
    private static final int IMAGE_SIZE = 100;

    private Achievement achievement;

    private VerticalLayout modulLayout;
    private HorizontalLayout contentLayout;
    private VerticalLayout desctriptionLayout;

    private Course achievementCourse;

    private Label achievementName;
    private Image achievementImage;
    private Label achievementDescription;
    private ProgressBar workingOnProgress;



    public ShowAchievementModul(User user, Achievement achievement) {
        super(user);
        this.achievement = achievement;

        modulLayout = new VerticalLayout();
        modulLayout.addStyleName("module");
        layout.addComponent(modulLayout);

        achievementName = new Label(this.achievement.getName());
        achievementName.addStyleName("moduleHead");
        modulLayout.addComponent(achievementName);

        contentLayout = new HorizontalLayout();
        contentLayout.setStyleName("moduleContent");
        modulLayout.addComponent(contentLayout);

        achievementImage = achievement.getImage();
        achievementImage.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        achievementImage.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        contentLayout.addComponent(achievementImage);

        desctriptionLayout = new VerticalLayout();
        contentLayout.addComponent(desctriptionLayout);

        achievementDescription = new Label(this.achievement.getDescription());
        desctriptionLayout.addComponent(achievementDescription);

        workingOnProgress = new ProgressBar(0.0f);
        desctriptionLayout.addComponent(workingOnProgress);




    }
}
