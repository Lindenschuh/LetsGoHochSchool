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



    private MyUI ui;
    private Achievement achievement;
    private float progressValue;
    private int usersNumber;
    private int userSum;

    private VerticalLayout modulLayout;
    private HorizontalLayout contentLayout;
    private VerticalLayout descriptionLayout;
    private HorizontalLayout progressLayout;

    private Course achievementCourse;

    private Label achievementName;
    private Image achievementImage;
    private Label achievementDescription;
    private Label progressLbl;
    private ProgressBar workingOnProgress;



    public ShowAchievementModul(User user, MyUI ui,Achievement achievement) {
        super(user);
        this.ui = ui;
        this.achievement = achievement;

        modulLayout = new VerticalLayout();
        modulLayout.addStyleName("module");
        layout.addComponent(modulLayout);

        achievementName = new Label(this.achievement.getName());
        achievementName.addStyleName("moduleHead");
        modulLayout.addComponent(achievementName);

        contentLayout = new HorizontalLayout();
        contentLayout.setStyleName("moduleContent");
        contentLayout.setWidth(calcWidth(), Sizeable.Unit.PIXELS);
        modulLayout.addComponent(contentLayout);

        achievementImage = new Image(null, achievement.getImage().getSource());
        achievementImage.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        achievementImage.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        contentLayout.addComponent(achievementImage);

        descriptionLayout = new VerticalLayout();
        descriptionLayout.setStyleName("descriptionLayout");
        contentLayout.addComponent(descriptionLayout);
        contentLayout.setExpandRatio(descriptionLayout, 1);

        achievementDescription = new Label(this.achievement.getDescription());
        achievementDescription.setStyleName("descriptionLecture");
        descriptionLayout.addComponent(achievementDescription);

        progressLayout = new HorizontalLayout();
        progressLayout.setSpacing(true);
        descriptionLayout.addComponent(progressLayout);

        workingOnProgress = new ProgressBar(0.5f);
        progressLayout.addComponent(workingOnProgress);

        achievement.getUserProgress().put(user, achievement.getMaxValue());

        achievement.achievementFinished(user);

        actualizeProgressBar();


        //TODO: Alignment right funktioniert noch nicht.
        progressLbl = new Label(usersNumber + "/" + userSum);
        progressLayout.addComponent(progressLbl);
        //progressLayout.setComponentAlignment(progressLbl, Alignment.TOP_RIGHT);
        //progressLayout.setWidthUndefined();

        ui.getPage().addBrowserWindowResizeListener(event -> contentLayout.setWidth(calcWidth(), Sizeable.Unit.PIXELS));

    }

    public int calcWidth() {
        int naviWidth = 180;
        int pagePadding = 40;
        int modulePadding = 15;

        return ui.getPage().getBrowserWindowWidth() - (naviWidth + 2 * pagePadding + 2 * modulePadding);

    }

    public void actualizeProgressBar() {
        usersNumber = achievement.getUserFinished().size();
        userSum = usersNumber + achievement.getUserProgress().size();

        progressValue = (float) usersNumber/userSum;

        workingOnProgress.setValue(progressValue);
    }
}
