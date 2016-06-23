package model;

import com.vaadin.ui.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lars on 02.06.2016.
 */
public class Achievement implements DataObject {

    private String name;
    private Course course;
    private Image img;
    private int maxValue;
    private HashMap<User, Integer> userProgress;
    private ArrayList<User> userFinished;
    //TODO: Bedinugen aufbauen (?)


    public Achievement(String name, Course course, int maxValue) {
        this.name = name;
        this.course = course;
        this.maxValue = maxValue;
        this.userProgress = new HashMap<>();
        this.userFinished = new ArrayList<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setImage(Image i) {
        this.img = i;
    }

    @Override
    public Image getImage() {
        return img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return name;
    }

    public void addUser(User user) {
        userProgress.put(user, 0);
        user.addAchievment(this);
    }

    public boolean isFinishedByAll() {
        if (userProgress.isEmpty())
            return true;
        else
            return false;
    }

    public boolean achievementFinished(User user) throws NullPointerException {
        if(this.userProgress.get(user) >= this.maxValue) {
            user.finishAchievement(this);
            this.userFinished.add(user);
            this.userProgress.remove(user);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<User> getUserFinished() { return userFinished; }

    public HashMap<User, Integer> getUserProgress() { return userProgress; }

    public int getOnesUserProgress(User user) {
        if (this.userProgress.containsKey(user)) {
            return this.userProgress.get(user);
        } else {
            return this.maxValue;
        }
    }


}
