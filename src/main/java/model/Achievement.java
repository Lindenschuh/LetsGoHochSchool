package model;

/**
 * Created by Lars on 02.06.2016.
 */
public class Achievement {

    private String name;
    private boolean finished;

    //TODO: Bedinugen aufbauen (?)


    public Achievement(String name) {
        this.name = name;
        this.finished = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
