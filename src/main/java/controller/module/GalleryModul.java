package controller.module;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import util.GalleryButtonListener;
import view.MyUI;
import model.User;
import model.DataObject;
import util.GalleryItemListener;


/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class GalleryModul extends Modul {

    /**
     * Spacing between the items in pixel.
     */
    private final static int SPACING_SIZE = 12;

    /**
     * Size of the items in pixel.
     */
    private final static int COMPONENT_SIZE = 100;

    /**
     * Size of the icons/buttons at the bottom right corner in pixel.
     */
    private final static int ICON_SIZE = 25;

    /**
     * Gallery name.
     */
    private String name;

    /**
     * Max amount of items that can be shown in one column with the current view size.
     */
    private int maxColumns;

    /**
     * Width of the module in pixel.
     */
    private int moduleWidth;

    /**
     * Show all items flag.
     */
    private boolean showAll;

    /**
     * Show add button flag.
     */
    private boolean showAdd;

    /**
     * Max module width.
     */
    private boolean maxWidth;

    /**
     * The ui.
     */
    private MyUI ui;

    /**
     * Label for the gallery name.
     */
    private Label nameLabel;

    /**
     * Icon buttons.
     */
    private Image addBtn;
    private Image maxBtn;
    private Image minBtn;

    /**
     * Module head with the gallery name.
     */
    private HorizontalLayout headLayout;

    /**
     * Contains the images.
     */
    private GridLayout contentLayout;

    /**
     * Conteints the add, min and max button.
     */
    private HorizontalLayout footLayout;

    /**
     * The Module.
     */
    private VerticalLayout moduleLayout;

    /**
     * Images from the data objects for the gallery.
     */
    private ArrayList<Image> dataImg;

    /**
     * Data objects.
     */
    private ArrayList<DataObject> data;

    /**
     * Item clicked itemListeners.
     */
    private ArrayList<GalleryItemListener> itemListeners;
    private ArrayList<GalleryButtonListener> buttonListener;

    /**
     * The String that gets shown, when the gallery is empty.
     */
    private String emptyMsg;

    /**
     * Shows all courses from the given user.
     * @param user The {@link User}.
     * @param ui The {@link MyUI}.
     */
    public GalleryModul(User user, MyUI ui) {
        super(user);
        this.ui = ui;
        showAll = false;
        showAdd = false;
        maxWidth = false;
        data = new ArrayList<>();
        dataImg = new ArrayList<>();
        itemListeners = new ArrayList<>();
        buttonListener = new ArrayList<>();

        createLayout();
    }


    /**
     * Calculate the amount of items that can be
     * shown in the gallery and the module width.
     */
    private void calcLayout() {
        maxColumns = 0;

        int naviWidth = 180;
        int pageSpace = 40;
        int paddingBig = 15;
        int browserWidth = ui.getPage().getBrowserWindowWidth();
        int contentWidth = browserWidth - naviWidth - pageSpace - 2 * paddingBig;

        while (contentWidth >= (COMPONENT_SIZE + SPACING_SIZE)) {
            contentWidth -= COMPONENT_SIZE + SPACING_SIZE;
            maxColumns++;
        }
        moduleWidth = maxColumns * (COMPONENT_SIZE + SPACING_SIZE) + 2 * paddingBig;
    }


    /**
     * Update the gallery.
     */
    private void update() {

        //calc the new layout size.
        calcLayout();

        //Refresh.
        nameLabel.setValue(name);
        contentLayout.removeAllComponents();

        if (maxWidth) {
            layout.setWidth(Integer.toString(moduleWidth) + "px");
        }

        if (data.size() == 0) {
            if (emptyMsg != null) {
                if (maxWidth) {
                    contentLayout.setWidth(Integer.toString(moduleWidth) + "px");
                }
                contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
                Label emptyLbl = new Label(emptyMsg);
                contentLayout.setHeight("130px");
                contentLayout.setColumnExpandRatio(0, 1);
                contentLayout.setRowExpandRatio(0, 1);
                emptyLbl.setStyleName("empty");
                contentLayout.addComponent(emptyLbl);
                layout.setVisible(true);
            } else {
                layout.setVisible(false);
            }
        } else {

            if (maxColumns < 1) {
                layout.setVisible(false);
            } else {
                layout.setVisible(true);
                contentLayout.setColumns(maxColumns);
            }

            if (data.size() <= maxColumns) {
                contentLayout.setColumns(data.size());
                showAll = false;
                minBtn.setVisible(false);
                maxBtn.setVisible(false);
                footLayout.setVisible(false);
                dataImg.forEach(img -> contentLayout.addComponent(img));
            } else if (showAll) {

                //Manage buttons.
                minBtn.setVisible(true);
                maxBtn.setVisible(false);
                footLayout.setVisible(true);

                //Add all images.
                dataImg.forEach(img -> contentLayout.addComponent(img));

            } else {

                int counter = 0;
                minBtn.setVisible(false);
                maxBtn.setVisible(true);
                footLayout.setVisible(true);

                for (Image img: dataImg) {
                    if (counter < maxColumns) {
                        counter++;
                        contentLayout.addComponent(img);
                    }
                }
            }
        }
        //Add button need?
        addBtn.setVisible(showAdd);

        //Hide the foot layout, if it's not need.
        footLayout.setVisible(showAdd || minBtn.isVisible() || maxBtn.isVisible());
    }

    /**
     * Create the images for the data.
     */
    private void fillList() {
        dataImg.clear();
        data.forEach(data -> {

            //create images for the data
            Image img = new Image(null, data.getImage().getSource());
            img.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
            img.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
            img.setDescription(data.getName());

            //Image click listener.
            img.addClickListener(clickEvent ->
                //Call the itemListeners.
                itemListeners.forEach(galleryLister -> galleryLister.itemClicked(data)));

            //Add the image.
            dataImg.add(img);
        });
    }

    /**
     * Create the layout.
     */
    private void createLayout() {

        //Create everything.
        nameLabel = new Label("");
        moduleLayout = new VerticalLayout();
        contentLayout = new GridLayout(1, 1);
        footLayout = new HorizontalLayout();

        loadMaxBtn();
        loadMinBtn();
        loadAddBtn();

        //Put the layout together
        footLayout.addComponents(addBtn, minBtn, maxBtn);
        moduleLayout.addComponents(nameLabel, contentLayout, footLayout);
        layout.addComponents(moduleLayout);


        //Styling.
        nameLabel.setStyleName("moduleHead");
        contentLayout.setStyleName("moduleContent");
        footLayout.setStyleName("moduleFoot");
        footLayout.setWidth("100%");
        moduleLayout.setStyleName("module");

        contentLayout.setHideEmptyRowsAndColumns(true);
        contentLayout.setSpacing(true);

        //Add a resize listener to update the gallery.
        ui.getPage().addBrowserWindowResizeListener(event -> update());
    }

    private void loadMaxBtn() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/maxLight.png");
        if (f.exists()) {
            FileResource resource = new FileResource(f);
            maxBtn = new Image(null, resource);
            maxBtn.setStyleName("moduleIcon");
            maxBtn.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            maxBtn.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            maxBtn.setDescription("Weitere Kurse anzeigen.");
            maxBtn.setVisible(false);
            maxBtn.addClickListener(event -> {
                showAll = true;
                update();
            });
        }
    }

    private void loadMinBtn() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/minLight.png");
        if(f.exists()) {
            FileResource resource = new FileResource(f);
            minBtn = new Image(null, resource);
            minBtn.setStyleName("moduleIcon");
            minBtn.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            minBtn.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            minBtn.setDescription("Weniger Kurse anzeigen.");
            minBtn.setVisible(false);
            minBtn.addClickListener(event -> {
                showAll = false;
                update();
            });
        }
    }

    private void loadAddBtn() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/plusLight.png");
        if (f.exists()) {
            FileResource resource = new FileResource(f);
            addBtn = new Image(null, resource);
            addBtn.setStyleName("moduleIcon");
            addBtn.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            addBtn.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            addBtn.setDescription("Kurs hinzuf\u00fcgen");
            addBtn.setVisible(false);
            addBtn.addClickListener(clickEvent ->
                    buttonListener.forEach(galleryListener -> galleryListener.addButtonClicked()));
        }
    }

    /**
     * Set the gallery name.
     * @param n Name of the gallery.
     */
    public void setName(String n) {
       name = n;
       update();
    }

    public void setEmptyMsg(String msg) {
        emptyMsg = msg;
    }

    /**
     * Set the date to show in the gallery.
     * Users, Courses and Achievements supported.
     * @param data Data to show.
     */
    public void setData(ArrayList<DataObject> data) {
        this.data.clear();
        this.data.addAll(data);
        fillList();
        update();
    }

    /**
     * Max the width of the gallery no matter, how many items are in the gallery.
     * @param maxWidth Max width flag.
     */
    public void setMaxWidth(boolean maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Add a listener to the gallery that get called, when a gallery item is clicked.
     * Lambda Supported. Returns the DataObject of the clicked Item.
     * @param listener Add a listener or use lambda.
     */
    public void addItemClickedListener(GalleryItemListener listener) {
        itemListeners.add(listener);
    }

    /**
     * Add a button clicked listener and show the add button.
     * @param listener Add button listener.
     */
    public void addButtonClickedListener(GalleryButtonListener listener){
        showAdd = true;
        buttonListener.add(listener);
    }

}
