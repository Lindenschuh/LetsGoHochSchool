package controller;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.VerticalLayout;
import controller.module.GalleryModul;
import controller.module.Modul;
import model.Achievement;
import model.User;
import util.Master;
import view.MyUI;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class AchievementsController extends Modul {

    private static final int COMPONENT_SIZE = 100;

    private VerticalLayout moduleLayout;

    public AchievementsController(User user, MyUI ui) {
        super(user);
        moduleLayout = new VerticalLayout();
        createLayout(user, ui);
    }

    private void createLayout(User user, MyUI ui) {
        if(user.isAdmin()) {
            createAdminLayout(user, ui);
        } else {
            createUserLayout(user, ui);
        }
        moduleLayout.setStyleName("page");
        moduleLayout.setSpacing(true);

        layout.setWidth("100%");
        layout.addComponent(moduleLayout);
    }


    private void createAdminLayout(User user, MyUI ui) {
        user.getCourses().forEach(c -> {
            ArrayList<Achievement> achievements = new ArrayList<>();
            GalleryModul courseAchievementsGallery = new GalleryModul(user, ui);

            courseAchievementsGallery.setName(c.getName());
            courseAchievementsGallery.addItemClickedListener(e ->
                    ui.setContentPage(new AchievementDetailController(user, ui,(Achievement) e)));
            courseAchievementsGallery.addButtonClickedListener(() -> System.out.println("Add button clicked."));
            courseAchievementsGallery.setMaxWidth(true);
            courseAchievementsGallery.setEmptyMsg("Keine Erfolge vorhanden.");

            Master.allAchievements.forEach(achievement -> {
                if(achievement.getCourse() == c) {
                    achievements.add(achievement);
                }
            });
            courseAchievementsGallery.setData((ArrayList) achievements);
            moduleLayout.addComponent(courseAchievementsGallery.getContent());
        });
    }

    private void createUserLayout(User user, MyUI ui){
        ArrayList<Achievement> openAchievements = new ArrayList<>();
        ArrayList<Achievement> finishedAchievements = new ArrayList<>();

        GalleryModul openAchievementsGallery = new GalleryModul(user, ui);
        GalleryModul finishedAchievementsGallery = new GalleryModul(user, ui);

        openAchievementsGallery.setName("Offene Erfolge");
        openAchievementsGallery.setEmptyMsg("Keine Erfolge vorhanden.");
        openAchievementsGallery.setMaxWidth(true);
        finishedAchievementsGallery.setName("Abgeschlossene Erfolge");
        finishedAchievementsGallery.setEmptyMsg("Keine Erfolge vorhanden.");
        finishedAchievementsGallery.setMaxWidth(true);

        user.getFinishedAchievment().forEach(fin -> finishedAchievements.add(fin));
        user.getWorkingOnAchievment().forEach(work -> openAchievements.add(work));

        openAchievementsGallery.setData((ArrayList) openAchievements);
        finishedAchievementsGallery.setData((ArrayList) finishedAchievements);

        moduleLayout.addComponent(openAchievementsGallery.getContent());
        moduleLayout.addComponent(finishedAchievementsGallery.getContent());
    }



}