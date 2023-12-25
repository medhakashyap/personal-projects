package com.example.stickhero;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage mainstage) throws Exception {
        mainstage.setTitle("StickHero");

        Pane homepane = new Pane();
        Pane gameplayPane = new Pane(); //Placeholder Pane without any functionality
        Pane loadGamePane= new Pane();  //Placeholder Pane without any functionality

        Button loadGameBtn = new Button("Load Game");
        loadGameBtn.setPrefSize(200, 70);
        homepane.getChildren().add(loadGameBtn);
        loadGameBtn.setOnAction(actionEvent -> {
            mainstage.getScene().setRoot(loadGamePane);
        });

        Button newGameBtn = new Button("New Game");
        newGameBtn.setPrefSize(200, 70);
        homepane.getChildren().add(newGameBtn);
        newGameBtn.setOnAction(actionEvent -> {
            mainstage.getScene().setRoot(gameplayPane);
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setPrefSize(200, 70);
        homepane.getChildren().add(exitBtn);
        exitBtn.setOnAction(actionEvent -> {
            Platform.exit();
        });

        Button changeCharacterBtn = new Button("Change Character");
        changeCharacterBtn.setPrefSize(200, 70);
        homepane.getChildren().add(changeCharacterBtn);
        changeCharacterBtn.setOnAction(actionEvent -> {
            mainstage.getScene().setRoot(gameplayPane);
        });

        loadGameBtn.setLayoutX(550);
        loadGameBtn.setLayoutY(100);
        newGameBtn.setLayoutX(550);
        newGameBtn.setLayoutY(210);
        exitBtn.setLayoutX(550);
        exitBtn.setLayoutY(320);
        changeCharacterBtn.setLayoutX(120);
        changeCharacterBtn.setLayoutY(370);

        Image actor = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Actor.png")));
        ImageView actorView = new ImageView(actor);
        homepane.getChildren().add(actorView);
        actorView.setLayoutX(140);
        actorView.setLayoutY(140);

        Image settingsIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/settings-filled.png")));
        ImageView settingIconView = new ImageView(settingsIcon);
        homepane.getChildren().add(settingIconView);
        settingIconView.setLayoutX(830);
        settingIconView.setLayoutY(20);


        Image actor2=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Actor.png")));
        ImageView actorView2=new ImageView(actor2);
        gameplayPane.getChildren().add(actorView2);
        actorView2.setLayoutX(350);
        actorView2.setLayoutY(150);

        Image pauseIcon=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/pause.png")));
        ImageView pauseIconView=new ImageView(pauseIcon);
        gameplayPane.getChildren().add(pauseIconView);
        pauseIconView.setLayoutX(830);
        pauseIconView.setLayoutY(20);

        Text message=new Text("No Games Saved");
        message.setFont(new Font(20));
        loadGamePane.getChildren().add(message);
        message.setLayoutX(350);
        message.setLayoutY(100);

        Scene homeScreen = new Scene(homepane, 900, 485);
        homeScreen.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode()== KeyCode.ESCAPE){
                mainstage.getScene().setRoot(homepane);
            }
        });

        mainstage.setScene(homeScreen);
        mainstage.show();
    }
}
