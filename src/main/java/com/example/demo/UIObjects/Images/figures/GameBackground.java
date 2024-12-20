package com.example.demo.UIObjects.Images.figures;

import com.example.demo.UIObjects.Images.actors.Projectile;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.GameState;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Objects;

/**
 * Represents the game background and handles interaction with user input.
 */
public class GameBackground extends ImageView {

    private final Group root;
    private final GameState gameState;

    /**
     * Initializes the game background with specified game state and pause screen action.
     *
     * @param gameState          The current state of the game.
     * @param load_pause_screen  Action to load the pause screen.
     */
    public GameBackground(GameState gameState, Runnable load_pause_screen) {
        this.gameState = gameState;
        this.root = gameState.root;

        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(gameState.levelBackground)).toExternalForm()));
        this.setFocusTraversable(true);
        this.setFitHeight(DataUtilities.ScreenHeight);
        this.setFitWidth(DataUtilities.ScreenWidth);
        this.setUserData("back_name");

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP || kc == KeyCode.W) gameState.user.moveUp();
                if (kc == KeyCode.DOWN || kc == KeyCode.S) gameState.user.moveDown();
                if (kc == KeyCode.SPACE) fireProjectile();
                if (kc == KeyCode.P) load_pause_screen.run();
            }
        });

        this.setOnKeyReleased(e -> {
            KeyCode kc = e.getCode();
            if ((kc == KeyCode.UP || kc == KeyCode.DOWN)) gameState.user.stop();
            if (kc == KeyCode.W || kc == KeyCode.S) gameState.user.stop();
        });

        this.setOnMouseClicked(e -> {
            if (e.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
                fireProjectile();
            }
        });
    }

    /**
     * Adds the background image to the scene graph.
     */
    public void initializeBackground() {
        root.getChildren().add(this);
    }

    /**
     * Fires a projectile from the user's plane and plays the associated sound.
     */
    public void fireProjectile() {
        Projectile projectile = gameState.user.fireProjectile();
        projectile.getProjectileSound().play();
        root.getChildren().add(projectile);
        gameState.userProjectiles.add(projectile);
    }
}