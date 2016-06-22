package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import model.User;
import util.Master;

/**
 * Created by Kevin on 10/06/2016.
 */
public class NewUser extends Modul {

    VerticalLayout verticalLayout;
    HorizontalLayout horiLayout;
    TextField username, email, checkEmail;
    PasswordField password, checkPassword;
    CheckBox admin;
    Button submit;
    TextField speakTime;
    TextField Raum;
    Label sz;

    public NewUser(User user) {
        super(user);

        verticalLayout  = new VerticalLayout();
        horiLayout = new HorizontalLayout();

        username        = new TextField();
        email           = new TextField();
        checkEmail      = new TextField();
        password        = new PasswordField();
        checkPassword   = new PasswordField();
        admin           = new CheckBox();
        submit          = new Button("Submit");

        speakTime = new TextField();

        Raum = new TextField();

        username.setInputPrompt("Benutzername");
        email.setInputPrompt("E-mailadresse");
        checkEmail.setInputPrompt("E-mailadress bestätigen");
        password.setInputPrompt("Passwort");
        checkPassword.setInputPrompt("Passwort bestätigen");
        admin.setValue(false);
        admin.addValueChangeListener(valueChangeEvent -> setTime() );
        submit.addClickListener(clickEvent -> submit());
        sz              = new Label("Sprechzeiten", ContentMode.HTML);

        speakTime.setInputPrompt("Zeit");

        Raum.setInputPrompt("Raum");


        CreateLayout();
    }

    private void CreateLayout()
    {
        verticalLayout.addComponent(username);
        verticalLayout.addComponent(email);
        verticalLayout.addComponent(checkEmail);
        verticalLayout.addComponent(password);
        verticalLayout.addComponent(checkPassword);
        verticalLayout.addComponent(new Label("Admin", ContentMode.HTML));
        verticalLayout.addComponent(admin);
        verticalLayout.addComponent(submit);
        layout.addComponent(verticalLayout);
    }

    private void setTime()
    {
        if(admin.getValue() == true) {
            verticalLayout.removeComponent(submit);
            verticalLayout.addComponent(sz);

            horiLayout.addComponent(speakTime);
            horiLayout.addComponent(Raum);
            verticalLayout.addComponent(horiLayout);

            verticalLayout.addComponent(submit);
        }

        if(admin.getValue() == false)
        {
            verticalLayout.removeComponent(sz);
            verticalLayout.removeComponent(horiLayout);
        }
    }

    private String getTimeForm()
    {

        String time;

        time = "<div id = \"ip\"><p>";

        if(!speakTime.isEmpty())
        {
            time = time + speakTime.getValue() + "<br>" + Raum.getValue();
        }

        time = time + "</p></div><style type=\"text/css\"> #ip { background-color: #d3d3d3; padding: 1.3em;} </style>";

        return time;

    }

    private void submit()
    {
        boolean failed = false;
        String errorMsg = "";

        if(username.isEmpty())
        {
            failed = true;
            errorMsg = errorMsg + "Kein Benutzername angegeben\n";
        }

        if(!email.getValue().equals(checkEmail.getValue()))
        {
            failed = true;
            errorMsg = errorMsg + "Emailadressen stimmen nicht überein\n";
        }
        else if(email.isEmpty())
        {
            failed = true;
            errorMsg = errorMsg + "Keine Emailadresse angegeben";
        }

        if(!password.getValue().equals(checkPassword.getValue()))
        {
            failed = true;
            errorMsg = errorMsg + "Passwörter stimmen nicht überein\n";
        }
        else if(password.isEmpty())
        {
            failed = true;
            errorMsg = errorMsg + "Kein Passwort angegeben\n";
        }

        if(getTimeForm() == "" && admin.getValue())
        {
            failed = true;
            errorMsg = errorMsg + "Keine Sprechzeit angegeben\n";
        }

        if(speakTime.isEmpty() != Raum.isEmpty())
        {
            failed = true;
            errorMsg = errorMsg + "Raum oder Zeit für Montag Vergessen\n";
        }


        if(failed == true)
        {
            Notification notify = new Notification(errorMsg, Notification.Type.ERROR_MESSAGE);
            notify.setPosition(Position.TOP_CENTER);
            notify.show(Page.getCurrent());
            return;
        }

        //Master.allUser.add(new User(username.getValue(), email.getValue(), password.getValue(), admin.getValue(), getTimeForm()));

        Notification success = new Notification("Benutzer erfolgreich erstellt", Notification.Type.HUMANIZED_MESSAGE);
        success.setDelayMsec(1000);
        success.setPosition(Position.TOP_CENTER);
        success.show(Page.getCurrent());
    }
}
