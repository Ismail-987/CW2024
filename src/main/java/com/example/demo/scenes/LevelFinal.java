package com.example.demo.scenes;

import com.example.demo.UIObjects.Images.actors.FighterPlane;
import com.example.demo.factories.LevelView;
import com.example.demo.factories.LevelViewLevelFinal;
import com.example.demo.UIObjects.Images.actors.Boss;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class LevelFinal extends LevelParent {

	private final FighterPlane boss;
	private LevelViewLevelFinal levelView;
	private MediaPlayer youWinMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouWinMusic).toString()));

	public LevelFinal(double screenHeight, double screenWidth) {
		super(DataUtilities.LevelFinalBackgroundImage,DataUtilities.LevelFinalMusic, screenHeight, screenWidth, DataUtilities.LevelFinalPlayerHealth,DataUtilities.LevelFinalNumber,DataUtilities.LevelFinalName);
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
			FileUtility.saveGameStatus(DataUtilities.LevelOne);

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
		levelView = new LevelViewLevelFinal(getRoot(), DataUtilities.LevelFinalPlayerHealth,DataUtilities.LevelFinalBackgroundImage,getUser());
		return levelView;

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
