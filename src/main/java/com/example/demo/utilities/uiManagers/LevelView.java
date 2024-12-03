package com.example.demo.utilities.uiManagers;

import com.example.demo.UIObjects.Containers.*;
import com.example.demo.UIObjects.Images.figures.*;
import com.example.demo.utilities.FileUtility;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;

import javafx.scene.control.Label;


public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int PAUSE_BUTTON_X_POSITION = 1200;
	private static final int PAUSE_BUTTON_Y_POSITION = 15;
	private static final int PAUSE_SCENE_X_POSITION = 375;
	private static final int PAUSE_SCENE_Y_POSITION = 100;
	private final Group root;
	private final GameState gameState;


	private final HeartDisplay heartDisplay;
	public final PauseButton pauseButton;
	private  PauseScreen pauseScreen;
	private final Runnable goToHomeScene;
	public  Label levelLabel;
	public  Label scoreLabel;
	public GameBackground background;
	
	public LevelView(Group root, GameState gameState, Runnable goToHomeScene) {
		this.root = root;
		this.gameState = gameState;
		this.goToHomeScene = goToHomeScene;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, gameState.initialHealth);
		this.pauseButton = new PauseButton(PAUSE_BUTTON_X_POSITION,PAUSE_BUTTON_Y_POSITION);
		this.background = new GameBackground(this.gameState,()->{initializePauseScreen();});
		this.background.initializeBackground();
	}


	public void levelViewInitializer (){
		initializePauseButton();
		initializeLevelLabels();
		initializeHeartDisplay();
		initializeShield();
	}

	public void initializeHeartDisplay() {

		root.getChildren().add(heartDisplay.getContainer());
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}
	public void showShield(){

	}

    public ShieldImage getShield() {

		return null;
    }

	public void initializeShield(){

	}
	public Group createGameFinishedScreen(){
		return new Group();
	};


	public void resume_game(){
		pauseButton.setVisible(true);
		this.pauseScreen.get_scene_container().setVisible(false);
		gameState.timeline.play();
		gameState.Backgroundmusic.play();
	}
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
		levelLabel.getStyleClass().add("level-label");

		this.root.getChildren().add(scoreLabel);
		this.root.getChildren().add(levelLabel);
	}

	public void  initializePauseButton () {

		this.pauseButton.setOnMousePressed(e -> {
			load_pause_screen();
		});
		root.getChildren().add(pauseButton);
	}
	public void load_pause_screen(){
		gameState.timeline.pause();
		gameState.Backgroundmusic.pause();
		pauseButton.setVisible(false);
		initializePauseScreen();
	}

	public void initializePauseScreen(){
		this.pauseScreen = new PauseScreen(PAUSE_SCENE_X_POSITION,PAUSE_SCENE_Y_POSITION,this::resume_game,goToHomeScene,()->{
			FileUtility.saveGameStatus(gameState.levelClassName);});
		root.getChildren().add(pauseScreen.get_scene_container());
	}


}
