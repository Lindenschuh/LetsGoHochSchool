package view;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
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

    //debug flag -> skip login screen.
    private static final boolean DEBUG = true;

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
            searchController = new SearchController(currentUser, this);
            NavigationBarController navi = new NavigationBarController(currentUser, this);

            //Add navi and content layout
            bottomLayout.addComponent(navi.getContent());
            bottomLayout.addComponent(contentLayout);

            //Set layout behavior.
            bottomLayout.setExpandRatio(navi.getContent(), 15);
            bottomLayout.setExpandRatio(contentLayout, 85);

            //Set the start page.
            setPage(new HomeController(currentUser, this).getContent());
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

    public void setPage(CssLayout pageContent) {
        contentLayout.removeAllComponents();
        searchController.show(false);
        contentLayout.addComponent(searchController.getContent());
        contentLayout.addComponent(pageContent);
    }

    public SearchController getSearch() {
        return searchController;
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
