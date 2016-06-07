package model;

import com.vaadin.ui.Image;

/**
 * Created by Lars on 02.06.2016.
 */
public class Achievement implements DataObject {

    private String name;
    private Course course;
    private boolean finished;
    private Image img;
    //TODO: Bedinugen aufbauen (?)


    public Achievement(String name, Course course) {
        this.name = name;
        this.course = course;
        this.finished = false;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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

}
