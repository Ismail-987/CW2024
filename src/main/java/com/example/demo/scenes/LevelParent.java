package com.example.demo.scenes;

import java.beans.PropertyChangeSupport;
import com.example.demo.UIObjects.Containers.LoseScreen;
import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Abstract class representing a parent level in a game, managing game state and transitions.
 */
public abstract class LevelParent {

	/**
	 * Indicates whether the level currently exists.
	 */
	public boolean exist = false;

	private GameState gameState;
	private LoseScreen lossScreen;
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
		this.gameState = new GameState(this::updateScene, this::loseGame, root, levelNo);
		this.levelView = instantiateLevelView();
		gameState.initializeTimeline();
		gameState.friendlyUnits.add(gameState.user);
	}

	/**
	 * Gets the PropertyChangeSupport object.
	 *
	 * @return the support instance
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
		exist = false;
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
		exist = true;
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
	 * Updates the scene by spawning enemies and managing game loop.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		gameState.runGameLoopThread();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Spawns enemy units in the scene.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Updates the level view, such as removing player hearts upon damage.
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
		gameState.timeline.stop();
		gameState.Backgroundmusic.stop();
		this.levelView.pauseButton.setVisible(false);
		root.getChildren().add(initializeLooseScreen());
	}

	/**
	 * Gets the root group node for this level.
	 *
	 * @return the root group
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
	 * Initializes and returns the lose screen.
	 *
	 * @return the container Group for the lose screen
	 */
	public Group initializeLooseScreen() {
		lossScreen = new LoseScreen(355, 160);
		return lossScreen.get_scene_container();
	}
}