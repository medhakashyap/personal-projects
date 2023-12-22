package stickhero;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.Serializable;

public class GameEngine extends Application {
    public static Game[] GameData=new Game[5];
    private static int Best=0;

    public static int getBest() {
        return Best;
    }

    public static void setBest(int best) {
        Best = best;
    }

    public static int index=0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainWindow mainWindow=new MainWindow();
        mainWindow.start(stage);
        stage.show();
    }

    public static void newGame(Stage stage, MainWindow mainWindow, int key){
        Game game=new Game(key);
        GameHandler gameHandler=new GameHandler(game,mainWindow);
        game.setRunning(true);
        gameHandler.start(stage);
    }
    public static void loadGame(Game game,Stage stage, MainWindow mainWindow){
        GameHandler gameHandler=new GameHandler(game,mainWindow);
        game.setRunning(true);
        gameHandler.start(stage);
    }

}
