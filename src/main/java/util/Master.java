package util;


import com.vaadin.server.FileResource;

import com.vaadin.ui.Image;

import model.Achievement;
import model.Course;
import model.User;
import org.zoodb.jdo.ZooJdoHelper;
import org.zoodb.tools.ZooHelper;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Lars on 02.06.2016.
 */
public class Master {
   public static ArrayList<Course> allCourse = new ArrayList<>();
   public static ArrayList<User> allUser = new ArrayList<>();
   public static ArrayList<Achievement> allAchievements = new ArrayList<>();
   public static String dbName = "letsGODb";




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
        ArrayList<User> allAdim = new ArrayList<>();

        //Administaratoren
        //Hauptadministrator
        allAdim.add(new User("Prof. Dr. Dieter Hoffmann", "dhoffmann", "dieter.hoffmann@prof.hs-heilbronn.de" ,"password", "29-06-2016 13:00", 20, "B 017"));

        //Nebenadministratoren
        allAdim.add(new User("Prof. Dr. Dr. Ing. Peter Schmidt", "pschmidt", "peter.schmidt@prof.hs-heilbronn.de", "7654321", "27-06-2016 11:00", 20, "G 712"));
        allAdim.add(new User("Prof. Dr. Mag. Albus Percival Wulfric Brian Dumbledore", "adumbledor", "albus.dumbledore@prof.hs-heilbronn.de", "123456Seven", "23-06-2016 15:00" , 20,"A 211"));
        allAdim.add(new User("Dr. Jugine Soldberg", "jsoldberg", "Jugine.Soldberg@prof.hs-heilbronn.de", "wololo", "24-06-2016 09:00", 20, "C 402"));

        //Studenten
        //Hauptstudenten: 3
        allUser.add(new User("Steve Urcle", "surcle", "surcle@stud.hs-heilbronn.de","1234"));
        allUser.add(new User("Bart Simpson", "bsimpson", "bsimpson@stu.hs-heilbronn.de", "eatShorts"));
        allUser.add(new User("Frida Bertholdt", "fberthold", "fberthold@stud.hs-heilbronn.de", "Wanderblume"));

