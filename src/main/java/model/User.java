package model;

import com.vaadin.ui.Image;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lars on 02.06.2016.
 */
public class User {
    public String name;
    public String email;
    public String password;
    public boolean admin;
    public HashMap<String,ArrayList<String>> todos;
    public ArrayList<Course> courses;
    public String Times;
    public Image image;

    //TODO: Konstrucktur

}
