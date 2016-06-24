package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
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
        area.setValue(/*"<h2>" + course.getName() + "</h2>" +
                ////TODO: addd requirements, when available
                "<br>*/"<b>Professor: </b>" + course.getAdmin().getName() +
                "<br><b>Raum: </b>" + course.getRoom() +
                "<br><b>Vorlesungsanzahl: </b>" + course.getDates().size() +
                "<br><b>Vorlesungsbeginn: </b>" + course.getDates().get(0) +
                "<br><b>Vorlesungsende: </b>" + course.getDates().get(course.getDates().size() - 1) +
                "<br><br><u>Beschreibung</u>" +
                "<br><br>" + course.getDescription()
        );
    }


    private void setupLayout() {

        updateText();

        Label moduleName = new Label("Kursinfo");
        HorizontalLayout footLayout = new HorizontalLayout();
        VerticalLayout vertilayout = new VerticalLayout();

        layout.setStyleName("module");
        moduleName.setStyleName("moduleHead");
        vertilayout.setStyleName("moduleContent");
        footLayout.setStyleName("moduleFoot");
        layout.removeAllComponents();
        layout.addComponents(moduleName, vertilayout);

        vertilayout.addComponent(area);

        if (usr.isAdmin()) {
            Button bttn = new Button("Bearbeiten");
            bttn.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    footLayout.removeComponent(bttn);
                    RichTextArea rta = new RichTextArea();
                    rta.setCaption("Bearbeiten");
                    rta.setValue(course.getDescription());
                    vertilayout.addComponent(rta);

                    Button bttn1 = new Button("Submit");
                    bttn1.addClickListener(new Button.ClickListener() {
                        public void buttonClick(Button.ClickEvent event) {
                            course.setDescription(rta.getValue());
                            vertilayout.removeComponent(rta);
                            footLayout.removeComponent(bttn1);
                            footLayout.addComponent(bttn);
                            updateText();
                        }
                    });
                    footLayout.addComponent(bttn1);
                }
            });
            footLayout.addComponent(bttn);
            layout.addComponent(footLayout);
        }

        if(!usr.getCourses().contains(course))
        {
            Button addCourse = new Button("Einschreiben");
            addCourse.addClickListener(clickEvent -> {
                usr.addCourse(course);

                Notification notify = new Notification("Erfolgreich eingeschrieben", Notification.Type.ASSISTIVE_NOTIFICATION);
                notify.setDelayMsec(1000);
                notify.setPosition(Position.TOP_RIGHT);
                notify.show(Page.getCurrent());

                vertilayout.removeAllComponents();
                setupLayout();

            });
            footLayout.addComponent(addCourse);
            layout.addComponent(footLayout);
        }
    }
}