        //Nebenstudenten: 9
        allUser.add(new User("Marc Hoshi", "mhoshi", "mhoshi@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("April Herold", "aherold", "aherold@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("Diddi Krause", "dkrause", "dkrause@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("Peter Peters", "ppeters", "ppeters@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("Lasse Arnold", "larnold", "larnold@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("Lydia Grosser", "lgrosser", "lgrosser@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("Marco Bamberg", "mbamberg", "mbamberg@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("Paula Groenninger", "pgroennin", "pgroennin@stud.hs-heilbronn.de", "123"));
        allUser.add(new User("Bob Marley", "bmarley", "bmarley@stud.hs-heilbronn.de", "123"));



        //Courses
        //0
        allCourse.add(new Course("Raketen Wissenschaften", allAdim.get(1),"RocketThings","30-05-2016 11:30",10,"A 200"));
        //Achievements "Raketen Wissenschaften"
        allAchievements.add(new Achievement("Astronaut", allCourse.get(0), 10, "\u00dcberstehe alle k\u00f6rperlichen Tests."));
        allAchievements.add(new Achievement("Raketen starten", allCourse.get(0), 4, "Alle Verbrennungstriebwerktests wurden bestanden."));
        allAchievements.add(new Achievement("Ortung", allCourse.get(0), 5, "Alle Navigationsübungen bestanden."));

        //1
        allCourse.add(new Course("Englisch", allAdim.get(3),"EnglishThings","02-06-2016 09:45",20,"B 120"));
        //Achievements "Englisch"
        allAchievements.add(new Achievement("Auslandsemester", allCourse.get(1), 3, "Drei mal an einem Austauschprojekt teilgenommen."));
        allAchievements.add(new Achievement("Well Speaker", allCourse.get(1), 1, "Referatnote gut oder besser"));

        //2
        allCourse.add(new Course("Verteilte Systeme", allAdim.get(1),"SingalThings","03-06-2016 09:45",20,"B 200"));
        //Achievements "Verteilte Systeme"
        allAchievements.add(new Achievement("Cloud", allCourse.get(2), 100, "In den Tests zusammen 100 Punkte erreicht."));
        allAchievements.add(new Achievement("Netzwerker", allCourse.get(2), 10, "Alle 10 verschiedenen Netzwerkaufgaben gebaut"));

        //3
        allCourse.add(new Course("Signalverarbeitung 1", allAdim.get(1),"SingalThings","31-05-2016 14:00",20,"B 318"));
        //Achievments "Signalverarbeitung"
        allAchievements.add(new Achievement("Funkmast", allCourse.get(3), 2, "Beim Aufstellen und Einstellen von 2 Funkmasten teilgenommen."));
        allAchievements.add(new Achievement("Signalverarbeiter", allCourse.get(3), 1, "Test f\u00fcr Siegnalveratbeung bestanden."));
        allAchievements.add(new Achievement("Elektronik", allCourse.get(3), 23, "Alle Schaltungen im Labor gel\u00f6tet."));
        //4
        allCourse.add(new Course("Signalverarbeitung 2", allAdim.get(1),"SingalThings","03-06-2016 11:30",20,"F 203"));
        //Achievements "Signalverarbetung 2"
        allAchievements.add(new Achievement("Sinus-Schwinger", allCourse.get(4), 15, "Mehr als 15 Punkte im Messungslabor gesammelt."));
        allAchievements.add(new Achievement("Microcontroller", allCourse.get(4), 13, "Der Programierte Microcontroller erf\u00fcllt alle 13 Kriterien."));
        allAchievements.add(new Achievement("Komplex macht es einfacher", allCourse.get(4), 6, "Alle \u00dcbungen f\u00fcr Komplexe Zahlen abgegeben"));
        allAchievements.add(new Achievement("Schaltplan zeichner", allCourse.get(4), 4, "Bei allen Schaltplanvorlesungen teilgenommen und \u00dcbungen bestanden."));

        //5
        allCourse.add(new Course("Spieleentwicklung 1", allAdim.get(0),"PMT Things","31-05-2016 09:45",20,"A 210"));
        //Achievements "Spieleentwicklung 1"
        allAchievements.add(new Achievement("Pong", allCourse.get(5), 1,"Erstes Spiel geschaffen."));
        allAchievements.add(new Achievement("Map Creator", allCourse.get(5), 5, "Editor f\u00fcr Maps geschrieben, der mindestens 5 Kriterien erf\u00fcllt."));
        allAchievements.add(new Achievement("Gaming", allCourse.get(5), 45, "45 Stunden an Spieleentwicklung get\u00e4tigt"));

        //6
        allCourse.add(new Course("Spieleentwicklung 2", allAdim.get(0),"Gaming Things","03-06-2016 13:00",20,"X 001"));
        //Achievements "Spieleentwicklung 2"
        allAchievements.add(new Achievement("Gaming Jam", allCourse.get(6), 1, "Teilnahme an der Gaming Jam dises jahr teilgenommen." ));
        allAchievements.add(new Achievement("Unity Developer", allCourse.get(6), 35, "Alle Teilschritte f\u00fcr die Unity\u00fcbungen vollendet."));

        //7
        allCourse.add(new Course("Mathematik", allAdim.get(1), "Malen nach Zahlen f\u00fcr Fortgeschrittene.","02-06-2016 08:00",20,"A 201"));
        //Achievments "Mathematik"
        allAchievements.add(new Achievement("Mathe King", allCourse.get(7), 1, "Mathematik erfolgreich abegschlossen."));
        allAchievements.add(new Achievement("Buecherwurm",allCourse.get(7), 5, "Du hast alle f\u00fcnf vorlesungsbezogene B\u00fccher durchgearbeitet."));


        //8
        allCourse.add(new Course("Projekt Management und Tools", allAdim.get(2),"Werde zum Scrum Master und mache Scrum Sachen.","01-06-2016 09:45",20,"F 213"));
        allAchievements.add(new Achievement("Scrum",allCourse.get(8), 50, "In den Tests 50 % ereicht"));
        allAchievements.add(new Achievement("Navigator",allCourse.get(8), 5, "Die Projektwoche wurde nach Scrum durchgef\u00fchrt und jeder Tag erfolgreich Pr\u00e4sentiert."));
        allAchievements.add(new Achievement("Git Master",allCourse.get(8), 7, "GIT wurde erfolgreich in das Projekt einbezogen."));

        //9
        allCourse.add(new Course("Personal Productivity", allAdim.get(2),"PersonalThings","02-06-2016 11:30",20,"D 101"));
        allAchievements.add(new Achievement("Erstes Buch",allCourse.get(9), 10, "Alle Kapitel wurden zum erten Thema erarbeitet."));
        allAchievements.add(new Achievement("UI Designer",allCourse.get(9), 4, "Das User Interface erf\u00fcllt alle 4 Bewertungskriterien."));

        //10
        allCourse.add(new Course("Programmieren", allAdim.get(0),"ProgrammingThings","31-05-2016 11:30",20,"B 212"));
        allAchievements.add(new Achievement("HTML Fabrik",allCourse.get(10), 10, "Der Studierende kann sehr gut mit HTML umgehen."));
        allAchievements.add(new Achievement("Konsole",allCourse.get(10), 3, "Vorlesung: \"Wie arbeite ich mit der Konsole\" geh\u00f6rt und \u00dcbung bestanden."));
        allAchievements.add(new Achievement("JUnit",allCourse.get(10), 5, "Testen ist kein Problem mehr."));
        allAchievements.add(new Achievement("Programmierer Ass",allCourse.get(10), 70, "Mindestens 70 Punkte im Abschlussprojekt erhalten."));


        //11
        allCourse.add(new Course("Virtual Reality", allAdim.get(0),"VRThings","01-06-2016 11:30",20,"F 335"));
        allAchievements.add(new Achievement("Bluetooth Expert",allCourse.get(11), 14, "Das Virtual Reality device kann \u00fcber Bluetooth angesteuert werden."));
        allAchievements.add(new Achievement("Coding Master",allCourse.get(11), 30, "Mit der Bibliothek umzugehen ist ein Kinderspiel"));

        //12
        allCourse.add(new Course("Medizinische Informatik", allAdim.get(3), "Medizinische Informatik", "03-06-2016 08:00", 20, "F 212"));
        allAchievements.add(new Achievement("Medizin",allCourse.get(12), 13, "Alle medizinischen Vorlesungen geh\u00f6rt."));
        allAchievements.add(new Achievement("Frankenstein",allCourse.get(12), 1, "Geschichte der Medizin Test bestanden."));

        //Kursbelegungen
        //Steve Urcle
        allUser.get(0).addCourse(allCourse.get(0));
        ArrayList<String> todo = new ArrayList<>();
        todo.add("Schuhe putzen");
        todo.add("affen schälen");
        todo.add("banane streicheln");
        allUser.get(0).setTodos(todo, allCourse.get(0).getName());
        todo.clear();

        allUser.get(0).addCourse(allCourse.get(10));
        todo.add("pyramiden essen");
        todo.add("kuchen schauen");
        allUser.get(0).setTodos(todo, allCourse.get(10).getName());
        todo.clear();

        allUser.get(0).addCourse(allCourse.get(9));
        todo.add("ball lecken");
        todo.add("krake riechen");
        todo.add("parfüm anfassen");
        todo.add("attila gr\u00fc\u00dfen");
        allUser.get(0).setTodos(todo, allCourse.get(9).getName());
        todo.clear();

        allUser.get(0).addCourse(allCourse.get(7));
        todo.add("spiel putzen");
        allUser.get(0).setTodos(todo, allCourse.get(7).getName());
        todo.clear();

        //Bart Simpson
        allUser.get(1).addCourse(allCourse.get(2));
        todo.add("sigrund f\u00fcttern");
        todo.add("Pc sauber machen");
        todo.add("Alternative suchen");
        allUser.get(1).setTodos(todo, allCourse.get(2).getName());
        todo.clear();

        allUser.get(1).addCourse(allCourse.get(3));
        todo.add("kameraden fragen");
        todo.add("salte sammeln");
        allUser.get(1).setTodos(todo, allCourse.get(3).getName());
        todo.clear();


        allUser.get(1).addCourse(allCourse.get(8));
        todo.add("mc hammer ist ein dilema");
        todo.add("suchen finden weiter schauen");
        allUser.get(1).setTodos(todo, allCourse.get(8).getName());
        todo.clear();

        allUser.get(1).addCourse(allCourse.get(7));
        todo.add("omnipotentus maximus");
        todo.add("Harry potter auf ein bier einladen");
        allUser.get(1).setTodos(todo, allCourse.get(7).getName());
        todo.clear();

        allUser.get(1).addCourse(allCourse.get(5));
        todo.add("Steve der mieft");
        allUser.get(1).setTodos(todo, allCourse.get(5).getName());
        todo.clear();

        //Frida Bertholdt
        allUser.get(2).addCourse(allCourse.get(8));
        todo.add("Kevin the nevin");
        todo.add("Hallo Mutter ich bin im TV");
        todo.add(" Ron the weasel");
        allUser.get(2).setTodos(todo, allCourse.get(8).getName());
        todo.clear();

        allUser.get(2).addCourse(allCourse.get(5));
        todo.add("Agathe Bauer");
        todo.add("Flamingo Flamingo");
        allUser.get(2).setTodos(todo, allCourse.get(5).getName());
        todo.clear();

        allUser.get(2).addCourse(allCourse.get(2));
        todo.add("Harald Potter zum wasser einladen");
        todo.add("Salty mcSalt face");
        allUser.get(2).setTodos(todo, allCourse.get(2).getName());
        todo.clear();

        //Andere Benutzer in Kurse eintragen
        //3
        allUser.get(3).addCourse(allCourse.get(12));
        allUser.get(3).addCourse(allCourse.get(6));
        allUser.get(3).addCourse(allCourse.get(11));
        allUser.get(3).addCourse(allCourse.get(5));
        allUser.get(3).addCourse(allCourse.get(7));

        //4
        allUser.get(4).addCourse(allCourse.get(8));
        allUser.get(4).addCourse(allCourse.get(3));
        allUser.get(4).addCourse(allCourse.get(2));

        //5
        allUser.get(5).addCourse(allCourse.get(10));
        allUser.get(5).addCourse(allCourse.get(5));
        allUser.get(5).addCourse(allCourse.get(6));

        //6
        allUser.get(6).addCourse(allCourse.get(3));
        allUser.get(6).addCourse(allCourse.get(12));
        allUser.get(6).addCourse(allCourse.get(11));
        allUser.get(6).addCourse(allCourse.get(10));
        allUser.get(6).addCourse(allCourse.get(5));

        //7
        allUser.get(7).addCourse(allCourse.get(1));
        allUser.get(7).addCourse(allCourse.get(0));

        //8
        allUser.get(8).addCourse(allCourse.get(11));
        allUser.get(8).addCourse(allCourse.get(7));
        allUser.get(8).addCourse(allCourse.get(0));

        //9
        allUser.get(9).addCourse(allCourse.get(1));
        allUser.get(9).addCourse(allCourse.get(2));
        allUser.get(9).addCourse(allCourse.get(3));
        allUser.get(9).addCourse(allCourse.get(4));

        //10
        allUser.get(10).addCourse(allCourse.get(3));
        allUser.get(10).addCourse(allCourse.get(5));
        allUser.get(10).addCourse(allCourse.get(9));

        //11
        allUser.get(11).addCourse(allCourse.get(12));
        allUser.get(11).addCourse(allCourse.get(8));
        allUser.get(11).addCourse(allCourse.get(5));




        allUser.addAll(allAdim);


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


    public static void loadDB()
    {

        if(!ZooHelper.dbExists(Master.dbName))
        {
            ZooHelper.createDb(Master.dbName);
            Master.makeTest();
        }else {

            PersistenceManager pm = ZooJdoHelper.openDB(Master.dbName);
            pm.currentTransaction().begin();

            Extent<User> users = pm.getExtent(User.class);
            users.forEach(user -> allUser.add(user));
            users.closeAll();

            Extent<Course> courses = pm.getExtent(Course.class);
            courses.forEach(course -> allCourse.add(course));
            courses.closeAll();

            Extent<Achievement> achievements = pm.getExtent(Achievement.class);
            achievements.forEach(achievement -> allAchievements.add(achievement));
            achievements.closeAll();

            pm.currentTransaction().commit();
            closeDB(pm);


        }
    }

    private static void closeDB(PersistenceManager pm) {

        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        pm.close();
        pm.getPersistenceManagerFactory().close();


    }
    
    static {
        ZooHelper.removeDb(Master.dbName);
    }


}
