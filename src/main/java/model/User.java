package model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.vaadin.server.FileResource;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lars on 02.06.2016.
 */

public class User extends ZooPC implements DataObject{

    private String name;
    private String loginName;
    private String email;
    private String password;
    private boolean admin;
    private HashMap<String,ArrayList<String>> todos;
    private ArrayList<Course> courses;
    private ArrayList<LocalDateTime> times;
    private String room;
    private Image image;
    private ArrayList<Achievement> workingOnAchievment;
    private ArrayList<Achievement> finishedAchievment;


    private User(){

    }

    public User(String name, String loginName, String email, String password, String times, int semesterLength, String room)
    {
        this.name = name;
        this.loginName = loginName;
        this.email= email;
        this.password = password;
        this.admin = true;
        this.room = room;
        this.courses = new ArrayList<>();
        this.todos = new HashMap<>();

        this.times = new ArrayList<>();
        generateDate(times, semesterLength);
    }

    public User(String name, String loginName, String email, String password) {
        this.name = name;
        this.loginName = loginName;
        this.email= email;
        this.password = password;
        this.admin = false;
        this.courses = new ArrayList<>();
        this.todos = new HashMap<>();


        this.workingOnAchievment = new ArrayList<>();
        this.finishedAchievment = new ArrayList<>();

    }

    @Override
    public void setImage(Image image)
    {
        this.image = image;
        serilzeMe();
    }

    public void setTodos(ArrayList<String> todoList, String course)
    {
        ArrayList<String> newTodos = (ArrayList<String>) todoList.clone();
        todos.put(course,newTodos);
        serilzeMe();
    }

    @Override
    public String getName() {
        deSerilzeMe();
        return name;
    }

    public String getEmail() {
        deSerilzeMe();
        return email;
    }

    public boolean isAdmin() {
        deSerilzeMe();
        return admin;
    }

    public ArrayList<String> getTodos(String coursName) {
        deSerilzeMe();
        return todos.get(coursName)==null? new ArrayList<String>():todos.get(coursName);
    }

    public ArrayList<Course> getCourses() {
        deSerilzeMe();
        return courses;
    }

    public ArrayList<LocalDateTime> getTimes() {
        deSerilzeMe();
        return times;
    }

    public String getRoom() {
        deSerilzeMe();
        return room;
    }

    @Override
    public Image getImage() {
        deSerilzeMe();
        return image;
    }

    public boolean validation(String name, String password)
    {
        if(this.loginName.toLowerCase().equals(name.toLowerCase())&& this.password.equals(password))
            return true;
        else
            return false;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.setUserList(this);
        serilzeMe();
    }

    public void addAdminToCourse(Course course) {
        courses.add(course);
        serilzeMe();
    }

    private void generateDate(String date,int lessons) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime firstLesson = LocalDateTime.parse(date,formatter);


        for(int i = 0; i<lessons;i++)
        {
            times.add(firstLesson.plusDays(i*7));
        }


    }

    public void removeCourse(Course course) {
        courses.remove(course);
        serilzeMe();
    }

    @Override
    public String toString() {
        deSerilzeMe();
        return email;
    }

    public void addAchievment(Achievement achievment) {
        this.workingOnAchievment.add(achievment);
        serilzeMe();
    }

    public ArrayList<Achievement> getWorkingOnAchievment() { return workingOnAchievment; }

    public ArrayList<Achievement> getFinishedAchievment() {
        return finishedAchievment;
    }

    public String getLoginName() {
        return loginName;
    }

    public void finishAchievement(Achievement achievement) {
        this.finishedAchievment.add(achievement);
        this.workingOnAchievment.remove(achievement);
        serilzeMe();

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
    public boolean equals(Object obj)
    {
        if(((DataObject)obj).getName().equals(this.getName()))
            return true;
        else
            return false;
    }

}
