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

    private final static int SPACING_SIZE = 43;
    private final static int COMPONENT_SIZE = 100;
    private final static int ICON_SIZE = 25;

    private String name;
    private int maxColumns;
    private boolean showAll;
    private boolean showAdd;

    private MyUI ui;
    private Label nameLabel;
    private Image addBtn;
    private Image maxBtn;
    private Image minBtn;
    private Image emptyImg;

    private HorizontalLayout headLayout;
    private GridLayout contentLayout;
    private HorizontalLayout footLayout;
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
        layout = new CssLayout();
        data = new ArrayList<>();

        createLayout();
    }


    private void calcLayout() {
        int columnCounter = 0;
        int width = (int) (ui.getPage().getBrowserWindowWidth() * (ui.getContentLayout().getWidth() / 100));
        while (width >= (COMPONENT_SIZE + SPACING_SIZE)) {
            width -= COMPONENT_SIZE + SPACING_SIZE;
            columnCounter++;
        }
        maxColumns = columnCounter;
    }

    private void update() {
        if (data.size() == 0) {
            //TODO: Aktion durchführen, wenn Liste leer ist.
        } else {
            calcLayout();
            nameLabel.setValue(name);

            ArrayList<Component> tmpComponent = new ArrayList<>();
            contentLayout.forEach(component -> tmpComponent.add(component));
            contentLayout.removeAllComponents();
            contentLayout.setColumns(maxColumns);

            if (data.size() <= maxColumns) {
                contentLayout.setColumns(data.size());
                showAll = false;
                minBtn.setVisible(false);
                maxBtn.setVisible(false);
                footLayout.setVisible(false);
                tmpComponent.forEach(c -> c.setVisible(true));

            } else if (showAll) {
                minBtn.setVisible(true);
                maxBtn.setVisible(false);
                footLayout.setVisible(true);
                tmpComponent.forEach(c -> c.setVisible(true));

            } else {

                int counter = 0;
                minBtn.setVisible(false);
                maxBtn.setVisible(true);
                footLayout.setVisible(true);

                for (Component c: tmpComponent) {
                    if (counter < maxColumns) {
                        counter++;
                        c.setVisible(true);
                    } else {
                        c.setVisible(false);
                    }
                }
            }
            addBtn.setVisible(showAdd);
            footLayout.setVisible(showAdd || minBtn.isVisible() || maxBtn.isVisible());
            tmpComponent.forEach(component -> contentLayout.addComponent(component));

        }
    }

    private void fillList() {

        data.forEach(data -> {

            //create images for the data
            Image img = new Image(null, data.getImage().getSource());
            img.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
            img.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
            img.setDescription(data.getName());
            img.setVisible(false);

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
            contentLayout.addComponent(img);
        });
    }

    private void createLayout() {

        calcLayout();


        //Create everything.
        nameLabel = new Label("");
        moduleLayout = new VerticalLayout();
        headLayout = new HorizontalLayout();
        contentLayout = new GridLayout(maxColumns + 1, 2);
        footLayout = new HorizontalLayout();

        createMaxBtn();
        createMinBtn();
        createAddBtn();
        createEmptyImg();



        //Put the layout together
        headLayout.addComponent(nameLabel);

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
        contentLayout.setStyleName("moduleContent");
        footLayout.setStyleName("moduleFoot");

        moduleLayout.setStyleName("module");


        footLayout.setSpacing(true);
        contentLayout.setSpacing(true);

        footLayout.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
        moduleLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        //moduleLayout.setComponentAlignment(footLayout, Alignment.MIDDLE_RIGHT);


        //Add a resize listener.
        ui.getPage().addBrowserWindowResizeListener(l -> {
            calcLayout();
            update();
        });
    }

    private void createMaxBtn() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/max.png");
        if (f.exists()) {
            FileResource resource = new FileResource(f);
            maxBtn = new Image(null, resource);
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

}
