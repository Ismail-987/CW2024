package com.example.demo.factories;

import com.example.demo.UIObjects.Containers.HeartDisplay;
import com.example.demo.UIObjects.Containers.LoseScreen;

import com.example.demo.UIObjects.Images.actors.UserPlane;
import com.example.demo.UIObjects.Images.figures.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class LevelViewLevelTwo extends LevelView{


    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;
    private static final int WIN_IMAGE_X_POSITION = 355;
    private static final int WIN_IMAGE_Y_POSITION = 175;
    private static final int LOSS_SCREEN_X_POSITION = -160;
    private static final int LOSS_SCREEN_Y_POSITION = -375;
    private static final int PAUSE_BUTTON_X_POSITION = 1200;
    private static final int PAUSE_BUTTON_Y_POSITION = 15;
    private static final int PAUSE_SCENE_X_POSITION = 375;
    private static final int PAUSE_SCENE_Y_POSITION = 100;
    private final Group root;
    private ImageView background;

    private PauseButton pause_button;
    private final WinImage winImage;
    private final GameOverImage gameOverImage;
    private final HeartDisplay heartDisplay;


    public LevelViewLevelTwo(Group root, int heartsToDisplay, String backgroundImageName, UserPlane user) {
        super(root, heartsToDisplay, backgroundImageName, user);
        this.root = root;
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
        this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
        this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));

    }

    public void initializeHeartDisplay() {

        root.getChildren().add(heartDisplay.getContainer());
    }

    public ImageView createPauseButton(){
        return new PauseButton(PAUSE_BUTTON_X_POSITION,PAUSE_BUTTON_Y_POSITION);
    }


    public Group createLooseScreen (){
        return new LoseScreen(PAUSE_SCENE_X_POSITION,PAUSE_SCENE_Y_POSITION).get_scene_container();
    }

    public void showWinImage() {
        root.getChildren().add(winImage);
        winImage.showWinImage();
    }

    public void showGameOverImage() {
        root.getChildren().add(gameOverImage);
    }

    public void removeHearts(int heartsRemaining) {
        int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
        for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
            heartDisplay.removeHeart();
        }
    }
    public void showShield(){

    }

    public ShieldImage getShield() {
        return null;
    }


    public void addImagesToRoot(){

    }
    public void initializeShield(){

    }
    public Group createGameFinishedScreen(){
        return new Group();
    };
}
