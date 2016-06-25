package controller;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import controller.module.AchievementProgressModul;
import controller.module.Modul;
import controller.module.ShowAchievementModul;
import model.Achievement;
import model.User;
import view.MyUI;

/**
 * Created by Aljos on 25.06.2016.
 */
public class AchievementDetailController extends Modul {

    private static final String OPEN_ACHIEVMENTS_TEXT = "Nutzer die das Achievment bearbeiten:";
    private static final String FINISHED_ACHIEVMENTS_TEXT = "Nutzer die das Achievment beendet haben:";

    private MyUI ui;
    private Achievement achievement;

    private ShowAchievementModul showAchievementModul;

    private VerticalLayout contentLayout;
    private VerticalLayout userOpen;
    private Label headerOpen;
    private VerticalLayout userFinished;
    private Label headerFinished;


    public AchievementDetailController(User user, MyUI ui, Achievement achievement) {
        super(user);
        this.achievement = achievement;
        this.ui = ui;

        layout.addStyleName("page");
        contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);
        layout.addComponent(contentLayout);

        showAchievementModul = new ShowAchievementModul(user, ui, achievement);
        contentLayout.addComponent(showAchievementModul.getContent());

        userOpen = new VerticalLayout();
        userOpen.setStyleName("module");
        contentLayout.addComponent(userOpen);

        headerOpen = new Label(OPEN_ACHIEVMENTS_TEXT);
        headerOpen.addStyleName("moduleHead");
        userOpen.addComponent(headerOpen);


        achievement.getUserProgress().forEach((k,v) -> {
            AchievementProgressModul achievementProgressModul = new AchievementProgressModul(k, achievement, false);
            userOpen.addComponent(achievementProgressModul.getContent());
        });

        userFinished = new VerticalLayout();
        userFinished.setStyleName("module");
        contentLayout.addComponent(userFinished);

        headerFinished = new Label(FINISHED_ACHIEVMENTS_TEXT);
        headerFinished.setStyleName("moduleHead");
        userFinished.addComponent(headerFinished);

        achievement.getUserFinished().forEach(u -> {
            AchievementProgressModul achievementProgressModul = new AchievementProgressModul(u, achievement, true);
            userFinished.addComponent(achievementProgressModul.getContent());
        });

    }
}
