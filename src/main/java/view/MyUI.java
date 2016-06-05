package view;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import controller.*;
import model.User;
import util.Master;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
//TODO Main klasse mit USER
@Theme("mytheme")
@Widgetset("com.Demo.MyAppWidgetset")
public class MyUI extends UI {

    private AbstractLayout contentLayout;
    private Label title;
    private User currentUser;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //Wrapper Layout for the whole page
        final VerticalLayout wrapperLayout = new VerticalLayout();
        setContent(wrapperLayout);


        //Top Layout for the head
        CssLayout topLayout = new CssLayout();
        topLayout.addStyleName("top");
        wrapperLayout.addComponent(topLayout);

        title = new Label("Let's GO HochSCHOOL");
        title.addStyleName("h1");
        topLayout.addComponent(title);

        //Bottom Layout for the menu and content
        HorizontalLayout bottomLayout = new HorizontalLayout();
        bottomLayout.setSizeFull();
        wrapperLayout.addComponent(bottomLayout);



        //menue layout for all buttons in the menu
        CssLayout menuLayout = new CssLayout();
        menuLayout.addStyleName("menu");
        bottomLayout.addComponent(menuLayout);

        Button search = new Button("Search");
        search.setSizeFull();
        search.setIcon(FontAwesome.SEARCH);
        menuLayout.addComponent(search);

        Button home = new Button("Home");
        home.setSizeFull();
        home.setIcon(FontAwesome.HOME);
        menuLayout.addComponent(home);

        Button profile = new Button("Profile");
        profile.addClickListener(e -> setProfilePage());
        profile.setSizeFull();
        profile.setIcon(FontAwesome.USER);
        menuLayout.addComponent(profile);

        Button course = new Button("Course");
        course.addClickListener(e -> setCoursePage());
        course.setSizeFull();
        course.setIcon(FontAwesome.BOOK);
        menuLayout.addComponent(course);

        Button schedule = new Button("Schedule");
        schedule.setSizeFull();
        schedule.setIcon(FontAwesome.CALENDAR);
        menuLayout.addComponent(schedule);

        Button achievements = new Button("Achievements");
        achievements.addClickListener( e -> setAchievementPage());
        achievements.setSizeFull();
        achievements.setIcon(FontAwesome.TROPHY);
        menuLayout.addComponent(achievements);

        Button settings = new Button("Settings");
        settings.setSizeFull();
        settings.setIcon(FontAwesome.COG);
        menuLayout.addComponent(settings);


        //Layout for the content
        contentLayout = new VerticalLayout();
        contentLayout.setSizeFull();
        contentLayout.addStyleName("contend");
        contentLayout.setHeight("100%");
        bottomLayout.addComponent(contentLayout);

        bottomLayout.setExpandRatio(menuLayout, 15);
        bottomLayout.setExpandRatio(contentLayout, 85);


        Master.makeTest();
        currentUser = Master.allUser.get(0);



    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public AbstractLayout getContentLayout() {
        return contentLayout;
    }

    public void setTitle(String title) { this.title.setValue(title); }

    private void setAchievementPage() {
        contentLayout.removeAllComponents();
        AchievementsController a = new AchievementsController(currentUser, this);
        contentLayout.addComponent(a.getContent());
    }

    private void setCoursePage() {
        contentLayout.removeAllComponents();
        CourseController c = new CourseController(currentUser);
        contentLayout.addComponent(c.getContent());

    }

    private void setProfilePage() {
        contentLayout.removeAllComponents();
        ProfileController p = new ProfileController(currentUser, this);
        contentLayout.addComponent(p.getContent());
    }


}
