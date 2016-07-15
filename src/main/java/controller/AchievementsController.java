package controller;

import com.vaadin.ui.VerticalLayout;
import controller.module.GalleryModul;
import controller.module.Modul;
import model.Achievement;
import model.User;
import util.Master;
import view.MyUI;

import java.util.ArrayList;

/**
 * The achievement page.
 * Admin gets a list of all achievements sorted by the courses.
 * Users get a list of open and finished achievements.
 * @author Andreas Reinsch (193790).
 * @version 0.1
 */
public class AchievementsController extends Modul {

    private VerticalLayout moduleLayout;

    public AchievementsController(User user, MyUI ui) {
        super(user);
        moduleLayout = new VerticalLayout();

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


    /**
     * Create the layout for a admin.
     * @param user The current user.
     * @param ui The my ui.
     */
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
                if(achievement.getCourse().equals(c) ) {
                    achievements.add(achievement);
                }
            });
            courseAchievementsGallery.setData((ArrayList) achievements);
            moduleLayout.addComponent(courseAchievementsGallery.getContent());
        });
    }

    /**
     * Create the layout for a normal user.
     * @param user The current user.
     * @param ui The my ui.
     */
    private void createUserLayout(User user, MyUI ui){
        ArrayList<Achievement> openAchievements = new ArrayList<>();
        ArrayList<Achievement> finishedAchievements = new ArrayList<>();

        GalleryModul openAchievementsGallery = new GalleryModul(user, ui);
        GalleryModul finishedAchievementsGallery = new GalleryModul(user, ui);

        openAchievementsGallery.setName("Offene Erfolge");
        openAchievementsGallery.addItemClickedListener(e ->
                ui.setContentPage(new AchievementDetailController(user, ui, (Achievement) e)));
        openAchievementsGallery.setEmptyMsg("Keine Erfolge vorhanden.");
        openAchievementsGallery.setMaxWidth(true);
        finishedAchievementsGallery.setName("Abgeschlossene Erfolge");
        finishedAchievementsGallery.addItemClickedListener(e ->
                ui.setContentPage(new AchievementDetailController(user, ui, (Achievement) e)));
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