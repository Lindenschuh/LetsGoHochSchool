package controller.module;

import com.vaadin.client.debug.internal.Icon;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import model.Achievement;
import model.User;

/**
 * Created by Aljos on 25.06.2016.
 */
public class AchievementProgressModul extends Modul {

    private static final int ICON_SIZE = 20;
    private static final int IMAGE_SIZE = 100;

    private Achievement achievement;
    private int tempAdded;
    private boolean temporary;
    private boolean trashEnabled;

    private HorizontalLayout contentLayout;
    private VerticalLayout descriptionLayout;
    private HorizontalLayout progressLayout;

    private Image userImage;
    private Label userName;
    private ProgressBar userProgress;
    private Label progressLbl;
    private NativeButton addButton;
    private NativeButton trashButon;





    public AchievementProgressModul(User user, Achievement achievement, boolean finished) {
        super(user);
        this.achievement = achievement;

        contentLayout = new HorizontalLayout();
        contentLayout.addStyleName("moduleContent");
        layout.addComponent(contentLayout);

        userImage = user.getImage();
        userImage.setWidth(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        userImage.setHeight(IMAGE_SIZE, Sizeable.Unit.PIXELS);
        contentLayout.addComponent(userImage);

        if(!finished) {
            descriptionLayout = new VerticalLayout();
            descriptionLayout.addStyleName("descriptionLayout");
            contentLayout.addComponent(descriptionLayout);

            userName = new Label(user.getName());
            userName.addStyleName("descriptionLecture");
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
            addButton.addStyleName("borderlessButton");
            addButton.addClickListener(e ->  {
                add();
            });
            progressLayout.addComponent(addButton);

            proofTemp();

            setUpProgressBar();


        } else {
            descriptionLayout = new VerticalLayout();
            descriptionLayout.addStyleName("descriptionLayout");
            contentLayout.addComponent(descriptionLayout);

            userName = new Label(user.getName());
            userName.addStyleName("descriptionLecture");
            contentLayout.addComponent(userName);
        }

    }

    private void setUpProgressBar() {
        int currentValue = achievement.getUserProgress().get(user);
        userProgress.setValue((float) currentValue / achievement.getMaxValue());
        progressLbl.setValue(achievement.getUserProgress().get(user).toString() + "/" + achievement.getMaxValue());

    }

    private void add() {
        int temp = achievement.getUserProgress().get(user);
        if(temp < achievement.getMaxValue()) {
            temporary = true;
            tempAdded++;
            achievement.getUserProgress().put(user, ++temp);
        }
        setUpProgressBar();
        proofTemp();
    }

    private void trash() {
        int temp = achievement.getUserProgress().get(user);
        temporary = false;
        achievement.getUserProgress().put(user, temp - tempAdded);
        setUpProgressBar();
        proofTemp();
        tempAdded = 0;
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
    }
}
