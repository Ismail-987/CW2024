package com.example.demo.utilities.uiManagers;

import com.example.demo.UIObjects.Containers.*;
import com.example.demo.UIObjects.Images.figures.*;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;

import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * Manages the level view, including UI components and game state transitions.
 */
public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int PAUSE_BUTTON_X_POSITION = 1200;
	private static final int PAUSE_BUTTON_Y_POSITION = 20;
	private static final int PAUSE_SCENE_X_POSITION = 375;
	private static final int PAUSE_SCENE_Y_POSITION = 100;
	private final Group root;
	private final GameState gameState;


	private final HeartDisplay heartDisplay;
	public final PauseButton pauseButton;
	private  PauseScreen pauseScreen;
	private WinScreen winScreen;
	private LoseScreen lossScreen;
	private final Runnable goToHomeScene;
	public  Label levelLabel;
	public  Label scoreLabel;
	public GameBackground background;
	public Button powerUpButton;

	/**
	 * Constructs a LevelView with root node, game state, and a callback to go to home.
	 *
	 * @param root          The root group for the level view.
	 * @param gameState     The current state of the game.
	 * @param goToHomeScene Runnable to execute when transitioning to the home scene.
	 */
	public LevelView(Group root, GameState gameState, Runnable goToHomeScene) {
		this.root = root;
		this.gameState = gameState;
		this.goToHomeScene = goToHomeScene;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, gameState.initialHealth);
		this.pauseButton = new PauseButton(PAUSE_BUTTON_X_POSITION,PAUSE_BUTTON_Y_POSITION);
		this.background = new GameBackground(this.gameState,()->{initializePauseScreen();});
		this.background.initializeBackground();

	}

	/**
	 * Initializes the level view components.
	 */
	public void levelViewInitializer (){
		initializePauseButton();
		initializeLevelLabels();
		initializeHeartDisplay();
		initializeShield();
		initializePowerUpButton();
	}

	/**
	 * Initializes and adds the heart display to the root group.
	 */
	public void initializeHeartDisplay() {

		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * Removes hearts from the display based on remaining hearts.
	 *
	 * @param heartsRemaining The number of hearts remaining to display.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * Shows the shield on the screen.
	 */
	public void showShield(){

	}

	/**
	 * Returns the shield image.
	 *
	 * @return The ShieldImage instance.
	 */
    public ShieldImage getShield() {

		return null;
    }

	/**
	 * Initializes the shield component.
	 */
	public void initializeShield(){

	}

	/**
	 * Initializes and returns the game finished screen within the game interface.
	 * This method creates a new instance of the GameFinishedScreen class with specific
	 * layout positions and the current game state. It retrieves the scene container
	 * from the GameFinishedScreen instance, which contains the interactive elements
	 * for the game finished scenario.
	 *
	 * @return The Group containing the game finished screen elements.
	 */
	public Group initializeGameFinishedScreen(){
		return new GameFinishedScreen(375, 100,gameState).get_scene_container();
	};


	/**
	 * Resumes the game from the pause state.
	 */
	public void resume_game(){
		pauseButton.setVisible(true);
		this.pauseScreen.get_scene_container().setVisible(false);
		gameState.timeline.play();
		gameState.Backgroundmusic.play();
	}

	/**
	 * Initializes and configures the level and score labels for display in the level view.
	 *
	 * This method sets up the graphical display elements for the current game's level
	 * and score. It initializes the score label with an empty string and positions it
	 * on the screen with specified layout coordinates. The visual style of the score
	 * label is customized by adding a specific style class.
	 *
	 * The level label is initialized with a default text of "LEVEL". However, if the
	 * current game level is the final level, the text is updated to reflect "FINAL LEVEL"
	 * instead, concatenated with the current level's name. For other levels, it displays
	 * the level number and name.
	 *
	 * The positions for both labels are set with designated layout coordinates, and
	 * their respective style classes are applied. Both labels are then added to the
	 * root node, making them part of the visible UI during gameplay.
	 */
	public void initializeLevelLabels (){
		scoreLabel = new Label("");
		scoreLabel.setLayoutX(870);
		scoreLabel.setLayoutY(30);
		scoreLabel.getStyleClass().add("score-label");

		levelLabel = new Label("LEVEL") ;
		if (gameState.levelNumber == 4){
			levelLabel.setText("FINAL LEVEL "+" : "+gameState.levelName);
		}else {
			levelLabel.setText("LEVEL "+ gameState.levelNumber +" : "+gameState.levelName);
		}

		levelLabel.setLayoutY(30);
		levelLabel.setLayoutX(468);
		levelLabel.getStyleClass().add("score-label");

		this.root.getChildren().add(scoreLabel);
		this.root.getChildren().add(levelLabel);
	}

	/**
	 * Initializes the pause button functionality.
	 */
	public void  initializePauseButton () {

		this.pauseButton.setOnMousePressed(e -> {
			load_pause_screen();
		});
		root.getChildren().add(pauseButton);
	}

	/**
	 * Loads and displays the pause screen within the level view.
	 *
	 * This method pauses the game's timeline and background music, making the pause
	 * button invisible. It initializes and displays the pause screen, allowing players
	 * to interact with options such as resuming the game or returning to the home scene.
	 */
	public void load_pause_screen(){
		gameState.timeline.pause();
		gameState.Backgroundmusic.pause();
		pauseButton.setVisible(false);
		initializePauseScreen();
	}

	/**
	 * Initializes the pause screen within the level view.
	 *
	 * This method creates an instance of the PauseScreen class with specified
	 * layout positions for the pause scene on the UI. It sets up callback
	 * functions for various actions such as resuming the game, going back to the
	 * home scene, and saving the game state. After the PauseScreen is initialized,
	 * its container is added to the root node's children, making it visible
	 * within the game interface.
	 */
	public void initializePauseScreen(){
		this.pauseScreen = new PauseScreen(gameState,this::resume_game,goToHomeScene,()->{
			FileUtility.saveGameStatus(gameState.levelClassName);});
		root.getChildren().add(pauseScreen.get_scene_container());
	}

	/**
	 * Initializes the win screen component and adds it to the root node.
	 *
	 * This method creates a new instance of the WinScreen class using predefined
	 * X and Y positions, as well as the current game state. It then retrieves the
	 * scene container from the WinScreen instance and adds it to the root node's
	 * children, making the win screen visible in the game interface.
	 */
	public void initializeWinScreen(){
		this.winScreen = new WinScreen(PAUSE_SCENE_X_POSITION,PAUSE_SCENE_Y_POSITION,this.gameState);
		root.getChildren().add(winScreen.get_scene_container());
	}

	/**
	 * Handles the scenario where the player loses the game. This method stops any ongoing
	 * game processes related to the active game state, hides the pause button, and displays
	 * the lose screen.
	 *
	 * This is achieved by calling the `loseGameScenario` method on the `gameState` object,
	 * which stops the timeline and switches the background music to game over music. It then
	 * sets the visibility of the pause button to false, preventing interactions that are not
	 * pertinent to the end-game state. Lastly, it adds the lose screen to the root node,
	 * providing a visual indication to the player that the game has been lost.
	 */
	public void looseGameScenario(){
		this.gameState.loseGameScenario();
		pauseButton.setVisible(false);
		root.getChildren().add(initializeLooseScreen());
	}
	/**
	 * Initializes and returns the lose screen.
	 *
	 * @return the container Group for the lose screen
	 */
	public Group initializeLooseScreen() {
		lossScreen = new LoseScreen(355, 160,this.gameState);
		return lossScreen.get_scene_container();
	}

	/**
	 * Initializes the power-up button within the level view.
	 *
	 * This method creates a new button labeled "Power Up" and randomly positions
	 * it within the game's UI, with specific x and y coordinates. The button
	 * acquires a predefined style class designated as "power-up-button".
	 * Clicking the button triggers an event that activates a power-up within the
	 * game. This is achieved by setting the `isPowerUpActive` flag in the
	 * `DataUtilities` class to true, while simultaneously deactivating the button
	 * itself by setting the `isButtonActive` flag to false and making the button
	 * invisible. Finally, the button is added to the root node of the view.
	 */
	public void initializePowerUpButton(){
		powerUpButton = new Button("Power Up");
		powerUpButton.setLayoutX(Math.random()*1000);
		powerUpButton.setLayoutY(Math.random()*600+50);
		powerUpButton.getStyleClass().add("power-up-button");
		powerUpButton.setOnMouseClicked(e -> {
			DataUtilities.isPowerUpActive = true;
			DataUtilities.isButtonActive = false;
			this.powerUpButton.setVisible(false);
		});
		root.getChildren().add(powerUpButton);
	}

	public void levelViewUILoopLogic(){
		updatePowerUpButton();
		updatePowerUp();
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
	public void updatePowerUpButton(){
		if(DataUtilities.isButtonActive){
			DataUtilities.frameswithButton++;
		} else if (buttonShouldBeActive()) {

			if(!DataUtilities.isPowerUpActive){
				powerUpButton.setLayoutX(Math.random()*1000);
				powerUpButton.setLayoutY(Math.random()*700+50);
				powerUpButton.setVisible(true);
				DataUtilities.isButtonActive = true;
				DataUtilities.frameswithButton = 0;
			}

		}
		if(buttonIsExhausted()){
			DataUtilities.isButtonActive = false;
			DataUtilities.frameswithButton = 0;
			powerUpButton.setVisible(false);
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

	/**
	 * Sets the pauseScreen of the game.
	 * @param pauseScreen
	 */
	public void setPauseScreen(PauseScreen pauseScreen) {
		this.pauseScreen = pauseScreen;
	}

	/**
	 * Sets the winScreen of the game.
	 * @param winScreen
	 */
	public void setWinScreen(WinScreen winScreen) {
		this.winScreen = winScreen;
	}

	/**
	 * Sets the lossScreen of the game.
	 * @param lossScreen	Requires a LoseScreen object
	 */
	public void setLossScreen(LoseScreen lossScreen) {
		this.lossScreen = lossScreen;
	}
}
