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
		gameState.exist = true;
		return scene;
	}

	/**
	 * Starts the gameplay by focusing and playing the timeline.
	 */
	public void startGame() {
		levelView.background.requestFocus();
		gameState.timeline.play();
	}

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
		updateButton();
		updatePowerUp();
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
	 * Updates the level view by adjusting the heart display to match the current
	 * health of the user. This method removes hearts from the display to reflect
	 * the player's remaining health as retrieved from the game state.
	 */
	public void updateLevelView() {
		levelView.removeHearts(gameState.user.getHealth());
	}

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

	/**
	 * Updates the state and appearance of the power-up button based on certain conditions.
	 *
	 * The method performs the following actions:
	 * - If the button is currently active, it increments the frame count that the button
	 *   has been visible.
	 * - If the button is not active and should be made active according to certain logic,
	 *   and if no power-up is currently active, the button is placed at a new random location
	 *   and made visible. The button is marked as active and its frame count is reset.
	 * - If the button's active frame count has reached its maximum threshold, the button is
	 *   deactivated, its frame count is reset, and it is made invisible.
	 */
	private void updateButton(){
		if(DataUtilities.isButtonActive){
			DataUtilities.frameswithButton++;
		} else if (buttonShouldBeActive()) {

			if(!DataUtilities.isPowerUpActive){
				levelView.powerUpButton.setLayoutX(Math.random()*1000);
				levelView.powerUpButton.setLayoutY(Math.random()*700+50);
				levelView.powerUpButton.setVisible(true);
				DataUtilities.isButtonActive = true;
				DataUtilities.frameswithButton = 0;
			}

		}
		if(buttonIsExhausted()){
			DataUtilities.isButtonActive = false;
			DataUtilities.frameswithButton = 0;
			levelView.powerUpButton.setVisible(false);
		}
	}

	/**
	 * Determines if the button should be active based on a random probability.
	 *
	 * @return true if the button should be active, false otherwise
	 */
	private boolean buttonShouldBeActive(){
		return Math.random() < DataUtilities.BUTTON_PROBABILITY;
	}

	/**
	 * Checks whether the power-up button has been active for the maximum number of frames.
	 *
	 * @return true if the button's active frame count has reached the maximum threshold defined
	 *         by MAX_FRAMES_FOR_BUTTON, false otherwise.
	 */
	private Boolean buttonIsExhausted(){
		return DataUtilities.frameswithButton == DataUtilities.MAX_FRAMES_FOR_BUTTON;
	}

	/**
	 * Updates the state of the power-up based on its current activity and duration.
	 *
	 * If the power-up is active, increments the frame count indicating the duration
	 * for which the power-up has been active. Once the power-up has been active for
	 * its maximum allowed duration, it is deactivated and the frame count is reset.
	 */
	private void updatePowerUp(){
		if(DataUtilities.isPowerUpActive){
			DataUtilities.frameswithPowerUp++;
		}

		if(powerUpIsExhausted()){
			DataUtilities.isPowerUpActive = false;
			DataUtilities.frameswithPowerUp = 0;
		}
	}

	/**
	 * Checks if the power-up has been active for the maximum number of frames.
	 *
	 * @return true if the power-up's active frame count has reached the maximum
	 *         threshold defined by MAX_FRAMES_FOR_POWER_UP, false otherwise.
	 */
	private Boolean powerUpIsExhausted(){
		return DataUtilities.frameswithPowerUp == DataUtilities.MAX_FRAMES_FOR_POWER_UP;
	}
}