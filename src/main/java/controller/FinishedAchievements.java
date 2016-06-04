package controller;

import com.vaadin.server.Page;
import com.vaadin.ui.AbstractLayout;
import model.Achievement;
import model.User;
import util.Master;
import view.MyUI;

import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class FinishedAchievements  extends AchievementsGallery{


    public FinishedAchievements(User user, MyUI ui) {
        super(user, ui, "Abgeschlossene Erfolge");
        update();
    }

    @Override
    public void update() {

        ArrayList<Achievement> achievements = new ArrayList<>();
        ArrayList<Achievement> dummyAchievements = Master.allAchievements;

        //Instate of the dummy data use the user data.
        dummyAchievements.forEach(achievement -> {
            //filter the achievemnts here
            if(achievement.isFinished()) {
                achievements.add(achievement);
            }
        });
        setAchievements(achievements);

        updateLayout();
    }
}
