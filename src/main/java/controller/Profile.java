package controller;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.vaadin.ui.*;
import model.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Aljos on 02.06.2016.
 */
public class Profile extends Modul {
    private Image img;
    private String name;
    private String email;

    private User user;

    private String times;

    public Profile(User user) {
        super(user);
        this.img = user.getImage();
        this.name = user.getName();
        this.email = user.getEmail();
        this.user = user;

        if(this.user.isAdmin()) {
            this.times =user.getTimes();
        }

        setupLayout();
    }

    private void setupLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        horizontalLayout.addComponent(img);
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        verticalLayout.setMargin(true);
        horizontalLayout.addComponent(verticalLayout);
        verticalLayout.addComponent(new Label(name));
        verticalLayout.addComponent(new Label(email));
        if(this.user.isAdmin()) {
            try {
                CustomLayout customLayout = new CustomLayout(new ByteArrayInputStream(times.getBytes()));
                horizontalLayout.addComponent(customLayout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        layout.addComponent(horizontalLayout);

    }
}
