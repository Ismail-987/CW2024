package com.example.demo.scenes;


import java.beans.PropertyChangeSupport;
import com.example.demo.UIObjects.Containers.LoseScreen;
import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;
import javafx.scene.Scene;


public abstract class LevelParent {

	public boolean exist = false;
	private GameState gameState;
	private LoseScreen lossScreen;
	private final Group root;
	private final Scene scene;
	private final PropertyChangeSupport support;
	private LevelView levelView;

	public LevelParent(int levelNo) {
		this.root = new Group();
		this.scene = new Scene(root, DataUtilities.ScreenWidth, DataUtilities.ScreenHeight);
		this.support = new PropertyChangeSupport(this);
		this.gameState = new GameState(this::updateScene,this::loseGame,root,levelNo);
		this.levelView = instantiateLevelView();
		gameState.initializeTimeline();
		gameState.friendlyUnits.add(gameState.user);
	}


	public PropertyChangeSupport getSupport(){

		return this.support;
	}

	public void goToScene(String levelName) {
		winGame();
		exist = false;
		support.firePropertyChange("Page Change", null, levelName);
	}


	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		gameState.sceneInitializationScenario();
		levelView.levelViewInitializer();
		exist = true;
		return scene;
	}


	public void startGame() {
		levelView.background.requestFocus();
		gameState.timeline.play();
	}

	private void updateScene() {
		spawnEnemyUnits();
		gameState.runGameLoopThread();
		updateLevelView();
		checkIfGameOver();
	}

	protected abstract void spawnEnemyUnits();

	public void updateLevelView() {

		levelView.removeHearts(gameState.user.getHealth());
}
	protected abstract void checkIfGameOver();

	protected void winGame() {
		gameState.timeline.stop();
		gameState.Backgroundmusic.stop();
	}

	protected void loseGame() {
		gameState.timeline.stop();
		gameState.Backgroundmusic.stop();
		this.levelView.pauseButton.setVisible(false);
		root.getChildren().add(initializeLooseScreen());
	}

	protected Group getRoot() {
		return root;
	}

	protected double getEnemyMaximumYPosition() {
		return gameState.maximumEnemyPosition;
	}


	public GameState getGameState(){
		return gameState;
	}

	public Scene getScene(){
		return this.scene;
	}

	public LevelView getLevelView(){
		return this.levelView;
	}


	public Group initializeLooseScreen (){
		lossScreen = new LoseScreen(355,160);
		return lossScreen.get_scene_container();
	}

}
