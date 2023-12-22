package stickhero;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.*;

public class Blocks implements WalkAssist {
    private static Blocks BLOCKS;
    private ArrayList<Block> blocks;
    private static Map<ImageView, Block> blockMap=new HashMap<ImageView, Block>();
    private ArrayList<ImageView> onSceneBlocks;
    private boolean spawnPerm;
    private boolean lock;
    private Pane pane;

    private Blocks(Pane pane) {
        this.blocks = new ArrayList<>();
        this.onSceneBlocks = new ArrayList<>(); // Initialize onSceneBlocks
        this.lock = false;
        this.spawnPerm = true;
        this.pane=pane;
        loadBlocks();
    }


    public static Blocks getInstance(Pane pane) {
        if (BLOCKS == null) {
            BLOCKS = new Blocks(pane);
        }
        return BLOCKS;
    }

    public static int getRandomNumber() {
        Random random = new Random();
        // Generate a random integer to choose block
        int probability = random.nextInt(100); // 0, 1, or 2;
        if (probability<30){
            return 0;
        } else if (probability<55 && probability>30) {
            return 3;
        } else if(probability>55 && probability<65) {
            return 2;
        } else {
            return 1;
        }
    }

    public void loadBlocks() {
        this.blocks.add(new Block(0, 74, 55));
        this.blocks.add(new Block(1, 149, 55));
        this.blocks.add(new Block(2, 69, 34));
        this.blocks.add(new Block(3,48,24));
        blockMap.put(blocks.get(0).getGraphic(), blocks.get(0));
        blockMap.put(blocks.get(1).getGraphic(), blocks.get(1));
        blockMap.put(blocks.get(2).getGraphic(), blocks.get(2));
        blockMap.put(blocks.get(3).getGraphic(), blocks.get(3));
        //System.out.println("blockMap contents: " + blockMap);
    }

    public void spawnBlock(Pane pane) {
        Coordinates coordinates=Coordinates.getCoordinates();
        double w = Constants.STAGE_WIDTH;
        int rand=getRandomNumber();
        Block block =new Block(rand, blocks.get(rand).getSize()[0],blocks.get(rand).getSize()[1]);
        ImageView blockImage = block.getGraphic();
        //System.out.println(spawnPerm);
        if (spawnPerm) {
            pane.getChildren().add(blockImage);
            blockImage.setLayoutY(Constants.BASE_Y);
            blockImage.setLayoutX(w);
            spawnPerm = false;
            BLOCKS.onSceneBlocks.add(blockImage);
            coordinates.addBlockCords(w,w + block.getSize()[0]);
        }
    }
    public void initiateGameSpawn(Pane pane) {
        Coordinates coordinates=Coordinates.getCoordinates();
        double w = Constants.BASE_X;
        int rand=getRandomNumber();
        Block block =new Block(rand, blocks.get(rand).getSize()[0],blocks.get(rand).getSize()[1]);
        ImageView blockImage = block.getGraphic();
        pane.getChildren().add(blockImage);
        blockImage.setLayoutY(Constants.BASE_Y);
        blockImage.setLayoutX(Constants.BASE_X-block.getSize()[0]);
        this.onSceneBlocks.add(blockImage);
        coordinates.addBlockCords(Constants.BASE_X-block.getSize()[0],Constants.BASE_X);

        rand=getRandomNumber();
        block=new Block(rand, blocks.get(rand).getSize()[0],blocks.get(rand).getSize()[1]);
        blockImage = block.getGraphic();
        double random_x=250+Math.random()*200;
        pane.getChildren().add(blockImage);
        blockImage.setLayoutY(Constants.BASE_Y);
        blockImage.setLayoutX(random_x);
        this.onSceneBlocks.add(blockImage);
        coordinates.addBlockCords(random_x,random_x+block.getSize()[0]);

        rand=getRandomNumber();
        random_x += block.getSize()[0]+Math.random()*300;
        block=new Block(rand, blocks.get(rand).getSize()[0],blocks.get(rand).getSize()[1]);
        blockImage = block.getGraphic();
        pane.getChildren().add(blockImage);
        blockImage.setLayoutY(Constants.BASE_Y);
        blockImage.setLayoutX(random_x);
        this.onSceneBlocks.add(blockImage);
        coordinates.addBlockCords(random_x,random_x+block.getSize()[0]);
    }
    public void toggleLock(){
        this.lock=(!this.lock);
    }
    @Override
    public void shift() throws ObjectTransitionException {
        if (!lock) {
            // Implement shift logic here
            Iterator<ImageView> iterator = BLOCKS.onSceneBlocks.iterator();
            //System.out.println(onSceneBlocks);
            while (iterator.hasNext()) {
                ImageView b = iterator.next();
                b.setLayoutX(b.getLayoutX() - Constants.WALK_PACE_PIXEL);
                if (b.getLayoutX() + 225 < 0) {
                    iterator.remove(); // Safe removal during iteration
                }
            }

            double rightmostX = BLOCKS.onSceneBlocks.stream()
                    .mapToDouble(b -> b.getLayoutX() + 225 + Math.random() * 200)
                    .max()
                    .orElse(0);

            if (rightmostX < 900) {
                this.spawnPerm = true;
                if (rightmostX < 700 && rightmostX > 650 && Math.random() < 0.002) {
                    // Condition 1
                    this.spawnPerm = true;
                    BLOCKS.spawnBlock(BLOCKS.pane);
                } else if (rightmostX < 700 && Math.random() < 0.0005) {
                    // Condition 2

                    this.spawnPerm = true;
                    BLOCKS.spawnBlock(BLOCKS.pane);
                } else if (rightmostX > 600 || Math.random() < 0.001) {
                    // Condition 3

                    this.spawnPerm = true;
                    BLOCKS.spawnBlock(BLOCKS.pane);
                }
            }
        } else {
            //System.out.println("Lock: " + BLOCKS.lock);
            throw new ObjectTransitionException("Blocks are Locked");
        }
    }

    public static void setBLOCKS() {
        Blocks.BLOCKS = null;
    }
}