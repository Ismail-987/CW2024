package com.example.demo.UIObjects.Containers;

import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.GameState;
import com.example.demo.utilities.NavigationUtilities;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the game finished screen with a background image and specified layout position.
 */
public class GameFinishedScreen {

    private Group root;
    private String image = "/com/example/demo/images/gameFinished.jpg";
    private ImageView pauseBackground = new ImageView();
    private Button restartGame;
    private Button homeButton;
    private Button quitButton;

    /**
     * Constructs a GameFinishedScreen at a specified position with the given game state.
     *
     * @param Xposition the X-axis position where the game finished screen is displayed.
     * @param Yposition the Y-axis position where the game finished screen is displayed.
     * @param gameState the current state of the game used for navigation support on button actions.
     */
    public GameFinishedScreen(double Xposition, double Yposition, GameState gameState) {
            creator(Xposition, Yposition, gameState);
            initializer();
    }

    /**
     * Constructs the game finished screen interface at the specified position
     * with interactive buttons for user actions, such as restarting the game,
     * returning home, or quitting the application.
     *
     * @param Xposition the X-axis position where the interface should be displayed
     * @param Yposition the Y-axis position where the interface should be displayed
     * @param gameState the current state of the game, which is utilized for navigation support when buttons are pressed
     */
    public void creator (double Xposition, double Yposition, GameState gameState){
        this.root = new Group();
        this.root.setLayoutX(Xposition);
        this.root.setLayoutY(Yposition);

        this.pauseBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.pauseBackground.setFitWidth(600);
        this.pauseBackground.setFitHeight(513);

        this.restartGame = new Button();
        restartGame.setLayoutX(74.8);
        restartGame.setLayoutY(348);
        restartGame.setMinWidth(110);
        restartGame.setMinHeight(50);
        restartGame.getStyleClass().add("play-buttons");

        this.homeButton = new Button();
        homeButton.setLayoutX(416);
        homeButton.setLayoutY(348);
        homeButton.setMinWidth(112.5);
        homeButton.setMinHeight(50);
        homeButton.getStyleClass().add("play-buttons");

        this.quitButton = new Button();
        quitButton.setLayoutX(230);
        quitButton.setLayoutY(348);
        quitButton.setMinWidth(150);
        quitButton.setMinHeight(50);
        quitButton.getStyleClass().add("play-buttons");

        restartGame.setOnMousePressed(e -> {
            gameState.exist=false;
            gameState.gameWonMusic.stop();
            NavigationUtilities.goToScene(gameState.support, DataUtilities.LevelOne);
        });
        homeButton.setOnMousePressed(e -> {
            gameState.exist=false;
            gameState.gameWonMusic.stop();
            NavigationUtilities.goToScene(gameState.support, DataUtilities.HomeScene);
        });
        quitButton.setOnMousePressed(e -> System.exit(1));
    }

    /**
     * Initializes the game finished screen by adding interactive elements to the root container.
     * This method adds the pause background and control buttons (restart game, home, quit) to the
     * layout group, preparing the screen for user interaction.
     */
    public void initializer(){
        root.getChildren().add(pauseBackground);
        root.getChildren().add(restartGame);
        root.getChildren().add(homeButton);
        root.getChildren().add(quitButton);
    }

    /**
     * Returns the root container for the game finished screen.
     *
     * @return The group containing the screen elements.
     */
    public Group get_scene_container() {
        return root;
    }
}