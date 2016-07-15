package controller;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import controller.module.AchievementProgressModul;
import controller.module.Modul;
import controller.module.ShowAchievementModul;
import model.Achievement;
import model.User;
import view.MyUI;

import java.util.ArrayList;

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
    private HorizontalLayout cancelSaveLayout;
    private Button cancel;
    private Button save;
    private Label headerOpen;
    private VerticalLayout userFinished;
    private Label headerFinished;

    private boolean buttonActive;
    private boolean oneModuleTrue;

    private ArrayList<AchievementProgressModul> progressModuls;




    public AchievementDetailController(User user, MyUI ui, Achievement achievement) {
        super(user);
        this.achievement = achievement;
        this.ui = ui;


        if(this.user.isAdmin()) {
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

            progressModuls = new ArrayList<>();

            achievement.getUserProgress().forEach((k, v) -> {
                AchievementProgressModul achievementProgressModul = new AchievementProgressModul(k, achievement, false, this);
                progressModuls.add(achievementProgressModul);
                userOpen.addComponent(achievementProgressModul.getContent());
            });

            buttonActive = false;
            oneModuleTrue = false;

            setOpenButtonLayout();


            userFinished = new VerticalLayout();
            userFinished.setStyleName("module");
            contentLayout.addComponent(userFinished);

            headerFinished = new Label(FINISHED_ACHIEVMENTS_TEXT);
            headerFinished.setStyleName("moduleHead");
            userFinished.addComponent(headerFinished);

            achievement.getUserFinished().forEach(u -> {
                AchievementProgressModul achievementProgressModul = new AchievementProgressModul(u, achievement, true, this);
                userFinished.addComponent(achievementProgressModul.getContent());
            });
        } else {
            layout.addStyleName("page");
            contentLayout = new VerticalLayout();
            contentLayout.setSpacing(true);
            layout.addComponent(contentLayout);

            showAchievementModul = new ShowAchievementModul(user, ui, achievement);
            contentLayout.addComponent(showAchievementModul.getContent());
        }
        
    }

    public void setOpenButtonLayout() {
        progressModuls.forEach(e -> {
            if(e.getTemp())
                oneModuleTrue = true;
        });

        if(!buttonActive && oneModuleTrue) {
            cancelSaveLayout = new HorizontalLayout();
            cancelSaveLayout.addStyleName("moduleFoot");
            cancelSaveLayout.setSpacing(true);
            userOpen.addComponent(cancelSaveLayout);

            cancel = new Button("Abbrechen");
            cancel.addClickListener(c -> ui.setContentPage(new AchievementsController(user, ui)));
            cancelSaveLayout.addComponent(cancel);

            save = new Button("Speichern");
            save.addClickListener(s -> saveData());
            cancelSaveLayout.addComponent(save);
            buttonActive = true;
        } else if(buttonActive && !oneModuleTrue) {
            userOpen.removeComponent(cancelSaveLayout);
            buttonActive = false;
        }
        oneModuleTrue = false;
    }

    private void saveData() {
        progressModuls.forEach(e -> {
            e.saveData();
            achievement.achievementFinished(e.getUser());
        });
        ui.setContentPage(new AchievementsController(user, ui));
    }

}
