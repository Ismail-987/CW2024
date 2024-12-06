package com.example.demo.scenes;

import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.utilities.uiManagers.LevelViewLevelFinal;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;

/**
 * Represents the final level of the game, handling boss interactions and game conclusions.
 */
public class LevelFinal extends LevelParent {

	private LevelViewLevelFinal levelView;

	/**
	 * Constructs the LevelFinal object with initial settings.
	 */
	public LevelFinal() {
		super(DataUtilities.LevelFinalNumber);
	}

	/**
	 * Checks if the game is over by evaluating the player's or boss's conditions.
	 */
	@Override
	protected void checkIfGameOver() {
		if (getGameState().userIsDestroyed()) {
			loseGame();
		} else if (getGameState().boss.isDestroyed()) {
			winGame();
			getGameState().gameWonMusic.play();
			levelView.pauseButton.setVisible(false);
			getRoot().getChildren().add(getLevelView().initializeGameFinishedScreen());
			FileUtility.saveGameStatus(DataUtilities.LevelOne);
		} else {
			levelView.scoreLabel.setText("SCORE : "
					+ (getGameState().boss.getInitHealth() - getGameState().boss.getHealth())
					+ " / " + getGameState().boss.getInitHealth());
		}
	}

	/**
	 * Spawns enemy units, specifically managing boss inclusion.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getGameState().getCurrentNumberOfEnemies() == 0) {
			getGameState().addEnemyUnit(getGameState().boss);
		}
	}

	/**
	 * Instantiates the final level's specific LevelView.
	 *
	 * @return a new LevelViewLevelFinal instance
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelFinal(getRoot(), getGameState(), () -> { goToScene(DataUtilities.HomeScene); });
		return levelView;
	}

	/**
	 * Updates the level view, reflecting player health and boss shield status.
	 */
	@Override
	public void updateLevelView() {
		levelView.removeHearts(getGameState().user.getHealth());
		if (getGameState().boss.isShielded()) {
			levelView.showShield();
			levelView.getShield().setLayoutY(getGameState().boss.getLayoutY() + getGameState().boss.getTranslateY());
		} else {
			levelView.hideShield();
		}
	}
}