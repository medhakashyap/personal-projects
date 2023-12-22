package stickhero;

import javafx.scene.Group;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Character {
    private static Map<String, Character> characters = new HashMap<String, Character>();

    private String name;

    public static Map<String, Character> getCharacters() {
        return characters;
    }

    private Group runningAnimationGroup;
    private Image stillImage;
    private CharacterAnimation characterAnimation;
    private double[] runningParameters;
    private double[] stillParameters;

    public static void load() throws IOException {
        try{
            loadWaterTanjiro();
            loadRunningTanjiro();
            loadZensituMainAnimation();
            loadRunningZenistu();
            loadNezukoMainAnimation();
            loadRunningNezuko();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public static void loadWaterTanjiro() throws IOException{
        Image[] tanjiroImages = {
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan0.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan1.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan2.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan3.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan4.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan5.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan6.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan7.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan8.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjrowaterstyle/tan9.png")))
        };
        CharacterAnimation ca = new CharacterAnimation();
        Group tanjiroWater;
        try {
            tanjiroWater = ca.createCharacterAnimation(tanjiroImages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image waterTanjiro = new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/tanjiroStill.png")));
        Character tanjiroMain = new Character("TanjiroMain", tanjiroWater, waterTanjiro);
        tanjiroMain.runningParameters[0]=1;
        tanjiroMain.runningParameters[1]=1;
        tanjiroMain.runningParameters[2]=-20;
        tanjiroMain.runningParameters[3]=10;
        characters.put("TanjiroMain", tanjiroMain);
    }
    public static void loadRunningTanjiro(){
        Image[] tanjiroRunningImages = {
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t1.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t2.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t3.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t4.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t5.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t6.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t7.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/tanjiroRunning/t8.png"))),
        };
        CharacterAnimation ca = new CharacterAnimation();
        Group tanjiroRunningGroup;
        try {
            tanjiroRunningGroup = ca.createCharacterAnimation(tanjiroRunningImages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image stillTanjiro = new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/tanjiroStill.png")));
        Character tanjiroRunning = new Character("TanjiroRunning", tanjiroRunningGroup, stillTanjiro);
        tanjiroRunning.runningParameters[0]=0.25;
        tanjiroRunning.runningParameters[1]=0.23;
        tanjiroRunning.runningParameters[2]=10;
        tanjiroRunning.runningParameters[3]=90;
        tanjiroRunning.stillParameters[0]=50;
        tanjiroRunning.stillParameters[1]=60;
        tanjiroRunning.stillParameters[2]=150;
        tanjiroRunning.stillParameters[3]=240;
        characters.put("TanjiroRunning", tanjiroRunning);
    }

    public static void loadRunningZenistu(){
        Image[] zenistuRunningImages = {
                new Image(String.valueOf(CharacterAnimation.class.getResource("/zenistuRunning/anime1.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/zenistuRunning/anime2.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/zenistuRunning/a3.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/zenistuRunning/anime3.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/zenistuRunning/anime4.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/zenistuRunning/anime5.png")))
        };
        CharacterAnimation ca= new CharacterAnimation();
        Group zenistuRunningGroup;
        try {
            zenistuRunningGroup = ca.createCharacterAnimation(zenistuRunningImages);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        Image stillZenistu = new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/ZenitsuStill.png")));
        Character zenistuRunning = new Character("ZenistuRunning",zenistuRunningGroup,stillZenistu);
        zenistuRunning.stillParameters[0]=75;
        zenistuRunning.stillParameters[1]=80;
        zenistuRunning.stillParameters[2]=140;
        zenistuRunning.stillParameters[3]=220;
        zenistuRunning.runningParameters[0]=0.23;
        zenistuRunning.runningParameters[1]=0.23;
        zenistuRunning.runningParameters[2]=20;
        zenistuRunning.runningParameters[3]=90;
        characters.put("ZenitsuRunning",zenistuRunning);
    }
    public static void loadZensituMainAnimation(){
        Image[] zenistuMainImages = {
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n1.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n2.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n3.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n4.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n5.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n6.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n7.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n8.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n9.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n10.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n11.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n12.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n13.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n14.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n15.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n16.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n17.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n18.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n19.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n20.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n21.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n22.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n23.png"))),
                new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/zenistuMain/n24.png")))
        };
        CharacterAnimation ca = new CharacterAnimation();
        Group zenistuMainGroup;
        try {
            zenistuMainGroup = ca.createCharacterAnimation(zenistuMainImages);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        Image stillZenitsu = new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/ZenitsuStill.png")));
        Character zenistuMain = new Character("ZenitsuMain",zenistuMainGroup, stillZenitsu);
        zenistuMain.runningParameters[0]=0.3;
        zenistuMain.runningParameters[1]=0.3;
        zenistuMain.runningParameters[2]=-400;
        zenistuMain.runningParameters[3]=-345;
        characters.put("ZenitsuMain",zenistuMain);
    }
    public static void loadRunningNezuko(){
        Image[] nezukoRunningImages = {
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n1.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n2.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n3.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n4.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n5.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n6.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n7.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/nezukoRunning/n8.png"))),
        };
        CharacterAnimation ca= new CharacterAnimation();
        Group nezukoRunningGroup;
        try {
            nezukoRunningGroup = ca.createCharacterAnimation(nezukoRunningImages);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        Image stillNezuko = new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/nezukoStill.png")));
        Character nezukoRunning = new Character("NezukoRunning",nezukoRunningGroup, stillNezuko);
        nezukoRunning.stillParameters[0]=70;
        nezukoRunning.stillParameters[1]=80;
        nezukoRunning.stillParameters[2]=150;
        nezukoRunning.stillParameters[3]=220;
        nezukoRunning.runningParameters[0]=0.22;
        nezukoRunning.runningParameters[1]=0.22;
        nezukoRunning.runningParameters[2]=15;
        nezukoRunning.runningParameters[3]=90;
        characters.put("NezukoRunning",nezukoRunning);
    }

    public static void loadNezukoMainAnimation(){
        Image[] nezukoImages = {
                new Image(String.valueOf(CharacterAnimation.class.getResource("/NezukoMain/c1.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/NezukoMain/c2.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/NezukoMain/c3.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/NezukoMain/c4.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/NezukoMain/c5.png"))),
                new Image(String.valueOf(CharacterAnimation.class.getResource("/NezukoMain/c6.png")))
        };
        CharacterAnimation ca = new CharacterAnimation();
        Group nezukoMainGroup;
        try {
            nezukoMainGroup = ca.createCharacterAnimation(nezukoImages);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        Image stillNezuko = new Image(Objects.requireNonNull(Character.class.getResourceAsStream("/nezukoStill.png")));
        Character nezukoMain = new Character("NezukoMain",nezukoMainGroup, stillNezuko);
        nezukoMain.runningParameters[0]=0.3;
        nezukoMain.runningParameters[1]=0.3;
        nezukoMain.runningParameters[2]=-360;
        nezukoMain.runningParameters[3]=-45;
        characters.put("NezukoMain",nezukoMain);
    }
    public Character(String name, Group runningGroup, Image stillImage) {
        this.name = name;
        this.runningAnimationGroup = runningGroup;
        this.stillImage = stillImage;
        this.runningParameters =new double[4];
        this.stillParameters=new double[4];
    }

    public void loadCharacterImages(Image[] characterImages) throws IOException{
        runningAnimationGroup = characterAnimation.createCharacterAnimation(characterImages);
    }

    public String getName() {
        return name;
    }

    public double[] getRunningParameters() {
        return runningParameters;
    }

    public double[] getStillParameters() {
        return stillParameters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getRunningGroup() {
        return runningAnimationGroup;
    }

    public void setRunningGroup(Group runningGroup) {
        this.runningAnimationGroup = runningGroup;
    }

    public Image getStillImage() {
        return stillImage;
    }

    public void setStillImage(Image stillImage) {
        this.stillImage = stillImage;
    }

    public void addToHashMap(String name, Character character){
        characters.put(name,character);
    }
}
