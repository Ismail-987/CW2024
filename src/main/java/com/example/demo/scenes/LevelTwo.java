package com.example.demo.scenes;

import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.FighterPlane;
import com.example.demo.factories.LevelView;
import com.example.demo.factories.LevelViewLevelTwo;
import com.example.demo.UIObjects.Images.actors.Boss;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/homescreenbackground.jpg";
	private static final String BACKGROUNDMUSIC =  "/com/example/demo/images/level1music.mp3";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final int CURRENT_LEVEL_NUMBER = 2;
	private static final String CURRENT_LEVEL_NAME = "PHOENIX-387";
	private final FighterPlane boss;
	private LevelViewLevelTwo levelView;
	private Group gameFinished;
	private MediaPlayer youWinMusic = new MediaPlayer(new Media(getClass().getResource("/com/example/demo/images/youwinmusic.mp3").toString()));

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME,BACKGROUNDMUSIC, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH,CURRENT_LEVEL_NUMBER,CURRENT_LEVEL_NAME);
		this.boss = new Boss();

	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
			youWinMusic.play();
			this.getPauseButton().setVisible(false);
			getRoot().getChildren().add(getLevelView().createGameFinishedScreen());
		}else{

			getScoreLabel().setText("SCORE : "+(boss.getInitHealth()- boss.getHealth())+ "/ "+ boss.getInitHealth());
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH,BACKGROUND_IMAGE_NAME,getUser());
		return levelView; // Polymorphism
		// This above function returns a subclass object to a parent class variable.
	}

	@Override
	public void updateLevelView(){
		levelView.removeHearts(getUser().getHealth());
		if (boss.isShielded()){
			levelView.showShield();
			levelView.getShield().setLayoutY(boss.getLayoutY()+boss.getTranslateY());
		}else{
			levelView.hideShield();
		}

	}
}
