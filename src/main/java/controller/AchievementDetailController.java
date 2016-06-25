package controller;

import controller.module.Modul;
import controller.module.ShowAchievementModul;
import model.Achievement;
import model.User;
import view.MyUI;

/**
 * Created by Aljos on 25.06.2016.
 */
public class AchievementDetailController extends Modul {

    private MyUI ui;
    private Achievement achievement;

    private ShowAchievementModul showAchievementModul;



    public AchievementDetailController(User user, MyUI ui, Achievement achievement) {
        super(user);
        this.achievement = achievement;
        this.ui = ui;

        showAchievementModul = new ShowAchievementModul(user, ui, achievement);

        layout.addComponent(showAchievementModul.getContent());
        layout.addStyleName("page");


    }
}
