package controller;

import com.vaadin.ui.CssLayout;
import model.User;

/**
 * Created by Lars on 02.06.2016.
 */

    class Modul {

    protected CssLayout layout = new CssLayout();

    protected Modul(User user)
    {

    }

    public CssLayout getContend()
    {
        return layout;
    }


}
