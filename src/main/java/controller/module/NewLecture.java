package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import controller.ProfileController;
import model.Course;
import model.User;
import util.Master;
import view.MyUI;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin Lee on 11/07/2016.
 */


public class NewLecture extends Modul {

    //Course(String name, User admin, String description, String date, int lessons, String room)

    VerticalLayout vertilay;
    TextField courseName;
    RichTextArea rta;
    DateField date;
    TextField hours;
    TextField room;
    Button submit;

    public NewLecture(User user, MyUI ui) {
        super(user);
        vertilay = new VerticalLayout();

        courseName = new TextField();
        courseName.setInputPrompt("Name");

        rta = new RichTextArea();
        rta.setCaption("Kurs Beschreibung");

        date = new DateField();
        date.setValue(new Date());
        date.setResolution(Resolution.MINUTE);
        date.setDateFormat("dd-MM-yyyy hh:mm");

        hours = new TextField();
        hours.setInputPrompt("Anzahl der Vorlesungen");

        room = new TextField();
        room.setInputPrompt("Raum");

        submit = new Button("Submit");
        submit.addClickListener(clickEvent -> {

            String s;
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");

            s = formatter.format(date.getValue());

            try {
                Integer.parseInt(hours.getValue());
            } catch ( Exception e) {
                Notification success = new Notification("Nur Zahlen in Anzahl der Vorlesungen", Notification.Type.ERROR_MESSAGE);
                success.setDelayMsec(1000);
                success.setPosition(Position.TOP_CENTER);
                success.show(Page.getCurrent());
                return;
            }



            Course newCourse = new Course(courseName.getValue(), user, rta.getValue(), s, Integer.parseInt(hours.getValue()), room.getValue());

            newCourse.setImage(Master.loadImage(newCourse));

            Master.allCourse.add(newCourse);
            //user.addCourse(newCourse);

            Notification success = new Notification("Course erfolgreich erstellt", Notification.Type.HUMANIZED_MESSAGE);
            success.setDelayMsec(1000);
            success.setPosition(Position.TOP_CENTER);
            success.show(Page.getCurrent());

            ui.setContentPage(new ProfileController(user, ui));

        });

        setupLayout();
    }

    private void setupLayout()
    {
        Label moduleName = new Label("Kurs erstellen");
        HorizontalLayout foot = new HorizontalLayout();
        VerticalLayout moduleLayout = new VerticalLayout();

        vertilay.addComponent(courseName);
        vertilay.addComponent(rta);
        vertilay.addComponent(date);
        vertilay.addComponent(hours);
        vertilay.addComponent(room);

        foot.addComponent(submit);

        moduleLayout.addComponents(moduleName, vertilay, foot);
        layout.addComponent(moduleLayout);

        //Styling
        vertilay.setStyleName("moduleContent");
        vertilay.setSpacing(true);
        foot.setStyleName("moduleFoot");
        moduleName.setStyleName("moduleHead");
        moduleLayout.setStyleName("module");
        layout.setStyleName("page");
    }
}
