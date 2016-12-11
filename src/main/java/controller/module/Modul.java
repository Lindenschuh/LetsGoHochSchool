package controller.module;

import com.vaadin.ui.CssLayout;
import model.User;

/**
 * Created by Lars on 02.06.2016.
 */

public class Modul {

    protected CssLayout layout = new CssLayout();
    protected User user;

    public Modul(User user) {
        this.user = user;
    }

    public CssLayout getContent()
    {
        return layout;
    }


}
