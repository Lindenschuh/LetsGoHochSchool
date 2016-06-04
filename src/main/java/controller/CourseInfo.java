package controller;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import model.Course;
import model.User;
import util.Master;


public class CourseInfo extends Modul {
    User usr;
    Course course;

    private CourseInfo(User user, Course course) {
        super(user);
        usr = user;
        this.course = course;
        setupLayout();
    }

    private void setupLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("<h2>Kursinfo<h2>", ContentMode.HTML));

        VerticalLayout vert = new VerticalLayout();
        vert.addComponent(new Label("<h1>" + course.getName() + "<h1>", ContentMode.HTML));

        //Course requirements
        VerticalLayout req = new VerticalLayout();
        req.addComponent(new Label("<u>Anforderungen:<u>", ContentMode.HTML));
        //// TODO: add requirements, when available
        //vert.addComponent(req);


        //Course description
        VerticalLayout desc = new VerticalLayout();
        desc.addComponent(new Label("<u>Beschreibung:<u>", ContentMode.HTML));
        TextArea area = new TextArea();
        area.setValue(course.getBeschreibung());
        desc.addComponent(area);

        Button bttn = new Button("Bearbeiten");
        bttn.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                desc.removeComponent(bttn);
                RichTextArea rta = new RichTextArea();
                rta.setCaption("Bearbeiten");
                rta.setValue(course.getBeschreibung());
                desc.addComponent(rta);

                Button bttn1 = new Button("Bearbeiten");
                bttn1.addClickListener(new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        area.setValue(rta.getValue());
                        desc.removeComponent(rta);
                        desc.removeComponent(bttn1);
                        desc.addComponent(bttn);
                    }
                });
            }
        });

        if (usr.isAdmin()) {
            desc.addComponent(bttn);
        }

        vert.addComponent(desc);

        //Professor
        HorizontalLayout prof = new HorizontalLayout();
        Label profe = new Label("<b>Professor: <b>" + course.getAdmin());
        prof.addComponent(profe);

        Button bttn2 = new Button("Bearbeiten");
        bttn2.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                desc.removeComponent(bttn2);
                RichTextArea rta = new RichTextArea();
                rta.setCaption("Bearbeiten");
                rta.setValue(profe.getValue());
                desc.addComponent(rta);

                Button bttn3 = new Button("Bearbeiten");
                bttn3.addClickListener(new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        profe.setValue(rta.getValue());
                        desc.removeComponent(rta);
                        desc.removeComponent(bttn3);
                        desc.addComponent(bttn2);
                    }
                });
                desc.addComponent(bttn3);
            }
        });
        if (usr.isAdmin()) {
            desc.addComponent(bttn2);
        }
        vert.addComponent(prof);

        //Room number
        HorizontalLayout room = new HorizontalLayout();
        Label rm = new Label("<b>Raum: <b>" + course.getRoom());
        room.addComponent(rm);

        Button bttn4 = new Button("Bearbeiten");
        bttn4.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                desc.removeComponent(bttn4);
                RichTextArea rta = new RichTextArea();
                rta.setCaption("Bearbeiten");
                rta.setValue(rm.getValue());
                desc.addComponent(rta);

                Button bttn5 = new Button("Bearbeiten");
                bttn5.addClickListener(new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        rm.setValue(rta.getValue());
                        desc.removeComponent(rta);
                        desc.removeComponent(bttn5);
                        desc.addComponent(bttn4);
                    }
                });
                desc.addComponent(bttn5);
            }
        });

        if (usr.isAdmin()) {
            desc.addComponent(bttn4);
        }

        vert.addComponent(room);

        //Number of lectures
        HorizontalLayout count = new HorizontalLayout();
        Label lec = new Label("<b>Vorlesungsanzahl: <b>" + course.getDates().size());
        count.addComponent(lec);

        Button bttn6 = new Button("Bearbeiten");
        bttn6.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                desc.removeComponent(bttn6);
                RichTextArea rta = new RichTextArea();
                rta.setCaption("Bearbeiten");
                rta.setValue(lec.getValue());
                desc.addComponent(rta);

                Button bttn7 = new Button("Bearbeiten");
                bttn7.addClickListener(new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        lec.setValue(rta.getValue());
                        desc.removeComponent(rta);
                        desc.removeComponent(bttn7);
                        desc.addComponent(bttn6);
                    }
                });
                desc.addComponent(bttn7);
            }
        });

        if (usr.isAdmin()) {
            desc.addComponent(bttn6);
        }
        vert.addComponent(count);

        //lecture begin
        HorizontalLayout begin = new HorizontalLayout();
        Label beg = new Label("<b>Vorlesungsbeginn: <b>" + course.getDates().get(0));
        begin.addComponent(beg);

        Button bttn8 = new Button("Bearbeiten");
        bttn8.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                desc.removeComponent(bttn8);
                RichTextArea rta = new RichTextArea();
                rta.setCaption("Bearbeiten");
                rta.setValue(beg.getValue());
                desc.addComponent(rta);

                Button bttn9 = new Button("Bearbeiten");
                bttn9.addClickListener(new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        beg.setValue(rta.getValue());
                        desc.removeComponent(rta);
                        desc.removeComponent(bttn9);
                        desc.addComponent(bttn8);
                    }
                });
                desc.addComponent(bttn9);
            }
        });

        if (usr.isAdmin()) {
            desc.addComponent(bttn8);
        }

        vert.addComponent(begin);

        //lecture end
        HorizontalLayout end = new HorizontalLayout();
        Label en = new Label("<b>Vorlesungsende: <b>" + course.getDates().get(course.getDates().size() - 1));
        end.addComponent(en);

        Button bttn10 = new Button("Bearbeiten");
        bttn10.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                desc.removeComponent(bttn10);
                RichTextArea rta = new RichTextArea();
                rta.setCaption("Bearbeiten");
                rta.setValue(en.getValue());
                desc.addComponent(rta);

                Button bttn11 = new Button("Bearbeiten");
                bttn11.addClickListener(new Button.ClickListener() {
                    public void buttonClick(Button.ClickEvent event) {
                        en.setValue(rta.getValue());
                        desc.removeComponent(rta);
                        desc.removeComponent(bttn11);
                        desc.addComponent(bttn10);
                    }
                });
                desc.addComponent(bttn11);
            }
        });

        if (usr.isAdmin()) {
            desc.addComponent(bttn10);
        }
        vert.addComponent(end);
    }
}
