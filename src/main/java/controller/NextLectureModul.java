package controller;


import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import model.Course;
import model.User;
import view.MyUI;


import java.io.File;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class NextLectureModul extends Modul {

    //TODO Mit css das Layout noch verfeinern.
    private static final String MODULE_NAME = "N\u00e4chste Vorlesung";
    private static final int IMAGE_SIZE = 100;
    private static final int ICON_SIZE = 20;

    private VerticalLayout moduleLayout;
    private VerticalLayout descriptionLayout;
    private HorizontalLayout dateLayout;
    private HorizontalLayout locationLayout;
    private HorizontalLayout contentLayout;

    private MyUI ui;
    private Course nextCourse;
    private LocalDateTime nextLecture;

    private Label nextName;
    private Label nextTime;
    private Label nextRoom;

    private Image freeImg;
    private Image courseImg;
    private Image defaultImg;
    private Image dateIcon;
    private Image locationIcon;


    public NextLectureModul(User user, MyUI ui) {
        super(user);
        this.ui = ui;

        moduleLayout = new VerticalLayout();
        contentLayout = new HorizontalLayout();
        descriptionLayout = new VerticalLayout();
        dateLayout = new HorizontalLayout();
        locationLayout = new HorizontalLayout();

        nextName = new Label("");
        nextTime = new Label("");
        nextRoom = new Label("");

        createLayout();

    }

    private void createLayout() {

        Label moduleName = new Label(MODULE_NAME);

        moduleName.setStyleName("h2");
        nextName.setStyleName("h3");

        createFreeImage();
        createDefaultImage();
        createDateIcon();
        createLocationIcon();

        update();

        dateLayout.addComponent(dateIcon);
        dateLayout.addComponent(nextTime);
        locationLayout.addComponent(locationIcon);
        locationLayout.addComponent(nextRoom);

        descriptionLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        descriptionLayout.addComponent(nextName);
        descriptionLayout.addComponent(dateLayout);
        descriptionLayout.addComponent(locationLayout);

        //contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        contentLayout.addComponent(courseImg);
        contentLayout.addComponent(descriptionLayout);
        contentLayout.setSpacing(true);

        moduleLayout.addComponent(moduleName);
        moduleLayout.addComponent(contentLayout);

        moduleLayout.setMargin(true);
        layout.addComponent(moduleLayout);
    }


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
                File f = new File(Paths.get("").toAbsolutePath().toString()
                        + "/Resource/Images/Course/" + nextCourse.toString() +".png");

                if (f.exists()) {
                    FileResource resource = new FileResource(f);
                    courseImg = new Image(null, resource);
                    courseImg.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
                    courseImg.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
                    moduleLayout.addComponent(courseImg);
                } else {
                    courseImg = defaultImg;
                }
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

    private void createDefaultImage() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Course/default.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            defaultImg = new Image(null, resource);
            defaultImg.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
            defaultImg.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
            defaultImg.addClickListener(event -> {
                //update();
            });
        }
    }

    private void createFreeImage() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Course/Freizeit.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            freeImg = new Image(null, resource);
            freeImg.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
            freeImg.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        }
    }

    private void createDateIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/date.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            dateIcon = new Image(null, resource);
            dateIcon.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            dateIcon.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            dateIcon.setDescription("Datum");
        }
    }
    
    private void createLocationIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/location.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            locationIcon = new Image(null, resource);
            locationIcon.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            locationIcon.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            locationIcon.setDescription("Raum");
        }
    }

    private String createTimeLabel(LocalDateTime date) {


        String year = Integer.toString(date.getYear());
        String month = Integer.toString(date.getMonthValue());
        if(date.getMonthValue() < 10) {
            month = "0" + month;
        }
        String day = Integer.toString(date.getDayOfMonth());
        if(date.getDayOfMonth() < 10) {
            day = "0" + day;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        String hour = Integer.toString(date.getHour());
        String min = Integer.toString(date.getMinute());

        StringBuilder dateBuilder = new StringBuilder();

        switch(dayOfWeek.getValue()) {
            case 0:
                dateBuilder.append("Mo ");
                break;
            case 1:
                dateBuilder.append("Di ");
                break;
            case 2:
                dateBuilder.append("Mi ");
                break;
            case 3:
                dateBuilder.append("Do ");
                break;
            case 4:
                dateBuilder.append("Fr ");
                break;
            case 5:
                dateBuilder.append("Sa ");
                break;
            case 6:
                dateBuilder.append("So ");
        }

        dateBuilder.append(day);
        dateBuilder.append(".");
        dateBuilder.append(month);
        dateBuilder.append(", ");

        dateBuilder.append(hour);
        dateBuilder.append(":");
        dateBuilder.append(min);
        dateBuilder.append(" ");

        return dateBuilder.toString();
    }
}


