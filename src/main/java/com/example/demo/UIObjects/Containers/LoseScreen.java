package com.example.demo.UIObjects.Containers;

import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.GameState;
import com.example.demo.utilities.NavigationUtilities;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.beans.PropertyChangeSupport;
import java.util.Objects;

/**
 * Represents the lose screen display with various action buttons.
 */
public class LoseScreen {

    private Group lossScreen;
    private String image = "/com/example/demo/images/youLostScreen.jpg";
    private ImageView lossBackground = new ImageView();
    private Button lossHomeButton;
    private Button lostRestartButton;
    private Button lostSkipLevelButton;
    private Button lostTipsButton;
    private Button lostQuitButton;
    private PropertyChangeSupport support;
    private GameState gameState;

    /**
     * Constructs a new LoseScreen with the specified position and game state.
     *
     * @param Xposition The x-coordinate for the screen layout.
     * @param Yposition The y-coordinate for the screen layout.
     * @param gameState The current state of the game, providing context for the lose screen.
     */
    public LoseScreen(double Xposition, double Yposition,GameState gameState ) {
        this.gameState = gameState;
        creator(Xposition, Yposition);
        initializer();
    }

    /**
     * Returns the group container for the lose screen.
     *
     * @return The group containing the lose screen elements.
     */
    public Group get_scene_container() {
        return lossScreen;
    }

    /**
     * Initializes and adds UI components to the loss screen.
     */
    public void initializer() {
        lossScreen.getChildren().add(lossBackground);
        lossScreen.getChildren().add(lostTipsButton);
        lossScreen.getChildren().add(lossHomeButton);
        lossScreen.getChildren().add(lostRestartButton);
        lossScreen.getChildren().add(lostSkipLevelButton);
        lossScreen.getChildren().add(lostQuitButton);
    }

    /**
     * Creates the loss screen components and sets their properties.
     *
     * @param Xposition The x-coordinate for the layout position.
     * @param Yposition The y-coordinate for the layout position.
     */
    public void creator(double Xposition, double Yposition) {
        this.lossScreen = new Group();
        this.lossScreen.setLayoutX(Xposition);
        this.lossScreen.setLayoutY(Yposition);
        this.lossBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.lossBackground.setFitWidth(600);
        this.lossBackground.setFitHeight(513);

        this.lostQuitButton = new Button("Quit");
        lostQuitButton.setMinWidth(179.6);
        lostQuitButton.setMinHeight(35.2);
        lostQuitButton.setLayoutX(383.6);
        lostQuitButton.setLayoutY(238);
        this.lostQuitButton.setOnMousePressed(e -> System.exit(1));

        this.lossHomeButton = new Button("Home");
        lossHomeButton.setMinWidth(169.6);
        lossHomeButton.setMinHeight(35.2);
        lossHomeButton.setLayoutX(198.8);
        lossHomeButton.setLayoutY(378);
        this.lossHomeButton.setOnMousePressed(e -> {
            gameState.exist=false;
            NavigationUtilities.goToScene(gameState.support, DataUtilities.HomeScene);});

        this.lostRestartButton = new Button("Replay");
        lostRestartButton.setMinWidth(175.8);
        lostRestartButton.setMinHeight(35.2);
        lostRestartButton.setLayoutX(40);
        lostRestartButton.setLayoutY(240);
        this.lostRestartButton.setOnMousePressed(e -> {
            gameState.exist=false;
            NavigationUtilities.goToScene(gameState.support, gameState.levelClassName);
        });

        this.lostTipsButton = new Button("Tips");
        lostTipsButton.setMinWidth(167.6);
        lostTipsButton.setMinHeight(35.2);
        lostTipsButton.setLayoutX(196.6);
        lostTipsButton.setLayoutY(425.2);
        this.lostTipsButton.setOnMousePressed(e ->
        {
            gameState.exist=false;
            NavigationUtilities.goToScene(gameState.support,DataUtilities.HomeScene);
        });

        this.lostSkipLevelButton = new Button("Skip level");
        lostSkipLevelButton.setMinWidth(169.6);
        lostSkipLevelButton.setMinHeight(35.2);
        lostSkipLevelButton.setLayoutX(214.2);
        lostSkipLevelButton.setLayoutY(238);
        this.lostSkipLevelButton.setOnMousePressed(e -> {
            gameState.exist=false;
            NavigationUtilities.goToScene(gameState.support, gameState.NextLevelClassName);});
    }
}