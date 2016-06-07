package controller.module;

import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
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

    private GridLayout contentLayout;
    private HorizontalLayout btnLayout;
    private VerticalLayout moduleLayout;

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


    private void calcLayoutSize() {
        int columnCounter = 0;
        int width = (int) (ui.getPage().getBrowserWindowWidth() * (ui.getContentLayout().getWidth() / 100));
        while (width >= (COMPONENT_SIZE + SPACING_SIZE)) {
            width -= COMPONENT_SIZE + SPACING_SIZE;
            columnCounter++;
        }
        maxColumns = columnCounter;
    }

    private void update() {
        if(data != null) {
            if (data.size() == 0) {
                //TODO: Aktion durchführen, wenn Liste leer ist.
            } else {
                contentLayout.removeAllComponents();

                calcLayoutSize();
                nameLabel.setValue(name);

                if (data.size() < maxColumns) {
                    contentLayout.setColumns(data.size());
                } else {
                    contentLayout.setColumns(maxColumns);
                }

                data.forEach(dataBean -> {
                    if (contentLayout.getCursorY() == 0 || showAll) {
                        Image img = dataBean.getImage();
                        img.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                        img.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                        contentLayout.addComponent(img);
                    }
                });

                if (!showAll) {
                    contentLayout.setRows(2);
                } else {
                    contentLayout.setRows(contentLayout.getCursorY() + 1);
                }

                addBtn.setVisible(showAdd);

                if (data.size() <= maxColumns) {
                    maxBtn.setVisible(false);
                } else {
                    maxBtn.setVisible(!showAll);
                }
            }
        }
    }

    private void createLayout() {
        nameLabel = new Label("");

        calcLayoutSize();
        createMaxBtn();
        createMinBtn();
        createAddBtn();
        createEmptyImg();

        ui.getPage().addBrowserWindowResizeListener(l -> {
            calcLayoutSize();
            update();
        });

        moduleLayout = new VerticalLayout();
        contentLayout = new GridLayout(maxColumns + 1, 2);
        btnLayout = new HorizontalLayout();

        btnLayout.setDefaultComponentAlignment(Alignment.TOP_RIGHT);
        btnLayout.addComponent(addBtn);
        btnLayout.addComponent(minBtn);
        btnLayout.addComponent(maxBtn);


        contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);


        nameLabel.setStyleName("h3");
        moduleLayout.addComponent(nameLabel);
        moduleLayout.addComponent(contentLayout);
        moduleLayout.addComponent(btnLayout);
        moduleLayout.setComponentAlignment(btnLayout, Alignment.MIDDLE_RIGHT);

        moduleLayout.setMargin(true);
        contentLayout.setSpacing(true);
        btnLayout.setSpacing(true);
        layout.addComponents(moduleLayout);
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
                maxBtn.setVisible(false);
                minBtn.setVisible(true);
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
                minBtn.setVisible(false);
                maxBtn.setVisible(true);
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
        update();
    }

    public void showAddBtn(boolean addBtn) {
        this.showAdd = addBtn;
        update();
    }

}
