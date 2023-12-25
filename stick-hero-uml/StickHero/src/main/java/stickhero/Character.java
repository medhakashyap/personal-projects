package stickhero;

public class Character {
    private String name;
    private boolean isAlive;
    private Coordinate coordinate;

    // constructor
    public Character(String name, boolean isAlive, Coordinate coordinate){
        this.name = name;
        this.isAlive = isAlive;
        this.coordinate = coordinate;
    }
    // getters
    public String getName() {
        return name;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void moveForward(){

    }
    public void die(){

    }
    public void flipCharacter(Character ch){

    }
}
