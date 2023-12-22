package stickhero;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Hero implements WalkAssist{
    private Character character;
    private boolean life;
    private boolean up;
    private ImageView still;
    private Group running;
    private double[] runningParameters;
    private double[] stillParameters;
    private Pane pane;
    public Hero(Character character, Pane pane) {
        this.character = character;
        this.life=true;
        this.up=false;
        this.still=new ImageView(character.getStillImage());
        this.running=character.getRunningGroup();
        this.pane=pane;
        this.runningParameters =character.getRunningParameters();
        this.stillParameters=character.getStillParameters();
    }
    public void addToPane(){
        double width=60; double height=70;
        this.pane.getChildren().add(this.still);
        this.still.setFitHeight(height);
        this.still.setFitWidth(width);
        this.still.setLayoutX(Constants.BASE_X-width);
        this.still.setLayoutY(Constants.BASE_Y-height);
    }
    public Character getCharacter() {
        return character;
    }

    public double[] getRunningParameters() {
        return runningParameters;
    }

    public double[] getStillParameters() {
        return stillParameters;
    }

    public boolean isAlive() {
        return life;
    }

    public boolean isUp() {
        return up;
    }

    public ImageView getStill() {
        return still;
    }

    public Group getRunning() {
        return running;
    }

    public Pane getPane() {
        return pane;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    public void toggle(boolean x) {
        this.up = x;
    }

    public void setStill(ImageView still) {
        this.still = still;
    }

    public void setRunning(Group running) {
        this.running = running;
    }

    @Override
    public void shift() throws ObjectTransitionException {
        pane.getChildren().remove(this.still);
        pane.getChildren().add(running);
    }
}
