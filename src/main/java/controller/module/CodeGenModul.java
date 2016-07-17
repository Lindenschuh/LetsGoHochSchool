package controller.module;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import model.Course;
import model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Lars Assmus on 09.06.2016.
 */
// TODO: Modul kann erst bei einer Persistens richtig getestet werden da man 1 user und ein Admin brauch um diese sachen zu triggern
public class CodeGenModul extends Modul {

    User user;
    Course course;
    final int VALIDTIME = 300000; // dauer für die gülitigkeit des codes 300000 == 5 min
    final int MESSAGETIME = 1000;// dauer einer meldung

    public CodeGenModul(User user, Course course) {
        super(user);

        this.user= user;
        this.course= course;

        buildField();
    }

    private void buildField() {
        if(user.isAdmin())
            buildAdmin();
        else
            buildUser();

    }


    private void buildUser() {
        HorizontalLayout horigeller = new HorizontalLayout();
        TextField codeInput = new TextField();
        codeInput.addStyleName("code");
        Button submit = new Button("check");
        submit.addClickListener(e -> checkCode(codeInput));
        horigeller.setSpacing(true);
        horigeller.addComponents(codeInput,submit);
        layout.addComponent(horigeller);



    }
    //TODO: Value im modelspeichern um achievements zu triggern und abusing vermeiden (mehrmals den code eingeben)
    private void checkCode(TextField codeInput) {
        String code = codeInput.getValue();
        if(course.getCode() != null)
        {
            if(course.getCode().equals(code))
            {
                codeInput.setValue("");
                Notification notify = new Notification("Erfolgreich", Notification.Type.ASSISTIVE_NOTIFICATION);
                notify.setDelayMsec(MESSAGETIME);
                notify.setPosition(Position.TOP_RIGHT);
                notify.show(Page.getCurrent());
            }
            else
            {
                codeInput.setValue("");
                Notification notify = new Notification("Falscher Code", Notification.Type.ERROR_MESSAGE);
                notify.setDelayMsec(MESSAGETIME);
                notify.setPosition(Position.TOP_RIGHT);
                notify.show(Page.getCurrent());
            }


        }else
        {
            codeInput.setValue("");
            Notification notify = new Notification("Aktuell ist kein Code vorhanden", Notification.Type.HUMANIZED_MESSAGE);
            notify.setDelayMsec(MESSAGETIME);
            notify.setPosition(Position.TOP_RIGHT);
            notify.show(Page.getCurrent());
        }

    }


    private void buildAdmin() {
        HorizontalLayout horigeller = new HorizontalLayout();
        TextField codeData = new TextField();
        codeData.addStyleName("code");
        codeData.setReadOnly(true);
        Button generate = new Button("Generate");
        generate.addClickListener(e -> genHash(codeData));
        horigeller.setSpacing(true);
        horigeller.addComponents(codeData,generate);
        layout.addComponent(horigeller);

    }

    private void genHash(TextField codeData) {
        String code = UUID.randomUUID().toString();

        if(course.getCode()== null) {


            code = code.subSequence(0, 6).toString();

            course.setCode(code);
            codeData.setReadOnly(false);
            codeData.setValue(code);
            codeData.setReadOnly(true);

            Notification notify = new Notification(code,"ist der Aktuelle code", Notification.Type.HUMANIZED_MESSAGE);
            notify.setDelayMsec(MESSAGETIME);
            notify.setPosition(Position.TOP_RIGHT);
            notify.show(Page.getCurrent());

            ScheduledExecutorService execSer = Executors.newScheduledThreadPool(5);
            execSer.schedule(()->clearCode() ,VALIDTIME, TimeUnit.MILLISECONDS);


        }
        else {
            codeData.setReadOnly(false);
            codeData.setValue(course.getCode());
            codeData.setReadOnly(true);
            Notification notify = new Notification(course.getCode(),"ist der Aktuelle code", Notification.Type.ERROR_MESSAGE);
            notify.setDelayMsec(MESSAGETIME);
            notify.setPosition(Position.TOP_RIGHT);
            notify.show(Page.getCurrent());
        }

    }

    private void clearCode() {
        course.clearCode();
    }
}
