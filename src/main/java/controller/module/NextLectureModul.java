package controller.module;


import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import model.Course;
import model.User;


import java.io.File;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * The next lecture module displays the next lecture of a user.
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class NextLectureModul extends Modul {

    /**
     * The name of the module.
     */
    private static final String MODULE_NAME = "N\u00e4chste Vorlesung";

    /**
     * The width and height of the image in pixels.
     */
    private static final int IMAGE_SIZE = 100;

    /**
     * The width and height of the icon in pixels.
     */
    private static final int ICON_SIZE = 20;

    /**
     * Layouts.
     */
    private VerticalLayout moduleLayout;
    private VerticalLayout descriptionLayout;
    private HorizontalLayout dateLayout;
    private HorizontalLayout locationLayout;
    private HorizontalLayout contentLayout;

    /**
     * The next course of the user.
     */
    private Course nextCourse;

    /**
     * The date of the next course.
     */
    private LocalDateTime nextLecture;

    /**
     * Label with the name of the next course.
     */
    private Label nextName;

    /**
     * Label with the date of the next course.
     */
    private Label nextTime;

    /**
     * Label with the room of the next course.
     */
    private Label nextRoom;

    private Image freeImg;
    private Image courseImg;
    private Image defaultImg;
    private Image dateIcon;
    private Image locationIcon;

    /**
     * Create a new next lecture module.
     * @param user User
     */
    public NextLectureModul(User user) {
        super(user);

        createLayout();
    }

    /**
     * Setup the layout.
     */
    private void createLayout() {

        //Create the GUI components.
        nextName = new Label("");
        nextTime = new Label("");
        nextRoom = new Label("");
        Label moduleName = new Label(MODULE_NAME);

        moduleLayout = new VerticalLayout();
        contentLayout = new HorizontalLayout();
        descriptionLayout = new VerticalLayout();
        dateLayout = new HorizontalLayout();
        locationLayout = new HorizontalLayout();

        //Bilder laden
        loadFreeImage();
        loadDefaultImage();
        loadDateIcon();
        loadLocationIcon();

        //Elemente füllen.
        update();

        //Styling
        moduleName.setStyleName("moduleHead");
        contentLayout.setStyleName("moduleContent");
        descriptionLayout.setStyleName("descriptionLayout");
        nextName.setStyleName("descriptionLecture");
        nextRoom.setStyleName("descriptionDetail");
        nextTime.setStyleName("descriptionDetail");
        moduleLayout.setStyleName("module");

        //Zusammen setzen.
        dateLayout.addComponent(dateIcon);
        dateLayout.addComponent(nextTime);
        locationLayout.addComponent(locationIcon);
        locationLayout.addComponent(nextRoom);

        descriptionLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        descriptionLayout.addComponent(nextName);
        descriptionLayout.addComponent(dateLayout);
        descriptionLayout.addComponent(locationLayout);

        contentLayout.addComponent(courseImg);
        contentLayout.addComponent(descriptionLayout);

        moduleLayout.addComponent(moduleName);
        moduleLayout.addComponent(contentLayout);

        layout.addComponent(moduleLayout);
    }


    /**
     * Update the module.
     */
    private void update() {

        if((user.getCourses().size() > 0)) {
            user.getCourses().forEach(course -> {
                course.getDates().forEach(date -> {
                    if(!date.isBefore(LocalDateTime.now())) {
                        if(nextLecture == null) {
                            nextLecture = date;
                            nextCourse = course;
                        } else if (date.isBefore(nextLecture)){
                            nextLecture = date;
                            nextCourse = course;
                        }
                    }
                });
            } );

            if (nextCourse != null) {

                courseImg = new Image(null, nextCourse.getImage().getSource());
                courseImg.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
                courseImg.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
                moduleLayout.addComponent(courseImg);

                nextName.setValue(nextCourse.getName());
                nextRoom.setValue(nextCourse.getRoom());
                nextTime.setValue(createTimeLabel(nextLecture));
            }
        } else {
            courseImg = freeImg;
            nextName.setValue("Vorlesungsfreie Zeit");
            nextTime.setValue("Genie\u00dfen Sie die Ferien!");
            nextRoom.setValue("");
        }
    }

    private void loadDefaultImage() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Course/default.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            defaultImg = new Image(null, resource);
            defaultImg.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
            defaultImg.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        }
    }

    private void loadFreeImage() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Course/Freizeit.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            freeImg = new Image(null, resource);
            freeImg.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
            freeImg.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
            freeImg.setDescription("Ferien!");
        }
    }

    private void loadDateIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/dateLight.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            dateIcon = new Image(null, resource);
            dateIcon.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            dateIcon.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            dateIcon.setDescription("Datum");
        }
    }
    
    private void loadLocationIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/locationLight.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            locationIcon = new Image(null, resource);
            locationIcon.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            locationIcon.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            locationIcon.setDescription("Raum");
        }
    }

    /**
     * Format the date.
     * @param date Date to format.
     * @return Formatted date as string.
     */
    private String createTimeLabel(LocalDateTime date) {

        String month = Integer.toString(date.getMonthValue());
        if (date.getMonthValue() < 10) {
            month = "0" + month;
        }

        String day = Integer.toString(date.getDayOfMonth());
        if (date.getDayOfMonth() < 10) {
            day = "0" + day;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        String hour = Integer.toString(date.getHour());
        if (date.getHour() < 10) {
            hour = "0" + hour;
        }

        String min = Integer.toString(date.getMinute());
        if (date.getMinute() < 10) {
            min = "0" + min;
        }

        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.GERMANY) +
                " " + day + "." + month + ", " + hour + ":" + min;
    }
}


