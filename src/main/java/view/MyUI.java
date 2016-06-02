package view;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
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

    GridLayout mainLayout = new GridLayout();


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Master.makeTest();

        User myUser = Master.allUser.get(0);

        TextField text = new TextField("WOW");
        text.setValue(myUser.getName());

        mainLayout.addComponent(text);

        setContent(mainLayout);


    }



    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
