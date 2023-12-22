package stickhero;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.util.Objects;
import javafx.scene.media.*;
import stickhero.Helper;
import javafx.scene.control.Slider;

public class MainWindow extends Application {
    GameEngine gameEngine;

    public MainWindow(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public static String[][] characterKeys = {
            {"TanjiroMain", "TanjiroRunning"},
            {"NezukoMain", "NezukoRunning"},
            {"ZenitsuMain", "ZenitsuRunning"}
    };
    private static int currentCharacter=0;
    private static Group currentRunningGroup=null;

    public static int getCurrentCharacter() {
        return currentCharacter;
    }

    public static void setCurrentCharacter(int currentCharacter) {
        MainWindow.currentCharacter = currentCharacter;
    }

    public MainWindow() {}

    @Override
    public void start(Stage mainstage) throws Exception {
        mainstage.setTitle("StickHero");
        Pane homepane = new Pane();

        Pane loadGamePane = new Pane();  //Placeholder Pane without any functionality
        Scene homeScreen = new Scene(homepane, 900, 485);

        Character.load();
        Helper helper=Helper.getHelper();

        Image bg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/bgmainscreen.jpg")));
        ImageView bgView = new ImageView(bg);
        bgView.setFitHeight(485);
        bgView.setFitWidth(900);
        homepane.getChildren().add(bgView);
        bgView.setLayoutX(0);
        bgView.setLayoutY(0);

//        Button loadGameBtn = helper.addButton(homepane, 200,70, 540, 100, helper.loadImageView("/LoadGameButton.png"));
//        loadGameBtn.setOnAction(actionEvent -> {
//            mainstage.getScene().setRoot(loadGamePane);
//        });

        Button newGameBtn = helper.addButton(homepane, 200, 70, 540, 210, helper.loadImageView("/NewGameButton.png"));
        newGameBtn.setOnAction(actionEvent -> {
            //trigger an event in GameEngine;
            GameEngine.newGame(mainstage, this, currentCharacter);
        });

        Button exitBtn = helper.addButton(homepane, 200, 70, 540, 320, helper.loadImageView("/QuitButton.png"));
        exitBtn.setOnAction(actionEvent -> {
            Platform.exit();
        });

        Button changeCharacterBtn = helper.addButton(homepane, 200, 70, 120, 370, helper.loadImageView("/ChangeCharacterButton.png"));
        changeCharacterBtn.setOnAction(actionEvent -> {
            mainstage.getScene().setRoot(changeCharacter(homepane, homeScreen));
        });

        Button settingBtn = helper.addButton(homepane, 58.5, 58.5, 810, 20, helper.loadImageView("/SettingsButton.png"));
        settingBtn.setOnAction(actionEvent -> {
            mainstage.getScene().setRoot(settingsPane(homepane, homeScreen));
        });
        setCharacter(homepane);
        homeScreen.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode()== KeyCode.ESCAPE){
                mainstage.getScene().setRoot(homepane);
            }
        });
        mainstage.setScene(homeScreen);
        mainstage.show();
    }
    public static Pane changeCharacter(Pane homepane, Scene homescreen){
        Pane pane=new Pane();
        Image bg = new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/bgmainscreen.jpg")));
        ImageView bgView = new ImageView(bg);
        bgView.setFitHeight(485);
        bgView.setFitWidth(900);
        pane.getChildren().add(bgView);
        ImageView tan=new ImageView(Character.getCharacters().get(characterKeys[0][0]).getStillImage());
        ImageView nez=new ImageView(Character.getCharacters().get(characterKeys[1][0]).getStillImage());
        ImageView zen=new ImageView(Character.getCharacters().get(characterKeys[2][0]).getStillImage());
        pane.getChildren().add(tan);
        pane.getChildren().add(nez);
        pane.getChildren().add(zen);
        tan.setFitWidth(180); tan.setFitHeight(210);
        nez.setFitWidth(200); nez.setFitHeight(200);
        zen.setFitWidth(240); zen.setFitHeight(230);
        tan.setLayoutX(100); tan.setLayoutY(90);
        nez.setLayoutX(370); nez.setLayoutY(110);
        zen.setLayoutX(610); zen.setLayoutY(70);

        ImageView block1=Helper.getHelper().loadImageView("/blocks/block1.png");
        ImageView block2=Helper.getHelper().loadImageView("/blocks/block1.png");
        ImageView block3=Helper.getHelper().loadImageView("/blocks/block1.png");
        pane.getChildren().add(block1);
        pane.getChildren().add(block2);
        pane.getChildren().add(block3);

        block1.setFitWidth(230);
        block1.setFitHeight(65);
        block2.setFitWidth(230);
        block2.setFitHeight(65);
        block3.setFitWidth(230);
        block3.setFitHeight(65);
        block1.setLayoutX(70); block1.setLayoutY(300);
        block2.setLayoutX(340); block2.setLayoutY(300);
        block3.setLayoutX(610); block3.setLayoutY(300);

        Button button1 = new Button("");
        Button button2 = new Button("");
        Button button3 = new Button("");

        button1.setPrefSize(230, 230);
        button2.setPrefSize(230, 230);
        button3.setPrefSize(230, 230);
        button1.setStyle("-fx-background-color: transparent;");
        button2.setStyle("-fx-background-color: transparent;");
        button3.setStyle("-fx-background-color: transparent;");

        pane.getChildren().addAll(button1, button2, button3);

        button1.setLayoutX(70);
        button1.setLayoutY(70);

        button2.setLayoutX(340);
        button2.setLayoutY(70);

        button3.setLayoutX(610);
        button3.setLayoutY(70);
        button1.setOnAction(actionEvent -> {
            if(currentCharacter!=0){currentCharacter=0;
            setCharacter(homepane);}
            homescreen.setRoot(homepane);
        });
        button2.setOnAction(actionEvent -> {
            if(currentCharacter!=1){currentCharacter=1;
            setCharacter(homepane);}
            homescreen.setRoot(homepane);
        });
        button3.setOnAction(actionEvent -> {
            if(currentCharacter!=2) {
                currentCharacter = 2;
                setCharacter(homepane);
            }
            homescreen.setRoot(homepane);
        });
        return pane;
    }
    public static Pane settingsPane(Pane pane, Scene scene){
        MediaPlayer mediaPlayer;
        Slider volumeSlider;
        Pane root = new Pane();
        Helper helper=Helper.getHelper();
        Image backgroundImage = new Image("/bgmainscreen.jpg");
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, null, null, null, null);
        Background bg = new Background(backgroundImg);
        root.setBackground(bg);

        Button pauseMusicBtn = helper.addButton(root, 317, 73, 300, 150, helper.loadImageView("/pauseMusic.png"));
        Button playMusicBtn = helper.addButton(root, 317, 73, 300, 250, helper.loadImageView("/playMusic.png"));

        // Create and position the volume slider
        volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setPrefWidth(150);
        volumeSlider.setLayoutX(380); // Adjust the X position as needed
        volumeSlider.setLayoutY(100); // Adjust the Y position as needed

        String musicFile = "/marioMusic.mp3";
        Media media = new Media(MainWindow.class.getResource(musicFile).toString());

        mediaPlayer = new MediaPlayer(media);

        playMusicBtn.setOnAction(actionEvent -> mediaPlayer.play());
        pauseMusicBtn.setOnAction(actionEvent -> mediaPlayer.pause());

        // Add an event handler to update volume in real-time
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue());
        });

        // Add the volume slider to the root pane
        root.getChildren().add(volumeSlider);
        return root;
    }
    public static void setCharacter(Pane pane){
        if (currentRunningGroup != null) {
            pane.getChildren().remove(currentRunningGroup);
            currentRunningGroup = null; // Reset the reference
        }
        String key=characterKeys[currentCharacter][0];
        Character character=Character.getCharacters().get(key);
        Group running=character.getRunningGroup();
        pane.getChildren().add(running);
        running.setScaleX(character.getRunningParameters()[0]);
        running.setScaleY(character.getRunningParameters()[1]);
        running.setLayoutX(character.getRunningParameters()[2]);
        running.setLayoutY(character.getRunningParameters()[3] - 10);
        currentRunningGroup=running;
    }


    public static void main(String[] args) {
        launch();
    }
}
