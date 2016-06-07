package controller.module;

import com.vaadin.server.*;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.Course;
import model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Lars Assmus on 03.06.2016.
 */

//TODO: Updaten wenn in laufzeit files verändert werden
public class FileModul extends Modul {

    private Course course;
    private User user;
    private ArrayList<File> files;

    public FileModul(User user, Course course) {
        super(user);
        this.course = course;
        this.user = user;


        creatView();
    }


    private void creatView() {
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

        if(user.isAdmin()) {
            Button bEdit = new Button("Bearbeiten");
            bEdit.addClickListener(e -> startEdit());
            vert.addComponent(bEdit);
        }
        layout.addComponent(vert);



    }

    private void startEdit() {
        layout.removeAllComponents();
        creatEditView();

    }

    private void creatEditView() {
        files = course.getFiles();
        VerticalLayout vert = new VerticalLayout();
        vert.addComponent(new Label("<h2>Dokumente<h2>", ContentMode.HTML));

        for(int i = 0; i<files.size();i++)
        {
            final int aktFile = i;
            HorizontalLayout hori = new HorizontalLayout();

            Label lFiles = new Label(FontAwesome.FILE.getHtml() + " "+files.get(i).getName(),ContentMode.HTML);
            lFiles.addStyleName(ValoTheme.BUTTON_BORDERLESS);
            hori.addComponent(lFiles);
            Button bDelete = new Button();
            bDelete.setIcon(FontAwesome.TRASH);
            bDelete.setSizeFull();
            bDelete.setStyleName(ValoTheme.BUTTON_BORDERLESS);
            bDelete.addClickListener(e -> deleteFile(aktFile));
            hori.addComponent(bDelete);
            vert.addComponent(hori);
        }


        Button bDone = new Button("Fertig");
        bDone.addClickListener(e -> endEdit());



        Upload up = new Upload("Upload File", new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String s1) {
                FileOutputStream fos = null; // Stream to write to
                try {
                    String stringPath = Paths.get("").toAbsolutePath().toString();
                    // Open the file for writing.
                    File file = new File(stringPath+"/CourseFolder/" + course.getName() + "/"+ filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file<br/>",
                            e.getMessage(),
                            Notification.Type.ERROR_MESSAGE)
                            .show(Page.getCurrent());
                    return null;
                }
                return fos;
            }
        });

        up.addFinishedListener(e -> updateAfter());
        vert .addComponent(up);
        vert.addComponent(bDone);



        layout.addComponent(vert);
    }

    private void updateAfter() {
        layout.removeAllComponents();
        creatEditView();
    }


    private void endEdit() {
        layout.removeAllComponents();
        creatView();
    }

    private void deleteFile(int aktFile) {
        layout.removeAllComponents();
        File dead = files.get(aktFile);
        dead.delete();
        files.remove(aktFile);
        course.setFiles(files);
        creatEditView();
    }


}
