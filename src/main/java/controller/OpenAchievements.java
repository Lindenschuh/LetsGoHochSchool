package controller;

import model.Achievement;
import model.User;

import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class OpenAchievements extends AchievementsGallery {

    public OpenAchievements(User user, int width) {
        super(user, width, "Offene Erfolge");
    }

    @Override
    public void update() {

        ArrayList<Achievement> achievements = new ArrayList<>();
        ArrayList<Achievement> dummyAchievements = new ArrayList<>();

        dummyAchievements.add(new Achievement("Mathe Koenig"));
        dummyAchievements.add(new Achievement("Programmier Ass"));
        dummyAchievements.add(new Achievement("Raketen start"));
        dummyAchievements.add(new Achievement("Coding Master"));

        //Instate of the dummy data use the user data.
        dummyAchievements.forEach(achievement -> {
            //filter the achievemnts here
            if(!achievement.isFinished()) {
                achievements.add(achievement);
            }
        });

        setAchievements(achievements);
    }
}
