package controller.module;


import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import model.User;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Profile class to show the module Profile
 *
 * Created by Aljos on 02.06.2016.
 */
public class Profile extends Modul {
    private Image img;
    private String name;
    private String email;

    private User user;

    private ArrayList<LocalDateTime> times;
    private LocalDateTime nextTime;

    private String room;

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
        this.img = new Image(null, user.getImage().getSource());
        this.name = user.getName();
        this.email = user.getEmail();
        this.user = user;

        if(this.user.isAdmin()) {
            this.times = user.getTimes();
            this.room = user.getRoom();

        }

        setupLayout();
    }

    private void setupLayout() {
        //defining the horizontal layout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //set all components in the middle center
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //config user image
        img.setDescription(user.getName());
        img.setWidth(125, Sizeable.Unit.PIXELS);
        img.setHeight(125, Sizeable.Unit.PIXELS);

        //fill times if user is admin
        update(times);


        // add user image
        horizontalLayout.addComponent(img);


        //vertical layout for user information
        VerticalLayout verticalLayout = new VerticalLayout();

        //the text should be displayed in the middle left of the vertical layout
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        //the vertical layout shouldn't be direct to the image
        verticalLayout.setMargin(true);


        //adding the vertical layout to the horizontal layout
        horizontalLayout.addComponent(verticalLayout);

        //addin the name and email to the vertical layout
        verticalLayout.addComponent(new Label(name));
        verticalLayout.addComponent(new Label(email));

        //if the user has admin rights, add the custom layout with the times String
        //from the user.
        if(this.user.isAdmin()) {
            VerticalLayout timesLayout = new VerticalLayout();
            horizontalLayout.addComponent(timesLayout);
            Label nextTimesHeader = new Label("Nächste Sprechzeit:");
            timesLayout.addComponent(nextTimesHeader);
            Label nextTimes = new Label(createTimeLabel(this.nextTime));
            timesLayout.addComponent(nextTimes);
            Label nextRoom = new Label(this.room);
            timesLayout.addComponent(nextRoom);


        }


        // adds the horizontal layout to the css layout from module, so it can be
        // displayed in the MyUI Class
        horizontalLayout.setMargin(true);
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
}
