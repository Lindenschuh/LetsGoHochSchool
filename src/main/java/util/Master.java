package util;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import model.Course;
import model.User;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Lars on 02.06.2016.
 */
public class Master {
   public static ArrayList<Course> allCourse = new ArrayList<Course>();
   public static ArrayList<User> allUser = new ArrayList<User>();


    public static void makeTest()
    {
        testDatesProfile();
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


    /*
    * To show the Profile in the MyUI simply input this code:
    * Master.makeTest();
    *
    * User user = Master.allUser.get(0);
    * Profile profile = new Profile(user);
    *
    * setContent(profile.getContend());
    */
    private static void testDatesProfile() {
        User user = new User("Steve","Steve@stud.hs-heilbronn.de","123",false,"");
        User admin = new User("Albus Percival Wulfric Brian Dumbledore", "adumbledore@prof.hs-heilbronn.de", "123456Seven", true, "<div id = \"ip\"><p>Mi 12.30 - 13.30<br>A527</p></div><style type=\"text/css\"> #ip { background-color: #d3d3d3; width : 150px; height : 150px;} </style>");

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        FileResource resourceUser = new FileResource(new File(s + "/Resource/Images/profile_User.png"));
        Image imageUser = new Image(null, resourceUser);

        FileResource resourceAdmin = new FileResource(new File(s + "/Resource/Images/profile_Admin.png"));
        Image imageAdmin = new Image(null, resourceAdmin);

        user.setImage(imageUser);
        admin.setImage(imageAdmin);

        allUser.add(user);
        allUser.add(admin);
        allCourse.add(new Course("Mathe",null,"MatheThings","25.10.1995:20:00",10,"200"));

    }

    private static void todoTest(User user) {
        ArrayList<String> newTodos = new ArrayList<>();
        newTodos.add("Rechne aufgabe 2");
        newTodos.add("Uebe brüche");
        user.setTodos(newTodos,"Mathe");
    }

}
