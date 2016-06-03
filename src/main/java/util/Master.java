package util;

import model.Course;
import model.User;

import java.util.ArrayList;

/**
 * Created by Lars on 02.06.2016.
 */
public class Master {
   public static ArrayList<Course> allCourse = new ArrayList<Course>();
   public static ArrayList<User> allUser = new ArrayList<User>();


    public static void makeTest()
    {

    }

    /**
     * Create some test data.
     */
    public static void makeCourseModulTestData() {
        allUser.clear();
        allCourse.clear();

        allUser.add(new User("Steve","Steve@stud.hs-heilbronn.de","123",false,""));
        allCourse.add(new Course("Mathe",null,"MatheThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Englisch",null,"EnglishThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Programmieren",null,"ProgrammingThings","25.10.1995:20:00",10,"200"));
        /*allCourse.add(new Course("Signal Verarbeitung 1",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 2",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 3",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 4",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 5",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 6",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 7",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 8",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 9",null,"SingalThings","25.10.1995:20:00",10,"200"));
        allCourse.add(new Course("Signal Verarbeitung 10",null,"SingalThings","25.10.1995:20:00",10,"200"));*/

        allCourse.forEach(c -> allUser.get(0).addCourse(c));
    }
}
