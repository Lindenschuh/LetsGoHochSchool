package model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.vaadin.ui.Image;
import org.zoodb.api.impl.ZooPC;
import util.Master;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lars on 02.06.2016.
 */

public class Achievement extends ZooPC implements DataObject {

    private String name;
    private Course course;
    private Image img;
    private int maxValue;
    private HashMap<User, Integer> userProgress;
    private ArrayList<User> userFinished;
    private String description;
    //TODO: Bedinugen aufbauen (?)

    private Achievement()
    {

    }
    public Achievement(String name, Course course, int maxValue, String description) {
        this.name = name;
        this.course = course;
        this.course.addAchievement(this);
        this.maxValue = maxValue;
        this.description = description;
        this.userProgress = new HashMap<>();
        this.userFinished = new ArrayList<>();
    }
    private void serilzeMe()
    {
        zooActivateWrite();
    }

    private void deSerilzeMe()
    {

        zooActivateRead();

    }


    @Override
    public String getName() {
        deSerilzeMe();
        return this.name;
    }

    @Override
    public void setImage(Image i) {
        this.img = i;
        serilzeMe();
    }

    @Override
    public Image getImage() {
        deSerilzeMe();
        return img;
    }

    public void setName(String name) {
        this.name = name;
        serilzeMe();
    }

    public Course getCourse() {
        deSerilzeMe();
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        serilzeMe();
    }

    @Override
    public String toString() {
        deSerilzeMe();
        return name;
    }

    public int getMaxValue() {
        deSerilzeMe();
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        serilzeMe();
    }

    public String getDescription() {
        deSerilzeMe();
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        serilzeMe();
    }

    public void addUser(User user) {
        userProgress.put(user, 0);
        user.addAchievment(this);
        serilzeMe();
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
            serilzeMe();
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<User> getUserFinished() {
        deSerilzeMe();
        return userFinished; }

    public HashMap<User, Integer> getUserProgress() {
        deSerilzeMe();
        return userProgress; }

    public int getOnesUserProgress(User user) {
        deSerilzeMe();
        if (this.userProgress.containsKey(user)) {
            return this.userProgress.get(user);
        } else {
            return this.maxValue;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if(((DataObject)obj).getName().equals(this.getName()))
            return true;
        else
            return false;
    }

}
