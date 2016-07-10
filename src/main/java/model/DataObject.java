package model;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Image;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public interface DataObject {

    //void setName(String n);
    String  getName();

    void setImage(Image i);
    Image getImage();



}
