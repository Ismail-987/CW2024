package com.example.demo.UIObjects.Containers;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;

import java.sql.Time;
import java.util.Objects;

/**
 * Represents the win screen display in the game with control buttons.
 */
public class WinScreen {
    private Group root;

    private String image = "/com/example/demo/images/winScreen.jpg";
    private ImageView winBackground = new ImageView();
    private Group winScreen;
    private  Button winNextLevelButton;
    private Button winReplayLevelButton;
    private  Button winQuitButton;
    private Button winSaveButton;
    private Button  winHomeButton;
    private Button winSettingsButton;


    /**
     * Constructs a WinScreen with specified positions and actions for buttons.
     *
     * @param Xposition      The x-coordinate for the screen layout position.
     * @param Yposition      The y-coordinate for the screen layout position.
     * @param goToHome       Runnable to execute on home button action.
     * @param goToNextLevel  Runnable to execute on next level action.
     * @param replayLevel    Runnable to execute on replay level action.
     * @param saveGameLevel  Runnable to execute on save action.
     */
    public WinScreen(double Xposition, double Yposition, Runnable goToHome,Runnable goToNextLevel,Runnable replayLevel, Runnable saveGameLevel){

       creator(Xposition, Yposition, goToHome,goToNextLevel,replayLevel,saveGameLevel);
       initializer();
    }

    /**
     * Returns the group container for the win screen.
     *
     * @return The group containing the win screen elements.
     */
    public Group get_scene_container(){

        return winScreen;
    }

    /**
     * Initializes and adds UI components to the win screen.
     */
    public void initializer(){
        winScreen.getChildren().add(winBackground);
        winScreen.getChildren().add(winSaveButton);
        winScreen.getChildren().add(winSettingsButton);
        winScreen.getChildren().add(winQuitButton);
        winScreen.getChildren().add(winNextLevelButton);
        winScreen.getChildren().add(winReplayLevelButton);
        winScreen.getChildren().add(winHomeButton);
    }

    /**
     * Creates the win screen components and sets their properties.
     *
     * @param Xposition      The x-coordinate for the layout position.
     * @param Yposition      The y-coordinate for the layout position.
     * @param goToHome       Runnable to execute on home button action.
     * @param goToNextLevel  Runnable to execute on next level action.
     * @param replayLevel    Runnable to execute on replay level action.
     * @param saveGameLevel  Runnable to execute on save action.
     */
    public void creator(double Xposition, double Yposition, Runnable goToHome,Runnable goToNextLevel,Runnable replayLevel, Runnable saveGameLevel){
        this.winScreen = new Group();
        // DO SOME IN-LINE STYLING
        this.winScreen.setLayoutX(Xposition);
        this.winScreen.setLayoutY(Yposition);
        this.winBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.winBackground.setFitWidth(600);
        this.winBackground.setFitHeight(513);


        this.winQuitButton = new Button("Quit Game");
        winQuitButton.setMinWidth(125.6);
        winQuitButton.setMinHeight(74.8);
        winQuitButton.setLayoutX(87.2);
        winQuitButton.setLayoutY(350.8);
        winQuitButton.getStyleClass().add("youWin-buttons");

        this.winNextLevelButton = new Button("Next Level");
        winNextLevelButton.setMinWidth(103.6);
        winNextLevelButton.setMinHeight(74.8);
        winNextLevelButton.setLayoutX(67.2);
        winNextLevelButton.setLayoutY(262.2);
        winNextLevelButton.getStyleClass().add("pause-buttons");

        this.winReplayLevelButton = new Button("Replay level");
        winReplayLevelButton.setMinWidth(125.6);
        winReplayLevelButton.setMinHeight(74.8);
        winReplayLevelButton.setLayoutX(386.8);
        winReplayLevelButton.setLayoutY(345.8);
        winReplayLevelButton.getStyleClass().add("youWin-buttons");

        this.winHomeButton = new Button("Home Button");
        winHomeButton.setMinWidth(103.6);
        winHomeButton.setMinHeight(74.8);
        winHomeButton.setLayoutX(431.2);
        winHomeButton.setLayoutY(262.2);
        winHomeButton.getStyleClass().add("youWin-buttons");

        this.winSaveButton = new Button("Save");
        winSaveButton.setMinWidth(103.6);
        winSaveButton.setMinHeight(74.8);
        winSaveButton.setLayoutX(185.2);
        winSaveButton.setLayoutY(262.2);
        winSaveButton.getStyleClass().add("youWin-buttons");

        this.winSettingsButton = new Button("Settings");
        winSettingsButton.setMinWidth(103.6);
        winSettingsButton.setMinHeight(74.8);
        winSettingsButton.setLayoutX(307);
        winSettingsButton.setLayoutY(262.2);
        winSettingsButton.getStyleClass().add("youWin-buttons");


        this.winSettingsButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.winSaveButton.setOnMousePressed(e -> {
            saveGameLevel.run();
        });

        this.winQuitButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.winNextLevelButton.setOnMousePressed(e -> {
            goToNextLevel.run();
        });

        this.winHomeButton.setOnMousePressed(e -> {
            goToHome.run();
        });

        this.winReplayLevelButton.setOnMousePressed(e -> {
            replayLevel.run();
        });
    }
}
