package view;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import controller.*;
import controller.module.Modul;
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

    /*
     * Debug flag -> Set true skip login screen.
     */
    private static final boolean DEBUG = true;

    private NavigationBarController naviBar;
    private SearchController searchBar;
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

        //Create test data.
        Master.makeTest();

        //DEBUG -> Set a currentUser to skip the login screen.
        if(DEBUG) {
            currentUser = Master.allUser.get(1);
        }

        //Login page?
        if(currentUser == null)
        {
           LoginPage(wrapperLayout);
        }else {

            //Top Layout for the head.
            CssLayout topLayout = new CssLayout();
            topLayout.addStyleName("top");
            wrapperLayout.addComponent(topLayout);

            //Create title.
            title = new Label("Let's GO HochSCHOOL");
            title.addStyleName("h1");
            topLayout.addComponent(title);

            //Bottom Layout for the menu and content.
            HorizontalLayout bottomLayout = new HorizontalLayout();
            bottomLayout.setSizeFull();
            wrapperLayout.addComponent(bottomLayout);

            //Layout for the content
            contentLayout = new VerticalLayout();
            contentLayout.setSizeFull();
            contentLayout.addStyleName("contend");
            contentLayout.setHeight("100%");

            //Create navigation and search bar.
            searchBar = new SearchController(currentUser, this);
            naviBar = new NavigationBarController(currentUser, this);

            //Add navi and content layout
            bottomLayout.addComponent(naviBar.getContent());
            bottomLayout.addComponent(contentLayout);

            //Set layout behavior.
            bottomLayout.setExpandRatio(naviBar.getContent(), 15);
            bottomLayout.setExpandRatio(contentLayout, 85);

            //Set the start page.
            setPage(new HomeController(currentUser, this));
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public VerticalLayout getContentLayout() {
        return contentLayout;
    }

    public void setTitle(String title) { this.title.setValue(title); }

    public void setPage(Modul module) {
        contentLayout.removeAllComponents();

        searchBar.show(false);
        naviBar.setPage(module);

        contentLayout.addComponent(searchBar.getContent());
        contentLayout.addComponent(module.getContent());
    }

    public SearchController getSearch() {
        return searchBar;
    }

    private void LoginPage(Layout layout)
    {
        VerticalLayout vert = new VerticalLayout();
        TextField login = new TextField();
        PasswordField pass = new PasswordField();
        Button sub = new Button("Submit");
        sub.addClickListener(e -> checkLogin(login,pass));
        vert.addComponents(login,pass,sub);
        layout.addComponent(vert);


    }

    private void checkLogin(TextField login, PasswordField pass ) {

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
            Notification notify = new Notification("error" ,"username or Password is wrong", Notification.Type.ERROR_MESSAGE);
            notify.setDelayMsec(1000);
            notify.setPosition(Position.TOP_RIGHT);
            notify.show(Page.getCurrent());


        }else {

            Notification notify = new Notification("Loggin" ,"Hallo "+ currentUser.getName(), Notification.Type.ASSISTIVE_NOTIFICATION);
            notify.setDelayMsec(1000);
            notify.setPosition(Position.TOP_RIGHT);
            notify.show(Page.getCurrent());

            setContent(null);
            initilize();

        }

    }


}
