package controller;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import model.Course;
import model.User;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Lars Assmus on 03.06.2016.
 */

//TODO: Updaten wenn in laufzeit files verändert werden
public class FileModul extends Modul {

    private Course course;
    private ArrayList<File> files;

    public FileModul(User user, Course course) {
        super(user);
        this.course = course;


        CreatView();
    }

    private void CreatView() {
        files = course.getFiles();
        VerticalLayout vert = new VerticalLayout();
        vert.addComponent(new Label("<h2>Dokumente<h2>", ContentMode.HTML));

        for(int i = 0; i<files.size();i++)
        {
            Button butt = new Button(files.get(i).getName());
            butt.setIcon(FontAwesome.FILE);
            butt.addStyleName(ValoTheme.BUTTON_BORDERLESS);
            vert.addComponent(butt);
            Resource res = new FileResource(files.get(i));
            FileDownloader fd = new FileDownloader(res);
            fd.extend(butt);
        }

        Button bEdit = new Button();
        bEdit.addClickListener(e -> startEdit());
        vert.addComponent(bEdit);
        layout.addComponent(vert);



    }

    private void startEdit() {
    }


}
