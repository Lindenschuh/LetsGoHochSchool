package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import controller.HomeController;
import model.Course;
import model.User;
import view.MyUI;


public class CourseInfo extends Modul {
    User usr;
    Course course;
    Label area;
    MyUI ui;

    /**
     * konstruktor
     * @param user der user der die seite aufruft
     * @param course der kurs für den die Info angezeigt werden soll
     * @param ui die UI in der die KursInfo angezeigt werden soll
     */
    public CourseInfo(User user, Course course, MyUI ui) {
        super(user);
        usr = user;
        this.ui = ui;
        area = new Label("", ContentMode.HTML);
        this.course = course;
        setupLayout();
    }

    /**
     * updated then text mit den momentanen kurs daten
     */
    private void updateText()
    {
        area.setValue(/*"<h2>" + course.getName() + "</h2>" +
                ////TODO: addd requirements, when available
                "<br>*/"<b>Professor: </b>" + course.getAdmin().getName() +
                "<br><b>Raum: </b>" + course.getRoom() +
                "<br><b>Vorlesungsanzahl: </b>" + course.getDates().size() +
                "<br><b>Vorlesungsbeginn: </b>" + course.getDates().get(0).toString().substring(11) + " Uhr" +
                "<br><b>Vorlesungsdauer: </b>" + course.getDuration() + " min" +
                "<br><br><b><u>Beschreibung</u></b>" +
                "<br><br>" + course.getDescription()
        );
    }


    /**
     * erstellt layout und zeigt es an
     */
    private void setupLayout() {

        updateText();

        Label moduleName = new Label("Kursinfo");
        HorizontalLayout footLayout = new HorizontalLayout();
        VerticalLayout vertilayout = new VerticalLayout();

        layout.setWidth("100%");
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
                    rta.setValue("<font color=\"#e0e0e0\">" +
                            course.getDescription() +
                            "</font>");

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

        if (!user.isAdmin()) {
            Button bttn = new Button("Ausschreiben");
            bttn.addClickListener(clickEvent -> {
                user.removeCourse(course);
                ui.setContentPage(new HomeController(user, ui));
            });

            footLayout.addComponent(bttn);
            layout.addComponent(footLayout);
        }
    }
}
