package util;

import model.DataObject;

/**
 * Created by Andreas Reinsch (193790).
 * @version 0.1
 */
@FunctionalInterface
public interface GalleryItemListener {

    /**
     * Gallery item got clicked.
     * @param data DataObject from the gallery item.
     */
    void itemClicked(DataObject data);
}
