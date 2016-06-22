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
        allUser.add(new User("Steve","steve@stud.hs-heilbronn.de","123",false,""));
        allUser.add(new User("Albus Percival Wulfric Brian Dumbledore", "adumbledore@prof.hs-heilbronn.de", "123456Seven", true, "<div id = \"ip\"><p>Mi 12.30 - 13.30<br>A527</p></div><style type=\"text/css\"> #ip { background-color: #d3d3d3; padding: 1.3em;} </style>"));
        allUser.add(new User("Bob Marley","bob@stud.hs-heilbronn.de", "000",false,""));

        //create some courses
        allCourse.add(new Course("Raketen Wissenschaften", allUser.get(1),"RocketThings","30-05-2016 11:30",10,"A 200"));
        allCourse.add(new Course("Signalverarbeitung 1", allUser.get(1),"SingalThings","31-05-2016 14:00",10,"B 318"));
        allCourse.add(new Course("Englisch", allUser.get(1),"EnglishThings","02-06-2016 09:45",10,"B 120"));
        allCourse.add(new Course("Virtual Reality", allUser.get(1),"VRThings","01-06-2016 11:30",10,"F 335"));
        allCourse.add(new Course("Spieleentwicklung 1", allUser.get(1),"PMTThings","31-05-2016 09:45",10,"A 210"));
        allCourse.add(new Course("Programmieren", allUser.get(1),"ProgrammingThings","31-05-2016 11:30",10,"B 212"));
        allCourse.add(new Course("Mathematik", allUser.get(1), "Malen nach Zahlen f\u00fcr Fortgeschrittene.","02-06-2016 08:00",10,"A 201"));
        allCourse.add(new Course("Projekt Management und Tools", allUser.get(1),"Werde zum Scrum Master und mache Scrum Sachen.","01-06-2016 09:45",10,"F 213"));
        allCourse.add(new Course("Personal Productivity", allUser.get(1),"PersonalThings","02-06-2016 11:30",10,"D 101"));
        allCourse.add(new Course("Spieleentwicklung 2", allUser.get(1),"Gaming Things","03-06-2016 13:00",10,"X 001"));
        allCourse.add(new Course("Signalverarbeitung 2", allUser.get(1),"SingalThings","03-06-2016 11:30",10,"F 203"));
        allCourse.add(new Course("Verteilte Systeme", allUser.get(1),"SingalThings","03-06-2016 09:45",10,"B 200"));

        //create some achievements
        //Raketen Wissenschaften
        Achievement a2 = new Achievement("Astronaut", allCourse.get(0));
        a2.setFinished(true);
        allAchievements.add(a2);
        allAchievements.add(new Achievement("Programmier Ass", allCourse.get(0)));
        allAchievements.add(new Achievement("Raketen start", allCourse.get(0)));

        //Signalverarbeitung 1
        allAchievements.add(new Achievement("Elektronik", allCourse.get(1)));
        allAchievements.add(new Achievement("Microcontroller", allCourse.get(1)));
        allAchievements.add(new Achievement("Signalverarbeiter", allCourse.get(1)));
        allAchievements.add(new Achievement("Funkmast", allCourse.get(1)));

        //Englisch
        allAchievements.add(new Achievement("Erstes Buch", allCourse.get(2)));
        allAchievements.add(new Achievement("Buecherwurm", allCourse.get(2)));

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

        //Mathe
        Achievement a1 = new Achievement("Math King", allCourse.get(6));
        a1.setFinished(true);
        allAchievements.add(a1);

        //Projekt Management und Tools
        allAchievements.add(new Achievement("Scrum", allCourse.get(7)));

        //Personal Productivity
        allAchievements.add(new Achievement("Git Master", allCourse.get(8)));
        allAchievements.add(new Achievement("JUnit", allCourse.get(8)));

        //Verteilte Systeme
        allAchievements.add(new Achievement("Cloud", allCourse.get(11)));


        //Lade Bilder
        allUser.forEach(u -> u.setImage(loadImage(u)));
        allCourse.forEach(c -> c.setImage(loadImage(c)));
        allAchievements.forEach(a -> a.setImage(loadImage(a)));

        //Erstelle To do's
        ArrayList<String> todos = new ArrayList<>();
        todos.add("Das letzte Übungsblatt bearbeiten, um Erfolg zu erhalten.");
        todos.add("Eine andere wirklich tolle und anspruchsvolle Arbeit erledigen.");
        todos.add("Lorem ipsum iwas Textum omine Lambda lasgom.");
        todos.add("Muuuuh.");

        //Put it together
        allUser.forEach(u -> allCourse.forEach(c -> {
            u.addCourse(c);
            u.setTodos(todos, c.getName());
        }));

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

        FileResource resourceUser = new FileResource(new File(s + "/Resource/Images/steve@stud.hs-heilbronn.de.png"));
        Image imageUser = new Image(null, resourceUser);

        FileResource resourceAdmin = new FileResource(new File(s + "/Resource/Images/adumbledore@prof.hs-heilbronn.de.png"));
        Image imageAdmin = new Image(null, resourceAdmin);


        allUser.add(user);
        allUser.add(admin);
        allCourse.add(new Course("Mathe",null,"MatheThings","25-10-2016 20:00",10,"200"));

    }

    private static void todoTest(User user) {
        ArrayList<String> newTodos = new ArrayList<>();
        newTodos.add("Mathe Aufgabe 2");
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

    /**
     * Load the images for the given object.
     * Not working for users yet.
     * @param o Object that needs an image.
     * @return Image for the object.
     */
    public static Image loadImage(Object o) {
        Image img;
        FileResource resource;

        String className = o.getClass().getName();
        className = className.substring(className.indexOf('.') + 1);

        File pngFile = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/" + className + "/" + o.toString() +".png");

        File jpgFile = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/" + className + "/" + o.toString() +".jpg");

        File defaultFile = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/" + className + "/default.png");

        if (pngFile.exists()) {
            resource = new FileResource(pngFile);
        } else if(jpgFile.exists()){
            resource = new FileResource(jpgFile);
        } else {
            resource = new FileResource(defaultFile);
        }
        img = new Image(null, resource);
        img.setDescription(o.toString());
        return img;
    }

}
