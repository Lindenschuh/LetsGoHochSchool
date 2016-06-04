package controller;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import model.Achievement;
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
public abstract class AchievementsGallery extends Modul{


    private final static int COMPONENT_SIZE = 100;
    private final static int SPACING_SIZE = 43;

    private String name;
    private int maxColumns;
    private boolean showAll;
    int componentCounter;

    private MyUI ui;
    protected User user;
    private Label nameLabel;
    private Image moreAchievementsImg;

    private GridLayout contentLayout;
    private VerticalLayout moduleLayout;

    private ArrayList<Achievement> achievements;
    private ArrayList<Image> achievementImage;
    private ArrayList<CustomLayout> achievementLabel;

    //TODO


    public AchievementsGallery(User user, MyUI ui, String name) {
        super(user);
        this.user = user;
        this.name = name;
        this.ui = ui;

        showAll = false;
        layout = new CssLayout();

        achievements = new ArrayList<>();
        achievementImage = new ArrayList<>();
        achievementLabel = new ArrayList<>();

        createLayout();
    }

    protected void calcLayoutSize() {
        int columnCounter = 0;
        int width = (int) (ui.getPage().getBrowserWindowWidth() * (ui.getContentLayout().getWidth() / 100));

        while(width > 0) {
            width -= COMPONENT_SIZE + SPACING_SIZE;
            columnCounter++;
        }
        maxColumns = columnCounter - 1;
    }

    protected void createLayout() {
        calcLayoutSize();

        ui.getPage().addBrowserWindowResizeListener(l -> {
            calcLayoutSize();
            update();
        });

        moduleLayout = new VerticalLayout();
        contentLayout = new GridLayout();

        contentLayout = new GridLayout(maxColumns , 1);
        contentLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        nameLabel = new Label(getName());
        nameLabel.setStyleName("h3");
        moduleLayout.addComponent(nameLabel);
        moduleLayout.addComponent(contentLayout);

        File f = new File(Paths.get("").toAbsolutePath().toString() + "/Resource/Images/Icons/ellipsis-h.png");

        if(f.exists()) {
            FileResource resource = new FileResource(f);
            moreAchievementsImg = new Image(null, resource);
            moreAchievementsImg.setDescription("Alle Erfolge anzeigen.");
            moreAchievementsImg.addClickListener( event -> {
                showAll = !showAll;
                updateLayout();
            });
        }

        moduleLayout.setMargin(true);
        contentLayout.setSpacing(true);
        layout.addComponents(moduleLayout);
    }

    protected void updateLayout (){
        if(contentLayout != null) {
            contentLayout.removeAllComponents();
            achievementImage.clear();

            achievements.forEach( achievement -> {
                File f = new File(Paths.get("").toAbsolutePath().toString()
                        + "/Resource/Images/Achievements/" + achievement.getName() +".png");

                if(f.exists()) {
                    FileResource resource = new FileResource(f);
                    Image tmpImage = new Image(null, resource);
                    tmpImage.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                    tmpImage.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                    tmpImage.setDescription(achievement.getName());
                    achievementImage.add(tmpImage);
                } else {
                    try {
                        CustomLayout customLayout = new CustomLayout(
                                new ByteArrayInputStream(
                                        ("<p> <center>" + achievement.getName() +  "</center> </p>").getBytes()));
                        customLayout.setWidth(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                        customLayout.setHeight(COMPONENT_SIZE, Sizeable.Unit.PIXELS);
                        achievementLabel.add(customLayout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });

            componentCounter = 0;

            if(showAll) {
                contentLayout.setColumns(maxColumns - 1);
            } else {
                contentLayout.setColumns(maxColumns);
                contentLayout.setRows(1);
            }

            achievementImage.forEach(img -> {
                if( contentLayout.getCursorX() < maxColumns - 1|| showAll){
                    contentLayout.addComponent(img);
                    componentCounter++;
                }
            });


            if (achievementImage.size() == maxColumns) {
                //contentLayout.addComponent(new Label(Integer.toString(achievementImage.size())));
                contentLayout.addComponent(achievementImage.get(maxColumns - 1));
            } else if (achievementImage.size() > maxColumns) {
                contentLayout.addComponent(moreAchievementsImg);
                contentLayout.setComponentAlignment(moreAchievementsImg, Alignment.BOTTOM_RIGHT);
            }
        }
    }

    public abstract void update ();


    public String getName() {
        return name;
}

    public void setName(String name) {
        this.name = name;
        nameLabel.setValue(this.name);
    }

    public ArrayList<Achievement> getAchievements() {
        return achievements;
    }

    protected void setAchievements(ArrayList<Achievement> achievements) {
        this.achievements.clear();
        this.achievements.addAll(achievements);
    }

}
