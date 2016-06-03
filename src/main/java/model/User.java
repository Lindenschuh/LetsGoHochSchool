package model;

import com.vaadin.ui.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lars on 02.06.2016.
 */
public class User {

    private String name;
    private String email;
    private String password;
    private boolean admin;
    private HashMap<String,ArrayList<String>> todos;
    private ArrayList<Course> courses;
    private String times;
    private Image image;

    //TODO: Default image für jeden Benutzer?


    public User(String name,String email, String password,boolean admin, String times)
    {
        this.name = name;
        this.email= email;
        this.password = password;
        this.admin = admin;
        this.times= times;
        this.courses = new ArrayList<>();
        todos = new HashMap<String, ArrayList<String>>();
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public void setTodos(ArrayList<String> todoList,String course)
    {
        todos.put(course,todoList);
    }

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
        return todos.get(coursName);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public String getTimes() {
        return times;
    }

    public Image getImage() {
        return image;
    }

    public boolean validation(String name,String password)
    {
        if(this.name.toLowerCase() == name.toLowerCase()&& this.password == password)
            return true;
        else
            return false;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
}
