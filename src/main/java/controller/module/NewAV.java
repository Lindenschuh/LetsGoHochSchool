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

    private MyUI ui;
    private VerticalLayout verticalLayout;
    private TextField name;
    private Course course;
    private TextField maxValue;
    private TextField descriptionField;
    private Button submit;
    static String errorMsg = "";


    public NewAV(User user, MyUI ui, Course course) {
        super(user);
        this.ui = ui;
        this.course = course;

        verticalLayout  = new VerticalLayout();
        name            = new TextField();
        maxValue        = new TextField();
        descriptionField= new TextField();
        submit          = new Button("Erstellen");

        name.setInputPrompt("Name");
        maxValue.setInputPrompt("Benötigter Wert");
        descriptionField.setInputPrompt("Beschreibung");
        submit.addClickListener(clickEvent -> {

        boolean valid = false;

             valid = testData(course);

            try {
                Integer.parseInt(maxValue.getValue());
            } catch (Exception e) {
                Notification success = new Notification("Nur Zahlen in \"Benötigter Wert\"", Notification.Type.ERROR_MESSAGE);
                success.setDelayMsec(1000);
                success.setPosition(Position.TOP_CENTER);
                success.show(Page.getCurrent());
                return;
            }

            if (!valid) {
                return;
            }

            Master.allAchievements.add(new Achievement(name.getValue(), this.course, Integer.parseInt(maxValue.getValue()), descriptionField.getValue()));
            Master.allAchievements.get(Master.allAchievements.size() - 1).setImage(Master.loadImage(Master.allAchievements.get(Master.allAchievements.size() - 1)));

            Master.allUser.forEach(user1 -> {
                if (user1.getCourses().contains(this.course) && !user1.isAdmin()) {
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
        errorMsg = "";
        if (name.isEmpty()) {
            valid = false;
            errorMsg += "Kein Name angeben.";
        } else {
            Master.allAchievements.forEach(achievement -> {
                if(achievement.getName().equals(name.getValue())) {
                    errorMsg += "Achievement bereits vorhanden\n";
                }
            });
        }

        if (errorMsg.contains("Achievement bereits vorhanden\n")) {
            valid = false;
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
        VerticalLayout moduleLayout = new VerticalLayout();
        HorizontalLayout footLayout = new HorizontalLayout();
        Label nameLbl = new Label("Erfolg erstellen");

        verticalLayout.setSpacing(true);
        verticalLayout.addComponent(name);
        verticalLayout.addComponent(maxValue);
        verticalLayout.addComponent(descriptionField);

        footLayout.addComponent(submit);

        moduleLayout.addComponents(nameLbl, verticalLayout, footLayout);

        layout.addComponent(moduleLayout);

        nameLbl.setStyleName("moduleHead");
        verticalLayout.setStyleName("moduleContent");
        footLayout.setStyleName("moduleFoot");
        moduleLayout.setStyleName("module");
        moduleLayout.setWidth(Integer.toString(calcWidth()) + "px");
        layout.setStyleName("page");
    }

    public int calcWidth() {
        int naviWidth = 180;
        int pagePadding = 40;
        int modulePadding = 15;

        return ui.getPage().getBrowserWindowWidth() - (naviWidth + 2 * pagePadding + 2 * modulePadding);
    }
}
