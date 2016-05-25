package com.lars;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.lars.MyAppWidgetset")
public class MyUI extends UI {

    private  CustomerService service = CustomerService.getInstance();
    private Grid grid = new Grid();
    private TextField filterText = new TextField();


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final  VerticalLayout layout = new VerticalLayout();

        updateList();

        filterText.setInputPrompt("filter by nam....");
        filterText.addTextChangeListener(e -> {grid.setContainerDataSource(new BeanItemContainer<>(Customer.class,service.findAll(e.getText())));});

        Button clearFilterTextBnt = new Button(FontAwesome.TIMES);
        clearFilterTextBnt.setDescription("Clear the current filter");
        clearFilterTextBnt.addClickListener(e ->{filterText.clear(); updateList();});


        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBnt);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        CustomForm form = new CustomForm(this);
        form.setVisible(false);

        grid.addSelectionListener(e ->{
            if (e.getSelected().isEmpty()) {
                form.setVisible(false);
            }else{
                Customer customer = (Customer) e.getSelected().iterator().next();
                form.setCustomer(customer);
            }
        });

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e ->{grid.select(null); form.setCustomer(new Customer());});

        HorizontalLayout toolbar = new HorizontalLayout(filtering,addCustomerBtn);
        toolbar.setSpacing(true);
        grid.setColumns("firstName","lastName","email");

        HorizontalLayout main = new HorizontalLayout(grid,form);
        main.setSpacing(true);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid,1);

        layout.addComponents(toolbar,main);



        layout.setMargin(true);
        setContent(layout);
    }

    public void updateList() {
        List<Customer> costumers = service.findAll(filterText.getValue()
        );
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
