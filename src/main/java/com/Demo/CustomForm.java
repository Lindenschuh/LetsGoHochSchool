package com.Demo;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class CustomForm extends FormLayout
{
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private NativeSelect status = new NativeSelect("Status");
    private PopupDateField birthdate = new PopupDateField("Birthday");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;

    public CustomForm(MyUI myUI)
    {
        this.myUI = myUI;
        status.addItems(CustomerStatus.values());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        save.addClickListener(e->this.save());
        delete.addClickListener(e ->this.delete());

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setSpacing(true);
        addComponents(firstName,lastName,email,status,birthdate,buttons);


    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
        BeanFieldGroup.bindFieldsUnbuffered(customer,this);

        // show delete button only for customers who already in the database
        delete.setVisible(customer.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void delete()
    {
        service.delete(customer);
        myUI.updateList();
        setVisible(false);

    }

    private void save()
    {
        service.save(customer);
        myUI.updateList();
        setVisible(false);
    }
}
