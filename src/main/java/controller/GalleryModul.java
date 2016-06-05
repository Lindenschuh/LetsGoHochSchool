package controller;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import controller.Modul;
import model.User;
import view.MyUI;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class GalleryModul<T> extends Modul {

    private final static int SPACING_SIZE = 43;
    private final static int COMPONENT_SIZE = 100;

    private String name;
    private int maxColumns;
    private boolean showAll;
    private boolean showAdd;

    private MyUI ui;
    private Label nameLabel;
    private Image defaultImg;
    private Image addObjectBtn;
    private Image allObjectsBtn;

    private GridLayout contentLayout;
    private VerticalLayout moduleLayout;

    private ArrayList<T> data;
    private ArrayList<Image> objectImgs;


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
        objectImgs = new ArrayList<>();

        createLayout();
    }



    private void calcLayoutSize() {
        int columnCounter = 0;
        int width = (int) (ui.getPage().getBrowserWindowWidth() * (ui.getContentLayout().getWidth() / 100));

        while(width > 0) {
            width -= COMPONENT_SIZE + SPACING_SIZE;
            columnCounter++;
        }
        maxColumns = columnCounter - 1;
    }


    private void update() {
        if(data != null) {
            objectImgs.clear();
            contentLayout.removeAllComponents();

            calcLayoutSize();
            createDefaultImage();
            nameLabel.setValue(name);

            data.forEach(dataBean -> {

                String className = dataBean.getClass().getName();
                className = className.substring(className.indexOf('.') + 1);

                File f = new File(Paths.get("").toAbsolutePath().toString()
                        + "/Resource/Images/" + className + "/" + dataBean.toString() +".png");

                if (f.exists()) {
                    FileResource resource = new FileResource(f);
                    Image tmpImage = new Image(null, resource);
                    tmpImage.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                    tmpImage.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                    tmpImage.setDescription(dataBean.toString());
                    objectImgs.add(tmpImage);
                } else {
                    defaultImg.setDescription(dataBean.toString());
                    objectImgs.add(defaultImg);
                }
            });

            if(showAll) {
                contentLayout.setColumns(maxColumns - 1);
            } else {
                contentLayout.setColumns(maxColumns);
                contentLayout.setRows(1);
            }

            //Add courses
            objectImgs.forEach(img -> {
                int maxComponents = maxColumns - 1;

                if( contentLayout.getCursorX() < maxComponents || showAll){
                    contentLayout.addComponent(img);
                }
            });

            //Button management
            if (objectImgs.size() < maxColumns && user.isAdmin() && showAdd) {
                contentLayout.addComponent(addObjectBtn);

            } else if (objectImgs.size() == maxColumns) {
                if (user.isAdmin()) {
                    contentLayout.addComponent(allObjectsBtn);
                    contentLayout.setComponentAlignment(allObjectsBtn, Alignment.BOTTOM_LEFT);
                } else {
                    contentLayout.addComponent(objectImgs.get(objectImgs.size() - 1));
                }

            } else if (objectImgs.size() > maxColumns) {
                if (showAll && user.isAdmin() && showAdd) {
                    contentLayout.addComponent(addObjectBtn);
                }
                contentLayout.addComponent(allObjectsBtn);
                contentLayout.setComponentAlignment(allObjectsBtn, Alignment.BOTTOM_LEFT);
            }

        }
    }


    private void createLayout() {

        calcLayoutSize();
        createAddButton();
        createShowAllButton();

        ui.getPage().addBrowserWindowResizeListener(l -> {
            calcLayoutSize();
            update();
        });

        moduleLayout = new VerticalLayout();
        contentLayout = new GridLayout(maxColumns + 1, 1);
        contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        nameLabel = new Label("");
        nameLabel.setStyleName("h3");
        moduleLayout.addComponent(nameLabel);
        moduleLayout.addComponent(contentLayout);

        moduleLayout.setMargin(true);
        contentLayout.setSpacing(true);
        layout.addComponents(moduleLayout);
    }

    private void createShowAllButton() {
        File f = new File(Paths.get("").toAbsolutePath().toString()
                + "/Resource/Images/Icons/ellipsis-h.png");
        if(f.exists()) {
            FileResource resource = new FileResource(f);
            allObjectsBtn = new Image(null, resource);
            allObjectsBtn.setDescription("Neuen Kurs erstellen.");
            allObjectsBtn.addClickListener(event -> {
                showAll = !showAll;
                update();
            });
        }
    }

    private void createAddButton() {
        if(user.isAdmin()) {
            File f = new File(Paths.get("").toAbsolutePath().toString()
                    + "/Resource/Images/Icons/add.jpg");
            if(f.exists()) {
                FileResource resource = new FileResource(f);
                addObjectBtn = new Image(null, resource);
                addObjectBtn.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                addObjectBtn.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                addObjectBtn.setDescription("Kurs hinzufÃ¼gen");
                addObjectBtn.addClickListener(event -> {
                    //showAll = !showAll;
                    //update();
                });
            }

        }
    }

    private void createDefaultImage() {
        if(data != null && data.size() > 0) {

            String className = data.get(0).getClass().getName();
            className = className.substring(className.indexOf('.') + 1);

            File f = new File(Paths.get("").toAbsolutePath().toString()
                    + "/Resource/Images/" + className + "/default.png");

            if(f.exists()) {
                FileResource resource = new FileResource(f);
                defaultImg = new Image(null, resource);
                defaultImg.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                defaultImg.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                allObjectsBtn.addClickListener(event -> {
                    //update();
                });
            }
        }
    }

    public void setName(String n) {
       name = n;
       update();
    }

    public void setData(ArrayList<T> data) {
        this.data.clear();
        this.data.addAll(data);
        update();
    }

    public void showAddBtn(boolean addBtn) {
        this.showAdd = addBtn;
        update();
    }

}
