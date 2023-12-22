package stickhero;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Objects;

public class Helper {
    private static Helper helper=null;
    private Helper(){
    }
    public static Helper getHelper(){
        if (helper==null){
            helper=new Helper();
        }
        return helper;
    }
    public Button addButton(Pane pane, double sizex, double sizey, double x, double y, ImageView graphic){
        Button button=new Button("test");
        button.setPrefSize(sizex,sizey);
        button.setStyle("-fx-background-color: transparent;");
        if(graphic!=null){
            button.setGraphic(graphic);
        }
        pane.getChildren().add(button);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

    public ImageView loadImageView(String path){
        Image image=new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        ImageView imageView=new ImageView(image);
        return imageView;
    }
}
