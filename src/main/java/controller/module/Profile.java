package controller.module;


import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import model.User;

/**
 * Profile class to show the module Profile
 *
 * Created by Aljos on 02.06.2016.
 */
public class Profile extends Modul {
    private Image img;
    private String name;
    private String email;

    private User user;

    private String times;

    /**
     * Constructor for the Profile page. Calls a User and puts information
     *like profile picture, name and e-mail address on the screen.
     * If the user is an administrator, the times to meet hin will be
     * displayed in a small HTML format.
     *
     * @param user the User calling this Window.
     */


    public Profile(User user) {
        super(user);
        this.img = new Image(null, user.getImage().getSource());
        this.name = user.getName();
        this.email = user.getEmail();
        this.user = user;

        if(this.user.isAdmin()) {
            this.times = user.getTimes();
        }

        setupLayout();
    }

    private void setupLayout() {
        //defining the horizontal layout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //set all components in the middle center
        horizontalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        //config user image
        img.setDescription(user.getName());
        img.setWidth(125, Sizeable.Unit.PIXELS);
        img.setHeight(125, Sizeable.Unit.PIXELS);

        // add user image
        horizontalLayout.addComponent(img);


        //vertical layout for user information
        VerticalLayout verticalLayout = new VerticalLayout();

        //the text should be displayed in the middle left of the vertical layout
        verticalLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

        //the vertical layout shouldn't be direct to the image
        verticalLayout.setMargin(true);


        //adding the vertical layout to the horizontal layout
        horizontalLayout.addComponent(verticalLayout);

        //addin the name and email to the vertical layout
        verticalLayout.addComponent(new Label(name));
        verticalLayout.addComponent(new Label(email));

        //if the user has admin rights, add the custom layout with the times String
        //from the user.
        if(this.user.isAdmin()) {

                Label lable = new Label(this.times, ContentMode.HTML);
                horizontalLayout.addComponent(lable);

        }


        // adds the horizontal layout to the css layout from module, so it can be
        // displayed in the MyUI Class
        horizontalLayout.setMargin(true);
        layout.addComponent(horizontalLayout);

    }
}
