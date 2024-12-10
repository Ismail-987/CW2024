package com.example.demo.scenes;

import java.beans.PropertyChangeSupport;
import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Abstract class representing the common characteristics and behaviors of a game level.
 * This class provides foundational functionality for managing game states, scenes, and
 * transitions between levels. Subclasses are required to implement specific methods to
 * define unique behaviors such as spawning enemy units and handling game over scenarios.
 */
public abstract class LevelParent {

	/**
	 * Indicates whether the level currently exists.
	 */

	private GameState gameState;
	private final Group root;
	private final Scene scene;
	private final PropertyChangeSupport support;
	private LevelView levelView;

	/**
	 * Constructs a LevelParent for a specific level number.
	 *
	 * @param levelNo the level number to initialize
	 */
	public LevelParent(int levelNo) {
		this.root = new Group();
		this.scene = new Scene(root, DataUtilities.ScreenWidth, DataUtilities.ScreenHeight);
		this.support = new PropertyChangeSupport(this);
		this.gameState = new GameState(this::updateScene, this::loseGame, root, levelNo,this.support);
		this.levelView = instantiateLevelView();
		gameState.initializeTimeline();
		gameState.friendlyUnits.add(gameState.user);
	}

	/**
	 * Retrieves the PropertyChangeSupport associated with this instance.
	 *
	 * @return the PropertyChangeSupport instance used to support bound properties and listeners.
	 */
	public PropertyChangeSupport getSupport() {
		return this.support;
	}

	/**
	 * Changes the current scene to another level.
	 *
	 * @param levelName the name of the next level
	 */
	public void goToScene(String levelName) {
		winGame();
		gameState.exist = false;
		support.firePropertyChange("Page Change", null, levelName);
	}

	/**
	 * Instantiates a LevelView object for the derived class.
	 *
	 * @return the instantiated LevelView
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene by setting up game elements.
	 *
	 * @return the initialized scene
	 */
	public Scene initializeScene() {
		gameState.sceneInitializationScenario();
		levelView.levelViewInitializer();
		return scene;
	}

	/**
	 * Starts the gameplay by focusing and playing the timeline.
	 */
	public void startGame() {
		levelView.background.requestFocus();
		gameState.timeline.play();
	}

	public abstract void updateLevelView();
	/**
	 * Updates the state of the current game scene by performing the following actions:
	 *
	 * - Spawns new enemy units in the scene by invoking the abstract `spawnEnemyUnits` method.
	 * - Updates the display and functionality of the power-up button by calling `updateButton`.
	 * - Modifies the power-up state based on its current status and duration using `updatePowerUp`.
	 * - Executes the main game loop logic by invoking `runGameLoopThread` on the `gameState` object.
	 * - Refreshes the graphical user interface based on the current gameplay state through `updateLevelView`.
	 * - Determines if the current game session has ended using the `checkIfGameOver` method and takes necessary actions if so.
	 *
	 * This method is intended to be called continuously to maintain the game's state during active gameplay.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		levelView.levelViewUILoopLogic();
		gameState.runGameLoopThread();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Spawns enemy units for the current level.
	 *
	 * This abstract method should be implemented by subclasses
	 * to define the logic for creating and placing enemy units
	 * within the game scene. It is typically invoked during the
	 * game loop to continuously add new enemy challenges as the
	 * game progresses.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Checks if the game is over and takes appropriate action.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Handles game winning logic, stopping the timeline and music.
	 */
	protected void winGame() {
		gameState.timeline.stop();
		gameState.Backgroundmusic.stop();
	}

	/**
	 * Handles game losing logic, stopping the game elements and displaying the lose screen.
	 */
	protected void loseGame() {
		levelView.looseGameScenario();
	}

	/**
	 * Retrieves the root group associated with the current level.
	 *
	 * @return the root group of the current level
	 */
	protected Group getRoot() {
		return root;
	}

	/**
	 * Gets the maximum Y position for enemy entities.
	 *
	 * @return the maximum enemy Y position
	 */
	protected double getEnemyMaximumYPosition() {
		return gameState.maximumEnemyPosition;
	}

	/**
	 * Returns the game state associated with this level.
	 *
	 * @return the game state
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Returns the scene associated with this level.
	 *
	 * @return the scene
	 */
	public Scene getScene() {
		return this.scene;
	}

	/**
	 * Returns the level view associated with this level.
	 *
	 * @return the level view
	 */
	public LevelView getLevelView() {
		return this.levelView;
	}


}