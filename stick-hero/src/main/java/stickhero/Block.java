package stickhero;

import javafx.scene.image.ImageView;

public class Block {
    ImageView graphic;
    Double[] size;
    public Block(int i, double x, double y){
        graphic=Helper.getHelper().loadImageView("/blocks/block"+i+".png");
        size = new Double[]{x, y};
    }

    public ImageView getGraphic() {
        return graphic;
    }

    public Double[] getSize() {
        return size;
    }
}
