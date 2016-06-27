package util;

import com.thoughtworks.xstream.XStream;
import com.vaadin.server.FileResource;

import com.vaadin.ui.Image;

import model.Achievement;
import model.Course;
import model.User;

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


    public static void makeTest()
    {
        makeCourseModulTestData();
    }



    /**
     * Create some test data.
     */
    public static void makeCourseModulTestData() {
        //TODO: there must be a rework for the testdata!

        allUser.clear();
        allCourse.clear();
        allAchievements.clear();

        //create some users
        allUser.add(new User("Steve","steve@stud.hs-heilbronn.de","123",false, "", 0,""));
        allUser.add(new User("Albus Percival Wulfric Brian Dumbledore", "adumbledore@prof.hs-heilbronn.de", "123456Seven", true,"16-03-2016 15:00" ,30 ,"A 211"));
        allUser.add(new User("Bob Marley","bob@stud.hs-heilbronn.de", "000",false,"", 0,""));

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
        Achievement a2 = new Achievement("Programierer Ass", allCourse.get(0), 15, "Du musst 15 Übungen vollendet haben.");
        allAchievements.add(a2);

        allAchievements.add(new Achievement("Raketen start", allCourse.get(0), 30, "Alle Übungen müssen erledigt sein und in einem Projekt abgegebn werden."));



        //Signalverarbeitung 1
        allAchievements.add(new Achievement("Elektronik", allCourse.get(1), 1, "Erste Schaltung erfolgreich gebaut."));

        allAchievements.add(new Achievement("Microcontroller", allCourse.get(1), 25, "Alle Übungen zu Mikrocontroller wurden abgeschlossen."));
        allAchievements.add(new Achievement("Signalverarbeiter", allCourse.get(1), 1,"Der schriftliche Test wurde bestanden."));
        allAchievements.add(new Achievement("Funkmast", allCourse.get(1), 10, "Das Projekt Funkmast wurde abgeschlossen."));

        //Englisch
        allAchievements.add(new Achievement("Erstes Buch", allCourse.get(2), 1, "Der Sprachtest ist bestanden."));
        allAchievements.add(new Achievement("Buecherwurm", allCourse.get(2), 20, "20 Bücher wurden gelesen und referenziert."));

        //Spieleentwicklung 1
        allAchievements.add(new Achievement("Gaming", allCourse.get(4), 5, "Alle schritte zum ersten Spiel wurden erfolgreich geschafft."));
        allAchievements.add(new Achievement("Unity Developer", allCourse.get(4), 1, "Erstes Unity-Projekt wurde abgeschlossen."));

        //Programmieren
        Achievement a3 = new Achievement("HTML Fabrik", allCourse.get(5), 1, "Der code um eine HTML-Seite zu generieren funktioniert einwandfrei.");
        Achievement a4 = new Achievement("UI Designer", allCourse.get(5), 15, "Alle Übungen zum User-Design wurden gemacht.");

        allAchievements.add(a3);
        allAchievements.add(a4);

        /*
        //Mathe
        Achievement a1 = new Achievement("Math King", allCourse.get(6));
        allAchievements.add(a1);

        //Projekt Management und Tools
        allAchievements.add(new Achievement("Scrum", allCourse.get(7)));

        //Personal Productivity
        allAchievements.add(new Achievement("Git Master", allCourse.get(8)));
        allAchievements.add(new Achievement("JUnit", allCourse.get(8)));

        //Verteilte Systeme
        allAchievements.add(new Achievement("Cloud", allCourse.get(11)));
        */


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

    public static void saveData()
    {
        String stringPath = Paths.get("").toAbsolutePath().toString();
        XStream xs = new XStream();

        try {
            xs.toXML(Master.allUser, new FileWriter(new File(stringPath+"/XMLSave/" + "User.xml")));
            xs.toXML(Master.allCourse, new FileWriter(new File(stringPath+"/XMLSave/" + "course.xml")));
            xs.toXML(Master.allAchievements, new FileWriter(new File(stringPath+"/XMLSave/" + "achivements.xml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void loadData()
    {
        String stringPath = Paths.get("").toAbsolutePath().toString();
        XStream xs = new XStream();


        try {
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(stringPath+"/XMLSave/" + "User.xml"));
            StringBuffer buff = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                buff.append(line);
            }
            Master.allUser = (ArrayList<User>) xs.fromXML(buff.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(stringPath+"/XMLSave/" + "course.xml"));
            StringBuffer buff = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                buff.append(line);
            }
            Master.allCourse = (ArrayList<Course>) xs.fromXML(buff.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(stringPath+"/XMLSave/" + "achivements.xml"));
            StringBuffer buff = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                buff.append(line);
            }
            Master.allAchievements = (ArrayList<Achievement>) xs.fromXML(buff.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }







    }


}
