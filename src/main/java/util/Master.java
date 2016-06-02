package util;

import model.Course;
import model.User;

import java.util.ArrayList;

/**
 * Created by Lars on 02.06.2016.
 */
public class Master {
    static ArrayList<Course> allCourse = new ArrayList<Course>();
    static ArrayList<User> allUser = new ArrayList<User>();


    public static void makeTest()
    {
        allUser.add(new User("Steve","Steve@stud.hs-heilbronn.de","123",false,""));
        allCourse.add(new Course("Mathe",null,"MatheThings","25.10.1995:20:00",10,"200"));
    }
}
