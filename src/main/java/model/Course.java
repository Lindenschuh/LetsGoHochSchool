package model;

import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Lars on 02.06.2016.
 */
public class Course {
    private String name;
    private ArrayList<Achievement> achievements;
    private User admin;
    private String beschreibung;
    private ArrayList<File> files;
    private File dir;
    private ArrayList<LocalDateTime> dates;
    private String room;
    //TODO: Bild ?


    public Course(String name,User admin,String beschreibung,String date, int lessons,String room){
        String stringPath = Paths.get("").toAbsolutePath().toString();
        this.name = name;
        this.admin = admin;
        this.beschreibung = beschreibung;
        this.room = room;
        this.dir = new File(stringPath+"/CourseFolder/"+name);
        achievements = new ArrayList<Achievement>();
        dates = new ArrayList<LocalDateTime>();

        generateDate(date,lessons);
        creatDir();
        loadFiles();
    }

    private void loadFiles() {
        if(dir.listFiles()!= null)
            files = new ArrayList<File>(Arrays.asList(dir.listFiles()));
        else
            files = new ArrayList<File>();
    }

    private void creatDir() {
        if(!dir.exists())
            dir.mkdir();

    }

    private void generateDate(String date,int lessons) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime firstLesson = LocalDateTime.parse(date,formatter);


        for(int i = 0; i<lessons;i++)
        {
            dates.add(firstLesson.plusDays(i*7));
        }


    }
    public void addFile(File file)
    {
        files.add(file);
    }

    public void  addAchievement(Achievement achiv)
    {
        achievements.add(achiv);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public User getAdmin() {
        return admin;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public ArrayList<LocalDateTime> getDates() {
        return dates;
    }

    public String getRoom() {
        return room;
    }
}
