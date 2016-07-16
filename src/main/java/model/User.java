package model;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Image;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lars on 02.06.2016.
 */
public class User implements DataObject{

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
    }

    public void setTodos(ArrayList<String> todoList, String course)
    {
        ArrayList<String> newTodos = (ArrayList<String>) todoList.clone();
        todos.put(course,newTodos);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public ArrayList<String> getTodos(String coursName) {
        return todos.get(coursName)==null? new ArrayList<String>():todos.get(coursName);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<LocalDateTime> getTimes() {
        return times;
    }

    public String getRoom() { return room; }

    @Override
    public Image getImage() {
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
    }

    public void addAdminToCourse(Course course) {
        courses.add(course);
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
    }

    @Override
    public String toString() {
        return email;
    }

    public void addAchievment(Achievement achievment) {
        this.workingOnAchievment.add(achievment);
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
