package controller.module;


import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import model.User;

import java.io.File;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Profile class to show the module Profile
 *
 * Created by Aljos on 02.06.2016.
 */
public class Profile extends Modul {

    private static final int ICON_SIZE = 20;

    private LocalDateTime nextTime;

    private Image dateIcon;
    private Image roomIcon;

    /**
     * Constructor for the Profile page. Calls a User and puts information
     *like profile picture, name and e-mail address on the screen.
     * If the user is an administrator, the times to meet hin will be
     * displayed in a small HTML format.
     *
     * @param user the User calling this Window.
     */


    public Profile(User user) {
        super(user);

        setupLayout();
    }

    private void setupLayout() {
        //defining the horizontal layout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //set all components in the middle center
        //horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //user image
        Image userImg = new Image(null, user.getImage().getSource());
        userImg.setDescription(user.getName());
        userImg.setWidth(125, Sizeable.Unit.PIXELS);
        userImg.setHeight(125, Sizeable.Unit.PIXELS);


        //vertical layout for user information
        VerticalLayout verticalLayout = new VerticalLayout();

        //the text should be displayed in the middle left of the vertical layout
        //verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        //the vertical layout shouldn't be direct to the image
        verticalLayout.setMargin(true);

        //adding the name and email to the vertical layout
        verticalLayout.addComponents(new Label(user.getName()),new Label(user.getEmail()));

        //adding the vertical layout to the horizontal layout
        horizontalLayout.addComponents(userImg, verticalLayout);


        //if the user has admin rights, add the custom layout with the times String
        //from the user.
        if(this.user.isAdmin()) {

            update(user.getTimes());

            loadDateIcon();
            loadRoomIcon();
            Label nextTimesHeader = new Label("Nächste Sprechzeit:");
            Label dateLbl = new Label(createTimeLabel(this.nextTime));
            Label roomLbl = new Label(user.getRoom());
            VerticalLayout timesLayout = new VerticalLayout();
            VerticalLayout descriptionLayout = new VerticalLayout();
            HorizontalLayout dateLayout = new HorizontalLayout();
            HorizontalLayout roomLayout = new HorizontalLayout();

            timesLayout.setStyleName("talkTime");
            nextTimesHeader.setStyleName("talkTimeHead");
            descriptionLayout.setStyleName("moduleContent");
            dateLbl.setStyleName("descriptionDetail");
            roomLbl.setStyleName("descriptionDetail");

            dateLayout.addComponents(dateIcon, dateLbl);
            roomLayout.addComponents(roomIcon, roomLbl);
            descriptionLayout.addComponents(dateLayout, roomLayout);
            timesLayout.addComponents(nextTimesHeader, descriptionLayout);
            horizontalLayout.addComponent(timesLayout);

        }


        // adds the horizontal layout to the css layout from module, so it can be
        // displayed in the MyUI Class
        layout.setStyleName("moduleSimple");
        layout.addComponent(horizontalLayout);

    }


    private void update(ArrayList<LocalDateTime> times) {
        times.forEach(time -> {
            if(!time.isBefore(LocalDateTime.now())) {
                if(this.nextTime == null) {
                    nextTime = time;
                } else if(time.isBefore(nextTime)) {
                    nextTime = time;
                }
            }
        });
    }

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

        StringBuilder dateBuilder = new StringBuilder();

        switch(dayOfWeek.getValue()) {
            case 0:
                dateBuilder.append("So ");
                break;
            case 1:
                dateBuilder.append("Mo ");
                break;
            case 2:
                dateBuilder.append("Di ");
                break;
            case 3:
                dateBuilder.append("Mi ");
                break;
            case 4:
                dateBuilder.append("Do ");
                break;
            case 5:
                dateBuilder.append("Fr ");
                break;
            case 6:
                dateBuilder.append("Sa ");
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

    private void loadRoomIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/locationLight.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            roomIcon = new Image(null, resource);
            roomIcon.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            roomIcon.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            roomIcon.setDescription("Raum");
        }
    }

}
