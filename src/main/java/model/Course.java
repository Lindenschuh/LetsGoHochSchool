package model;


import com.vaadin.ui.Image;
import org.zoodb.api.impl.ZooPC;
import util.Master;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lars on 02.06.2016.
 */

public class Course extends ZooPC implements DataObject {
    private String name;
    private ArrayList<Achievement> achievements;
    private User admin;
    private String description;
    private ArrayList<File> files;
    private File dir;
    private ArrayList<LocalDateTime> dates;
    private String room;
    private Image img;
    private int duration; //in minuten
    private String code;
    private  ArrayList<User> userList;


    private Course()
    {

    }


    public Course(String name, User admin, String description, String date, int lessons, String room){
        String stringPath = Paths.get("").toAbsolutePath().toString();
        userList = new ArrayList<>();
        this.name = name;
        this.admin = admin;
        this.admin.addAdminToCourse(this);
        this.description = description;
        this.room = room;
        this.dir = new File(stringPath+"/CourseFolder/"+name);
        achievements = new ArrayList<Achievement>();
        dates = new ArrayList<LocalDateTime>();
        this.duration = 90;
        clearCode();
        generateDate(date,lessons);
        creatDir();
        loadFiles();

    }
    private void serilzeMe()
    {

        zooActivateWrite();
    }

    private void deSerilzeMe()
    {

        zooActivateRead();

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

    public void clearCode()
    {
        this.code = null;
        serilzeMe();
    }

    public void setCode(String code) {
        this.code = code;
        serilzeMe();
    }

    public String getCode()
    {
        deSerilzeMe();
        return code;
    }

    public void addFile(File file)
    {
        files.add(file);
        serilzeMe();
    }


    public void  addAchievement(Achievement achiv)
    {
        achievements.add(achiv);
        serilzeMe();
    }

    @Override
    public String getName() {
        deSerilzeMe();
        return name;
    }

    @Override
    public void setImage(Image i) {
        this.img = i;
        serilzeMe();
    }

    @Override
    public Image getImage() {
        deSerilzeMe();
        return img;
    }

    public ArrayList<Achievement> getAchievements() {
        deSerilzeMe();
        return achievements;
    }

    public User getAdmin() {
        deSerilzeMe();
        return admin;
    }
    public int getDuration() {
        deSerilzeMe();
        return duration;
    }
    public String getDescription() {
        deSerilzeMe();
        return description;
    }

    public void setDescription(String bes) { this.description = bes;
    serilzeMe();
    }

    public ArrayList<File> getFiles() {

        loadFiles();
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
        serilzeMe();
    }

    public ArrayList<LocalDateTime> getDates() {
        deSerilzeMe();
        return dates;
    }

    public String getRoom() {
        deSerilzeMe();
        return room;
    }

    public ArrayList<User> getUserList()
    {
        deSerilzeMe();
        return userList;
    }

    public void setUserList(User user)
    {
        userList.add(user);
        achievements.forEach(a -> {
                a.addUser(user);
        });
        serilzeMe();
    }

    @Override
    public String toString() {
        deSerilzeMe();
        return name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(((DataObject)obj).getName().equals(this.getName()))
            return true;
        else
            return false;
    }
}
