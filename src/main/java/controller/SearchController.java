package controller;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
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
    private HorizontalLayout searchLayout;
    private VerticalLayout searchResultLayout;
    private CssLayout searchBar;
    private Modul previousPage;

    public SearchController(User user, MyUI ui) {
        super(user);
        this.ui = ui;

        searchBar = new CssLayout();
        searchField = new TextField();
        searchLayout = new HorizontalLayout();
        searchResultLayout = new VerticalLayout();

        createLayout();
    }

    private void createLayout() {

        createLayoutSearchBar();
        createLayoutSearchResult();

        //Text changed Listener. Do something.
        searchField.addTextChangeListener(event -> update(event.getText()));

        //Listener gets called, when the page get switched.
        ui.getContentLayout().addComponentAttachListener(event -> {
            if(ui.getContentPage() != null) {
                if(ui.getContentPage() != previousPage && ui.getContentPage() != this) {
                    show(false);
                }
            }
        });
    }

    private void createLayoutSearchResult() {
        searchResultLayout = new VerticalLayout();
        searchResultLayout.setStyleName("page");
        searchResultLayout.setSpacing(true);
        layout.addComponent(searchResultLayout);
    }


    private void createLayoutSearchBar() {

        //The search is hidden by default.
        searchLayout.setVisible(false);

        //Styling.
        searchLayout.setStyleName("content");
        searchField.setStyleName("searchField");

        //load the icons.
        loadSearchIcon();
        loadPlaceholderIcon();

        //add everything to the layout
        searchLayout.addComponent(searchField);
        searchLayout.addComponent(searchIcon);

        searchBar.addComponent(placeholder);
        searchBar.addComponent(searchLayout);
    }


    private void loadSearchIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/search.png");
        if (f.exists()) {
            FileResource r = new FileResource(f);
            searchIcon = new Image(null, r);
            searchIcon.setDescription("Search");
            searchIcon.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            searchIcon.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
        }
    }

    private void loadPlaceholderIcon() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/search_placeholder.png");
        if (f.exists()) {
            FileResource r = new FileResource(f);
            placeholder = new Image(null, r);
            placeholder.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            placeholder.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
        }
    }


    public void show(boolean show) {

        //Show placeholder or searchfield.
        searchLayout.setVisible(show);
        placeholder.setVisible(!show);

        if (show) {
            //Search pops up. Set focus.
            searchField.focus();
        } else {
            //Search got hide. Reset it.
            searchField.setValue("");
            update("");
        }
    }

    public boolean isVisible() {
        return searchLayout.isVisible();
    }

    public CssLayout getSearchBar() {
        return searchBar;
    }

    public void update(String search) {

        //Clean up.
        searchResultLayout.removeAllComponents();

        //Get the current page.
        if (ui.getContentPage() != null) {
            if (!(ui.getContentPage() instanceof SearchController)) {
                previousPage = ui.getContentPage();
            }
        }

        if (search.equals("")) {

            //Turn search off.
            if (layout.isVisible()) {
                if (previousPage != null) {
                    ui.setContentPage(previousPage);
                }
                layout.setVisible(false);
            }

        } else {

            //Search for achievements
            Master.allCourse.forEach(course -> {

                //Everything to lowercase.
                String lowerName = course.getName().toLowerCase();
                String lowerInput = search.toLowerCase();

                //Compare.
                if (lowerName.contains(lowerInput)) {
                    searchResultLayout.addComponent(new SearchResultModul(user, ui, course).getContent());
                }
            });

            //Search for courses
            Master.allUser.forEach(u -> {
                //Everything to lowercase.
                String lowerName = u.getName().toLowerCase();
                String lowerInput = search.toLowerCase();

                //Compare.
                if (lowerName.contains(lowerInput)) {
                    searchResultLayout.addComponent(new SearchResultModul(user, ui, u).getContent());
                }
            });

            //Search for users
            Master.allAchievements.forEach(achievement -> {

                //Everything to lowercase.
                String lowerName = achievement.getName().toLowerCase();
                String lowerInput = search.toLowerCase();

                //Compare.
                if (lowerName.contains(lowerInput)) {
                    searchResultLayout.addComponent(
                            new SearchResultModul(user, ui, achievement).getContent());
                }
            });

            //Search for to do's
            user.getCourses().forEach(course ->
                user.getTodos(course.getName()).forEach(todo -> {

                    //Everything to lower case.
                    String lowerName = todo.toLowerCase();
                    String lowerInput = search.toLowerCase();

                    //Compare.
                    if (lowerName.contains(lowerInput)) {
                        searchResultLayout.addComponent(new SearchResultModul(user, ui, todo).getContent());
                    }
                })
            );

            //we find something?
            if (searchResultLayout.getComponentCount() > 0) {

                //Make the layout visible and set the page.
                layout.setVisible(true);
                ui.setContentPage(this);
            } else if (layout.isVisible() && previousPage != null) {

                //Return to the old page
                ui.setContentPage(previousPage);
            }
        }
    }

}