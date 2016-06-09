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

    private SearchController searchController;
    private VerticalLayout contentLayout;
    private Label title;
    private User currentUser = null;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initilize();
        
    }

    private void initilize() {
        //Wrapper Layout for the whole page
        final VerticalLayout wrapperLayout = new VerticalLayout();
        setContent(wrapperLayout);
        Master.makeTest();
        // Info: if statement wieder einwerfen und currentUser = master.blabla aus kommentieren zum einloggen
        //if(currentUser == null)
        //{
        //   LoginPage(wrapperLayout);
        //}else {


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
            search.addClickListener(e -> searchController.show(!searchController.isVisible()));
            search.setSizeFull();
            search.setIcon(FontAwesome.SEARCH);
            menuLayout.addComponent(search);


            Button home = new Button("Home");
            home.addClickListener(e -> setHomePage());
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
            schedule.addClickListener(e -> setCalenderPage());
            schedule.setSizeFull();
            schedule.setIcon(FontAwesome.CALENDAR);
            menuLayout.addComponent(schedule);


            Button achievements = new Button("Achievements");
            achievements.addClickListener(e -> setAchievementPage());
            achievements.setSizeFull();
            achievements.setIcon(FontAwesome.TROPHY);
            menuLayout.addComponent(achievements);

        /*
        Button settings = new Button("Settings");
        settings.setSizeFull();
        settings.setIcon(FontAwesome.COG);
        menuLayout.addComponent(settings);
        */

            //Layout for the content
            contentLayout = new VerticalLayout();
            contentLayout.setSizeFull();
            contentLayout.addStyleName("contend");
            contentLayout.setHeight("100%");
            bottomLayout.addComponent(contentLayout);

            bottomLayout.setExpandRatio(menuLayout, 15);
            bottomLayout.setExpandRatio(contentLayout, 85);



            currentUser = Master.allUser.get(1);
            //DON'T REMOVE or null pointer.
            searchController = new SearchController(currentUser, this);
            setHomePage();
        //}
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public VerticalLayout getContentLayout() {
        return contentLayout;
    }

    public void setTitle(String title) { this.title.setValue(title); }

    private void setHomePage() {
        contentLayout.removeAllComponents();
        HomeController h = new HomeController(currentUser, this);
        searchController.show(false);
        contentLayout.addComponent(searchController.getContent());
        contentLayout.addComponent(h.getContent());
    }
    private void setCalenderPage() {
        contentLayout.removeAllComponents();
        CalenderController cal = new CalenderController(currentUser);
        searchController.show(false);
        contentLayout.addComponent(searchController.getContent());
        contentLayout.addComponent(cal.getContent());
    }

    private void setAchievementPage() {
        contentLayout.removeAllComponents();
        AchievementsController a = new AchievementsController(currentUser, this);
        searchController.show(false);
        contentLayout.addComponent(searchController.getContent());
        contentLayout.addComponent(a.getContent());
    }

    private void setCoursePage() {
        contentLayout.removeAllComponents();
        CourseController c = new CourseController(currentUser);
        searchController.show(false);
        contentLayout.addComponent(searchController.getContent());
        contentLayout.addComponent(c.getContent());

    }

    private void setProfilePage() {
        contentLayout.removeAllComponents();
        ProfileController p = new ProfileController(currentUser, this);
        searchController.show(false);
        contentLayout.addComponent(searchController.getContent());
        contentLayout.addComponent(p.getContent());
    }

    private void LoginPage(Layout layout)
    {
        VerticalLayout vert = new VerticalLayout();
        TextField login = new TextField();
        PasswordField pass = new PasswordField();
        Label message = new Label();
        Button sub = new Button("Submit");
        sub.addClickListener(e -> checkLogin(login,pass,message));
        vert.addComponents(message,login,pass,sub);
        layout.addComponent(vert);


    }

    private void checkLogin(TextField login, PasswordField pass,Label message) {
        message.setValue("");
        for(User user: Master.allUser)
        {
            if(user.validation(login.getValue(),pass.getValue()))
            {
                currentUser = user;
                break;
            }


        }

        if(currentUser == null)
        {
            message.setValue("error username or Password is wrong");

        }else {

            setContent(null);
            initilize();

        }

    }


}
