package controller;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import model.User;
import util.Master;



public class CourseInfo extends Modul
{

    private CourseInfo(User user)
    {
        super(user);
        setupLayout();
    }

    private void setupLayout()
    {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("<h2>Kursinfo<h2>", ContentMode.HTML));

        Master.allCourse.forEach(course -> {
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
            vert.addComponent(desc);

            HorizontalLayout prof = new HorizontalLayout();
            prof.addComponent(new Label("<b>Professor: <b>" + course.getAdmin()));
            vert.addComponent(prof);

            HorizontalLayout room = new HorizontalLayout();
            room.addComponent(new Label("<b>Raum: <b>" + course.getRoom()));
            vert.addComponent(room);

            HorizontalLayout count = new HorizontalLayout();
            count.addComponent(new Label("<b>Vorlesungsanzahl: <b>" + course.getDates().size()));
            vert.addComponent(count);

            HorizontalLayout begin = new HorizontalLayout();
            begin.addComponent(new Label("<b>Vorlesungsbeginn: <b>" + course.getDates().get(0)));
            vert.addComponent(begin);

            HorizontalLayout end = new HorizontalLayout();
            end.addComponent(new Label("<b>Vorlesungsende: <b>" + course.getDates().get(course.getDates().size()-1)));
            vert.addComponent(end);
        });
    }
}
