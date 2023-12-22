package stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Cherry implements WalkAssist{


    private static ImageView cherry;
    private boolean up;
    public Cherry() {
        cherry=null;
    }

    public ImageView getCherry() {
        return cherry;
    }

    public boolean isUp() {
        return up;
    }

    public void spawnCherry(Pane pane, double x){
        ImageView cherryView = new ImageView(new Image("/cherry.png"));
        cherryView.setFitWidth(20);
        cherryView.setFitWidth(20);
        pane.getChildren().add(cherryView);
        boolean flag=true;
        for (double[] d:Coordinates.getCoordinates().getBlockCords()){
            if (!(d[0]-10>x && d[1]+10<x)){
                flag=false;
            }
        }
        if(flag){
            double r=Math.random();
            if (r<0.65){
                cherryView.setLayoutX(x);
                cherryView.setLayoutY(270);
                up=true;
            }
            else {
                cherryView.setLayoutX(x);
                cherryView.setLayoutY(310);
                up=false;
            }
            cherry=cherryView;
        }
    }
    public boolean eatCherry(Hero hero){
        if(cherry!=null) {
            if (cherry.getLayoutX() == Constants.BASE_X) {
                boolean flag = this.isUp() == hero.isUp();
                cherry.setVisible(false);
                cherry = null;
                return flag;
            }
        }
        else {
            return false;
        };
        return false;
    }
    @Override
    public void shift() throws ObjectTransitionException {
        if(cherry!=null) {
            cherry.setLayoutX(cherry.getLayoutX() - Constants.WALK_PACE_PIXEL);
        }
    }
}
