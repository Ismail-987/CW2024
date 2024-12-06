package com.example.demo.utilities.uiManagers;

import com.example.demo.utilities.GameState;
import javafx.scene.Group;

/**
 * Manages the level view specifically for the third level of the game.
 */
public class LevelViewLevelThree extends LevelView {

    /**
     * Constructs a LevelViewLevelThree with root node, game state, and a callback to go to the home screen.
     *
     * @param root           The root group for the level view.
     * @param gameState      The current state of the game.
     * @param goToHomeScreen Runnable to execute when transitioning to the home screen.
     */
    public LevelViewLevelThree(Group root, GameState gameState, Runnable goToHomeScreen) {
        super(root, gameState, goToHomeScreen);
    }
}