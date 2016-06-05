package controller;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import model.Course;
import model.User;


public class CourseInfo extends Modul {
    User usr;
    Course course;
    Label area;

    public CourseInfo(User user, Course course) {
        super(user);
        usr = user;
        area = new Label("", ContentMode.HTML);
        this.course = course;
        setupLayout();
    }

    private void updateText()
    {
        area.setValue("<h2><u>Kursinfo</u><h2>" +
                "<br><h2>" + course.getName() + "</h2>" +
                ////TODO: addd requirements, when available
                "<br><b>Professor: <b>" + course.getAdmin().getName() +
                "<br><b>Raum: </b>" + course.getRoom() +
                "<br><b>Vorlesungsanzahl: </b>" + course.getDates().size() +
                "<br><b>Vorlesungsbeginn: </b>" + course.getDates().get(0) +
                "<br><b>Vorlesungsende: </b>" + course.getDates().get(course.getDates().size() - 1) +
                "<br><u>Beschreibung</u>" +
                "<br>" + course.getBeschreibung()
        );
    }


    private void setupLayout() {

        updateText();
        VerticalLayout vertilayout = new VerticalLayout();
        vertilayout.addComponent(area);

        Button bttn = new Button("Bearbeiten");
        bttn.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                vertilayout.removeComponent(bttn);
                RichTextArea rta = new RichTextArea();
                rta.setCaption("Bearbeiten");
                rta.setValue(course.getBeschreibung());
                vertilayout.addComponent(rta);

                Button bttn1 = new Button("Submit");
                bttn1.addClickListener(new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        course.setBeschreibung(rta.getValue());
                        vertilayout.removeComponent(rta);
                        vertilayout.removeComponent(bttn1);
                        vertilayout.addComponent(bttn);
                        updateText();
                    }
                });
                vertilayout.addComponent(bttn1);
            }
        });

        if (usr.isAdmin()) {
            vertilayout.addComponent(bttn);
        }


        layout.addComponent(vertilayout);
    }
}
