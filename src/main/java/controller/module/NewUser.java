package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import model.User;
import util.Master;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin on 10/06/2016.
 */
public class NewUser extends Modul {

    VerticalLayout verticalLayout;
    HorizontalLayout horiLayout;
    TextField name, username, email, checkEmail;
    PasswordField password, checkPassword;
    CheckBox admin;
    Button submit;
    TextField speakTime;
    TextField raum;
    TextField semesterlength;
    Label sz;
    DateField date;

    public NewUser(User user) {
        super(user);

        verticalLayout  = new VerticalLayout();
        horiLayout = new HorizontalLayout();

        name            = new TextField();
        username        = new TextField();
        email           = new TextField();
        checkEmail      = new TextField();
        password        = new PasswordField();
        checkPassword   = new PasswordField();
        admin           = new CheckBox();
        semesterlength  = new TextField();
        submit          = new Button("Submit");
        raum            = new TextField();
        date            = new DateField();
        speakTime       = new TextField();
        sz              = new Label("Sprechzeit\n", ContentMode.HTML);


        date.setValue(new Date());
        date.setResolution(Resolution.MINUTE);
        date.setDateFormat("dd-MM-yyyy hh:mm");

        name.setInputPrompt("Name");

        username.setInputPrompt("Benutzername");

        email.setInputPrompt("E-mailadresse");

        checkEmail.setInputPrompt("E-mailadress bestätigen");

        password.setInputPrompt("Passwort");

        checkPassword.setInputPrompt("Passwort bestätigen");

        submit.addClickListener(clickEvent -> submit());

        admin.setValue(false);

        admin.addValueChangeListener(valueChangeEvent -> {
           if  (admin.getValue()) {
               verticalLayout.removeComponent(submit);
               verticalLayout.addComponent(sz);
               verticalLayout.addComponent(date);
               verticalLayout.addComponent(speakTime);
               verticalLayout.addComponent(raum);
               verticalLayout.addComponent(submit);
           }

            if (!admin.getValue()) {
                verticalLayout.removeComponent(sz);
                verticalLayout.removeComponent(date);
                verticalLayout.removeComponent(speakTime);
                verticalLayout.removeComponent(raum);
                verticalLayout.removeComponent(submit);
                verticalLayout.addComponent(submit);
            }
       });


        CreateLayout();
    }

    private void CreateLayout()
    {
        verticalLayout.addComponent(name);
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

    private String getTimeForm()
    {

        String time;

        time = "<div id = \"ip\"><p>";

        if(!speakTime.isEmpty())
        {
            time = time + speakTime.getValue() + "<br>" + raum.getValue();
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

        if (admin.getValue()) {
            if (speakTime.isEmpty()) {
                failed = true;
                errorMsg = errorMsg + "Keine Sprechzeit angegeben\n";
            } else {
                try {
                    Integer.parseInt(speakTime.getValue());
                } catch (Exception e) {
                    failed = true;
                    errorMsg += "Nur zahlen in Sprechzeit!\n";
                }
            }

            if (raum.isEmpty()) {
                failed = true;
                errorMsg = errorMsg + "Raum oder Zeit für Montag Vergessen\n";
            }
        }

        if(failed == true)
        {
            Notification notify = new Notification(errorMsg, Notification.Type.ERROR_MESSAGE);
            notify.setPosition(Position.TOP_CENTER);
            notify.show(Page.getCurrent());
            return;
        }

        if (!admin.getValue()) {
            Master.allUser.add(new User(name.getValue(), username.getValue(), email.getValue(), password.getValue()));
        }

        if (admin.getValue()) {
           SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
           Master.allUser.add(new User(name.getValue(), username.getValue(), email.getValue(), password.getValue(), formatter.format(date.getValue()), Integer.parseInt(speakTime.getValue()), raum.getValue()));
        }

        Notification success = new Notification("Benutzer erfolgreich erstellt", Notification.Type.HUMANIZED_MESSAGE);
        success.setDelayMsec(1000);
        success.setPosition(Position.TOP_CENTER);
        success.show(Page.getCurrent());
    }
}
