package stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Stick implements WalkAssist{
    public static void setSTICK() {
        Stick.STICK = null;
    }

    private static Stick STICK;

    private Line stick;
    private int orientation; // 0: vertical, 1: rotating, 2: horizontal
    private boolean lock;    //lock for scene shift
    private boolean growLock; //lock for growing
    private boolean rotateLock; //lock for rotating
    private Pane pane;
    private double length;
    private double angle;
    final private double STARTX,STARTY;
    // Private constructor to prevent instantiation
    private Stick(Pane pane) {
        // Initialize default values or provide specific initial values
        this.STARTX=Constants.BASE_X-3;
        this.STARTY=Constants.BASE_Y+3;
        this.orientation = 0;
        this.lock = true;
        this.growLock=false;
        this.rotateLock=true;
        this.length=0;
        this.angle=0;

        // Initialize the Line
        this.stick = new Line(STARTX,STARTY,STARTX,STARTY);
        Color color = Color.web("#874D25");
        this.stick.setStroke(color);
        pane.getChildren().add(this.stick);
        this.stick.setStrokeWidth(6.0);
    }

    // Public method to get the singleton instance
    public static Stick getInstance(Pane pane) {
        if (STICK == null) {
            STICK = new Stick(pane);
        }
        return STICK;
    }
    public void growStick() {
        Timeline timeline=new Timeline();
        KeyFrame keyFrame=new KeyFrame(Duration.millis(Constants.STICK_GROW_PACE), actionEvent -> {
            if (this.length < 295 && !this.growLock) {
                //this.stick.setVisible(true);
                stick.setEndY(stick.getEndY() - Constants.STICK_GROW_PIXEL);
                this.rotateLock=false;
                this.length+=Constants.STICK_GROW_PIXEL;
            }
            else {
                timeline.pause();
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public boolean isGrowLock() {
        return growLock;
    }

    public void rotateStick() {
        if (!rotateLock) {
            this.growLock = true;
            Rotate rotate = new Rotate();
            rotate.setPivotX(this.STARTX);
            rotate.setPivotY(this.STARTY);
            rotate.setAngle(0.1);
            KeyFrame rotateFrame = new KeyFrame(Duration.millis(0.5), actionEvent -> {
                stick.getTransforms().add(rotate);
                angle += 0.1;
                if (angle >= 90) {
                    this.rotateLock = true;
                    unlock();
                }
            });
            Timeline timeline = new Timeline(rotateFrame);
            timeline.setCycleCount(901);
            timeline.play();
        }
    }

    public void resetStick(){
        this.stick.setEndX(STARTX);
        this.stick.setEndY(STARTY);
        this.stick.setStartX(STARTX);
        this.stick.setStartY(STARTY);
        this.length=0;
        this.angle=0;
        this.growLock=false;
        this.rotateLock=true;
        this.stick.getTransforms().clear();
    }

    public boolean isLock() {
        return lock;
    }

    public boolean isRotateLock() {
        return rotateLock;
    }

    public void unlock() {
        if(this.angle>=90) {
            this.lock = (!this.lock);
            this.orientation = 2;
        }
    }
    public void setLock(){
        this.lock=true;
        this.orientation=0;
    }

    public void setGrowLock(boolean growLock) {
        this.growLock = growLock;
    }

    public double getLength() {
        return length;
    }

    @Override
    public void shift() throws ObjectTransitionException {
        if ((!this.lock) && this.orientation==2) {
            double shiftAmount = Constants.WALK_PACE_PIXEL;
            // Shift both start and end X coordinates by the specified amount

            stick.setStartY(stick.getStartY() + shiftAmount);
            stick.setEndY(stick.getEndY() + shiftAmount);

        }
        else {
            System.out.println("Lock: "+ this.lock);
            System.out.print("Orientation state: "+ this.orientation);
            throw new ObjectTransitionException("Cannot move Stick in current state");
        }
    }
    public void printDetails(){
        System.out.println("Start "+STARTX +" "+STARTY);
        System.out.println("Current");
        System.out.println(this.stick.getStartX()+" "+ this.stick.getStartY());
        System.out.println(this.stick.getEndX()+" "+this.stick.getEndY());
    }
}

