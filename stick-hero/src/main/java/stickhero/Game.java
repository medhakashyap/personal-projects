package stickhero;

public class Game {
    private boolean running;
    private int characterKey;
    private int score;
    private int cherries;

    public Game(int characterKey) {
        this.running = false;
        this.characterKey = characterKey;
        this.score = 0;
        this.cherries = 0;
    }

    public boolean isRunning() {
        return running;
    }

    public int getCharacterKey() {
        return characterKey;
    }

    public int getScore() {
        return score;
    }

    public int getCherries() {
        return cherries;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCherries(int cherries) {
        this.cherries = cherries;
    }
}
