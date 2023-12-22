package stickhero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
public class Coordinates implements WalkAssist{
    private static Coordinates coordinates;
    private double[] stickCords;
    private ArrayList<double[]> blockCords;
    private ArrayList<Double> perfectCords;

    public static void setCoordinates() {
        Coordinates.coordinates = null;
    }

    public Coordinates() {
        this.stickCords = new double[]{0,0};
        this.blockCords = new ArrayList<>();
        this.perfectCords=new ArrayList<>();
    }
    public static Coordinates getCoordinates() {
        if (coordinates == null) {
            coordinates = new Coordinates();
        }
        return coordinates;
    }



    public double[] getStickCords() {
        return stickCords;
    }

    public void setStickCords(double start, double end) {
        //System.out.println("Setting Stick: "+start+" "+end);
        this.stickCords[0]=start;
        this.stickCords[1]=end;
    }
    public void addBlockCords(double start, double end){
        //System.out.println("ok");
        double[] a={start,end};
        blockCords.add(a);
        perfectCords.add((start+end)/2);
    }

    public ArrayList<double[]> getBlockCords() {
        return blockCords;
    }

    @Override
    public void shift() throws ObjectTransitionException {
        this.stickCords[0] -= Constants.WALK_PACE_PIXEL;
        this.stickCords[1] -= Constants.WALK_PACE_PIXEL;

        Iterator<double[]> iterator = this.blockCords.iterator();
        while (iterator.hasNext()) {
            double[] block = iterator.next();
            block[0] -= Constants.WALK_PACE_PIXEL;
            block[1] -= Constants.WALK_PACE_PIXEL;

            if (block[1] < 0) {
                iterator.remove();
                GameHandler.score++;
                for (double[] d: this.blockCords){
                    if (d[0]<Constants.BASE_X-20){
                        GameHandler.score++;
                    }
                }
            }
        }
    }
    public boolean checkGround(){
        for (double[] d: this.blockCords){
            if (d[0]<180 && d[1]>=200){
                return true;
            }
        }
        return false;
    }
    public boolean perfectHit(double hit){
        for (double d: perfectCords){
            if ((hit>d-Constants.PERFECT_THICKNESS) && (hit<d+Constants.PERFECT_THICKNESS)){
                return true;
            }
        }
        return false;
    }
    public void printCords(){
        System.out.println(Arrays.toString(this.stickCords));
        System.out.println(blockCords);
        for(double[] d:this.blockCords ){
            System.out.println("Block " +blockCords.indexOf(d) + Arrays.toString(d));
        }
    }
}
