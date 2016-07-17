package controller.module;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import controller.AchievementDetailController;
import model.Achievement;
import model.User;
import view.MyUI;

/**
 * Created by Aljos on 25.06.2016.
 */
public class AchievementProgressModul extends Modul {

    private static final int ICON_SIZE = 20;
    private static final int IMAGE_SIZE = 100;

    private MyUI ui;
    private Achievement achievement;
    private int tempAdded;
    private boolean temporary;
    private boolean trashEnabled;
    private boolean deleteUser;
    private AchievementDetailController controller;


    private HorizontalLayout contentLayout;
    private VerticalLayout descriptionLayout;
    private HorizontalLayout progressLayout;

    private Image userImage;
    private Label userName;
    private ProgressBar userProgress;
    private Label progressLbl;
    private NativeButton addButton;
    private NativeButton trashButon;
    private NativeButton deleteUserButton;





    public AchievementProgressModul(User user, MyUI ui, Achievement achievement, boolean finished, AchievementDetailController controller) {
        super(user);
        this.ui = ui;
        this.achievement = achievement;
        this.controller = controller;

        contentLayout = new HorizontalLayout();
        contentLayout.setStyleName("moduleContent");
        layout.addComponent(contentLayout);

        userImage = user.getImage();
        userImage.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        userImage.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        contentLayout.addComponent(userImage);

        if(!finished) {
            descriptionLayout = new VerticalLayout();
            descriptionLayout.setStyleName("descriptionLayout");
            contentLayout.addComponent(descriptionLayout);

            userName = new Label(user.getName());
            userName.setStyleName("descriptionLecture");
            descriptionLayout.addComponent(userName);

            progressLayout = new HorizontalLayout();
            progressLayout.setSpacing(true);
            descriptionLayout.addComponent(progressLayout);

            userProgress = new ProgressBar(0.5f);
            progressLayout.addComponent(userProgress);

            progressLbl = new Label(achievement.getUserProgress().get(user).toString() + "/" + achievement.getMaxValue());
            progressLayout.addComponent(progressLbl);

            addButton = new NativeButton();
            addButton.setIcon(FontAwesome.PLUS);
            addButton.setStyleName("borderlessButton");
            addButton.addClickListener(e ->  {
                add();
            });
            progressLayout.addComponent(addButton);

            proofTemp();

            setUpProgressBar();


        } else {
            contentLayout.setWidth(Integer.toString(calcWidth()) + "px");

            descriptionLayout = new VerticalLayout();


            descriptionLayout.setStyleName("descriptionLayout");
            contentLayout.addComponent(descriptionLayout);
            contentLayout.setExpandRatio(descriptionLayout, 1);

            progressLayout = new HorizontalLayout();
            progressLayout.setSpacing(true);
            descriptionLayout.addComponent(progressLayout);

            userName = new Label(user.getName());
            userName.setStyleName("descriptionLecture");
            progressLayout.addComponent(userName);
        }

    }

    private void setUpProgressBar() {
        int currentValue = achievement.getUserProgress().get(user) + tempAdded;
        userProgress.setValue((float) currentValue / achievement.getMaxValue());
        progressLbl.setValue(currentValue + "/" + achievement.getMaxValue());

    }

    private void add() {
        int temp = achievement.getUserProgress().get(user) + tempAdded;
        if(temp < achievement.getMaxValue()) {
            temporary = true;
            tempAdded++;
        }
        setUpProgressBar();
        proofTemp();
    }

    public void trash() {
        temporary = false;
        tempAdded = 0;
        setUpProgressBar();
        proofTemp();

    }

    private void proofTemp() {
        if(temporary && !trashEnabled) {
            trashButon = new NativeButton();
            trashButon.setIcon(FontAwesome.TRASH);
            trashButon.setStyleName("borderlessButton");
            trashButon.addClickListener(e -> {
                trash();
            });
            trashEnabled = true;
            progressLayout.addComponent(trashButon);
        } else if(!temporary && trashEnabled) {
            progressLayout.removeComponent(trashButon);
            trashEnabled = false;
        }
        controller.setOpenButtonLayout();
    }

    public void setupDelete() {
        deleteUserButton = new NativeButton();
        deleteUserButton.setIcon(FontAwesome.TIMES);
        deleteUserButton.setStyleName("borderlessButton");
        deleteUserButton.addClickListener(e -> {
            if (!deleteUser) {
                deleteUser = true;
                contentLayout.addStyleName("AlertModuleContent");
                deleteUserButton.setIcon(FontAwesome.REPLY);
            } else {
                deleteUser = false;
                contentLayout.removeStyleName("AlertModuleContent");
                deleteUserButton.setIcon(FontAwesome.TIMES);
            }
        });
        progressLayout.addComponent(deleteUserButton);
    }

    public void removeDelete() {
        progressLayout.removeComponent(deleteUserButton);
        if(deleteUser)
            contentLayout.removeStyleName("AlertModuleContent");
    }

    public void removeFinishedUser() {
        if(deleteUser) {
            achievement.getUserFinished().remove(user);
            achievement.addUser(user);
        }
    }


    public boolean getDeleteUser() { return this.deleteUser; }

    public boolean getTemp() { return this.temporary; }

    public void saveData() {
        int old = achievement.getUserProgress().get(user);
        achievement.getUserProgress().put(user, old + tempAdded);
        tempAdded = 0;
    }

    public User getUser() { return this.user;  }

    public int calcWidth() {
        int naviWidth = 180;
        int pagePadding = 40;
        int modulePadding = 15;

        return ui.getPage().getBrowserWindowWidth() - (naviWidth + 2 * pagePadding + 2 * modulePadding);

    }
}
