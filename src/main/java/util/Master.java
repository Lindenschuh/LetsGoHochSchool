package util;

import com.vaadin.server.FileResource;

import com.vaadin.ui.Image;

import model.Achievement;
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
   public static ArrayList<Course> allCourse = new ArrayList<>();
   public static ArrayList<User> allUser = new ArrayList<>();
    public static ArrayList<Achievement> allAchievements = new ArrayList<>();


    public static void makeTest()
    {
        makeCourseModulTestData();
    }



    /**
     * Create some test data.
     */
    public static void makeCourseModulTestData() {
        allUser.clear();
        allCourse.clear();
        allAchievements.clear();

        //create some users
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        allUser.add(new User("Steve","Steve@stud.hs-heilbronn.de","123",false,""));
        allUser.get(0).setImage(new Image(null, new FileResource(new File(s + "/Resource/Images/ProfilePictures/profile_User.png"))));
        allUser.add(new User("Albus Percival Wulfric Brian Dumbledore", "adumbledore@prof.hs-heilbronn.de", "123456Seven", true, "<div id = \"ip\"><p>Mi 12.30 - 13.30<br>A527</p></div><style type=\"text/css\"> #ip { background-color: #d3d3d3; padding: 1.3em;} </style>"));
        allUser.get(1).setImage(new Image(null, new FileResource(new File(s + "/Resource/Images/ProfilePictures/profile_Admin.png"))));

        //create some courses
        allCourse.add(new Course("Raketen Wissenschaften",allUser.get(1),"RocketThings","25-10-1995 20:00",10,"200"));
        allCourse.add(new Course("Mathe",allUser.get(1),"MatheThings","25-10-1995 20:00",10,"200"));
        allCourse.add(new Course("Englisch",allUser.get(1),"EnglishThings","25-10-1995 20:00",10,"200"));
        allCourse.add(new Course("Virtual Reality",allUser.get(1),"VRThings","25-10-1995 11:30",10,"200"));
        allCourse.add(new Course("Spieleentwicklung 1",allUser.get(1),"PMTThings","25-03-2016 09:45",10,"200"));
        allCourse.add(new Course("Programmieren",allUser.get(1),"ProgrammingThings","25-10-1995 20:00",10,"200"));
        allCourse.add(new Course("Signalverarbeitung 1",allUser.get(1),"SingalThings","25-03-2016 08:00",10,"200"));
        allCourse.add(new Course("Projekt Management und Tools",allUser.get(1),"PMTThings","25-03-2016 09:45",10,"200"));
        allCourse.add(new Course("Personal Productivity",allUser.get(1),"PersonalThings","24-03-2016 17:00",10,"200"));
        allCourse.add(new Course("Spieleentwicklung 2",allUser.get(1),"PMTThings","25-03-2016 09:45",10,"200"));
        allCourse.add(new Course("Signalverarbeitung 2",allUser.get(1),"SingalThings","25-10-1995 11:30",10,"200"));
        allCourse.add(new Course("Verteilte Systeme",allUser.get(1),"SingalThings","25-10-1995 11:30",10,"200"));
        allCourse.add(new Course("Studium",allUser.get(1),"StudyThings","25-10-1995 11:30",10,"200"));



        //create some achievements
        //Raketen Wissenschaften
        Achievement a1 = new Achievement("Math King", allCourse.get(0));
        Achievement a2 = new Achievement("Astronaut", allCourse.get(0));

        a1.setFinished(true);
        a2.setFinished(true);
        allAchievements.add(a1);
        allAchievements.add(a2);
        allAchievements.add(new Achievement("Programmier Ass", allCourse.get(0)));
        allAchievements.add(new Achievement("Raketen start", allCourse.get(0)));


        //Spieleentwicklung 1
        allAchievements.add(new Achievement("Gaming", allCourse.get(4)));
        allAchievements.add(new Achievement("Unity Developer", allCourse.get(4)));


        //Programmieren
        Achievement a3 = new Achievement("HTML Fabrik", allCourse.get(5));
        Achievement a4 = new Achievement("UI Designer", allCourse.get(5));
        a3.setFinished(true);
        a4.setFinished(true);
        allAchievements.add(a3);
        allAchievements.add(a4);


        //Signalverarbeitung 1
        allAchievements.add(new Achievement("Elektronik", allCourse.get(6)));
        //allAchievements.add(new Achievement("Microcontroller", allCourse.get(6)));
        allAchievements.add(new Achievement("Signalverarbeiter", allCourse.get(6)));
        allAchievements.add(new Achievement("Funkmast", allCourse.get(6)));


        //Projekt Management und Tools
        allAchievements.add(new Achievement("Scrum", allCourse.get(7)));


        //Personal Productivity
        allAchievements.add(new Achievement("Git Master", allCourse.get(8)));
        //allAchievements.add(new Achievement("JUnit", allCourse.get(8)));


        //Verteilte Systeme
        allAchievements.add(new Achievement("Cloud", allCourse.get(11)));


        //Studium
        allAchievements.add(new Achievement("Auslandssemester", allCourse.get(12)));
        allAchievements.add(new Achievement("Erstes Buch", allCourse.get(12)));
        allAchievements.add(new Achievement("Buecherwurm", allCourse.get(12)));



        //Put it together
        allCourse.forEach(c -> allUser.get(0).addCourse(c));
        allCourse.forEach(c -> allUser.get(1).addCourse(c));
        todoTest(Master.allUser.get(0));
    }


    /*
    * To show the Profile in the MyUI simply input this code:
    * Master.makeTest();
    *
    * User user = Master.allUser.get(0);
    * Profile profile = new Profile(user);
    *
    * setContent(profile.getContent());
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


        allUser.add(user);
        allUser.add(admin);
        allCourse.add(new Course("Mathe",null,"MatheThings","25-10-2016 20:00",10,"200"));

    }

    private static void todoTest(User user) {
        ArrayList<String> newTodos = new ArrayList<>();
        newTodos.add("Rechne aufgabe 2");
        newTodos.add("Uebe brueche");
        user.setTodos(newTodos,"Mathe");

        ArrayList<String> newTodos1 = new ArrayList<>();
        newTodos1.add("Boom");
        newTodos1.add("krass");
        user.setTodos(newTodos1,"Raketen-Wissenschaften");

        ArrayList<String> newTodos2 = new ArrayList<>();
        newTodos2.add("Observer?");
        newTodos2.add("Amd vs Nvidia!");
        user.setTodos(newTodos2,"Programmieren");


    }

}
