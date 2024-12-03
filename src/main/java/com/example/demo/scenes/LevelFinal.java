package com.example.demo.scenes;

import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.utilities.uiManagers.LevelViewLevelFinal;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;

public class LevelFinal extends LevelParent {

	private LevelViewLevelFinal levelView;

	public LevelFinal() {
		super(DataUtilities.LevelFinalNumber);
	}

	@Override
	protected void checkIfGameOver() {
		if (getGameState().userIsDestroyed()) {
			loseGame();
		}
		else if (getGameState().boss.isDestroyed()) {
			winGame();
			getGameState().gameWonMusic.play();
			levelView.pauseButton.setVisible(false);
			getRoot().getChildren().add(getLevelView().createGameFinishedScreen());
			FileUtility.saveGameStatus(DataUtilities.LevelOne);

		}else{
			levelView.scoreLabel.setText("SCORE : "+(getGameState().boss.getInitHealth()- getGameState().boss.getHealth())+ "/ "+ getGameState().boss.getInitHealth());
		}
	}


	@Override
	protected void spawnEnemyUnits() {
		if (getGameState().getCurrentNumberOfEnemies() == 0) {
			getGameState().addEnemyUnit(getGameState().boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelFinal(getRoot(),getGameState(),()->{goToScene(DataUtilities.HomeScene);});
		return levelView;

	}

	@Override
	public void updateLevelView(){
		levelView.removeHearts(getGameState().user.getHealth());
		if (getGameState().boss.isShielded()){
			levelView.showShield();
			levelView.getShield().setLayoutY(getGameState().boss.getLayoutY()+getGameState().boss.getTranslateY());
		}else{
			levelView.hideShield();
		}

	}
}
