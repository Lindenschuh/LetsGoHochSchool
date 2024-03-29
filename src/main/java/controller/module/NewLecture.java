package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import controller.CourseController;
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

    static String errormsg = "";

    /**
     * constructor der alle elemente der GUI initiallisiert
     * @param user user der die seite aufruft
     * @param ui ui in welcher die seite aufgerufen wird
     */
    public NewLecture(User user, MyUI ui) {
        super(user);

        vertilay = new VerticalLayout();

        courseName = new TextField();
        courseName.setInputPrompt("Kursname");

        rta = new RichTextArea();
        rta.setCaption("Kurs Beschreibung");
        rta.setValue("<font color=\"#e0e0e0\"> Kursbeschreibung einfügen. </font>");

        date = new DateField();
        date.setValue(new Date());
        date.setResolution(Resolution.MINUTE);
        date.setDateFormat("dd-MM-yyyy hh:mm");

        hours = new TextField();
        hours.setInputPrompt("Anzahl Vorlesungen");

        room = new TextField();
        room.setInputPrompt("Raum");

        submit = new Button("Submit");
        submit.addClickListener(clickEvent -> {

            String s;
            boolean valid = false;
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");

            valid = testValues();
            s = formatter.format(date.getValue());

            try {
                Integer.parseInt(hours.getValue());
            } catch ( Exception e) {
                Notification success = new Notification("Bitte nur Zahlen in \"Anzahl Vorlesungen\"", Notification.Type.ERROR_MESSAGE);
                success.setDelayMsec(1000);
                success.setPosition(Position.TOP_CENTER);
                success.show(Page.getCurrent());
                return;
            }

            if(!valid) {
                return;
            }


            Course newCourse = new Course(courseName.getValue(), user, rta.getValue(), s, Integer.parseInt(hours.getValue()), room.getValue());

            newCourse.setImage(Master.loadImage(newCourse));

            Master.allCourse.add(newCourse);

            Notification success = new Notification("Kurse erfolgreich erstellt", Notification.Type.HUMANIZED_MESSAGE);
            success.setDelayMsec(1000);
            success.setPosition(Position.TOP_CENTER);
            success.show(Page.getCurrent());

            ui.setContentPage(new CourseController(user, newCourse));

        });

        setupLayout();
    }

    /**
     * testet ob die eingaben richtig sind oder falsch
     * @return true o. false je nach dem ob die values valid sind
     */
    private boolean testValues() {
        errormsg = "";
        boolean valid = true;

        Master.allCourse.forEach(course -> {
            if (course.getName().equals(courseName.getValue())) {
                errormsg += "Kurs bereits vorhanden\n";
            }
        });

        if (errormsg.contains("Kurs bereits vorhanden")) {
            valid = false;
        }

        if (courseName.isEmpty()) {
            errormsg += "Kein Kursname angegeben \n";
            valid = false;
        }
        if (hours.isEmpty()) {
            errormsg += "Keine Vorlesungsanzahl \n";
            valid = false;
        }
        if (date.isEmpty()) {
            errormsg += "Keine Datum angegeben \n";
            valid = false;
        }
        if (room.isEmpty()) {
            errormsg += "Keinen Raum angegeben";
            valid = false;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            formatter.format(date.getValue());
        } catch(Exception e) {
            errormsg += "Ung\u00fcltiges Datum.";
            valid = false;
        }

        if (!valid) {
            Notification fail = new Notification(errormsg, Notification.Type.ERROR_MESSAGE);
            fail.setDelayMsec(1000);
            fail.setPosition(Position.TOP_CENTER);
            fail.show(Page.getCurrent());
        }
        return valid;

    }

    /**
     * erstellt die GUI und zeigt diese an
     */
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
