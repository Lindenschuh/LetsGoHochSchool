package controller.module;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import controller.CourseController;
import model.Course;
import model.User;
import model.DataObject;
import view.MyUI;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class GalleryModul extends Modul {

    private final static int SPACING_SIZE = 12;
    private final static int COMPONENT_SIZE = 100;
    private final static int ICON_SIZE = 25;

    private String name;
    private int maxColumns;
    private int moduleWidth;
    private boolean showAll;
    private boolean showAdd;
    private boolean maxWidth;

    private MyUI ui;
    private Label nameLabel;
    private Image addBtn;
    private Image maxBtn;
    private Image minBtn;
    //private Image emptyImg;

    private HorizontalLayout headLayout;
    private GridLayout contentLayout;
    private HorizontalLayout footLayout;
    private HorizontalLayout placeholder;
    private VerticalLayout moduleLayout;

    private ArrayList<Image> dataImg;
    private ArrayList<DataObject> data;

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
        dataImg = new ArrayList();

        createLayout();
    }


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

    private void update() {
        if (data.size() == 0) {
            //TODO: Aktion durchführen, wenn Liste leer ist.
            //Wird bist zum fix mal ausgeblendet.
            layout.setVisible(false);
        } else {

            calcLayout();
            nameLabel.setValue(name);
            contentLayout.removeAllComponents();
            if (maxWidth) {
                footLayout.setWidth(Integer.toString(moduleWidth) + "px");
            }

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
                minBtn.setVisible(true);
                maxBtn.setVisible(false);
                footLayout.setVisible(true);
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
            addBtn.setVisible(showAdd);
            footLayout.setVisible(showAdd || minBtn.isVisible() || maxBtn.isVisible());

        }
    }

    private void fillList() {
        dataImg.clear();
        data.forEach(data -> {

            //create images for the data
            Image img = new Image(null, data.getImage().getSource());
            img.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
            img.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
            img.setDescription(data.getName());
            //img.setVisible(false);

            //Format class name.
            String className = data.getClass().getName();
            if (className.contains(".")) {
                className = className.substring(className.indexOf('.') + 1, className.length());
            }

            //Which kind of listener do we need?
            switch (className) {
                case "Course":
                    img.addClickListener(clickEvent -> {
                        ui.setPage(new CourseController(user, (Course) data));
                    });
                    break;
            }

            //Add the image.
            dataImg.add(img);
        });
    }

    private void createLayout() {

        //Create everything.
        nameLabel = new Label("");
        moduleLayout = new VerticalLayout();
        headLayout = new HorizontalLayout();
        contentLayout = new GridLayout(1, 1);
        footLayout = new HorizontalLayout();
        placeholder = new HorizontalLayout();

        createMaxBtn();
        createMinBtn();
        createAddBtn();
        createEmptyImg();


        //Put the layout together
        headLayout.addComponent(nameLabel);

        footLayout.addComponent(placeholder);
        footLayout.addComponent(addBtn);
        footLayout.addComponent(minBtn);
        footLayout.addComponent(maxBtn);

        moduleLayout.addComponent(headLayout);
        moduleLayout.addComponent(contentLayout);
        moduleLayout.addComponent(footLayout);

        layout.addComponents(moduleLayout);


        //Styling.
        nameLabel.setStyleName("moduleName");
        headLayout.setStyleName("moduleHead");
        headLayout.setWidth("100%");
        contentLayout.setStyleName("moduleContent");
        footLayout.setStyleName("moduleFoot");
        footLayout.setWidth("100%");
        moduleLayout.setStyleName("module");

        contentLayout.setHideEmptyRowsAndColumns(true);
        contentLayout.setSpacing(true);

        footLayout.setExpandRatio(placeholder, 1);
        footLayout.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
        moduleLayout.setComponentAlignment(footLayout, Alignment.TOP_RIGHT);


        //Add a resize listener.
        ui.getPage().addBrowserWindowResizeListener(l -> update());
    }

    private void createMaxBtn() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/max.png");
        if (f.exists()) {
            FileResource resource = new FileResource(f);
            maxBtn = new Image(null, resource);
            maxBtn.setStyleName("moduleIcon");
            maxBtn.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            maxBtn.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            maxBtn.setDescription("Weitere Kurse anzeigen.");
            maxBtn.addClickListener(event -> {
                showAll = true;
                update();
            });
        }
    }

    private void createMinBtn() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/min.png");
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

    private void createAddBtn() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/plus.png");
        if(f.exists()) {
            FileResource resource = new FileResource(f);
            addBtn = new Image(null, resource);
            addBtn.setStyleName("moduleIcon");
            addBtn.setWidth(ICON_SIZE, Sizeable.Unit.PIXELS);
            addBtn.setHeight(ICON_SIZE, Sizeable.Unit.PIXELS);
            addBtn.setDescription("Kurs hinzuf\u00fcgen");
            addBtn.setVisible(false);
            /*addtBtn.addClickListener(event -> { });*/
        }
    }

    private void createEmptyImg() {
        //TODO: EMPTY LIST
    }

    public void setName(String n) {
       name = n;
       update();
    }

    public void setData(ArrayList<DataObject> data) {
        this.data.clear();
        this.data.addAll(data);
        fillList();
        update();
    }

    public void showAddBtn(boolean addBtn) {
        this.showAdd = addBtn;
        update();
    }

    public void setMaxWidth(boolean maxWidth) {
        this.maxWidth = maxWidth;
    }

}
