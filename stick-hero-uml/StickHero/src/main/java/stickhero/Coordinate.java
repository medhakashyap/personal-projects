package stickhero;

public class Coordinate {
    // attributes
    private int xCoordinate;
    private int yCoordinate;

    // constructor
    public Coordinate(int x, int y){
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    // getters
    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    // setters
    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
