package stickhero;

import java.util.ArrayList;

class ClassNotFoundException extends Exception {
    public ClassNotFoundException(String message) {
        super(message);
    }
}

class GameCannotBeLaunchedException extends Exception {
    public GameCannotBeLaunchedException(String message) {
        super(message);
    }
}

public class GameHandler {
    private ArrayList<Character> characterArrayList;
    private Game presentGame;
    private ArrayList<Game> gameArrayList;
    public GameHandler(ArrayList<Character> characterArrayList, Game presentGame, ArrayList<Game> gameArrayList) {
        this.characterArrayList = characterArrayList;
        this.presentGame = presentGame;
        this.gameArrayList = gameArrayList;
    }

    //setters
    public void setCharacterArrayList(ArrayList<Character> characterArrayList) {
        this.characterArrayList = characterArrayList;
    }

    public void setGameArrayList(ArrayList<Game> gameArrayList) {
        this.gameArrayList = gameArrayList;
    }

    public void setPresentGame(Game presentGame) {
        this.presentGame = presentGame;
    }

    public Game getPresentGame() {
        return presentGame;
    }

    //getters
    public ArrayList<Game> getGameArrayList() {
        return gameArrayList;
    }

    public ArrayList<Character> getCharacterArrayList() {
        return characterArrayList;
    }

    //Handling Methods
    public void start_game() throws Exception {
        try{
            Cherry cherry=new Cherry();
            cherry.spawnCherry();
            Pillar block=new Pillar();
            block.spawnPillar();
            Stick stick = new Stick();
            stick.spawnStick();
        } catch (Exception e){
            if (e instanceof ClassNotFoundException){
                throw new ClassNotFoundException("Class Not Found");
            } else if (e instanceof GameCannotBeLaunchedException){
                throw new GameCannotBeLaunchedException("Game Cannot be Launched");
            } else{
                throw new GameCannotBeLaunchedException("Game Cannot be Launched");
            }
        }
    }
    public void pause_game(){

    }
    public void restart_game(){

    }
    public void save_game(){

    }
    public void load_game(){

    }
    public void manage_game(){

    }
    public void end_game(){

    }
    public void resume_game(){

    }

}
