package model;

import com.vaadin.ui.Image;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lars on 02.06.2016.
 */
public class Course implements DataObject {
    private String name;
    private ArrayList<Achievement> achievements;
    private User admin;
    private String description;
    private ArrayList<File> files;
    private File dir;
    private ArrayList<LocalDateTime> dates;
    private String room;
    private Image img;


    public Course(String name, User admin, String description, String date, int lessons, String room){
        String stringPath = Paths.get("").toAbsolutePath().toString();
        this.name = name;
        this.admin = admin;
        this.description = description;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setImage(Image i) {
        this.img = i;
    }

    @Override
    public Image getImage() {
        return img;
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    public User getAdmin() {
        return admin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String bes) { this.description = bes; }

    public ArrayList<File> getFiles() {
        loadFiles();
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    public ArrayList<LocalDateTime> getDates() {
        return dates;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return name;
    }

}
