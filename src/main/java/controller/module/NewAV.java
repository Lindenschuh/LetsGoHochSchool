package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import controller.HomeController;
import model.Achievement;
import model.Course;
import model.User;
import util.Master;
import view.MyUI;

/**
 * Created by Kevin Lee on 14/07/2016.
 */
public class NewAV extends Modul {

    //String name, Course course, int maxValue, String description

    VerticalLayout verticalLayout;
    TextField name;
    Course course;
    TextField maxValue;
    RichTextArea rta;
    Button submit;


    public NewAV(User user, MyUI ui, Course course) {
        super(user);
        this.course = course;

        verticalLayout  = new VerticalLayout();
        name            = new TextField();
        maxValue        = new TextField();
        rta             = new RichTextArea();
        submit          = new Button("submit");

        name.setInputPrompt("Name");
        maxValue.setInputPrompt("Benötigter Wert");
        rta.setValue("Beschreibung");
        submit.addClickListener(clickEvent -> {

            if (testData(course)) {
                return;
            }

            try {
                Integer.parseInt(maxValue.getValue());
            } catch (Exception e) {
                Notification success = new Notification("Nur Zahlen in Benötigter Wert", Notification.Type.ERROR_MESSAGE);
                success.setDelayMsec(1000);
                success.setPosition(Position.TOP_CENTER);
                success.show(Page.getCurrent());
                return;
            }

            Master.allAchievements.add(new Achievement(name.getValue(), this.course, Integer.parseInt(maxValue.getValue()), rta.getValue()));
            Master.allAchievements.get(Master.allAchievements.size() - 1).setImage(Master.loadImage(Master.allAchievements.get(Master.allAchievements.size() - 1)));

            Master.allUser.forEach(user1 -> {
                if (user1.getCourses().contains(this.course)) {
                   user1.addAchievment(Master.allAchievements.get(Master.allAchievements.size() - 1));
                }
            });

            ui.setContentPage(new HomeController(user, ui));

            Notification success = new Notification("Achievement erstellt", Notification.Type.HUMANIZED_MESSAGE);
            success.setDelayMsec(1000);
            success.setPosition(Position.TOP_CENTER);
            success.show(Page.getCurrent());
        });

        setupLayout();
    }

    private boolean testData(Course course) {
        boolean valid = true;
        String errorMsg = "";
        if (name.isEmpty()) {
            valid = false;
            errorMsg += "Kein Kursname\n";
        }

        if (maxValue.isEmpty()) {
            valid = false;
            errorMsg += "Kein Max. Wert";
        }

        if (!valid) {
            Notification fail = new Notification(errorMsg, Notification.Type.ERROR_MESSAGE);
            fail.setDelayMsec(1000);
            fail.setPosition(Position.TOP_CENTER);
            fail.show(Page.getCurrent());
        }

        return valid;
    }

    private void setupLayout()
    {
        verticalLayout.addComponent(name);
        verticalLayout.addComponent(maxValue);
        verticalLayout.addComponent(rta);
        verticalLayout.addComponent(submit);
        layout.addComponent(verticalLayout);
    }
}
