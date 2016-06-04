package controller;

import com.vaadin.ui.CssLayout;
import model.User;

/**
 * Created by Lars on 02.06.2016.
 */

public class Modul {

    protected CssLayout layout = new CssLayout();

    public Modul(User user)
    {

    }

    public CssLayout getContent()
    {
        return layout;
    }


}
