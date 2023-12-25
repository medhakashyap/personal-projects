package stickhero;

public class Game {
    // attributes
    private String state;
    private int totalScore;
    private int collectedCherries;
    private int id;

    private Character character;
    // constructor
    public Game(String state, int totalScore, int collectedCherries, int id, Character character){
        this.state = state;
        this.totalScore = totalScore;
        this.collectedCherries = collectedCherries;
        this.id = id;
        this.character = character;
    }

    // getter functions
    public String getState(){return this.state;}
    public int getTotalScore(){return this.totalScore;}
    public int getCollectedCherries(){return this.collectedCherries;}
    public int getId(){return this.id;}

    public Character getCharacter() {
        return character;
    }

    // setter functions
    public void setState(String state){this.state = state;}
    public void setTotalScore(int totalScore){this.totalScore = totalScore;}
    public void setCollectedCherries(int collectedCherries){this.collectedCherries = collectedCherries;}
    public void setId(int id){this.id = id;}

    public void setCharacter(Character character) {
        this.character = character;
    }
}
