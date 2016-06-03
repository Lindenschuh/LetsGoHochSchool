package controller;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.server.FileResource;
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
public class CourseModul extends Modul {


    private int maxColumns;
    private boolean showAll;

    private Image moreCoursesImg;
    private GridLayout contentLayout;

    private ArrayList<Image> imagesCourses;
    private ArrayList<CustomLayout> labelsCourses;


    /**
     * The course module shows all the courses from a user.
     * @param user The {@link User}.
     * @param width Max width, for this view.
     */
    public CourseModul(User user, int width) {
        super(user);

        showAll = false;
        layout = new CssLayout();
        imagesCourses = new ArrayList<>();
        labelsCourses = new ArrayList<>();

        calcLayout(width);

        contentLayout = new GridLayout(maxColumns + 1, 1);
        contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        String stringPath = Paths.get("").toAbsolutePath().toString();

        user.getCourses().forEach(course -> {
            File f = new File(stringPath + "/Resource/Images/Courses/" + course.getName() +".jpg");

            if(f.exists()) {
                FileResource resource = new FileResource(f);
                Image tmpImage = new Image(null, resource);
                tmpImage.setWidth(100, Sizeable.Unit.PIXELS);
                tmpImage.setHeight(100, Sizeable.Unit.PIXELS);
                imagesCourses.add(tmpImage);
            } else {
                try {
                    CustomLayout customLayout = new CustomLayout(
                            new ByteArrayInputStream(
                                    ("<p> <center>" + course.getName() +  "</center> </p>").getBytes()));
                    customLayout.setWidth(100, Sizeable.Unit.PIXELS);
                    customLayout.setHeight(100, Sizeable.Unit.PIXELS);
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
            moreCoursesImg.addClickListener( event -> {
                showAll = !showAll;
                updateContent();
            });
        }

        updateContent();

        contentLayout.setSpacing(true);
        contentLayout.setMargin(true);
        layout.addComponents(contentLayout);
    }

    private void calcLayout(int width) {
        int columnCounter = 0;

        while(width > 0) {
            width -= 130;
            columnCounter++;
        }
        maxColumns = columnCounter - 1;
    }


    private void updateContent() {
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
}
