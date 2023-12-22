package stickhero;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CharacterAnimation {
    private Group characterGroup;
    private ExecutorService executorService;
    private Future<?> animationTask;

    public CharacterAnimation(){
        executorService = Executors.newSingleThreadExecutor();
    }
    public Group createCharacterAnimation(Image[] characterImages) throws IOException{
        ImageView[] imageViews = new ImageView[characterImages.length];
        for (int i = 0; i < characterImages.length; i++){
            imageViews[i] = new ImageView(characterImages[i]);
        }
        characterGroup = new Group(imageViews[0]);

        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);

        for(int i = 0; i < characterImages.length; i++){
            int index = i;
            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(200*i),
                    (event) -> {
                        updateImage(imageViews[index]);
                    }
            ));
        }
        t.play();
        return characterGroup;
    }

    private void updateImage(ImageView imageView){
        Platform.runLater(() -> characterGroup.getChildren().setAll(imageView));
    }

    public void startAnimation(Group animationGroup){
        animationTask = executorService.submit(()->{
            Platform.runLater(()->{
                animationGroup.setTranslateX(0);
                animationGroup.setTranslateY(0);
            });
        });
    }
    public void stopAnimation(){
        if (animationTask != null && !animationTask.isDone()){
            animationTask.cancel(true);
        }
        executorService.shutdown();
    }
}

