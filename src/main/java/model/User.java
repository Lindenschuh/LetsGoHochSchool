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
    private String email;
    private String password;
    private boolean admin;
    private HashMap<String,ArrayList<String>> todos;
    private ArrayList<Course> courses;
    //TODO: Sprechzeit als LocalDateTime? Könnte dann in NextLecture oder Stundenplan verwendet werden.
    private ArrayList<LocalDateTime> times;
    private String room;
    private Image image;


    public User(String name,String email, String password,boolean admin, String times, int semesterLength, String room)
    {
        this.name = name;
        this.email= email;
        this.password = password;
        this.admin = admin;
        this.room = room;
        this.courses = new ArrayList<>();
        todos = new HashMap<>();

        this.times = new ArrayList<>();
        if(this.admin)
            generateDate(times, semesterLength);
    }

    @Override
    public void setImage(Image image)
    {
        this.image = image;
    }

    public void setTodos(ArrayList<String> todoList,String course)
    {
        todos.put(course,todoList);
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

    public boolean validation(String name,String password)
    {
        if(this.name.toLowerCase().equals(name.toLowerCase())&& this.password.equals(password))
            return true;
        else
            return false;
    }

    public void addCourse(Course course) {

        courses.add(course);

        course.setUserList(User.this);
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

}
