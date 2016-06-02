package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lars on 02.06.2016.
 */
public class Course {
    public String name;
    public ArrayList<Achievement> achievements;
    public User admin;
    public String beschreibung;
    public ArrayList<File> files;
    public ArrayList<Date> dates;
    public String room;
    //TODO: Bild ?

    //TODO: Init Arrays
    public Course(String name,User admin,String beschreibung,String date,String room){

        this.name = name;
        this.admin = admin;
        this.beschreibung = beschreibung;
        this.room = room;

        generateDate(date);
    }

    private void generateDate(String date) {

    }
    public void addFile(File file)
    {
        files.add(file);
    }

    public void  addAchievement(Achievement achiv)
    {
        achievements.add(achiv);
    }
}
