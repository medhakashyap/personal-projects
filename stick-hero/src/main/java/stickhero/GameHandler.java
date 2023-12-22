package stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameHandler extends Application {
    Game game;
    MainWindow mainWindow;

    public GameHandler(Game game, MainWindow mainWindow) {
        this.game = game;
        this.mainWindow = mainWindow;
        score=0;
        cherryCount=0;
    }

    boolean spaceBarPressed=false;
    static Integer score=0;
    static Integer cherryCount=0;
    @Override
    public void start(Stage stage){
        Pane pane=new Pane();
        try {
            Character.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene mainScene=new Scene(pane,900,485);
        Image bg=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bg.jpg")));
        ImageView bgView=Helper.getHelper().loadImageView("/bg.jpg");
        //ImageView bgView2=Helper.getHelper().loadImageView("/bg.jpg");
        bgView.setVisible(true);
        System.out.println(true);
        bgView.setFitHeight(485); //bgView2.setFitHeight(485);
        bgView.setFitWidth(1200); //bgView2.setFitWidth(1200);
        pane.getChildren().addAll(bgView);
        System.out.println(pane.getChildren());

        Coordinates.setCoordinates();
        Blocks.setBLOCKS();
        Stick.setSTICK();
        pane.getChildren().add(new Line(Constants.BASE_X, 485,Constants.BASE_X,0));
        Blocks blocksClass=Blocks.getInstance(pane);
        Stick stick=Stick.getInstance(pane);
        Coordinates coordinates=Coordinates.getCoordinates();
        Cherry cherry=new Cherry();

        Hero hero=new Hero(Character.getCharacters().get(MainWindow.characterKeys[MainWindow.getCurrentCharacter()][1]), pane);

        ImageView stickhero=hero.getStill();
        Group runningHero=hero.getRunning();
        pane.getChildren().add(stickhero);
        pane.getChildren().add(runningHero);
        stickhero.setLayoutX(hero.getStillParameters()[2]);
        stickhero.setLayoutY(hero.getStillParameters()[3]);
        stickhero.setFitWidth(hero.getStillParameters()[0]);
        stickhero.setFitHeight(hero.getStillParameters()[1]);
        runningHero.setLayoutX(hero.getRunningParameters()[2]);
        runningHero.setLayoutY(hero.getRunningParameters()[3]);
        runningHero.setScaleX(hero.getRunningParameters()[0]);
        runningHero.setScaleY(hero.getRunningParameters()[1]);

        stickhero.setVisible(true);
        runningHero.setVisible(false);
        cherry.spawnCherry(pane,600);
        KeyFrame forWalk=new KeyFrame(Duration.millis(Constants.WALK_PACE_TIME), actionEvent -> {
            if (!stick.isLock()) {
                try {
                    stickhero.setVisible(false);
                    runningHero.setVisible(true);
                    blocksClass.shift();
                    stick.shift();
                    coordinates.shift();
                    cherry.shift();
                    if(cherry.eatCherry(hero)){cherryCount++;}
                    //coordinates.printCords();
                    if(coordinates.getStickCords()[1] < Constants.BASE_X){
                        stickhero.setVisible(true);
                        runningHero.setVisible(false);
                        //System.out.println("lock");
                        coordinates.printCords();
                        stick.setLock();
                        stick.resetStick();
                        if(!coordinates.checkGround()){
                            killHero(stickhero, stage, this.mainWindow,this.game);
                            System.out.println("Score: "+score);
                            if (score+ game.getScore()>GameEngine.getBest()){
                                GameEngine.setBest(score+game.getScore());
                                game.setScore(game.getScore()+score);
                                game.setCherries(game.getCherries()+cherryCount);
                            }
                        }
                    }
                } catch (ObjectTransitionException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Timeline walkTimeline=new Timeline(forWalk);
        walkTimeline.setCycleCount(Timeline.INDEFINITE);

        initiateGame(pane,blocksClass);
        blocksClass.spawnBlock(pane);

        mainScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()==KeyCode.SPACE && !stick.isGrowLock()){
                walkTimeline.pause();
                spaceBarPressed=true;
                stick.growStick();
            } else if (keyEvent.getCode()==KeyCode.DOWN) {
                hero.toggle(false);
                runningHero.setScaleY(-hero.getRunningParameters()[1]);
                runningHero.setLayoutY(180);
            } else if (keyEvent.getCode()==KeyCode.UP) {
                hero.toggle(true);
                runningHero.setScaleY(hero.getRunningParameters()[1]);
                runningHero.setLayoutY(hero.getRunningParameters()[3]);
            } else if(keyEvent.getCode()==KeyCode.ESCAPE){
                System.out.println("102");
                walkTimeline.pause();
                stage.setScene(new Scene(this.pausePane(stage, this.game, this.mainWindow, mainScene, walkTimeline),900,485));
            }
        });
        mainScene.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                spaceBarPressed = false;
                if (!stick.isGrowLock()) {coordinates.setStickCords(Constants.BASE_X, Constants.BASE_X+stick.getLength());}
                stick.setGrowLock(true);
                stick.rotateStick();
                coordinates.printCords();
                if(coordinates.perfectHit(Constants.BASE_X+stick.getLength())){score+=5;cherry.spawnCherry(pane,Constants.BASE_X+Math.random()*stick.getLength());}
                walkTimeline.play();
            }
        });

        stage.setScene(mainScene);
        stage.show();
    }
    public static void killHero(ImageView hero, Stage stage, MainWindow mainWindow,Game game){
        Timeline gravityTimeline=new Timeline();
        KeyFrame killFrame = new KeyFrame(Duration.millis(1), actionEvent -> {
            hero.setLayoutY(hero.getLayoutY() + 0.5);
            if(hero.getLayoutY()>485){
                //System.out.println("here rn");
                endGame(game, stage, mainWindow);
                gravityTimeline.pause();
            }
        });
        gravityTimeline.getKeyFrames().add(killFrame);
        gravityTimeline.setCycleCount(550);
        gravityTimeline.play();
    }
    public static void initiateGame(Pane pane,Blocks blocksClass){
        blocksClass.initiateGameSpawn(pane);
    }
    public static void endGame(Game game,Stage stage, MainWindow mainWindow) {
        Pane pane = new Pane();
        Rectangle rectangle = new Rectangle(0, 0, 900, 485);
        rectangle.setFill(Color.rgb(0, 0, 0, 0.2));
        pane.getChildren().add(rectangle);

        Image bgImage = new Image(Objects.requireNonNull(GameHandler.class.getResourceAsStream("/bg.jpg")));
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitHeight(485);
        bgView.setFitWidth(1200);
        pane.getChildren().add(bgView);

        // Create a Text node for the score
        Text scoreText = new Text("Score: " + score);
        scoreText.setFont(Font.font("Arial", 50));
        scoreText.setFill(Color.WHITE);
        scoreText.setLayoutX(350);
        scoreText.setLayoutY(200);
        pane.getChildren().add(scoreText);

        Text cherryText = new Text("" + cherryCount);
        cherryText.setFont(Font.font("Arial", 30));
        cherryText.setFill(Color.WHITE);
        cherryText.setLayoutX(850);
        cherryText.setLayoutY(45);
        pane.getChildren().add(cherryText);
        ImageView cherryIcon=new ImageView(new Image(Objects.requireNonNull(GameHandler.class.getResourceAsStream("/cherry.png"))));
        cherryIcon.setLayoutX(790);
        cherryIcon.setLayoutY(10);
        cherryIcon.setFitHeight(50);
        cherryIcon.setFitWidth(50);
        pane.getChildren().add(cherryIcon);

        Text bestText = new Text("" + GameEngine.getBest());
        bestText.setFont(Font.font("Arial", 30));
        bestText.setFill(Color.WHITE);
        bestText.setLayoutX(850);
        bestText.setLayoutY(110);
        pane.getChildren().add(bestText);
        ImageView bestIcon=new ImageView(new Image(Objects.requireNonNull(GameHandler.class.getResourceAsStream("/crown.png"))));
        bestIcon.setLayoutX(790);
        bestIcon.setLayoutY(70);
        bestIcon.setFitHeight(50);
        bestIcon.setFitWidth(50);
        pane.getChildren().add(bestIcon);

        ImageView button1 = new ImageView(new Image(Objects.requireNonNull(GameHandler.class.getResourceAsStream("/restartbutton.png"))));
        ImageView button2 = new ImageView(new Image(Objects.requireNonNull(GameHandler.class.getResourceAsStream("/QuitButton2.png"))));
        //ImageView button3 = new ImageView(new Image(Objects.requireNonNull(GameHandler.class.getResourceAsStream("/reviveButton.png"))));
        button1.setLayoutX(350);
        button1.setLayoutY(270);
//        button3.setLayoutX(350);
//        button3.setLayoutY(350);
        button2.setLayoutX(350);
        button2.setLayoutY(350);
        pane.getChildren().addAll(button2, button1);

        Button restartButton = new Button("");
        restartButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 190px; -fx-pref-height: 70px;");
        restartButton.setPrefSize(190,70);
        restartButton.setLayoutX(350);
        restartButton.setLayoutY(270);
        pane.getChildren().add(restartButton);

        // Create transparent "Quit" button
        Button quitButton = new Button("");
        quitButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 190px; -fx-pref-height: 70px;");
        quitButton.setPrefSize(190,70);
        quitButton.setLayoutX(350);
        quitButton.setLayoutY(350);

//        Button reviveButton = new Button("");
//        reviveButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 190px; -fx-pref-height: 70px;");
//        reviveButton.setPrefSize(190, 70);
//        reviveButton.setLayoutX(350); // Adjust the X position as needed
//        reviveButton.setLayoutY(270); // Adjust the Y position as needed

        pane.getChildren().add(quitButton);
        restartButton.setOnAction((actionEvent -> {
            try {
                mainWindow.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        quitButton.setOnAction(actionEvent -> Platform.exit());
//        reviveButton.setOnAction(actionEvent -> {
//            if (game.getCherries()>0) {
//                GameEngine.loadGame(game, stage, mainWindow);
//                game.setCherries(game.getCherries()-1);
//            }});
        Scene scene = new Scene(pane, 900, 485);
        stage.setScene(scene);
        //System.out.println("Setting the end game transition scene");
        stage.show();
    }
    public Pane pausePane(Stage stage,Game game, MainWindow mainWindow, Scene scene, Timeline walk){
        Pane root = new Pane();
        Helper helper = Helper.getHelper();

        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pauseScreen.png")));
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage,null,null,null,null);


        Background bg = new Background(backgroundImg);
        root.setBackground(bg);

        Button pauseBtn = helper.addButton(root, 58.5, 58.5, 380, 80, helper.loadImageView("/pauseButton.png"));
        Button restartBtn = helper.addButton(root,181,61,340,200,helper.loadImageView("/restartButton2.png"));
        Button saveGameBtn = helper.addButton(root,181,61,340,280,helper.loadImageView("/saveGameButton.png"));
        Button quitBtn = helper.addButton(root,181,61,340,360,helper.loadImageView("/quitBtn.png"));
        Button cherryBtn = helper.addButton(root,25,25,800,50,helper.loadImageView("/cherry.png"));
        Button crownBtn = helper.addButton(root,25,25,800,15,helper.loadImageView("/crown.png"));
        quitBtn.setOnAction(actionEvent -> Platform.exit());
        restartBtn.setOnAction(actionEvent -> {
            try {
                mainWindow.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        pauseBtn.setOnAction(actionEvent -> {stage.setScene(scene);walk.play();});
        saveGameBtn.setOnAction(actionEvent -> {
            if(GameEngine.index<=5){
                GameEngine.GameData[GameEngine.index]=game;
                GameEngine.index++;
                System.out.println(GameEngine.GameData);
            }
        });
        return root;
    }

    public static void setScore(Integer score) {
        GameHandler.score = score;
    }

    public static void main(String[] args) {
        launch();
    }
}
