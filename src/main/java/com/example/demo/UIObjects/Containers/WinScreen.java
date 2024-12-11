package com.example.demo.UIObjects.Containers;

import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import com.example.demo.utilities.GameState;
import com.example.demo.utilities.NavigationUtilities;
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

    private String image = "/com/example/demo/images/winScreen.jpg";
    private ImageView winBackground = new ImageView();
    private Group winScreen;
    private Button winNextLevelButton;
    private Button winReplayLevelButton;
    private Button winQuitButton;
    private Button winSaveButton;
    private Button  winHomeButton;
    private Button winSettingsButton;
    private GameState gameState;


    /**
     * Constructs a new WinScreen instance with the specified position and game state.
     *
     * @param Xposition The x-coordinate where the win screen should be displayed.
     * @param Yposition The y-coordinate where the win screen should be displayed.
     * @param gameStateVariable The current game state used to initialize the win screen.
     */
    public WinScreen(double Xposition, double Yposition, GameState gameStateVariable){
        this.gameState = gameStateVariable;
       creator(Xposition, Yposition, gameState);
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
     * @param gameState      The current state of the game used for navigation support on button actions.
     */
    public void creator(double Xposition, double Yposition,GameState gameState){
        this.winScreen = new Group();
        // DO SOME IN-LINE STYLING
        this.winScreen.setLayoutX(Xposition);
        this.winScreen.setLayoutY(Yposition);
        this.winBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.winBackground.setFitWidth(600);
        this.winBackground.setFitHeight(513);


        this.winQuitButton = new Button();
        winQuitButton.setMinWidth(125.6);
        winQuitButton.setMinHeight(74.8);
        winQuitButton.setLayoutX(87.2);
        winQuitButton.setLayoutY(350.8);
        winQuitButton.getStyleClass().add("youWin-buttons");

        this.winNextLevelButton = new Button();
        winNextLevelButton.setMinWidth(103.6);
        winNextLevelButton.setMinHeight(74.8);
        winNextLevelButton.setLayoutX(67.2);
        winNextLevelButton.setLayoutY(262.2);
        winNextLevelButton.getStyleClass().add("pause-buttons");

        this.winReplayLevelButton = new Button();
        winReplayLevelButton.setMinWidth(125.6);
        winReplayLevelButton.setMinHeight(74.8);
        winReplayLevelButton.setLayoutX(386.8);
        winReplayLevelButton.setLayoutY(345.8);
        winReplayLevelButton.getStyleClass().add("youWin-buttons");

        this.winHomeButton = new Button();
        winHomeButton.setMinWidth(103.6);
        winHomeButton.setMinHeight(74.8);
        winHomeButton.setLayoutX(431.2);
        winHomeButton.setLayoutY(262.2);
        winHomeButton.getStyleClass().add("youWin-buttons");

        this.winSaveButton = new Button();
        winSaveButton.setMinWidth(103.6);
        winSaveButton.setMinHeight(74.8);
        winSaveButton.setLayoutX(185.2);
        winSaveButton.setLayoutY(262.2);
        winSaveButton.getStyleClass().add("youWin-buttons");

        this.winSettingsButton = new Button();
        winSettingsButton.setMinWidth(103.6);
        winSettingsButton.setMinHeight(74.8);
        winSettingsButton.setLayoutX(307);
        winSettingsButton.setLayoutY(262.2);
        winSettingsButton.getStyleClass().add("youWin-buttons");


        this.winSettingsButton.setOnMousePressed(e -> {
            NavigationUtilities.goToScene(gameState.support,DataUtilities.SettingsScene);
        });

        this.winSaveButton.setOnMousePressed(e -> {
            FileUtility.saveGameStatus(gameState.NextLevelClassName);
        });

        this.winQuitButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.winNextLevelButton.setOnMousePressed(e -> {
            gameState.exist=false;
            gameState.gameWonMusic.stop();
            NavigationUtilities.goToScene(gameState.support, gameState.NextLevelClassName);
        });

        this.winHomeButton.setOnMousePressed(e -> {
            gameState.exist=false;
            gameState.gameWonMusic.stop();
            NavigationUtilities.goToScene(gameState.support, DataUtilities.HomeScene);
        });

        this.winReplayLevelButton.setOnMousePressed(e -> {
            gameState.exist=false;
            gameState.gameWonMusic.stop();
            NavigationUtilities.goToScene(gameState.support,gameState.levelClassName);
        });
    }
}
