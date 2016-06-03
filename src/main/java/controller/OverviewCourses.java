package controller;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import model.User;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class OverviewCourses extends Modul {

    private final static int SPACING_SIZE = 30;
    private final static int COMPONENT_SIZE = 100;
    private final static String MODUL_NAME = "Kurse";

    private int maxColumns;
    private boolean showAll;

    private User user;
    private Label nameLabel;
    private Image moreCoursesImg;
    private VerticalLayout moduleLayout;
    private GridLayout contentLayout;

    private ArrayList<Image> imagesCourses;
    private ArrayList<CustomLayout> labelsCourses;


    /**
     * The course module shows all the courses from a user.
     * @param user The {@link User}.
     * @param width Max width, for this view.
     */
    public OverviewCourses(User user, int width) {
        super(user);
        this.user = user;

        showAll = false;
        layout = new CssLayout();
        imagesCourses = new ArrayList<>();
        labelsCourses = new ArrayList<>();

        createLayout(width);
        update();

    }


    private void calcLayoutSize(int width) {
        int columnCounter = 0;

        while(width > 0) {
            width -= COMPONENT_SIZE + SPACING_SIZE;
            columnCounter++;
        }
        maxColumns = columnCounter - 1;
    }


    private void update() {
        contentLayout.removeAllComponents();

        imagesCourses.forEach(img -> {
            if( contentLayout.getCursorX() < maxColumns || showAll){
                contentLayout.addComponent(img);
            }
        });
        labelsCourses.forEach(label -> {
            if(contentLayout.getCursorX() < maxColumns || showAll){
                contentLayout.addComponent(label);
            }
        });

        contentLayout.addComponent(moreCoursesImg);
        contentLayout.setComponentAlignment(moreCoursesImg, Alignment.BOTTOM_RIGHT);
    }


    private void createLayout(int width) {
        calcLayoutSize(width);

        moduleLayout = new VerticalLayout();
        contentLayout = new GridLayout(maxColumns + 1, 1);
        contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        nameLabel = new Label(MODUL_NAME);
        moduleLayout.addComponent(nameLabel);
        moduleLayout.addComponent(contentLayout);

        String stringPath = Paths.get("").toAbsolutePath().toString();

        user.getCourses().forEach(course -> {
            File f = new File(stringPath + "/Resource/Images/Courses/" + course.getName() +".jpg");

            if(f.exists()) {
                FileResource resource = new FileResource(f);
                Image tmpImage = new Image(null, resource);
                tmpImage.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                tmpImage.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                tmpImage.setDescription(course.getName());
                imagesCourses.add(tmpImage);
            } else {
                try {
                    CustomLayout customLayout = new CustomLayout(
                            new ByteArrayInputStream(
                                    ("<p> <center>" + course.getName() +  "</center> </p>").getBytes()));
                    customLayout.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                    customLayout.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                    labelsCourses.add(customLayout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        File f = new File(stringPath + "/Resource/Images/Icons/ellipsis-h.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            moreCoursesImg = new Image(null, resource);
            moreCoursesImg.setDescription("Alle Kurse anzeigen.");
            moreCoursesImg.addClickListener( event -> {
                showAll = !showAll;
                update();
            });
        }

        moduleLayout.setSpacing(true);
        moduleLayout.setMargin(true);
        contentLayout.setSpacing(true);

        layout.addComponents(moduleLayout);
    }

}
