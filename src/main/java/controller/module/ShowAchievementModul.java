package controller.module;

import com.vaadin.ui.*;
import model.Course;
import model.User;
import view.MyUI;

/**
 * Created by Aljos on 25.06.2016.
 */
public class ShowAchievementModul extends Modul {

    private static final String MODUL_NAME = "Achievement:";
    private static final int IMAGE_SIZE = 100;

    private VerticalLayout modulLayout;
    private HorizontalLayout contentLayout;
    private VerticalLayout desctriptionLayout;

    private Course achievementCourse;

    private Image achievementImage;
    private Label achievementName;
    private Label achievementDescription;
    private ProgressBar workingOnProgress;



    public ShowAchievementModul(User user) {
        super(user);
    }
}
