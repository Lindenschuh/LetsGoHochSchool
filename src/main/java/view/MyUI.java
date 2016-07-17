package view;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;

import com.vaadin.event.ShortcutAction;
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
    private static final boolean DEBUG = false;



    private Modul currentPage;
    private NavigationBarController naviBar;
    private SearchController searchController;
    private VerticalLayout contentLayout;
    private Label title;
    private User currentUser = null;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initilize();
        
    }

    private void initilize() {
        Master.initilizeXstream();

        this.setStyleName("ui");
        //Wrapper Layout for the whole page
        final VerticalLayout wrapperLayout = new VerticalLayout();
        wrapperLayout.setWidth("100%");
        setContent(wrapperLayout);


            Master.loadDB();


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
            HorizontalLayout topLayout = new HorizontalLayout(  );
            topLayout.addStyleName("pageHeader");
            wrapperLayout.addComponent(topLayout);

            //Create title.
            title = new Label("Let's GO HochSCHOOL");
            topLayout.addComponent(title);

            //Bottom Layout for the menu and content.
            HorizontalLayout bottomLayout = new HorizontalLayout();
            wrapperLayout.addComponent(bottomLayout);

            //Layout for the content
            contentLayout = new VerticalLayout();

            //Create navigation and search bar.
            searchController = new SearchController(currentUser, this);
            naviBar = new NavigationBarController(currentUser, this);

            contentLayout.setWidth("100%");
            contentLayout.addComponent(searchController.getSearchBar());

            //Add navi and content layout
            bottomLayout.addComponent(naviBar.getContent());
            bottomLayout.addComponent(contentLayout);

            bottomLayout.setWidth("100%");
            bottomLayout.setExpandRatio(contentLayout, 1.0f);

            //Set the start page.
            setContentPage(new HomeController(currentUser, this));
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

    public void setContentPage(Modul module) {
        if(currentPage != null) {
            contentLayout.removeComponent(currentPage.getContent());
        }
        currentPage = module;
        contentLayout.addComponent(module.getContent());
    }

    public Modul getContentPage() {
        return currentPage;
    }

    public SearchController getSearch() {
        return searchController;
    }

    private void LoginPage(Layout layout)
    {
        Label title1 = new Label("Let's GO");
        Label title2 = new Label("HochSCHOOL");
        VerticalLayout vert = new VerticalLayout();
        VerticalLayout moduleLayout = new VerticalLayout();
        TextField login = new TextField();
        PasswordField pass = new PasswordField();
        Button sub = new Button("Login");

        title1.setStyleName("titleTop");
        title2.setStyleName("titleBottom");
        login.setInputPrompt("Benutzer");
        pass.setInputPrompt("Passwort");
        moduleLayout.setStyleName("loginModule");
        moduleLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        moduleLayout.setWidthUndefined();
        vert.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        vert.setHeight(getPage().getBrowserWindowHeight(), Unit.PIXELS);

        moduleLayout.addComponents(title1, title2, login,pass,sub);
        vert.addComponent(moduleLayout);
        layout.addComponent(vert);

        sub.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        sub.addClickListener(e -> checkLogin(login,pass));

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


    public User getUser() {
        return currentUser;
    }


}
