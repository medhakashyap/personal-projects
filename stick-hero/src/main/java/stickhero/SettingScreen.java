package com.example.demo1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.*;
import stickhero.Helper;

public class SettingScreen extends Application {
    private MediaPlayer mediaPlayer;
    private Slider volumeSlider;

    @Override
    public void start(Stage stage) {
        Helper helper = Helper.getHelper();
        Pane root = new Pane();

        Image backgroundImage = new Image("/bgmainscreen.jpg");
        BackgroundImage backgroundImg = new BackgroundImage(backgroundImage, null, null, null, null);
        Background bg = new Background(backgroundImg);
        root.setBackground(bg);

        Button pauseMusicBtn = helper.addButton(root, 317, 73, 300, 150, helper.loadImageView("/pauseMusic.png"));
        Button playMusicBtn = helper.addButton(root, 317, 73, 300, 250, helper.loadImageView("/playMusic.png"));

        // Create and position the volume slider
        volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setPrefWidth(100);
        volumeSlider.setLayoutX(50); // Adjust the X position as needed
        volumeSlider.setLayoutY(50); // Adjust the Y position as needed

        String musicFile = "/marioMusic.mp3";
        Media media = new Media(getClass().getResource(musicFile).toString());

        mediaPlayer = new MediaPlayer(media);

        playMusicBtn.setOnAction(actionEvent -> play());
        pauseMusicBtn.setOnAction(actionEvent -> pause());

        // Add an event handler to update volume in real-time
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue());
        });

        // Add the volume slider to the root pane
        root.getChildren().add(volumeSlider);

        Scene scene = new Scene(root, 900, 485);

        stage.setScene(scene);
        stage.setTitle("Settings Screen");
        stage.show();
    }

    private void play() {
        mediaPlayer.play();
    }

    private void pause() {
        mediaPlayer.pause();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
