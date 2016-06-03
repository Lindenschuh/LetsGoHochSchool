package controller;

import com.vaadin.ui.Label;
import model.Achievement;
import model.Course;
import model.User;
import util.Master;

import java.util.ArrayList;


/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class CourseAchievements extends AchievementsGallery{

    private Course course;

    public CourseAchievements(User user, Course course, int width) {
        super(user, width, course.getName());
        this.course = course;
        update();
    }

    @Override
    public void update() {
        ArrayList<Achievement> achievements = new ArrayList<>();
        ArrayList<Achievement> dummyAchievements = Master.allAchievements;

        //Instate of the dummy data use the user data.
        dummyAchievements.forEach(achievement -> {

            //filter for all achievements of a course
            if(course != null && course.getName().equals(achievement.getCourse().getName())) {
                achievements.add(achievement);
            }
        });
        setAchievements(achievements);

        updateLayout();
    }
}
