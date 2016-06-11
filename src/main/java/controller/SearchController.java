package controller;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractLayout;
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

    private final static int ICON_SIZE = 38;

    private MyUI ui;
    private Image placeholder;
    private Image searchIcon;
    private TextField searchField;
    private VerticalLayout moduleLayout;
    private HorizontalLayout searchLayout;
    private VerticalLayout searchResultLayout;
    private AbstractLayout page;

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

        moduleLayout.setStyleName("searchLayout");
        searchField.setStyleName("searchField");
        searchResultLayout.setStyleName("page");

        //The search is hidden by default.
        searchLayout.setVisible(false);

        //load the search icon
        createSearchIcon();
        createPlaceholderIcon();

        //Text changed. Do something.
        searchField.addTextChangeListener(event -> {

            //Get the current page, if we have one.
            if (ui.getComponentCount() >= 1) {
                page = (AbstractLayout) ui.getContentLayout().getComponent(1);
            }

            //Is the search field empty?
            if (event.getText().equals("")) {

                //hide the search and bring back the page.
                searchResultLayout.setVisible(false);
                page.setVisible(true);
            } else {
                //Clean up.
                searchResultLayout.removeAllComponents();

                //Search for achievements
                Master.allAchievements.forEach(achievement -> {

                    String lowerName = achievement.getName().toLowerCase();
                    String lowerInput = event.getText().toLowerCase();

                    if(lowerName.contains(lowerInput)) {
                        searchResultLayout.addComponent(new SearchResultModul(user, achievement).getContent());
                    }
                });

                //Search for courses
                Master.allCourse.forEach(course -> {
                    String lowerName = course.getName().toLowerCase();
                    String lowerInput = event.getText().toLowerCase();

                    if(lowerName.contains(lowerInput)) {
                        searchResultLayout.addComponent(new SearchResultModul(user, course).getContent());
                    }
                });

                //Search for users
                Master.allUser.forEach(u -> {

                    String lowerName = u.getName().toLowerCase();
                    String lowerInput = event.getText().toLowerCase();

                    if(lowerName.contains(lowerInput)) {
                        searchResultLayout.addComponent(new SearchResultModul(user, u).getContent());
                    }
                });

                //Search for to do's
                user.getCourses().forEach(course -> {
                    user.getTodos(course.getName()).forEach(todo -> {
                        String lowerName = todo.toLowerCase();
                        String lowerInput = event.getText().toLowerCase();

                        if(lowerName.contains(lowerInput)) {
                            searchResultLayout.addComponent(new SearchResultModul(user, todo).getContent());
                        }
                    });
                });

                //we find something?
                if (searchResultLayout.getComponentCount() > 0) {
                    //hide the page and show the search results.
                    searchResultLayout.setVisible(true);
                    page.setVisible(false);
                }
            }
        });

        //add everything to the layout
        searchLayout.addComponent(searchField);
        searchLayout.addComponent(searchIcon);

        searchLayout.setSpacing(true);
        searchResultLayout.setMargin(true);
        searchResultLayout.setSpacing(true);

        moduleLayout.addComponent(placeholder);
        moduleLayout.addComponent(searchLayout);
        moduleLayout.addComponent(searchResultLayout);

        layout.addComponent(moduleLayout);
    }

    private void createSearchIcon() {
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

    private void createPlaceholderIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/search_placeholder.png");
        if (f.exists()) {
            FileResource r = new FileResource(f);
            placeholder = new Image(null, r);
            placeholder.setDescription("ToDo");
            placeholder.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            placeholder.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
        }
    }

    private void update() {

    }

    public void show(boolean show) {
        searchLayout.setVisible(show);
        placeholder.setVisible(!show);

        if(show) {
            searchField.focus();
        } else {
            //Search got hide. Reset it.
            searchField.setValue("");
            searchResultLayout.removeAllComponents();
            searchResultLayout.setVisible(false);
            if(page != null) {
                page.setVisible(true);
            }
        }
    }

    public boolean isVisible() {
        return searchLayout.isVisible();
    }
}
