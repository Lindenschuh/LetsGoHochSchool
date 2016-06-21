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
        layout.setWidth("100%");
    }


    private void creatView() {
        files = course.getFiles();

        Label nameLbl = new Label("Dokumente");
        VerticalLayout vert = new VerticalLayout();
        VerticalLayout moduleLayout = new VerticalLayout();
        HorizontalLayout moduleFoot = new HorizontalLayout();

        for(int i = 0; i<files.size();i++)
        {
            Button butt = new NativeButton(files.get(i).getName());
            butt.setIcon(FontAwesome.FILE);
            butt.addStyleName("borderlessButton");
            vert.addComponent(butt);
            Resource res = new FileResource(files.get(i));
            FileDownloader fd = new FileDownloader(res);
            fd.extend(butt);
        }

        if(user.isAdmin()) {
            Button bEdit = new Button("Bearbeiten");
            bEdit.addClickListener(e -> startEdit());
            moduleFoot.addComponent(bEdit);
        }

        nameLbl.setStyleName("moduleHead");
        vert.setStyleName("moduleContent");
        moduleFoot.setStyleName("moduleFoot");
        moduleLayout.setStyleName("module");

        moduleLayout.addComponent(nameLbl);
        moduleLayout.addComponent(vert);
        moduleLayout.addComponent(moduleFoot);

        layout.addComponent(moduleLayout);

    }

    private void startEdit() {
        layout.removeAllComponents();
        creatEditView();

    }

    private void creatEditView() {
        files = course.getFiles();

        Label nameLbl = new Label("Dokumente");
        VerticalLayout moduleLayout = new VerticalLayout();
        HorizontalLayout moduleFoot = new HorizontalLayout();
        VerticalLayout vert = new VerticalLayout();

        for(int i = 0; i<files.size();i++)
        {
            final int aktFile = i;
            HorizontalLayout hori = new HorizontalLayout();

            Label lFiles = new Label(FontAwesome.FILE.getHtml() + " "+files.get(i).getName(),ContentMode.HTML);
            lFiles.addStyleName("borderlessButton");
            hori.addComponent(lFiles);
            Button bDelete = new NativeButton();
            bDelete.setIcon(FontAwesome.TRASH);
            bDelete.setSizeFull();
            bDelete.setStyleName("borderlessButton");
            bDelete.addClickListener(e -> deleteFile(aktFile));
            hori.addComponent(bDelete);
            vert.addComponent(hori);
        }


        Button bDone = new Button("Fertig");
        bDone.addClickListener(e -> endEdit());



        Upload up = new Upload("Upload file", new Upload.Receiver() {
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

        nameLbl.setStyleName("moduleHead");
        vert.setStyleName("moduleContent");
        moduleFoot.setStyleName("moduleFoot");
        moduleLayout.setStyleName("module");


        vert .addComponent(up);
        moduleFoot.addComponent(bDone);

        moduleLayout.addComponent(nameLbl);
        moduleLayout.addComponent(vert);
        moduleLayout.addComponent(moduleFoot);

        layout.addComponent(moduleLayout);
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
