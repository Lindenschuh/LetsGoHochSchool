package controller;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import controller.module.Modul;
import controller.module.SearchResultModul;
import model.User;
import util.Master;
import view.MyUI;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class SearchController extends Modul {

    private final static int ICON_SIZE = 37;

    private MyUI ui;
    private Image searchIcon;
    private TextField searchField;
    private VerticalLayout moduleLayout;
    private HorizontalLayout searchLayout;
    private VerticalLayout searchResultLayout;

    public SearchController(User user, MyUI ui) {
        super(user);
        this.ui = ui;

        searchField = new TextField();
        moduleLayout = new VerticalLayout();
        searchLayout = new HorizontalLayout();
        searchResultLayout = new VerticalLayout();

        createLayout();
    }

    private void createLayout () {

        createSearchIcon();

        searchField.addTextChangeListener(event -> {
            //Text changed. Do something.

            if (event.getText().equals("")) {
                searchResultLayout.setVisible(false);
            } else {
                searchResultLayout.setVisible(true);
                ui.getContentLayout().removeAllComponents();
                ui.getContentLayout().addComponent(this.getContent());
                moduleLayout.addComponent(searchResultLayout);
            }


            searchResultLayout.removeAllComponents();

            Master.allAchievements.forEach(achievement -> {

                String lowerName = achievement.getName().toLowerCase();
                String lowerInput = event.getText().toLowerCase();

                if(lowerName.contains(lowerInput)) {
                    searchResultLayout.addComponent(new SearchResultModul(user, achievement).getContent());
                }
            });

            Master.allCourse.forEach(course -> {
                String lowerName = course.getName().toLowerCase();
                String lowerInput = event.getText().toLowerCase();

                if(lowerName.contains(lowerInput)) {
                    searchResultLayout.addComponent(new SearchResultModul(user, course).getContent());
                }
            });

            Master.allUser.forEach(u -> {

                String lowerName = u.getName().toLowerCase();
                String lowerInput = event.getText().toLowerCase();

                if(lowerName.contains(lowerInput)) {
                    searchResultLayout.addComponent(new SearchResultModul(user, u).getContent());
                }
            });

            user.getCourses().forEach(course -> {
                user.getTodos(course.getName()).forEach(todo -> {
                    String lowerName = todo.toLowerCase();
                    String lowerInput = event.getText().toLowerCase();

                    if(lowerName.contains(lowerInput)) {
                        searchResultLayout.addComponent(new SearchResultModul(user, todo).getContent());
                    }
                });
            });
        });

        searchLayout.addComponent(searchField);
        searchLayout.addComponent(searchIcon);

        searchLayout.setSpacing(true);
        searchResultLayout.setMargin(true);
        searchResultLayout.setSpacing(true);

        moduleLayout.addComponent(searchLayout);
        layout.addComponent(moduleLayout);
    }

    private void createSearchIcon() {
        //TODO: Richtiges Icon verwenden.
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/search.png");
        if (f.exists()) {
            FileResource r = new FileResource(f);
            searchIcon = new Image(null, r);
            searchIcon.setDescription("ToDo");
            searchIcon.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            searchIcon.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
        }
    }

    private void update() {

    }

    public void show(boolean show) {
        layout.setVisible(show);
        if(show) {
            searchField.focus();
        } else {
            searchField.setValue("");
            searchResultLayout.removeAllComponents();
            searchResultLayout.setVisible(false);
        }
    }

    public boolean isVisible() {
        return layout.isVisible();
    }
}
